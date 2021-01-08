/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.config;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.vtc.event.common.utils.JsonMapperUtils;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Sep 24, 2019
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        Map<String, String> res = new HashedMap<String, String>();
        res.put("data", "");
        res.put("message", "No permission to do!");
        res.put("status", "06");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(403);
        response.getWriter().write(JsonMapperUtils.toJson(res));

    }

}
