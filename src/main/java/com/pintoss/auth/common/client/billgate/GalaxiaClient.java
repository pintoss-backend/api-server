package com.pintoss.auth.common.client.billgate;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import org.springframework.stereotype.Component;

@Component
public class GalaxiaClient {

    private final String HOST ;
    private final int PORT ;
    private final int TIMEOUT_MILLIS ;

    public GalaxiaClient() {
        this.HOST = "222.122.28.70";
        this.PORT = 21070;
        this.TIMEOUT_MILLIS = 2000;
    }

    public byte[] sendEncryptedRequest(byte[] payload) throws IOException {

        try (Socket socket = new Socket(HOST, PORT) ) {
            socket.setSoTimeout(TIMEOUT_MILLIS);

            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();

            outputStream.write(payload);
            outputStream.flush();;

            byte[] lengthBytes = new byte[4];
            int read = inputStream.read(lengthBytes);
            if (read < 4) {
                throw new IOException("전문 길이 헤더가 부족합니다.");
            }
            int totalLength = Integer.parseInt(new String(lengthBytes, "EUC-KR"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int remaining = totalLength;
            byte[] buffer = new byte[4096];

            while (remaining > 0) {
                int chunkSize = Math.min(buffer.length, remaining);
                int bytesRead = inputStream.read(buffer, 0, chunkSize);
                if (bytesRead == -1) break;
                baos.write(buffer, 0, bytesRead);
                remaining -= bytesRead;
            }

            return baos.toByteArray();
        } catch (SocketTimeoutException e) {
            throw new IOException("TCP 요청이 타임아웃 되었습니다", e);
        }
    }
}
