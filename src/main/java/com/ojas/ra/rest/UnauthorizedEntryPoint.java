package com.ojas.ra.rest;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author msunil
 *
 */
public class UnauthorizedEntryPoint 
{
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response
            
    ) throws IOException, ServletException
    {
        response.sendError(
                HttpServletResponse.SC_UNAUTHORIZED,
                "Unauthorized: Authentication token was either missing or invalid."
        );
    }
}
