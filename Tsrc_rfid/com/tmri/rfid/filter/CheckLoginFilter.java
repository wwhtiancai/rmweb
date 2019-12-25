package com.tmri.rfid.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tmri.framework.web.support.RequestContext;
import com.tmri.framework.web.support.UserState;
import com.tmri.rfid.util.GsonHelper;
import com.tmri.rfid.util.MapUtilities;
import com.tmri.rfid.util.RestfulSession;
import com.tmri.share.frm.bean.UserSession;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmri.rfid.bean.ClientUser;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.WebUtils;

/**
 * 非网页访问的权限过滤器
 * @author stone
 * @date 2015-12-7 上午8:56:06
 */
public class CheckLoginFilter extends GenericFilterBean {

    private FilterConfig config = null;

    private final static Logger LOG = LoggerFactory.getLogger(CheckLoginFilter.class);

    private List<String> ignoreList;

    private int timeout;

    private final PathMatcher pathMatcher = new AntPathMatcher();

    public FilterConfig getConfig() {
        return config;
    }

    private Set<String> ignoreSuffixes;

    private boolean isIgnore(String currentURL) {
        for (String url : ignoreList) {
            if (pathMatcher.match(url, currentURL)) {
                LOG.debug("url filter : white url list matches : [{}] match [{}] continue", url, currentURL);
                return true;
            }
        }
        return false;
    }

    protected Set getIgnoreSuffixes() {
        if (ignoreSuffixes == null || ignoreSuffixes.isEmpty()) {
            String[] ignoreSuffixList = { ".aif", ".aiff", ".asf", ".avi", ".bin", ".bmp", ".css", ".doc", ".eps", ".gif", ".hqx", ".js", ".jpg", ".jpeg", ".mid", ".midi", ".mov", ".mp3", ".mpg", ".mpeg", ".p65", ".pdf", ".pic", ".pict", ".png", ".ppt", ".psd", ".qxd", ".ram", ".ra", ".rm", ".sea", ".sit", ".stk", ".swf", ".tif", ".tiff", ".txt", ".rtf", ".vob", ".wav", ".wmf", ".xls", ".zip" };
            ignoreSuffixes = new HashSet<String>(Arrays.asList(ignoreSuffixList));
        }
        return ignoreSuffixes;
    }

    @Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String uri = ((HttpServletRequest)servletRequest).getRequestURI();

        if (!shouldProcessURL(request, uri)){
            filterChain.doFilter(request, response);
            return;
        }

        RequestContext brc = new RequestContext();
        brc.setWebRequest(new ServletWebRequest(request, response));

        RequestContext.setRequestContext(brc);

        String currentURL = request.getServletPath();

        if (isIgnore(currentURL)) {
            filterChain.doFilter(request, response);
            return;
        }

        LOG.debug("url filter : current url : [{}]", currentURL);
        String token = request.getParameter("token");
        if (StringUtils.isNotEmpty(token)) {

            boolean flag = false;
            try {
                ClientUser clientUser = RestfulSession.getSession(token);
                if (clientUser != null) {
                    if (!clientUser.getMenuList().contains(currentURL + "?method=" + request.getParameter("method"))) {
                        response.sendRedirect("/rmweb/be/login.rfid?method=noAuthorityMsg");
                        return;
                    }
                    Calendar now = Calendar.getInstance();
                    if (now.getTime().getTime() - clientUser.getLastViewTime().getTime() < timeout) {//如果该用户最后访问时间在30分钟以内
                        flag = true;//允许访问
                        RestfulSession.renewal(token);
                    }
                    UserState.setUser(clientUser.getSysUser());
                }

                if (!flag) {
                    response.sendRedirect("/rmweb/be/login.rfid?method=noLoginMsg");
                    return;
                }
            } catch (Exception e) {
                LOG.error("------> doFilter fail", e);
            }
        } else  {
            UserSession userSession = (UserSession)WebUtils.getSessionAttribute(request, "userSession");
            if (userSession == null) {
                if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                    Map resultMap = MapUtilities.buildMap("resultId", "98", "resultMsg", "用户未登录或登录超时，请重新登录");
                    String result = GsonHelper.getGson().toJson(resultMap);
                    LOG.info("------> CheckLoginFilter.doFilter() ajax调用超时" + result);
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-type", "text/html;charset=UTF-8");
                    PrintWriter out = response.getWriter();
                    out.println(result);
                } else {
                    LOG.info("------> CheckLoginFilter.doFilter() 超时");
                    response.sendRedirect("/rmweb/login.frm?method=logout");
                }
                return;
            }
            UserState.setUser(userSession.getSysuser());
        }
        filterChain.doFilter(request, response);
	}

    public void setIgnoreList(List<String> ignoreList) {
        this.ignoreList = ignoreList;
    }

    public void setTimeout(int timeout) {this.timeout = timeout;}

    protected boolean shouldProcessURL(HttpServletRequest request, String requestURI) {
        int pos = requestURI.lastIndexOf(".");
        if (pos > 0) {
            String suffix = requestURI.substring(pos);
            if (getIgnoreSuffixes().contains(suffix.toLowerCase())) {
                if (LOG.isTraceEnabled()) {
                    LOG.debug("------> CheckLoginFilter.doFilter() ignoring request due to suffix " + requestURI);
                }
                return false;
            }
        }
        return true;
    }
}
