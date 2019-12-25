package com.tmri.share.frm.util;

import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.tmri.share.ora.bean.DbResult;

public final class CommonResponse {

	public static final String success = "1";
	public static final String failed = "0";
	public static final String SuccessMessage = "操作成功！";
	public static final String FailedMessage = "操作失败！";
	public static final String ErrorPost = "非法的请求！";
	public static final String NoInput = "请输入必填项！";

	// zhoujn 20140317
	public static ModelAndView JSON(DbResult r) {
		ModelAndView view = new ModelAndView(JsonView.instance);
		try {
			String msg = r.getMsg();
            String msg1 = r.getMsg1();
			if (msg == null) {
				msg = "NullPointerException!";
			} else {
				msg = URLEncoder.encode(Common.ScriptToHtml(msg), "UTF-8");
			}
            String jsonString = "{\"code\":\"" + r.getCode() + "\",\"message\":\"" + msg
                    + "\",\"message1\":\"" + StringUtil.transBlank(msg1) + "\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		} catch (Exception ex) {
			System.out.println("URLEncoder Exception:" + ex.getMessage());
			String jsonString = "{\"code\":\"0\",\"message\":\"%E4%BF%9D%E5%AD%98%E5%BC%82%E5%B8%B8\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		}
		return view;
	}

	// code,message,retval
//	public static ModelAndView JSON(DbReturnInfo r) {
//		ModelAndView view = new ModelAndView(JsonView.instance);
//		try {
//			String msg = r.getMsg1();
//			if (msg == null) {
//				msg = "NullPointerException!";
//			} else {
//				msg = URLEncoder.encode(Common.ScriptToHtml(msg), "UTF-8");
//			}
//			String jsonString = "{\"code\":\"" + r.getCode() + "\",\"message\":\""
//					+ msg + "\",\"retval\":\"" + r.getMsg() + "\"}";
//			view.addObject(JsonView.JSON_ROOT, jsonString);
//		} catch (Exception ex) {
//			System.out.println("URLEncoder Exception:" + ex.getMessage());
//			String jsonString = "{\"code\":\"0\",\"message\":\"%E4%BF%9D%E5%AD%98%E5%BC%82%E5%B8%B8\"}";
//			view.addObject(JsonView.JSON_ROOT, jsonString);
//		}
//		return view;
//	}

	public static ModelAndView JSON(String message) {
		ModelAndView view = new ModelAndView(JsonView.instance);
		try {
			String msg = message;
			if (msg == null) {
				msg = "NullPointerException!";
			} else {
				msg = URLEncoder.encode(Common.ScriptToHtml(message), "UTF-8");
			}
			String jsonString = "{\"code\":\"0\",\"message\":\"" + msg + "\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		} catch (Exception ex) {
			System.out.println("URLEncoder Exception:" + ex.getMessage());
			String jsonString = "{\"code\":\"0\",\"message\":\"%E4%BF%9D%E5%AD%98%E6%88%90%E5%8A%9F\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		}
		return view;
	}

	public static ModelAndView JsonAjax(String message) {
		ModelAndView view = new ModelAndView(JsonView.instance);
		try {
			view.addObject(JsonView.JSON_ROOT, message);
		} catch (Exception ex) {
			System.out.println("URLEncoder Exception:" + ex.getMessage());
			String jsonString = "{}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		}
		return view;
	}

	public static ModelAndView JSON(Exception e) {
		ModelAndView view = new ModelAndView(JsonView.instance);
		try {
			String msg = StringUtil.getExpMsg(e);
			if (msg == null) {
				msg = "NullPointerException!";
			} else {
				msg = URLEncoder.encode(msg, "UTF-8");
			}
			String jsonString = "{\"code\":\"0\",\"message\":\"" + msg + "\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		} catch (Exception ex) {
			System.out.println("URLEncoder Exception:" + ex.getMessage());
			String jsonString = "{\"code\":\"0\",\"message\":\"%E4%BF%9D%E5%AD%98%E6%88%90%E5%8A%9F\"}";
			view.addObject(JsonView.JSON_ROOT, jsonString);
		}
		return view;
	}

	public static void setAlerts(String message, HttpServletRequest request) {
		String msg = message;
		if (message == null) {
			msg = "NullPointerException!";
		}
		request.setAttribute("error",
				"<script defer>alert(\'" + Common.ScriptToHtml(msg)
						+ "\');</script>");
	}

	public static void setAlerts(String message, boolean closed,
			HttpServletRequest request) {
		String msg = message;
		if (message == null) {
			msg = "NullPointerException!";
		}
		if (closed)
			request.setAttribute("error",
					"<script defer>alert(\'" + Common.ScriptToHtml(msg)
							+ "\');window.close();</script>");
		else
			request.setAttribute("error",
					"<script defer>alert(\'" + Common.ScriptToHtml(msg)
							+ "\');</script>");
	}

	public static void setErrors(Exception e, HttpServletRequest request) {
		String msg = e.getMessage();
		if (msg == null) {
			msg = "NullPointerException!";
		}
		request.setAttribute("error",
				"<script defer>alert('" + Common.ScriptToHtml(msg)
						+ "');top.location.href='/rmweb/500.html'</script>");
	}

	public static void PageRedirect(String sign, String message,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GB2312");
		try {
			PrintWriter out = response.getWriter();
			out.println("<script defer>parent.resultform(\'" + sign + "\',\'"
					+ Common.ScriptToHtml(message) + "\');</script>");
		} catch (Exception e) {
			System.out.println("returnform error:" + e.getMessage() + " at "
					+ Common.getNow());
		}
	}

	public static void pageRedirect(String funcName, String jsonMsg,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GB2312");
		try {
			DebugLog.debug(jsonMsg);
			PrintWriter out = response.getWriter();
			out.println("<script defer>parent." + funcName + "(\"" + jsonMsg
					+ "\");</script>");
		} catch (Exception e) {
			System.out.println("returnform error:" + e.getMessage() + " at "
					+ Common.getNow());
		}
	}
}
