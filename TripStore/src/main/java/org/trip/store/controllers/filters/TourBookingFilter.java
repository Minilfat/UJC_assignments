package org.trip.store.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebFilter(urlPatterns = "/tours/order")
public class TourBookingFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) {}

    @Override
    public void doFilter(ServletRequest req,
                         ServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {


        HttpServletRequest httpReq  = (HttpServletRequest) req;
        HttpServletResponse httpResp = (HttpServletResponse) resp;

        httpResp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
        httpResp.setDateHeader("Expires", 0); // Proxies.

        if (httpReq.getSession().getAttribute("logged") == null) {

            httpResp.sendRedirect(httpReq.getContextPath() + "/login");
            return;
        } else {
            httpReq.getRequestDispatcher("/tours/order").forward(req,resp);
        }
    }

    @Override
    public void destroy() {}
}
