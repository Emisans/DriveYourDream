<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>DriveYourDream - Registrazione</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">
</head>

<body>

    <header>
        <h1>DriveYourDream</h1>
        <p>Registrati sulla nostra piattaforma</p>
    </header>


    <%-- BARRA DI NAVIGAZIONE DINAMICA E RIGIDA PER I RUOLI --%>

    <nav>

        <a href="${pageContext.request.contextPath}/index.jsp">
            Home
        </a>

        <a href="${pageContext.request.contextPath}/CatalogoServlet">
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
                    <c:when test="${sessionScope.utente.ruolo eq 'ADMIN' || sessionScope.utente.ruolo eq 'admin'}">

                        <a href="${pageContext.request.contextPath}/AdminServlet"
                           style="background-color: #0a3d91;
                                  color: white;
                                  padding: 5px 10px;
                                  border-radius: 4px;
                                  font-weight: bold;">
                            🛠️ Pannello Admin
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

    </nav>


    <main style="max-width: 400px;
                 margin: 40px auto;
                 padding: 25px;
                 background: #f4f7f6;
                 border-radius: 8px;
                 box-shadow: 0 4px 8px rgba(0,0,0,0.1);">


        <h2 style="text-align: center;
                   color: #0a3d91;
                   margin-bottom: 20px;">
            Registrati
        </h2>


        <form  id="formRegistrazione"
        action="${pageContext.request.contextPath}/RegistrazioneServlet"
              method="POST"
              style="display: flex;
                     flex-direction: column;
                     gap: 15px;">


            <%-- NOME --%>
            <div style="display: flex; flex-direction: column;">

                <label for="nome"
                       style="font-weight: bold; margin-bottom: 5px;">
                    Nome:
                </label>

                <input type="text"
                       id="nome"
                       name="nome"
                       placeholder="Inserisci il tuo nome"
                       required
                       style="padding: 8px;
                              border-radius: 4px;
                              border: 1px solid #ccc;">
                              
                              <small id="error-nome" style="color: red;"></small>

            </div>


            <%-- COGNOME --%>
            <div style="display: flex; flex-direction: column;">

                <label for="cognome"
                       style="font-weight: bold; margin-bottom: 5px;">
                    Cognome:
                </label>

                <input type="text"
                       id="cognome"
                       name="cognome"
                       placeholder="Inserisci il tuo cognome"
                       required
                       style="padding: 8px;
                              border-radius: 4px;
                              border: 1px solid #ccc;">
                              
                              <small id="error-cognome" style="color: red;"></small>

            </div>


            <%-- EMAIL --%>
            <div style="display: flex; flex-direction: column;">

                <label for="email"
                       style="font-weight: bold; margin-bottom: 5px;">
                    Email:
                </label>

                <input type="email"
                       id="email"
                       name="email"
                       placeholder="esempio@email.it"
                       required
                       style="padding: 8px;
                              border-radius: 4px;
                              border: 1px solid #ccc;">
                              
                              <small id="error-email" style="color: red;"></small>

            </div>


            <%-- PASSWORD --%>
            <div style="display: flex; flex-direction: column;">

                <label for="password"
                       style="font-weight: bold; margin-bottom: 5px;">
                    Password:
                </label>

                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Inserisci la password"
                       required
                       style="padding: 8px;
                              border-radius: 4px;
                              border: 1px solid #ccc;">
                              
                              <small id="error-password" style="color: red;"></small>

            </div>


            <%-- PULSANTE REGISTRAZIONE --%>
            <button type="submit"
                    style="padding: 10px;
                           background-color: #0a3d91;
                           color: white;
                           border: none;
                           border-radius: 4px;
                           font-weight: bold;
                           cursor: pointer;
                           margin-top: 10px;">
                Crea Account
            </button>

        </form>

    </main>


    <%-- BANNER IN BASSO --%>

    <div style="width: 100%;
                margin-top: 50px;
                overflow: hidden;
                line-height: 0;
                box-shadow: 0 -4px 10px rgba(0,0,0,0.05);">

        <img src="${pageContext.request.contextPath}/images/car.png"
             alt="Banner DriveYourDream"
             style="width: 100%;
                    height: 200px;
                    object-fit: cover;
                    display: block;
                    border: none;">

    </div>
<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>

</html>