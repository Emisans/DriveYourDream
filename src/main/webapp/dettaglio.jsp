<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
           prefix="c" %>

<!DOCTYPE html>

<html lang="it">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>Dettaglio Veicolo - DriveYourDream</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>


<body>


    <!-- HEADER E NAV COMUNI -->

    <jsp:include page="/WEB-INF/fragments/header.jsp" />

    <jsp:include page="/WEB-INF/fragments/nav.jsp" />



    <main style="
        max-width: 800px;
        margin: 40px auto;
        padding: 30px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    ">


        <div style="
            display: flex;
            gap: 30px;
            flex-wrap: wrap;
        ">


            <!-- DATI VEICOLO -->

            <div style="
                flex: 1;
                min-width: 280px;
            ">


                <h2 style="
                    color: #0a3d91;
                    margin-bottom: 15px;
                ">

                    ${veicolo.marca}
                    ${veicolo.modello}

                </h2>


                <p style="
                    font-size: 1.1rem;
                    margin-bottom: 8px;
                ">

                    <strong>Sede:</strong>

                    ${veicolo.citta}

                </p>


                <p style="
                    font-size: 1.1rem;
                    margin-bottom: 8px;
                ">

                    <strong>Tariffa Giornaliera:</strong>

                    ${veicolo.prezzoGiornaliero} €
                    / giorno

                </p>


                <p style="
                    font-size: 1.1rem;
                    margin-bottom: 25px;
                ">

                    <strong>IVA Applicata:</strong>

                    ${veicolo.iva}%

                </p>



                <!-- AGGIUNTA AL CARRELLO -->

                <form action="${pageContext.request.contextPath}/CarrelloServlet"
                      method="GET"
                      style="
                          background: #f8f9fa;
                          padding: 20px;
                          border-radius: 6px;
                          border: 1px solid #eee;
                      ">


                    <input type="hidden"
                           name="azione"
                           value="aggiungi">


                    <input type="hidden"
                           name="id"
                           value="${veicolo.id}">


                    <label for="giorni">

                        Seleziona i giorni di noleggio:

                    </label>


                    <input type="number"
                           id="giorni"
                           name="giorni"
                           value="1"
                           min="1"
                           max="90"
                           required>


                    <button type="submit">

                        Aggiungi al Carrello

                    </button>


                </form>


            </div>


        </div>



        <!-- TORNA AL CATALOGO -->

        <div style="
            margin-top: 30px;
            border-top: 1px solid #eee;
            padding-top: 20px;
        ">


            <a href="${pageContext.request.contextPath}/search"
               style="
                   color: #0a3d91;
                   text-decoration: none;
                   font-weight: bold;
               ">

                ← Torna al Catalogo

            </a>


        </div>


    </main>


</body>

</html>