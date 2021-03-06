package com.example.ef_g8.Controllers;


import com.example.ef_g8.Beans.Empleado;
import com.example.ef_g8.Daos.EmpleadoDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action") == null ? "loginform" : request.getParameter("action");
        HttpSession session = request.getSession();

        switch (action) {
            case "loginform":
                Empleado empSession = (Empleado) session.getAttribute("empSession");
                if (empSession != null && empSession.getIdEmpleado() > 0) {
                    response.sendRedirect(request.getContextPath() + "/EmpleadoServlet");
                } else {
                    RequestDispatcher view = request.getRequestDispatcher("login/loginForm.jsp");
                    view.forward(request, response);
                }
                break;
            case "logout":
                session.invalidate();
                response.sendRedirect(request.getContextPath());
                break;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("inputDNI");
        String password = request.getParameter("inputPassword");

        if (username == null || password == null) {
            request.setAttribute("err", "El usuario o password no pueden ser vacíos");
            RequestDispatcher view = request.getRequestDispatcher("login/loginForm.jsp");
            view.forward(request, response);
        }else{
            EmpleadoDao empDao = new EmpleadoDao();
            Empleado emp = empDao.validarEmpleadoUsername(username);
            Empleado empleado= empDao.obtenerEmpleado(emp.getIdEmpleado());
            Boolean es=empDao.validarContrasenia(BigDecimal.valueOf(Double.parseDouble(password)),empleado);

            if (emp != null && es) {
                HttpSession session = request.getSession();
                session.setAttribute("empSession", emp);
                session.setMaxInactiveInterval(10 * 60); // 10 minutos
                response.sendRedirect(request.getContextPath() + "/EmpleadoServlet");

                int rol = emp.getRoles().get(0).getIdRol();

                switch (rol) {
                    case 1:
                        session.setAttribute("adminSession", emp);
                        response.sendRedirect(request.getContextPath() + "/ReportesServlet");
                        break;
                    case 2:
                        session.setAttribute("gestorSession",emp );
                        response.sendRedirect(request.getContextPath() + "/CarteleraServlet");
                        break;
                    case 3:
                        session.setAttribute("vendedorSession",emp );
                        response.sendRedirect(request.getContextPath() + "/CarteleraServlet");
                        break;
                    default:
                        System.out.println("ocurrio un error en el switch del login");
                }

            } else {
                request.setAttribute("err", "El usuario o password no existen");
                RequestDispatcher view = request.getRequestDispatcher("login/loginForm.jsp");
                view.forward(request, response);
            }
        }
    }
}
