package com.tmri.rfid.property;

import com.tmri.rfid.common.DataExchangeType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Joey on 2015/12/1.
 */
@Component("runtimeProperty")
public class RuntimeProperty {

    @Value("${database.script.folder}")
    private String dbScriptFolder;

    @Value("${tmri.stub.host}")
    private String tmriStubHost;

    @Value("${tmri.stub.port}")
    private String tmriStubPort;

    @Value("${tmri.fetch.vehicle.uri}")
    private String tmriFetchVehicleUri;

    @Value("${tmri.auto.generate.task}")
    private String autoGenerateTask;

    @Value("${remote.fetch.vehicle}")
    private String remoteFetchVehicle;

    @Value("${remote.fetch.vehicle.endpoint}")
    private String remoteFetchVehicleEndpoint;

    @Value("${remote.fetch.vehicle.serial.no}")
    private String remoteFetchVehicleSerialNo;

    @Value("${remote.fetch.vehicle.glbm}")
    private String remoteFetchVehicleGlbm;

    @Value("${remote.fetch.vehicle.ip}")
    private String remoteFetchVehicleIp;

    @Value("${data.exchange.type}")
    private String dataExchangeType;

    @Value("${data.exchange.folder}")
    private String dataExchangeFolder;

    @Value("${data.exchange.ftp.address}")
    private String dataExchangeFTPAddress;

    @Value("${data.exchange.ftp.username}")
    private String dataExchangeFTPUsername;

    @Value("${data.exchange.ftp.password}")
    private String dataExchangeFTPPassword;

    @Value("${log.path}")
    private String logPath;

    @Value("${file.folder}")
    private String fileFolder;

    public String getDbScriptFolder() {
        return dbScriptFolder;
    }

    public String getTmriStubHost() {
        return tmriStubHost;
    }

    public String getTmriStubPort() {
        return tmriStubPort;
    }

    public String getTmriFetchVehicleUri() {
        return tmriFetchVehicleUri;
    }

    public boolean isAutoGenerateTask() {

        return Boolean.valueOf(autoGenerateTask);
    }

    public boolean isRemoteFetchVehicle() {
        return Boolean.valueOf(remoteFetchVehicle);
    }

    public String getRemoteFetchVehicleEndpoint() {
        return remoteFetchVehicleEndpoint;
    }

    public String getAutoGenerateTask() {
        return autoGenerateTask;
    }

    public String getRemoteFetchVehicle() {
        return remoteFetchVehicle;
    }

    public String getRemoteFetchVehicleSerialNo() {
        return remoteFetchVehicleSerialNo;
    }

    public String getRemoteFetchVehicleGlbm() {
        return remoteFetchVehicleGlbm;
    }

    public String getRemoteFetchVehicleIp() {
        return remoteFetchVehicleIp;
    }

    public DataExchangeType getDataExchangeType() {
        DataExchangeType type = DataExchangeType.valueOf(dataExchangeType);
        if (type == null) return DataExchangeType.DB;
        return type;
    }

    public String getDataExchangeFolder() {
        return dataExchangeFolder;
    }

    public String getDataExchangeFTPAddress() {
        return dataExchangeFTPAddress;
    }

    public void setDataExchangeFTPAddress(String dataExchangeFTPAddress) {
        this.dataExchangeFTPAddress = dataExchangeFTPAddress;
    }

    public String getDataExchangeFTPUsername() {
        return dataExchangeFTPUsername;
    }

    public void setDataExchangeFTPUsername(String dataExchangeFTPUsername) {
        this.dataExchangeFTPUsername = dataExchangeFTPUsername;
    }

    public String getDataExchangeFTPPassword() {
        return dataExchangeFTPPassword;
    }

    public void setDataExchangeFTPPassword(String dataExchangeFTPPassword) {
        this.dataExchangeFTPPassword = dataExchangeFTPPassword;
    }

    public String getLogPath() {
        return logPath;
    }

    public String getFileFolder() {
        return fileFolder;
    }

}
