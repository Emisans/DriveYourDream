<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>DriveYourDream - Catalogo</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body style="margin: 0; padding: 0; display: flex; flex-direction: column; min-height: 100vh; overflow-x: hidden;">

    <div style="flex: 1;">
        <header>
            <h1>DriveYourDream</h1>
            <p>Il nostro Catalogo</p>
        </header>

        <nav>
            <a href="${pageContext.request.contextPath}/index.jsp">Home</a>
            <a href="${pageContext.request.contextPath}/CatalogoServlet">Catalogo</a>
           
            <c:choose>
                <c:when test="${empty sessionScope.utente}">
                    <a href="${pageContext.request.contextPath}/login.jsp">Login</a>
                    <a href="${pageContext.request.contextPath}/registrazione.jsp">Registrazione</a>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${sessionScope.utente.ruolo eq 'ADMIN' || sessionScope.utente.ruolo eq 'admin'}">
                            <a href="${pageContext.request.contextPath}/AdminServlet" style="background-color: #0a3d91; color: white; padding: 5px 10px; border-radius: 4px; font-weight: bold;">🛠️ Pannello Admin</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/carrello.jsp">Carrello</a>
                            <a href="${pageContext.request.contextPath}/AreaPersonaleServlet">Area Personale</a>
                        </c:otherwise>
                    </c:choose>
                    <a href="${pageContext.request.contextPath}/LogoutServlet">Logout (${sessionScope.utente.nome})</a>
                </c:otherwise>
            </c:choose>
        </nav>

        <main style="max-width: 1200px; margin: 0 auto; padding: 20px;">
            <h2 style="text-align: center; color: #333; margin-bottom: 30px;">Il nostro parco auto</h2>
            <div style="display: grid; grid-template-columns: repeat(auto-fill, minmax(280px, 1fr)); gap: 20px;">
                <c:forEach var="veicolo" items="${listaVeicoli}">
                    <div style="border: 1px solid #ddd; border-radius: 8px; padding: 15px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); background: white;">
                        <h3 style="margin-top: 0; color: #0a3d91;">${veicolo.marca} ${veicolo.modello}</h3>
                        <p style="margin: 5px 0;"><strong>Sede:</strong> ${veicolo.citta}</p>
                        <p style="margin: 5px 0; color: #28a745; font-size: 1.1rem; font-weight: bold;">${veicolo.prezzoGiornaliero} € / giorno</p>

                        <c:if test="${sessionScope.utente.ruolo eq 'ADMIN' || sessionScope.utente.ruolo eq 'admin'}">
                            <div style="background: #fff3cd; padding: 10px; border-radius: 6px; margin-top: 10px; border: 1px solid #ffeeba; text-align: center;">
                                <a href="${pageContext.request.contextPath}/AdminServlet?azione=toggleAttivo&id=${veicolo.id}&stato=false" style="background-color: #dc3545; color: white; padding: 5px 10px; border-radius: 4px; text-decoration: none; font-size: 0.8rem; font-weight: bold; display: inline-block;">❌ Disattiva Auto</a>
                            </div>
                        </c:if>
                        
                        <form action="${pageContext.request.contextPath}/CarrelloServlet" method="GET" style="border-top: 1px solid #eee; padding-top: 10px; margin-top: 10px;">
                            <input type="hidden" name="azione" value="aggiungi">
                            <input type="hidden" name="id" value="${veicolo.id}">
                            <label style="font-size: 0.85rem; display: block; margin-bottom: 5px;">Giorni noleggio:</label>
                            <input type="number" name="giorni" value="1" min="1" style="width: 60px; padding: 4px; margin-bottom: 8px;">
                            <button type="submit" style="background-color: #28a745; color: white; border: none; padding: 6px 12px; border-radius: 4px; width: 100%; font-weight: bold; cursor: pointer;">Prenota</button>
                        </form>
                    </div>
                </c:forEach>
            </div>
            <c:if test="${empty listaVeicoli}">
                <p style="text-align: center; color: gray; font-style: italic; margin-top: 40px;">Nessun veicolo caricato. Clicca sul tasto "Catalogo" nel menu per attivare il database.</p>
            </c:if>
        </main>
    </div>

    <%-- IMMAGINE CORRETTA ESTESA DA BORDO A BORDO --%>
    <div style="width: 100vw; position: relative; left: 50%; right: 50%; margin-left: -50vw; margin-top: 50px; overflow: hidden; line-height: 0; box-shadow: 0 -4px 10px rgba(0,0,0,0.05);">
        <img src="${pageContext.request.contextPath}/images/car.png" alt="Banner Auto" style="width: 100%; height: 400px; object-fit: fill; display: block; border: none;">
    </div>

</body>
</html>