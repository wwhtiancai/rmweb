package com.tmri.rfid.bean;

import java.util.Date;

/**
 * Created by Joey on 2016-03-08.
 */
public class VehicleLog extends VehicleInfo {

    private Long id; //–Ú∫≈
    private Long clxxid; //∂‘”¶Vehicle.Id

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClxxid() {
        return clxxid;
    }

    public void setClxxid(Long clxxid) {
        this.clxxid = clxxid;
    }
}
