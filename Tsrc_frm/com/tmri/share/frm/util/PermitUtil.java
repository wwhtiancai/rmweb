package com.tmri.share.frm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.tmri.share.frm.bean.Code;
import com.tmri.share.frm.service.GSysparaCodeService;

/**
 * 系统模块加载许可类
 * 
 * @author HuangShubing
 * 
 */
public final class PermitUtil {
    // 系统加载系统类别
    public static final String PLATFORM_SYSTEM_XTLB = "PLATFORM_SYSTEM_XTLB";

    public static final String XTLB_CORE = "00,31,60,61,62,63,64,65,66,67,68";

    public static final String KEY_MENU_CONFIG = "PERMIT_KEY_MENU_CONFIG";

    public static final String KEY_CLOUD_ENABLE = "CLOUD_ENABLE";

    public static final String KEY_KAFKA_ENABLE = "PERMIT_KEY_KAFKA_ENABLE";

    public static final String KEY_TOLLGATE_CONFIG = "PERMIT_KEY_TOLLGATE_CONFIG";

    public static final String KEY_CLIENT_CONFIG = "PERMIT_KEY_CLIENT_CONFIG";
    
    public static final String KEY_VIO_YXSXSH_LKYW = "VIO_YXSXSH_LKYW";

    private static final String PRE_MENU = "MENU";

    private static final String PRE_CLOUD = "CLOUD";

    private static final String PRE_TOLLGATE = "TOLLGATE";

    private static final String PRE_CLIENT = "CLIENT";

    private static final String PRE_XTLB = "XTLB";

    private static final String XTLB_SUSP = "63";

    private static final String XTLB_RECG = "71";

    private static final String XTLB_PTPS = "72";

    private static final String XTLB_PGIS = "73";

    private static final String XTLB_BIGDATA = "74";

    private static final String YWLB_CORE = "1";

    private static final String SPLIT_STR = "#";

    private static final String SPLIT_STR_AZDM = ":";

    private static final String SPLIT_STR_DMZ = ",";

    private static final String DMLB_YWLB = "0136";

    private static final String AES_KEY_PERMIT = "AsD#$fv123df45a%";

    private static final String ENABLE = "1";

    private static String localAzdm;

    private static String menuConfigValue;

    private static String coludEnableValue;
    
    private static String vioYxsxshLkywValue;

    private static List<Code> clientConfigs = new ArrayList<Code>();

    private static List<Code> tollgateConfigs = new ArrayList<Code>();

    private static GSysparaCodeService gSysparaCodeService;

    private PermitUtil() {
    }

    /**
     * 是否需要加载二次识别菜单
     * 
     * @author HuangShubing
     * @return true 加载 false 不加载
     */
    public static boolean loadRecgMenu() {
        return loadMenu(XTLB_RECG);
    }

    /**
     * 是否需要加载区间测速菜单
     * 
     * @author HuangShubing
     * @return true 加载 false 不加载
     */
    public static boolean loadPtpsMenu() {
        return loadMenu(XTLB_PTPS);
    }

    /**
     * 是否需要加载PGIS菜单
     * 
     * @author HuangShubing
     * @return true 加载 false 不加载
     */
    public static boolean loadPgisMenu() {
        return loadMenu(XTLB_PGIS);
    }

    /**
     * 是否需要加载大数据分析菜单
     * 
     * @author HuangShubing
     * @return true 加载 false 不加载
     */
    public static boolean loadBigdataMenu() {
        return loadMenu(XTLB_BIGDATA);
    }

    /**
     * 是否启用云平台
     * 
     * @author HuangShubing
     * @return true 启用 false 不启用
     */
    public static boolean isCloudEnable() {
        return ENABLE.equals(coludEnableValue);
    }
    
    /**
     * 是否优先筛选审核两客一危
     * */
    public static boolean isVioYxsxshLkyw(){
    	return ENABLE.equals(vioYxsxshLkywValue);
    }

    public static List<Code> getTollgateConfigs() {
        return tollgateConfigs;
    }

    public static List<Code> getClientConfigs() {
        return clientConfigs;
    }

    public static void init(GSysparaCodeService gSysparaCodeService) {
        if (null != gSysparaCodeService) {
            PermitUtil.gSysparaCodeService = gSysparaCodeService;
        }
    }

    public static void setLocalAzdm(String localAzdm) {
        PermitUtil.localAzdm = localAzdm;
    }

    public static void setMenuConfigValue(String menuConfigValue) {
        PermitUtil.menuConfigValue = getDecryptedValue(PRE_MENU, menuConfigValue);
    }

    public static void setColudEnableValue(String coludEnableValue) {
        PermitUtil.coludEnableValue = getDecryptedValue(PRE_CLOUD, coludEnableValue);
    }
    
    public static void setColudEnable(String coludEnableValue) {
        PermitUtil.coludEnableValue = coludEnableValue;
    }
    
    public static void setVioYxsxshLkywValue(String vioYxsxshLkywValue) {
        PermitUtil.vioYxsxshLkywValue = vioYxsxshLkywValue;
    }

    public static void setTollgateConfigValue(String tollgateConfigValue) {
        tollgateConfigValue = getDecryptedValue(PRE_TOLLGATE, tollgateConfigValue);
        Set<String> clientConfigSet = new HashSet<String>();
        clientConfigSet.add(YWLB_CORE);
        // clientConfigSet.add(YWLB_STATION);

        procConfigs(tollgateConfigValue, clientConfigSet, false);
    }

    public static void setClientConfigValue(String clientConfigValue) {
        clientConfigValue = getDecryptedValue(PRE_CLIENT, clientConfigValue);
        Set<String> clientConfigSet = new HashSet<String>();
        clientConfigSet.add(YWLB_CORE);
        // TODO 暂时屏蔽
        // clientConfigSet.add(YWLB_FOREIGN);
        // clientConfigSet.add(YWLB_STATION);

        procConfigs(clientConfigValue, clientConfigSet, true);
    }

    private static void procConfigs(String configValue, Set<String> clientConfigSet,
            boolean isClient) {
        if (StringUtil.checkBN(configValue)) {
            String[] dmzs = configValue.split(SPLIT_STR_DMZ);
            if (null != dmzs) {
                for (String dmz : dmzs) {
                    clientConfigSet.add(dmz);
                }
            }
        }

        ArrayList<String> list = new ArrayList<String>(clientConfigSet);
        Collections.sort(list);
        Code code = null;

        for (String dmz : list) {
            try {
                code = gSysparaCodeService.getCode(XTLB_SUSP, DMLB_YWLB, dmz);

                if (null != code) {
                    if (isClient) {
                        clientConfigs.add(code);
                    } else {
                        tollgateConfigs.add(code);
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static String getDecryptedValue(String pre, String encryptedValue) {
        String result = null;

        if (StringUtil.checkBN(encryptedValue) && StringUtil.checkBN(pre)
                && StringUtil.checkBN(localAzdm)) {
            try {
                String permitStr = AES.decrypt(encryptedValue, AES_KEY_PERMIT);

                if (StringUtil.checkBN(permitStr)) {
                    String[] tmp = permitStr.split(SPLIT_STR);
                    if (2 <= tmp.length && pre.equals(tmp[0]) && StringUtil.checkBN(tmp[1])) {
                        String[] tmp2 = tmp[1].split(SPLIT_STR_AZDM);
                        if (2 <= tmp2.length && localAzdm.equals(tmp2[0])) {
                            return tmp2[1];
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("解密过程中遇到错误, pre:" + pre + ",encryptedValue:" + encryptedValue
                        + "," + e.getMessage());
                e.printStackTrace();
            }
        }

        return result;
    }

    private static boolean loadMenu(String xtlb) {
        if (StringUtil.checkBN(menuConfigValue) && menuConfigValue.contains(xtlb)) {
            return true;
        }

        return false;
    }

    // 获取系统类别
    public static String getSystemxtlbValue(String PLATFORM_SYSTEM_XTLB) {
        String xtlbs = getDecryptedValue(PRE_XTLB, PLATFORM_SYSTEM_XTLB);
        return xtlbs;
    }
}
