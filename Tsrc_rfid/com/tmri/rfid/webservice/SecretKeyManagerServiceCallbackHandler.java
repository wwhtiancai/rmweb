/**
 * SecretKeyManagerServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.4  Built on : Dec 28, 2015 (10:03:39 GMT)
 */
package com.tmri.rfid.webservice;


/**
 *  SecretKeyManagerServiceCallbackHandler Callback class, Users can extend this class and implement
 *  their own receiveResult and receiveError methods.
 */
public abstract class SecretKeyManagerServiceCallbackHandler {
    protected Object clientData;

    /**
     * User can pass in any object that needs to be accessed once the NonBlocking
     * Web service call is finished and appropriate method of this CallBack is called.
     * @param clientData Object mechanism by which the user can pass in user data
     * that will be avilable at the time this callback is called.
     */
    public SecretKeyManagerServiceCallbackHandler(Object clientData) {
        this.clientData = clientData;
    }

    /**
     * Please use this constructor if you don't want to set any clientData
     */
    public SecretKeyManagerServiceCallbackHandler() {
        this.clientData = null;
    }

    /**
     * Get the client data
     */
    public Object getClientData() {
        return clientData;
    }

    /**
     * auto generated Axis2 call back method for generateRondomNumber method
     * override this method for handling normal response from generateRondomNumber operation
     */
    public void receiveResultgenerateRondomNumber(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.GenerateRondomNumberResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from generateRondomNumber operation
     */
    public void receiveErrorgenerateRondomNumber(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm2Decrypt method
     * override this method for handling normal response from sm2Decrypt operation
     */
    public void receiveResultsm2Decrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm2DecryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm2Decrypt operation
     */
    public void receiveErrorsm2Decrypt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm1CFBEncrypt method
     * override this method for handling normal response from sm1CFBEncrypt operation
     */
    public void receiveResultsm1CFBEncrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm1CFBEncryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm1CFBEncrypt operation
     */
    public void receiveErrorsm1CFBEncrypt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for genterateCAKeyPair method
     * override this method for handling normal response from genterateCAKeyPair operation
     */
    public void receiveResultgenterateCAKeyPair(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.GenterateCAKeyPairResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from genterateCAKeyPair operation
     */
    public void receiveErrorgenterateCAKeyPair(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchTagKeyForPerso method
     * override this method for handling normal response from fetchTagKeyForPerso operation
     */
    public void receiveResultfetchTagKeyForPerso(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchTagKeyForPersoResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchTagKeyForPerso operation
     */
    public void receiveErrorfetchTagKeyForPerso(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for checkSAMCert method
     * override this method for handling normal response from checkSAMCert operation
     */
    public void receiveResultcheckSAMCert(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.CheckSAMCertResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from checkSAMCert operation
     */
    public void receiveErrorcheckSAMCert(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchTagKeyForIssue method
     * override this method for handling normal response from fetchTagKeyForIssue operation
     */
    public void receiveResultfetchTagKeyForIssue(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchTagKeyForIssueResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchTagKeyForIssue operation
     */
    public void receiveErrorfetchTagKeyForIssue(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm1CBCDecrypt method
     * override this method for handling normal response from sm1CBCDecrypt operation
     */
    public void receiveResultsm1CBCDecrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm1CBCDecryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm1CBCDecrypt operation
     */
    public void receiveErrorsm1CBCDecrypt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm1CBCEncrypt method
     * override this method for handling normal response from sm1CBCEncrypt operation
     */
    public void receiveResultsm1CBCEncrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm1CBCEncryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm1CBCEncrypt operation
     */
    public void receiveErrorsm1CBCEncrypt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for exportPubKey method
     * override this method for handling normal response from exportPubKey operation
     */
    public void receiveResultexportPubKey(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.ExportPubKeyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from exportPubKey operation
     */
    public void receiveErrorexportPubKey(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchTagAuthKeyForIssue method
     * override this method for handling normal response from fetchTagAuthKeyForIssue operation
     */
    public void receiveResultfetchTagAuthKeyForIssue(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchTagAuthKeyForIssueResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchTagAuthKeyForIssue operation
     */
    public void receiveErrorfetchTagAuthKeyForIssue(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchSAMCert method
     * override this method for handling normal response from fetchSAMCert operation
     */
    public void receiveResultfetchSAMCert(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchSAMCertResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchSAMCert operation
     */
    public void receiveErrorfetchSAMCert(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm2Sign method
     * override this method for handling normal response from sm2Sign operation
     */
    public void receiveResultsm2Sign(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm2SignResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm2Sign operation
     */
    public void receiveErrorsm2Sign(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm3Digest method
     * override this method for handling normal response from sm3Digest operation
     */
    public void receiveResultsm3Digest(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm3DigestResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm3Digest operation
     */
    public void receiveErrorsm3Digest(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchSpecificKey method
     * override this method for handling normal response from fetchSpecificKey operation
     */
    public void receiveResultfetchSpecificKey(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchSpecificKeyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchSpecificKey operation
     */
    public void receiveErrorfetchSpecificKey(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchSAMSign method
     * override this method for handling normal response from fetchSAMSign operation
     */
    public void receiveResultfetchSAMSign(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchSAMSignResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchSAMSign operation
     */
    public void receiveErrorfetchSAMSign(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm1CFBDecrypt method
     * override this method for handling normal response from sm1CFBDecrypt operation
     */
    public void receiveResultsm1CFBDecrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm1CFBDecryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm1CFBDecrypt operation
     */
    public void receiveErrorsm1CFBDecrypt(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for generateSystemRootKey method
     * override this method for handling normal response from generateSystemRootKey operation
     */
    public void receiveResultgenerateSystemRootKey(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.GenerateSystemRootKeyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from generateSystemRootKey operation
     */
    public void receiveErrorgenerateSystemRootKey(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for createTmpProKey method
     * override this method for handling normal response from createTmpProKey operation
     */
    public void receiveResultcreateTmpProKey(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.CreateTmpProKeyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from createTmpProKey operation
     */
    public void receiveErrorcreateTmpProKey(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for fetchSAMKey method
     * override this method for handling normal response from fetchSAMKey operation
     */
    public void receiveResultfetchSAMKey(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.FetchSAMKeyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from fetchSAMKey operation
     */
    public void receiveErrorfetchSAMKey(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm2Verify method
     * override this method for handling normal response from sm2Verify operation
     */
    public void receiveResultsm2Verify(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm2VerifyResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm2Verify operation
     */
    public void receiveErrorsm2Verify(java.lang.Exception e) {
    }

    /**
     * auto generated Axis2 call back method for sm2Encrypt method
     * override this method for handling normal response from sm2Encrypt operation
     */
    public void receiveResultsm2Encrypt(
        com.tmri.rfid.webservice.SecretKeyManagerServiceStub.Sm2EncryptResponse result) {
    }

    /**
     * auto generated Axis2 Error handler
     * override this method for handling error response from sm2Encrypt operation
     */
    public void receiveErrorsm2Encrypt(java.lang.Exception e) {
    }
}
