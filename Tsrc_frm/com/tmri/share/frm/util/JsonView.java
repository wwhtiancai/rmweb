package com.tmri.share.frm.util;

import org.springframework.web.servlet.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.io.PrintWriter;

/**
 * Created by IntelliJ IDEA.
 * User: xuwenbin
 * Date: 2010-7-22
 * Time: 12:22:12
 * Jsonµƒ ”Õº
 */
public class JsonView implements View {
	public static final JsonView instance = new JsonView();
	public static final String JSON_ROOT = "root";
  public String getContentType() {
		return "text/html;charset=UTF-8";
	}

  private JsonView() {}

//	@SuppressWarnings("unchecked")
	public void render(Map model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		Object jsonStr = model.get(JSON_ROOT);
		if (jsonStr == null) {
			return;
		}
		PrintWriter writer = response.getWriter();
		writer.write(jsonStr.toString());
	}
}
