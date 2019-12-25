package com.tmri.framework.ctrl;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tmri.framework.service.SysuserManager;

/**
 * 会话计数
 * @author jianghailong
 *
 */
public class SessionListener  implements  HttpSessionListener{

	public void sessionCreated(HttpSessionEvent arg0){
		try{
			ApplicationContext ctx=null;
			if(ctx==null){
				ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getSession().getServletContext());
			}		
			SysuserManager sysuserManager = (SysuserManager)ctx.getBean(SysuserManager.class); 
			sysuserManager.recZxyhxx("1");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sessionDestroyed(HttpSessionEvent arg0){
		try{
			ApplicationContext ctx=null;
			if(ctx==null){
				ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(arg0.getSession().getServletContext());
			}
			SysuserManager sysuserManager = (SysuserManager)ctx.getBean(SysuserManager.class); 
			
			//删除临时表数据
            // String sessionid =arg0.getSession().getId();
            // TfcPassRstManager tfcPassRstManager =
            // (TfcPassRstManager)ctx.getBean(TfcPassRstManager.class);
            // tfcPassRstManager.delPassRstBySessionid(sessionid);
			
			sysuserManager.recZxyhxx("0");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
