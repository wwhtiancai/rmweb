package com.tmri.rfid.endpoints;

import com.tmri.framework.ctrl.FrmCtrl;
import com.tmri.framework.service.ProgramFoldManager;
import com.tmri.framework.service.SysuserManager;
import com.tmri.rfid.bean.ClientUser;
import com.tmri.rfid.service.ClientUserService;
import com.tmri.rfid.service.EriEquipmentService;
import com.tmri.rfid.service.EriReaderWriterService;
import com.tmri.rfid.service.OperationLogService;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.RestfulSession;
import com.tmri.share.frm.bean.Program;
import com.tmri.share.frm.bean.SysUser;
import com.tmri.share.frm.bean.UserSession;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.FuncUtil;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;

/**
 * Created by Joey on 2015/11/17.
 */
@Controller
@RequestMapping("/be/login.rfid")
public class LoginEndPoint extends FrmCtrl {

    private final static Logger LOG = LoggerFactory.getLogger(LoginEndPoint.class);

    @Resource
    private SysuserManager sysuserManager;
    @Resource
    private ClientUserService clientUserService;

	@Resource
	private ProgramFoldManager programFoldManager;

	@Resource
	private EriEquipmentService eriEquipmentService;

	@Resource
	private OperationLogService operationLogService;

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String login(@RequestParam(value = "yhdh", required = true) String yhdh,
                        @RequestParam(value = "mm", required = true) String mm,
						@RequestParam(value = "aqmkxh", required = false) String aqmkxh,
                        HttpServletRequest request)  throws Exception{
        LOG.info(String.format("------> post /login.rfid yhdh = %s, aqmkxh = %s", yhdh, aqmkxh));
        Map<String, Object> resultMap = new HashMap<String, Object>();
		
		try{
			String strRemoteAddr=FuncUtil.getRemoteIpdz(request); // 获取ip地址
			SysUser sysuser = sysuserManager.getSysuser(yhdh);
			if (sysuser == null) {
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg", "用户不存在");
			} else {
				int result = clientUserService.validateSysuser(yhdh, mm, strRemoteAddr);
				if ( result != 0 ) {
					resultMap.put("resultId", "01");
					resultMap.put("resultMsg", "密码错误");
				}else {
					UUID token = UUID.randomUUID();
	            	ClientUser clientUser = new ClientUser();
                    clientUser.setToken(token.toString());
                    clientUser.setSysUser(sysuser);
                    clientUser.setLastViewTime(new Date());
					RestfulSession.setSession(token.toString(), clientUser);
					HashMap paras = new HashMap();
					String XTYXMS = this.gSysparaCodeService.getSysParaValue("00","XTYXMS");
					paras.put("XTYXMS", XTYXMS);
					paras.put("DEBUG", "1");
					paras.put("RESTFUL", "true");
					HashMap map = programFoldManager.getFoldMenuStr(sysuser, paras);
					if (map.get("prolist") != null) {
						List<Program> programList = (List<Program>) map.get("prolist");
						for (Program program : programList) {
							clientUser.addMenu(program.getYmdz().startsWith("/") ?
									program.getYmdz() : "/" + program.getYmdz());
						}
					}
					if (StringUtils.isNotEmpty(aqmkxh)) {
						eriEquipmentService.register(aqmkxh, sysuser.getGlbm());
					}
	            	
	                resultMap.put("resultId", "00");
	                resultMap.put("user", sysuser);
	                resultMap.put("token", token.toString());
	            }
	        } 
		}catch(Exception ex){
			ex.printStackTrace();
			if(ex.getMessage()==null){
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg","java.lang.NullPointerException");
			}else if(ex.getMessage().equals(Constants.SYS_CHANGE_PASSWORD)||ex.getMessage().equals(Constants.SYS_PASSWORD_INVALID)||ex.getMessage().equals(Constants.SYS_FIRST_LOGIN)||ex.getMessage().equals(Constants.SYS_GLY_PASS)||ex.getMessage().equals(Constants.SYS_PTYH_PASS)){
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg",ex.getMessage());
			}else if(ex.getMessage().equals(Constants.SYS_PTYH_GDDZTS)){
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg",ex.getMessage());
			}else{
				resultMap.put("resultId", "01");
				resultMap.put("resultMsg",ex.getMessage());
			}
		}
		String result = GsonHelper.getGson().toJson(resultMap);
		operationLogService.log("RESTFUL_LOGIN", yhdh, result);
		LOG.info("------> post /be/login.rfid,login success: result = " + result);

        return GsonHelper.getGson().toJson(resultMap);
    }
    
    @RequestMapping(params = "method=noLoginMsg")
    @ResponseBody
    public String noLoginMsg(HttpServletRequest request)  throws Exception{
        LOG.info("------> post /login.rfid?method=noLoginMsg");
        
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("resultId", "98");
        resultMap.put("resultMsg", "用户未登陆");
        return GsonHelper.getGson().toJson(resultMap);
    }

	@RequestMapping(params = "method=noAuthorityMsg")
	@ResponseBody
	public String noAuthorityMsg() throws Exception {
		LOG.info("------> post /login.rfid?method=noAuthorityMsg");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("resultId", "97");
		resultMap.put("resultMsg", "用户未授权");
		return GsonHelper.getGson().toJson(resultMap);
	}

}
