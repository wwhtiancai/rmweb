package com.tmri.framework.ctrl;


import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tmri.share.cache.bean.SystemCache;
import com.tmri.share.frm.bean.SafeMachineInfo;
import com.tmri.share.frm.service.GSysparaCodeService;
import com.tmri.share.frm.util.Constants;
import com.tmri.share.frm.util.NetUtil;

public class SCtrl extends HttpServlet{
	public void init() throws ServletException{
	 Constants.SYS_VERSION = this.getServletContext().getInitParameter("version");
	 Constants.SYS_MADE_DATE = this.getServletContext().getInitParameter("versiondate");
	 Constants.SYS_TYPE = this.getServletContext().getInitParameter("systype");

		ApplicationContext ctx=null;
		if(ctx==null){
			ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
		}		
		
		GSysparaCodeService gSysparaCodeService = (GSysparaCodeService)ctx.getBean(GSysparaCodeService.class);
		Constants.SYS_XTZT = gSysparaCodeService.getSyspara("00","2","XTZT").getMrz();
		
		Constants.SYS_SFT = gSysparaCodeService.getSyspara("00","2","SFT").getMrz();
		
		//��ҷ��ʷ������趨��ǿ���
		String ipdz_wgfwfwq = gSysparaCodeService.getSyspara("00","2","WGFWFWQ").getMrz();
		String bdipdz = NetUtil.getLocalFiltedIP("");
		if(ipdz_wgfwfwq.indexOf(bdipdz)>=0){
			String[] ipStrings = ipdz_wgfwfwq.split(",");
			for(int i=0;i<ipStrings.length;i++){
				if(ipStrings[i].indexOf(bdipdz)>=0){
					int ipos = ipStrings[i].indexOf(":");
					Constants.SYS_WGFWFWQ_BJ = ipStrings[i].substring(ipos + 1);
				}
			}
		}else{
			Constants.SYS_WGFWFWQ_BJ = "0";
		}
		String webver=this.getServletContext().getServerInfo();
		String prefix=this.getServletContext().getRealPath("/")+"/";
		
		String fzjgString=gSysparaCodeService.getSysParaValue("00","BDFZJG");
		String fbrq=Constants.SYS_MADE_DATE.substring(0,4)+"."+Constants.SYS_MADE_DATE.substring(4,6)+"."+Constants.SYS_MADE_DATE.substring(6,8);

		// shijianrong 20120723 ��������WEB������IP�ͷ�����
		String currentHostServerName = this.getCurrentHostServerName(this
				.getServletContext());

		Constants.SYS_SERVERNAME = currentHostServerName;
		SystemCache.getInstance().reg(Constants.SYS_SERVLET_CONTEXT,this.getServletContext());
		
		//���ط�֤���ر����ڱ�����֤���ط�Χ�� 20110906
		if(Constants.SYS_TYPE.equals("1")){
			//������ģʽ
			//if(F1.getBj().length()==1||"81,82,83,84,85,92,93,94,95,96,".indexOf(F1.getBj1()+",")<0){
			SafeMachineInfo machineInfo = null;
			if(machineInfo!=null){
				String bdfzjg[] = fzjgString.split(",");
				for(int i=0;i<bdfzjg.length;i++){
					String tmpfzjgString = bdfzjg[i];
					if(bdfzjg[i].length()>2){
						tmpfzjgString = bdfzjg[i].substring(0,2);
					}
					if(machineInfo.getFzjg().indexOf(tmpfzjgString)<0){
						Constants.SYS_OTHER_STATE = "���ط�֤���ر����ڱ�����֤���ط�Χ�ڣ����ط�֤����Ϊ��" + fzjgString + "��,������֤����Ϊ��" + machineInfo.getFzjg() + "��";
					}
				}
			}
			//}
		}else{
			//������ģʽ
			SafeMachineInfo machineInfo = null;
			if(machineInfo!=null){
				String bdfzjg[] = fzjgString.split(",");
				for(int i=0;i<bdfzjg.length;i++){
					String tmpfzjgString = bdfzjg[i];
					if(bdfzjg[i].length()>2){
						tmpfzjgString = bdfzjg[i].substring(0,2);
					}
					if(machineInfo.getFzjg().indexOf(tmpfzjgString)<0){
						Constants.SYS_OTHER_STATE = "���ط�֤���ر����ڱ�����֤���ط�Χ�ڣ����ط�֤����Ϊ��" + fzjgString + "��,������֤����Ϊ��" + machineInfo.getFzjg() + "��";
					}
				}
			}
		}
	}

	// Clean up resources
	public void destroy(){

	}

	public String getCurrentHostServerName(ServletContext sc) {
		String r = "";
		if (sc != null) {
			String sname = sc.getServerInfo();
			if (sname.toLowerCase().indexOf("tomcat") > -1) {
				r = sname;
			} else if (sname.toLowerCase().indexOf("websphere") > -1) {
				r = (String) sc
						.getAttribute("com.ibm.websphere.servlet.application.host");
			} else {
				r = sname;
			}
		}
		return r;
	}
	
}
