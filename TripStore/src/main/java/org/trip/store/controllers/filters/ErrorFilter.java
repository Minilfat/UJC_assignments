package org.trip.store.controllers.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter("/*")
public class ErrorFilter implements Filter {


    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) {
        context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        if (context.getAttribute("dberror") != null) {
            System.out.println(context.getContextPath());
            context.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(req,resp);

        } else {
            chain.doFilter(req,resp);
        }
    }

    @Override
    public void destroy() {}
}
