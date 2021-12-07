package com.example.ef_g8.Filters;

import com.example.ef_g8.Beans.Empleado;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",servletNames = {"EmpleadoServlet","CarteleraServlet"})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        Empleado em = (Empleado) session.getAttribute("empleadoSession");

        if (em != null && em.getIdEmpleado() != 0) {
            chain.doFilter(request, response);
        } else {
            response.sendRedirect(request.getContextPath());
        }
    }
}
