package com.pintoss.auth.module.user.infra.mail;

import com.nimbusds.openid.connect.sdk.assurance.evidences.attachment.Attachment;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SendRequest {

    private String sender;
    private String receiver;
    private String subject;
    private String content;
    private List<Attachment> attachments = new ArrayList<>();

}
