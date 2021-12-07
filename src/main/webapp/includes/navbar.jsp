<%@ page import="com.example.ef_g8.Beans.Empleado" %>
<% String currentPage = request.getParameter("currentPage"); %>
<jsp:useBean id="empSession" type="com.example.ef_g8.Beans.Empleado" scope="session"
             class="com.example.ef_g8.Beans.Empleado"/>
<jsp:useBean id="top" type="java.lang.String" scope="session"/>


<nav class="navbar navbar-expand-md navbar-light bg-light">
    <a class="navbar-brand" href="#">T3L3 C4MP30N FUTS4L S.A.C 2019</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
        <ul class="navbar-nav">
            <% if (empSession.getIdEmpleado() > 0) { %>
            <li class="nav-item">
                <a class="nav-link <%=currentPage.equals("cou") ? "active" : ""%>"
                   href="<%=request.getContextPath()%>/CarteleraServlet">
                    Country
                </a>
            <li class="nav-item">
                <a class="nav-link <%=currentPage.equals("loc") ? "active" : ""%>"
                   href="<%=request.getContextPath()%>/ReportesServlet">
                    Location
                </a>
            </li>

            <li class="nav-item">
                <span class="nav-link text-dark">
                    Bienvenido <%=empSession.getNombre()%> <%=empSession.getApellido()%> - Rol: <%=session.getAttribute("rol")%>(<a
                        href="<%=request.getContextPath()%>/LoginServlet?action=logout">Cerrar sesion</a>)
                </span>
            </li>
            <% } else { %>
            <a class="nav-link" style="color: #007bff;" href="<%=request.getContextPath()%>/LoginServlet">(Iniciar
                Sesion)</a>
            <% } %>
        </ul>
    </div>
</nav>
