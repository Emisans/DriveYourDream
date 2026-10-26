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

    <title>DriveYourDream - Profilo</title>

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


    <jsp:include page="/WEB-INF/fragments/header.jsp" />

    <jsp:include page="/WEB-INF/fragments/nav.jsp" />


    <main style="
        max-width: 1000px;
        margin: 30px auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.05);
    ">


        <h2 style="
            color: #0a3d91;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
        ">

            I tuoi Dati Profilo

        </h2>


        <div style="
            margin: 20px 0;
            font-size: 1.1rem;
            line-height: 1.6;
        ">


            <p>
                <strong>Nome:</strong>
                ${sessionScope.utente.nome}
            </p>


            <p>
                <strong>Cognome:</strong>
                ${sessionScope.utente.cognome}
            </p>


            <p>
                <strong>Email / Username:</strong>
                ${sessionScope.utente.email}
            </p>


            <p>

                <strong>Ruolo Account:</strong>

                <span style="
                    text-transform: uppercase;
                    font-weight: bold;
                    color: #0a3d91;
                ">

                    ${sessionScope.utente.ruolo}

                </span>

            </p>


        </div>



        <h2 style="
            color: #0a3d91;
            border-bottom: 2px solid #eee;
            padding-bottom: 10px;
            margin-top: 40px;
        ">

            Storico Ordini e Noleggi

        </h2>



        <div style="margin-top: 20px;">


            <c:choose>


                <c:when test="${not empty requestScope.storicoOrdini}">


                    <table style="
                        width: 100%;
                        border-collapse: collapse;
                        margin-top: 15px;
                    ">


                        <thead>


                            <tr style="
                                background-color: #0a3d91;
                                color: white;
                                text-align: left;
                            ">

                                <th style="padding: 12px;">
                                    ID
                                </th>

                                <th style="padding: 12px;">
                                    Data Prenotazione
                                </th>

                                <th style="padding: 12px;">
                                    Veicoli Noleggiati
                                </th>

                                <th style="padding: 12px;">
                                    Giorni
                                </th>

                                <th style="padding: 12px;">
                                    Prezzo/Giorno
                                </th>

                                <th style="padding: 12px;">
                                    Totale Complessivo
                                </th>

                            </tr>


                        </thead>


                        <tbody>


                            <c:forEach var="ordine"
                                       items="${requestScope.storicoOrdini}">


                                <tr>


                                    <td style="padding: 12px;">
                                        #${ordine.id}
                                    </td>


                                    <td style="padding: 12px;">
                                        ${ordine.dataOrdine}
                                    </td>


                                    <td style="padding: 12px;">


                                        <c:forEach var="item"
                                                   items="${ordine.elementi}">

                                            <div>

                                                🚗

                                                <strong>
                                                    ${item.veicolo.marca}
                                                </strong>

                                                ${item.veicolo.modello}

                                                (${item.veicolo.citta})

                                            </div>

                                        </c:forEach>


                                    </td>


                                    <td style="padding: 12px;">


                                        <c:forEach var="item"
                                                   items="${ordine.elementi}">

                                            <div>
                                                ${item.giorni} gg
                                            </div>

                                        </c:forEach>


                                    </td>


                                    <td style="padding: 12px;">


                                        <c:forEach var="item"
                                                   items="${ordine.elementi}">

                                            <div>
                                                ${item.prezzoStorico} €
                                            </div>

                                        </c:forEach>


                                    </td>


                                    <td style="
                                        padding: 12px;
                                        font-weight: bold;
                                        color: #28a745;
                                    ">

                                        ${ordine.totaleComplessivo} €

                                    </td>


                                </tr>


                            </c:forEach>


                        </tbody>


                    </table>


                </c:when>



                <c:otherwise>


                    <p style="
                        color: gray;
                        font-style: italic;
                        text-align: center;
                        padding: 20px;
                    ">

                        Nessun acquisto o noleggio effettuato finora.

                    </p>


                </c:otherwise>


            </c:choose>


        </div>


    </main>


</div>


<jsp:include page="/WEB-INF/fragments/footer.jsp" />


</body>

</html>