package com.tmri.rfid.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by Joey on 2016-04-28.
 */
public class AjaxRequestFilter extends OncePerRequestFilter {

    public String filter(HttpServletRequest request, String input) {
        String ret = input;
        if (input == null || input.trim().equals("(null)")) {
            ret = null;
            return ret;
        }
        final String userAgent = request.getHeader("User-Agent");
        final String method = request.getMethod();
        //此处将浏览器端通过Ajax GET请求传入的中文参数解码，浏览器默认使用UTF-8进行编码
        //浏览器端采用二次编码方式，对中文参数进行编码
        if ((request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest"))
            || request.getRequestURI().indexOf("/ex/") > -1) {
            try {
                ret = URLDecoder.decode(input, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(new HttpServletRequestWrapper(request) {
            @Override
            public String getParameter(String name) {
                String value = super.getParameter(name);
                return filter(this, value);
            }

            @Override
            public String[] getParameterValues(String name) {
                String[] values = super.getParameterValues(name);
                if (values == null) {
                    return null;
                }
                for (int i = 0; i < values.length; i++) {
                    values[i] = filter(this, values[i]);
                }
                return values;
            }

        }, response);

    }
}
