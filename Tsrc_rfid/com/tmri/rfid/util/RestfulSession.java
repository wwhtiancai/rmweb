package com.tmri.rfid.util;

import com.tmri.rfid.bean.ClientUser;
import com.tmri.rfid.bean.PackageBox;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Joey on 2016-03-08.
 */
public class RestfulSession {

    private static Map<String, ClientUser> session = new HashMap<String, ClientUser>(10000);

    private static Map<String, String> clients = new HashMap<String, String>();

    public static ClientUser getSession(String sessionId) {
        return session.get(sessionId);
    }

    public static void setSession(String sessionId, ClientUser clientUser) {
        String yhdh = clientUser.getSysUser().getYhdh();
        String token = clients.get(yhdh);
        if (StringUtils.isNotEmpty(token)) {
            session.remove(token);
        }
        session.put(sessionId, clientUser);
        clients.put(clientUser.getSysUser().getYhdh(), sessionId);
    }

    public static void renewal(String sessionId) {
        session.get(sessionId).setLastViewTime(new Date());
    }

}
