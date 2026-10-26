<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>


<nav>

    <a href="${pageContext.request.contextPath}/index.jsp">
        Home
    </a>


    <a href="${pageContext.request.contextPath}/search">
        Catalogo
    </a>


    <c:choose>


        <%-- UTENTE NON LOGGATO --%>

        <c:when test="${empty sessionScope.utente}">

            <a href="${pageContext.request.contextPath}/login.jsp">
                Login
            </a>

            <a href="${pageContext.request.contextPath}/registrazione.jsp">
                Registrazione
            </a>

        </c:when>


        <%-- UTENTE LOGGATO --%>

        <c:otherwise>


            <c:choose>


                <%-- ADMIN --%>

                <c:when test="${sessionScope.utente.ruolo eq 'ADMIN'
                               || sessionScope.utente.ruolo eq 'admin'}">

                    <a href="${pageContext.request.contextPath}/AdminServlet">
                        Pannello Admin
                    </a>

                </c:when>


                <%-- CLIENTE --%>

                <c:otherwise>

                    <a href="${pageContext.request.contextPath}/carrello.jsp">
                        Carrello
                    </a>

                    <a href="${pageContext.request.contextPath}/AreaPersonaleServlet">
                        Area Personale
                    </a>

                </c:otherwise>


            </c:choose>


            <a href="${pageContext.request.contextPath}/LogoutServlet">
                Logout (${sessionScope.utente.nome})
            </a>


        </c:otherwise>


    </c:choose>

