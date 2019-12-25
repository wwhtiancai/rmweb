package com.tmri.framework.web.support;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

public interface RequestUserResolver {

    public Object getUser(HttpServletRequest request);

    public Object getUser();

    public Object getUser(WebRequest request);

    public void setUser(Object user);

    public String getUserRequestAttributeName();

    public void setUserRequestAttributeName(String userRequestAttributeName);

}