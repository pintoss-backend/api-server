package com.pintoss.auth.client.galaxia;

import com.pintoss.auth.core.order.domain.PurchaseResult;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GalaxiaPurchaseResponse {
    private String responseCode;
    private String approvalCode;
    private String openFlag;
    private String cardNo;
    private String remainPrice;
    private String itemName;
    private String printFlag1;
    private String printMsg1;
    private String printFlag2;
    private String printMsg2;
    private String printFlag3;
    private String printMsg3;
    private String printFlag4;
    private String printMsg4;
    private String printFlag5;
    private String printMsg5;

    public static GalaxiaPurchaseResponse fromBytes(byte[] plainBytes) {
        GalaxiaPurchaseResponse res = new GalaxiaPurchaseResponse();
        int idx = 0;

        try {
            res.setResponseCode(readField(plainBytes, idx, 4)); idx += 4;
//            res.setSuccess("0000".equals(res.getResponseCode()));
            res.setApprovalCode(readField(plainBytes, idx, 32)); idx += 32;
            res.setOpenFlag(readField(plainBytes, idx, 1)); idx += 1;
            res.setCardNo(readField(plainBytes, idx, 32)); idx += 32;
            res.setRemainPrice(readField(plainBytes, idx, 8)); idx += 8;
            res.setItemName(readField(plainBytes, idx, 32)); idx += 32;

            res.setPrintFlag1(readField(plainBytes, idx, 1)); idx += 1;
            res.setPrintMsg1(readField(plainBytes, idx, 32)); idx += 32;

            res.setPrintFlag2(readField(plainBytes, idx, 1)); idx += 1;
            res.setPrintMsg2(readField(plainBytes, idx, 32)); idx += 32;

            res.setPrintFlag3(readField(plainBytes, idx, 1)); idx += 1;
            res.setPrintMsg3(readField(plainBytes, idx, 32)); idx += 32;

            res.setPrintFlag4(readField(plainBytes, idx, 1)); idx += 1;
            res.setPrintMsg4(readField(plainBytes, idx, 32)); idx += 32;

            res.setPrintFlag5(readField(plainBytes, idx, 1)); idx += 1;
            res.setPrintMsg5(readField(plainBytes, idx, 32)); idx += 32;

            log.debug("[Galaxia 응답] 승인번호: {}, 카드번호: {}, 잔액: {}, 상품명: {}, 응답코드: {}",
                res.getApprovalCode(), res.getCardNo(), res.getRemainPrice(), res.getItemName(), res.getResponseCode());
        } catch (Exception e) {
            log.error("Galaxia 응답 파싱 중 오류 발생 : {}", e.getMessage(), e);
        }
        return res;
    }

    private static String readField(byte[] bytes, int offset, int length) throws UnsupportedEncodingException{
        if (offset + length > bytes.length) {
            throw new IndexOutOfBoundsException("바이트 배열 범위를 초과했습니다: offset=" + offset + ", length=" + length);
        }
        return new String(Arrays.copyOfRange(bytes, offset, offset + length), "EUC-KR").trim();
    }

    public PurchaseResult toResult() {
        return new PurchaseResult(
            "0000".equals(this.responseCode),
            this.approvalCode,
            enrichCardNo(),
            this.itemName,
            Long.parseLong(this.remainPrice),
            this.printMsg1 +","+ this.printMsg2 + ","+ this.printMsg3 + ","+ this.printMsg4 + ","+ this.printMsg5
        );
    }

    private String enrichCardNo() {
        if ("도서문화상품권".equals(this.getItemName())) {
            Matcher matcher = Pattern.compile("비밀번호[:：\\s]*([0-9]{4})")
                .matcher(this.getPrintMsg1());
            if (matcher.find()) {
                return this.getCardNo() + "-" + matcher.group(1);
            }
        }
        return this.getCardNo();
    }

}

