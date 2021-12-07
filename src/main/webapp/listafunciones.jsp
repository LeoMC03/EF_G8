<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.ef_g8.Beans.Cartelera" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listafunciones" type="java.util.ArrayList<com.example.ef_g8.Beans.Cartelera>" scope="request"/>
<jsp:useBean id="textoBusqueda" scope="request" type="java.lang.String" class="java.lang.String"/>
<!DOCTYPE html>
<html>
    <head>
        <title>Lista empleados</title>
        <jsp:include page="/includes/headCss.jsp"/>
    </head>
    <body>
        <div class='container'>
            <jsp:include page="/includes/navbar.jsp">
                <jsp:param name="currentPage" value="emp"/>
            </jsp:include>
            <div class="row mb-5 mt-4">
                <div class="col-md-7">
                    <h1>Lista de empleados</h1>
                </div>
                <% if (session.getAttribute("top") != "- Top 3" || session.getAttribute("top") != "- Top 4" ) {%>

                <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
                    <a href="<%= request.getContextPath()%>/cartelera?action=agregar" class="btn btn-primary">
                        Agregar nueva funcion</a>
                </div>
                <% } %>
            </div>
            <jsp:include page="/includes/infoMsgs.jsp"/>
            <table class="table">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Cadena</th>
                        <th>Cine</th>
                        <th>Pelicula</th>
                        <th>Horario</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int i = 1;
                        for (Cartelera c : listafunciones) {
                    %>
                    <tr>
                        <td><%= i%>
                        </td>
                        <td><%=c.getCine().getCadena().getNombreComercial()%>
                        </td>
                        <td><%=c.getCine().getNombre()%>
                        </td>
                        <td><%=c.getPelicula().getNombre()%>
                            <%if(c.getTresD()==1){%>
                            -3D
                            <%}%>
                            <%if(c.getDoblada()==1){%>
                            Doblada
                            <%}%>
                            <%if(c.getSubtitulada()==1){%>
                            Subtitulada
                            <%}%>
                        </td>
                        <td><%=c.getHorario()%>
                        </td>
                        <% if (session.getAttribute("top") != "- Top 2" && session.getAttribute("top") != "- Top 4") {%>
                        <td>
                            <a href="<%=request.getContextPath()%>/cartelera?action=editar&id=<%= c.getIdCartelera()%>"
                               type="button" class="btn btn-primary">
                                <i class="bi bi-pencil-square"></i>
                            </a>
                        </td>
                        <%}%>
                        <% if (session.getAttribute("top") != "- Top 3" && session.getAttribute("top") != "- Top 4") {%>
                        <td>
                            <a onclick="return confirm('¿Estas seguro de borrar?');"
                               href="<%=request.getContextPath()%>/cartelera?action=borrar&id=<%= c.getIdCartelera()%>"
                               type="button" class="btn btn-danger">
                                <i class="bi bi-trash"></i>
                            </a>
                        </td>
                        <%}%>
                    </tr>
                    <%
                            i++;
                        }
                    %>
                </tbody>
            </table>
            <jsp:include page="/includes/footer.jsp"/>
        </div>
    </body>
</html>
