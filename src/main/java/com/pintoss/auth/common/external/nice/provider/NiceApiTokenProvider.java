package com.pintoss.auth.common.external.nice.provider;

import com.pintoss.auth.common.external.nice.client.NiceApiClient;
import com.pintoss.auth.common.external.nice.client.dto.NiceApiCryptoTokenRequest;
import com.pintoss.auth.common.external.nice.client.dto.NiceApiCryptoTokenRequestDataBody;
import com.pintoss.auth.common.external.nice.client.dto.NiceApiCryptoTokenRequestDataHeader;
import com.pintoss.auth.common.external.nice.client.dto.NiceApiAccessTokenResponse;
import com.pintoss.auth.common.external.nice.client.dto.NiceApiCryptoTokenResponse;
import com.pintoss.auth.common.external.nice.config.NiceApiProperties;
import com.pintoss.auth.common.external.nice.store.NiceApiAccessToken;
import com.pintoss.auth.common.external.nice.store.NiceApiCryptoToken;
import com.pintoss.auth.common.external.nice.store.NiceApiSymmetricKey;
import com.pintoss.auth.common.external.nice.store.NiceApiTokenStore;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class NiceApiTokenProvider {

    private final NiceApiTokenStore store;
    private final NiceApiProperties niceApiProperties;
    private final NiceApiClient niceApiClient;

    public NiceApiAccessToken getAccessToken() {
        if((isEmptyAccessToken()) && isExpiredAccessToken()){
            NiceApiAccessTokenResponse response = niceApiClient.getAccessToken(
                    niceApiProperties.getAccessTokenUri(),
                    niceApiProperties.getClientId(),
                    niceApiProperties.getClientSecret()
            ).block();
            NiceApiAccessToken accessToken = new NiceApiAccessToken(
                    response.getDataBody().getAccess_token(),
                    LocalDateTime.now().plusSeconds(Long.parseLong(response.getDataBody().getExpires_in()))
            );
            saveAccessToken(accessToken);
            return accessToken;
        }
        return store.getAccessToken();
    }

    public NiceApiCryptoToken getCryptoToken() {
        if(isEmptyCryptoToken() || isExpiredCryptoToken()){
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            NiceApiCryptoTokenRequest niceApiCryptoTokenRequest = new NiceApiCryptoTokenRequest(
                    new NiceApiCryptoTokenRequestDataHeader("ko"),
                    new NiceApiCryptoTokenRequestDataBody(now.format(formatter), UUID.randomUUID().toString().substring(0,10), "1"));

            NiceApiCryptoTokenResponse response = niceApiClient.getCryptoToken(
                    niceApiProperties.getEncryptedTokenUri(),
                    getAccessToken().getValue(),
                    niceApiProperties.getClientId(),
                    niceApiProperties.getProductId(),
                    niceApiCryptoTokenRequest
            ).block();
            NiceApiCryptoToken cryptoToken = new NiceApiCryptoToken(
                    niceApiCryptoTokenRequest.getDataBody().getReq_dtim(),
                    niceApiCryptoTokenRequest.getDataBody().getReq_no(),
                    response.getDataBody().getSite_code(),
                    response.getDataBody().getToken_version_id(),
                    response.getDataBody().getToken_val(),
                    LocalDateTime.now().plusSeconds(response.getDataBody().getPeriod())
            );
            saveCryptoToken(cryptoToken);
            return cryptoToken;
        }
        return store.getCryptoToken();
    }

    public NiceApiSymmetricKey getSymmetricKey() {
        if(Objects.isNull(store.getSymmetricKey())) {
            return null;
        }
        return this.store.getSymmetricKey();
    }

    public boolean isEmptyAccessToken() {
        return Objects.isNull(store.getAccessToken());
    }

    public boolean isExpiredAccessToken() {
        if(Objects.isNull(store.getAccessToken())) {
            return true;
        }
        return store.getAccessToken().isExpired();
    }

    public boolean isEmptyCryptoToken() {
        return Objects.isNull(store.getCryptoToken());
    }

    public boolean isExpiredCryptoToken() {
        if(Objects.isNull(store.getCryptoToken())) {
            return true;
        }
        return store.getCryptoToken().isExpired();
    }

    public void saveAccessToken(NiceApiAccessToken accessToken) {
        this.store.setAccessToken(accessToken);
        this.store.setCryptoToken(null);
        this.store.setSymmetricKey(null);
    }

    public void saveCryptoToken(NiceApiCryptoToken cryptoToken) {
        this.store.setCryptoToken(cryptoToken);
        this.store.setSymmetricKey(null);
    }

    public void saveSymmetricKey(NiceApiSymmetricKey symmetricKey) {
        this.store.setSymmetricKey(symmetricKey);
    }
}
