<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>

<html lang="it">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>DriveYourDream - Home</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>


<body style="
    margin: 0;
    padding: 0;
    display: flex;
    flex-direction: column;
    min-height: 100vh;
    overflow-x: hidden;
">


<div style="flex: 1;">


    <!-- HEADER COMUNE -->

    <jsp:include page="/WEB-INF/fragments/header.jsp" />


    <!-- NAV COMUNE -->

    <jsp:include page="/WEB-INF/fragments/nav.jsp" />



    <!-- RICERCA -->

    <section style="
        max-width: 800px;
        margin: 30px auto;
        padding: 20px;
        background: #f4f7f6;
        border-radius: 8px;
    ">


        <h2 style="
            text-align: center;
            color: #0a3d91;
        ">

            Trova l'auto perfetta per il tuo viaggio

        </h2>


        <form action="${pageContext.request.contextPath}/search"
              method="GET"
              style="
                display: flex;
                flex-wrap: wrap;
                gap: 15px;
                justify-content: center;
              ">


            <!-- CITTA -->

            <div style="
                display: flex;
                flex-direction: column;
                min-width: 180px;
            ">

                <label>Città:</label>

                <input type="text"
                       name="citta"
                       value="${param.citta}"
                       placeholder="Es. Roma, Milano">

            </div>



            <!-- MARCA + AJAX -->

            <div style="
                display: flex;
                flex-direction: column;
                min-width: 180px;
                position: relative;
            ">

                <label>Marca:</label>


                <input type="text"
                       id="marcaRicerca"
                       name="marca"
                       value="${param.marca}"
                       autocomplete="off"
                       placeholder="Es. Fiat, Audi">


                <div id="suggerimentiMarca"
                     style="
                        position: absolute;
                        top: 45px;
                        left: 0;
                        right: 0;
                        background: white;
                        border: 1px solid #ccc;
                        z-index: 100;
                        display: none;
                     ">
                </div>

            </div>



            <!-- PREZZO -->

            <div style="
                display: flex;
                flex-direction: column;
                min-width: 180px;
            ">

                <label>
                    Prezzo Massimo (€/giorno):
                </label>

                <input type="number"
                       name="prezzoMax"
                       value="${param.prezzoMax}"
                       min="0"
                       placeholder="Es. 100">

            </div>


            <div style="
                display: flex;
                align-items: flex-end;
            ">

                <button type="submit">
                    Filtra
                </button>

            </div>


        </form>

    </section>



   <!-- VEICOLI -->

<main style="
    max-width: 1200px;
    margin: 0 auto;
    padding: 20px;
">

    <h2 style="
        text-align: center;
        color: #333;
    ">
        Veicoli Disponibili
    </h2>


    <div style="
        display: grid;
        grid-template-columns:
            repeat(auto-fill, minmax(280px, 1fr));
        gap: 20px;
    ">


        <c:forEach var="veicolo"
                   items="${risultati}">


            <div style="
                border: 1px solid #ddd;
                border-radius: 8px;
                padding: 15px;
                background: white;
            ">


                <h3>
                    ${veicolo.marca}
                    ${veicolo.modello}
                </h3>


                <p>
                    <strong>Sede:</strong>
                    ${veicolo.citta}
                </p>


                <p>
                    ${veicolo.prezzoGiornaliero}
                    € / giorno
                </p>



                <!-- DETTAGLI -->

                <a href="${pageContext.request.contextPath}/DettaglioServlet?id=${veicolo.id}"
                   style="
                       display: inline-block;
                       background-color: #ffffff;
                       color: #0a3d91;
                       border: 1px solid #0a3d91;
                       padding: 8px 15px;
                       border-radius: 6px;
                       text-decoration: none;
                       font-weight: bold;
                       margin-bottom: 15px;
                   ">

                    Dettagli

                </a>



                <!-- ADMIN -->

                <c:if test="${sessionScope.utente.ruolo eq 'ADMIN'
                            || sessionScope.utente.ruolo eq 'admin'}">

                    <div style="margin-bottom: 15px;">

                        <a href="${pageContext.request.contextPath}/AdminServlet?azione=toggleAttivo&id=${veicolo.id}&stato=false">

                            Disattiva Auto

                        </a>

                    </div>

                </c:if>



                <!-- PRENOTAZIONE -->

                <form action="${pageContext.request.contextPath}/CarrelloServlet"
                      method="GET">


                    <input type="hidden"
                           name="azione"
                           value="aggiungi">


                    <input type="hidden"
                           name="id"
                           value="${veicolo.id}">


                    <label>
                        Giorni noleggio:
                    </label>


                    <input type="number"
                           name="giorni"
                           value="1"
                           min="1"
                           required>


                    <button type="submit">

                        Prenota

                    </button>


                </form>


            </div>


        </c:forEach>



        <c:if test="${empty risultati}">

            <p>
                Nessun veicolo trovato.
            </p>

        </c:if>


    </div>


</main>

</div>


<!-- FOOTER/BANNER COMUNE -->

<jsp:include page="/WEB-INF/fragments/footer.jsp" />


<script>

    const contextPath =
        "${pageContext.request.contextPath}";

</script>


<script src="${pageContext.request.contextPath}/js/main.js">
</script>


</body>

</html>