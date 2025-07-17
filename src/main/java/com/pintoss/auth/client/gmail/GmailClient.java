package com.pintoss.auth.client.gmail;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import com.pintoss.auth.support.exception.ErrorCode;
import com.pintoss.auth.support.exception.client.BadRequestException;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.Session;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Getter
@Component
public class GmailClient {

    private final String APPLICATION_NAME ;
    private final String SENDER_EMAIL_ADDRESS ;
    private final String SERVICE_ACCOUNT_KEY_FILE ;
    private final List<String> OAUTH_SCOPES;
    private final Gmail gmail;


    public GmailClient(GmailApiProperties gmailApiProperties) {
        this.APPLICATION_NAME = gmailApiProperties.getApplicationName();
        this.SENDER_EMAIL_ADDRESS = gmailApiProperties.getUserEmail();
        this.SERVICE_ACCOUNT_KEY_FILE = gmailApiProperties.getServiceAccountKeys();
        this.OAUTH_SCOPES = gmailApiProperties.getOauthScopes();
        this.gmail = initializeGmail();
    }

    private Gmail initializeGmail() {
        HttpTransport httpTransport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        ServiceAccountCredentials credential = null;

        try {
            credential = ServiceAccountCredentials.fromStream(
                new ClassPathResource(SERVICE_ACCOUNT_KEY_FILE).getInputStream()
            );
        } catch (IOException e) {
            log.error("[Gmail API 인증서 로드 실패] message={}, class={}, line={}",
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getLineNumber()
            );
            throw new BadRequestException(ErrorCode.GMAIL_API_AUTHENTICATION_FAILED);
        }
        try {
            credential = (ServiceAccountCredentials) credential.createScoped(OAUTH_SCOPES).createDelegated(SENDER_EMAIL_ADDRESS);
        } catch (Exception e){
            log.error("[Gmail API 인증서 위임 실패] message={}, class={}, line={}",
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getLineNumber()
            );
            throw new BadRequestException(ErrorCode.GMAIL_API_AUTHENTICATION_FAILED);
        }

        return new Gmail.Builder(httpTransport, jsonFactory, new HttpCredentialsAdapter(credential))
            .setApplicationName(APPLICATION_NAME)
            .build();
    }

    public void send(SendRequest request) {
        try {
            MimeMessage email = createEmail(
                request.getReceiver(),
                request.getSender(),
                request.getSubject(),
                request.getContent(),
                request.getAttachments()
            );
            Message message = createEmailWithEmail(email);
            gmail.users().messages().send(request.getSender(), message).execute();
        } catch (GoogleJsonResponseException e) {
            log.error("[Gmail API Error] receiver={}, subject={}, status={}, message={}",
                request.getReceiver(),
                request.getSubject(),
                e.getStatusCode(),
                e.getDetails() != null ? e.getDetails().getMessage() : e.getMessage()
            );
            handleGmailApiException(e);
        } catch (IOException e) {
            log.error("[Gmail Network Error] receiver={}, subject={}, message={}",
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getLineNumber()
            );
            handleException(ErrorCode.GMAIL_API_NETWORK_ERROR, e);
        } catch (Exception e) {
            log.error("[Gmail Error] receiver={}, subject={}, message={}",
                request.getReceiver(),
                request.getSubject(),
                e.getMessage()
            );
            handleException(ErrorCode.EMAIL_PROCESSING_FAILED, e);
        }
    }

    private void handleGmailApiException(GoogleJsonResponseException e) {
        ErrorCode errorCode = switch (e.getStatusCode()) {
            case 429 -> ErrorCode.GMAIL_API_RATE_LIMIT_EXCEEDED;
            case 403 -> ErrorCode.GMAIL_API_FORBIDDEN;
            case 400 -> ErrorCode.GMAIL_API_BAD_REQUEST;
            default -> ErrorCode.GMAIL_API_CALL_FAILED;
        };
        handleException(errorCode, e);
    }

    private String getStackTraceSnippet(Exception e) {
        if(e.getStackTrace().length == 0) return "스택 없음";
        StackTraceElement top = e.getStackTrace()[0];
        return top.getClassName() + ":" + top.getMethodName() + ":" + top.getLineNumber();
    }
    private void handleException(ErrorCode errorCode, Exception e) {
        log.error("[Gmail API 예외 발생] code={}, message={}, exception={}, stackTrace={}",
            errorCode.getCode(),
            errorCode.getMessage(),
            e.getMessage(),
            getStackTraceSnippet(e)
        );
    }

    private Message createEmailWithEmail(MimeMessage email) {
        try {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            email.writeTo(buffer);
            byte[] bytes = buffer.toByteArray();
            String encodedEmail = Base64.getEncoder().encodeToString(bytes);
            Message message = new Message();
            message.setRaw(encodedEmail);
            return message;
        } catch (IOException | MessagingException e) {
            throw new BadRequestException(ErrorCode.EMAIL_PROCESSING_FAILED);
        }
    }

    private MimeMessage createEmail(String receiver, String sender, String subject, String content, List<Attachment> attachments) {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            MimeMessage email = new MimeMessage(session);
            email.setFrom(new InternetAddress(sender));
            email.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiver));
            email.setSubject(subject);
            MimeBodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(content, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(bodyPart);

            email.setContent(multipart);
            return email;
        } catch (AddressException e) {
            log.error("[이메일 주소 형식 오류] 잘못된 이메일 주소 형식입니다. message={}, class={}, line={}",
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getLineNumber()
            );
            throw new BadRequestException(ErrorCode.INVALID_EMAIL_FORMAT);
        } catch (MessagingException e) {
            log.error("[이메일 전송 구성 실패] 메시지 작성 중 예외 발생.. message={}, class={}, line={}",
                e.getMessage(),
                e.getStackTrace()[0].getClassName(),
                e.getStackTrace()[0].getLineNumber()
            );
            throw new BadRequestException(ErrorCode.EMAIL_PROCESSING_FAILED);
        }
    }
}
