package com.tmri.framework.web.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Service("requestUserResolver")
public class RequestUserResolverImpl implements ApplicationContextAware, RequestUserResolver {
    
    private static ApplicationContext applicationContext;

    protected static String userRequestAttributeName = "user";

    public Object getUser(HttpServletRequest request) {
        return getUser(new ServletWebRequest(request));
    }

    public Object getUser() {
        WebRequest request = RequestContext.getRequestContext().getWebRequest();
        return getUser(request);
    }

    public Object getUser(WebRequest request) {
        return request.getAttribute(getUserRequestAttributeName(), WebRequest.SCOPE_REQUEST);
    }

    public void setUser(Object user) {
        WebRequest request = RequestContext.getRequestContext().getWebRequest();
        if (user != null) {
            request.setAttribute(getUserRequestAttributeName(), user, WebRequest.SCOPE_REQUEST);
        }
    }

    public String getUserRequestAttributeName() {
        return userRequestAttributeName;
    }

    public void setUserRequestAttributeName(String userRequestAttributeName) {
        RequestUserResolverImpl.userRequestAttributeName = userRequestAttributeName;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RequestUserResolverImpl.applicationContext = applicationContext;
    }
    
    public static RequestUserResolver getRequestUserResolver() {
        return (RequestUserResolver) applicationContext.getBean("requestUserResolver");
    }
    
}