package com.tmri.share.frm.dao;

import java.util.Map;

import com.tmri.share.ora.bean.DbResult;


//鐢ㄤ簬绠�寲璋冪敤set瀛樺偍杩囩▼鐨勬柟娉�chenxh

public interface CommDao {
	DbResult operaPackage(String procname, Map<String, Object> hashmap);

	/**
	 * 鎵ц瀛樺偍杩囩▼锛屽瓨鍌ㄨ繃绋嬬殑杩斿洖鍊硷紝鍙湁涓�釜number鍨嬬殑code
	 * 
	 * @see CommDao#executeProc(String, int, Object...)
	 * 
	 * @param proc
	 *            瀛樺偍杩囩▼鍚嶅瓧package.proc
	 * @param args
	 *            鍙傛暟銆備釜鏁颁笉闄愶紝鎸夌収椤哄簭浼犲�?
	 * @return
	 */
	long executeProc1(String proc, final Object... args);

	/**
	 * 鎵ц瀛樺偍杩囩▼锛岃繑鍥炲�瀹氭锛孌bReturnInfo銆傛湰鏂规硶閫傚悎璋冪敤瀛樺偍杩囩▼锛岃繑鍥炵畝鍗曠粨鏋�?	 * 
	 * @param proc
	 *            瀛樺偍杩囩▼鐨勫悕绉般�pkg.proc
	 * @param retArgCount
	 *            杩斿洖鍊间釜鏁般�瑕佹眰5>retArgCount>0锛屽惁鍒欐湰鏂规硶鎶ラ敊銆�/br> <li>
	 *            绗竴涓繑鍥炲�绫诲�?锛岃缃负number锛屽搴擠bReturnInfo鐨刢ode瀛楁锛�?li>鍚庨潰鐨勮繑鍥炲�绫诲�?锛�
	 *            涓�緥涓簐archar锛屽搴斿瓧娈礫msg~msg3]
	 * @param args
	 *            瀛樺偍杩囩▼鐨勮姹傚弬鏁般�鍙互浼�~N涓�?
	 * @return {@link DbResult}
	 * 
	 * <pre>
	 * 瀛樺偍杩囩▼锛�<code> Procedure Changenet_Sys_User_Passwd ( In_Yhlx Varchar2, In_Yhdh
	 *         Varchar2, In_Newpasswd Varchar2, Rtn Out Number, Rtncolname Out
	 *         Varchar2, Rtnmsg Out Varchar2 ) Is </code>
	 *         ............................
	 * 
	 *         java浠ｇ�?<code> DbResult info =
	 *         executeProc("net_sys_pkg.Changenet_Sys_User_Passwd"
	 *         ,3,"yhlx","yhdh","passwd"); </code> 鍙敤鐨勮繑鍥炲�锛�?code>
	 *         info.getCode(); info.getMsg(); info.getMsg1(); </code>
	 * 
	 *         </pre>
	 * 
	 */
	public DbResult executeProc(String proc, int retArgCount,
			Object... args);
}
