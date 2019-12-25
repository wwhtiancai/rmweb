package com.tmri.rfid.util;

import com.tmri.rfid.bean.Vehicle;
import com.tmri.rfid.bean.VehicleInfo;
import com.tmri.rfid.bean.VehicleLog;

/**
 * Created by Joey on 2016/9/30.
 */
public class VehicleUtil {

    public static int getZzl(VehicleInfo vehicle) throws IllegalArgumentException {
        int zzl;
        if (vehicle.getCllx().toUpperCase().startsWith("Q")) {
            if (vehicle.getZqyzl() == null || vehicle.getZbzl() == null)
                throw new IllegalArgumentException("牵引车准牵引质量或整备质量为空");
            else {
                zzl = vehicle.getZbzl() + vehicle.getZqyzl();
            }
        } else {
            if (vehicle.getZbzl() == null && vehicle.getZzl() == null) {
                throw new IllegalArgumentException("整备质量和总质量都为空");
            } else if (vehicle.getZbzl() == null) {
                zzl = vehicle.getZzl();
            } else if (vehicle.getHdzzl() != null) {
                zzl = vehicle.getZbzl() + vehicle.getHdzzl();
            } else if (vehicle.getZzl() != null) {
                zzl = vehicle.getZzl();
            } else {
                throw new IllegalArgumentException("核定载质量和总质量都为空");
            }
        }
        return zzl;
    }

}
