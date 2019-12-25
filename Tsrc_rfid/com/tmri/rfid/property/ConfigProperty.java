package com.tmri.rfid.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Joey on 2015/12/1.
 */
@Component("configProperty")
public class ConfigProperty {

    @Value("${max.fetch.encryptor.index.times}")
    private int maxFetchEncryptorIndexTimes;

    @Value("${fetch.encryptor.index.interval}")
    private int fetchEncryptorIndexInterval;

    @Value("${encryptor.id.protected.key}")
    private String encryptorIdOfProtectedKey;

    @Value("${encryptor.id.encrypt.decrypt.key}")
    private String encryptorIdOfEncryptAndDecryptKey;

    @Value("${encryptor.id.u0xkl.key}")
    private String encryptorIdOfU0xklKey;

    @Value("${encryptor.webservice.useraccount.key}")
    private String encryptorWebUseraccountKey;

    @Value("${encryptor.webservice.password.key}")
    private String encryptorWebPasswordKey;

    @Value("${encryptor.webservice.url.key}")
    private String encryptorWebUrlKey;

    @Value("${encryptor.webservice.id.protected.key}")
    private String encryptorIdOfProtectKeyByKMS;

    @Value("${encryptor.webservice.id.encrypt.decrypt.key}")
    private String encryptorIdOfEncryptAndDecryptKeyByKMS;
    
    public int getMaxFetchEncryptorIndexTimes() {
        return maxFetchEncryptorIndexTimes;
    }

    public String getEncryptorIdOfProtectedKey() {
        return encryptorIdOfProtectedKey;
    }

    public String getEncryptorIdOfEncryptAndDecryptKey() {
        return encryptorIdOfEncryptAndDecryptKey;
    }

    public int getFetchEncryptorIndexInterval() {
        return fetchEncryptorIndexInterval;
    }

    public String getEncryptorIdOfU0xklKey() {
        return encryptorIdOfU0xklKey;
    }

    public String getEncryptorWebUseraccountKey() {
        return encryptorWebUseraccountKey;
    }

    public String getEncryptorWebPasswordKey() {
        return encryptorWebPasswordKey;
    }

    public String getEncryptorWebUrlKey() {
        return encryptorWebUrlKey;
    }

    public String getEncryptorIdOfProtectKeyByKMS() {
        return encryptorIdOfProtectKeyByKMS;
    }

    public String getEncryptorIdOfEncryptAndDecryptKeyByKMS() {
        return encryptorIdOfEncryptAndDecryptKeyByKMS;
    }
}

