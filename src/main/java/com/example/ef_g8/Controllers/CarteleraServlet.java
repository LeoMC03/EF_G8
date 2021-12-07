package com.example.ef_g8.Controllers;

import com.example.ef_g8.Beans.Cartelera;
import com.example.ef_g8.Daos.CarteleraDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Servlet", value = "/cartelera")
public class CarteleraServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        String id = request.getParameter("id");
        int idInt = Integer.parseInt(id);
        RequestDispatcher view;
        CarteleraDao carteleraDao = new CarteleraDao();
        switch (action) {
            case "lista":

                request.setAttribute("listafunciones", carteleraDao.listaFuncions(idInt));
                view = request.getRequestDispatcher("listafunciones.jsp");
                view.forward(request, response);
                break;
            case "agregar":
                if (session.getAttribute("top") != "- Top 4" && session.getAttribute("top") != "- Top 3" && session.getAttribute("top") != "- Top 1") {
                    response.sendRedirect("EmployeeServlet");
                } else {
                    request.setAttribute("listaTrabajos", carteleraDao.listaCines());
                    request.setAttribute("listaDepartamentos", carteleraDao.listaPeliculas());

                    view = request.getRequestDispatcher("agregarfuncion.jsp");
                    view.forward(request, response);
                }
                break;
            case "editar":

                if (request.getParameter("id") != null) {
                    String id2 = request.getParameter("id");

                    int id2Int = 0;
                    try {
                        id2Int = Integer.parseInt(id2);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EmployeeServlet");
                    }

                    Cartelera cartelera = carteleraDao.obtenerFuncion(idInt, id2Int);

                    if (cartelera == null || session.getAttribute("top").equals("- Top 2") || session.getAttribute("top").equals("- Top 4")) {
                        response.sendRedirect("EmployeeServlet");
                    } else {
                        request.setAttribute("listacines", carteleraDao.listaCines());
                        request.setAttribute("listapeliculas", carteleraDao.listaPeliculas());
                        view = request.getRequestDispatcher("agregarfuncion.jsp");
                        view.forward(request, response);
                    }

                } else {
                    response.sendRedirect("EmployeeServlet");
                }

                break;
            case "borrar":
                //if (session.getAttribute("top") != "- Top 4" && session.getAttribute("top") != "- Top 3") {
                if (request.getParameter("id") != null) {
                    String idc = request.getParameter("id");
                    int idcInt = Integer.parseInt(idc);
                    int employeeId = 0;
                    try {
                        employeeId = Integer.parseInt(id, idcInt);
                    } catch (NumberFormatException ex) {
                        response.sendRedirect("EmployeeServlet");
                    }
                    Cartelera cartelera = carteleraDao.obtenerFuncion(idInt, idcInt);

                    if (cartelera != null) {
                        carteleraDao.borrar(idInt);
                        request.getSession().setAttribute("err", "Funcion borrado exitosamente");
                        response.sendRedirect(request.getContextPath() + "/EmployeeServlet");
                    }
                } else {
                    response.sendRedirect("EmployeeServlet");
                }
                //}else{
                //  response.sendRedirect("EmployeeServlet");
                //}
                break;
        }


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
