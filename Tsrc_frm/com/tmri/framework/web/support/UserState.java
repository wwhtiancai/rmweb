package com.tmri.framework.web.support;

import com.tmri.share.frm.bean.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;

@Component("userState")
public class UserState {
    
    public static SysUser getUser(HttpServletRequest request) {
        return (SysUser) RequestUserResolverImpl.getRequestUserResolver().getUser(request);
    }
    
    public static SysUser getUser(WebRequest request) {
        return (SysUser) RequestUserResolverImpl.getRequestUserResolver().getUser(request);
    }
    
    public static SysUser getUser() {
        if (RequestContext.getRequestContext() == null
                || RequestContext.getRequestContext().getWebRequest() == null) {
            return null;
        }
        return (SysUser) RequestUserResolverImpl.getRequestUserResolver().getUser();
    }
    
    public static void setUser(SysUser user) {
        RequestUserResolverImpl.getRequestUserResolver().setUser(user);
    }

}