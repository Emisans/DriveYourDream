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

    <title>DriveYourDream - Carrello</title>

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
        max-width: 800px;
        margin: 30px auto;
        padding: 20px;
        background: white;
        border-radius: 8px;
        box-shadow: 0 4px 10px rgba(0,0,0,0.05);
    ">


        <h2 style="
            text-align: center;
            color: #0a3d91;
        ">

            Articoli nel tuo carrello

        </h2>


        <c:choose>


            <c:when test="${not empty sessionScope.carrello
                           && not empty sessionScope.carrello.elementi}">


                <table style="
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                ">


                    <thead>

                        <tr style="
                            background-color: #f4f7f6;
                            text-align: left;
                        ">

                            <th style="padding: 12px;">
                                Veicolo
                            </th>

                            <th style="padding: 12px;">
                                Giorni
                            </th>

                            <th style="padding: 12px;">
                                Prezzo Totale
                            </th>

                            <th style="padding: 12px;">
                                Azioni
                            </th>

                        </tr>

                    </thead>


                    <tbody>


                        <c:forEach var="item"
                                   items="${sessionScope.carrello.elementi}">


                            <tr>


                                <td style="
                                    padding: 12px;
                                    border-bottom: 1px solid #ddd;
                                ">

                                    <strong>
                                        ${item.veicolo.marca}
                                    </strong>

                                    ${item.veicolo.modello}

                                </td>



                                <td style="
                                    padding: 12px;
                                    border-bottom: 1px solid #ddd;
                                ">


                                    <form action="${pageContext.request.contextPath}/CarrelloServlet"
                                          method="POST"
                                          style="
                                            display: flex;
                                            gap: 5px;
                                            align-items: center;
                                          ">


                                        <input type="hidden"
                                               name="azione"
                                               value="modifica">


                                        <input type="hidden"
                                               name="id"
                                               value="${item.veicolo.id}">


                                        <input type="number"
                                               name="giorni"
                                               value="${item.giorni}"
                                               min="1"
                                               required
                                               style="
                                                    width: 55px;
                                                    padding: 5px;
                                               ">


                                        <button type="submit"
                                                style="
                                                    padding: 5px 8px;
                                                    cursor: pointer;
                                                ">

                                            Aggiorna

                                        </button>


                                    </form>


                                </td>



                                <td style="
                                    padding: 12px;
                                    border-bottom: 1px solid #ddd;
                                    font-weight: bold;
                                    color: #28a745;
                                ">

                                    ${item.veicolo.prezzoGiornaliero
                                      * item.giorni} €

                                </td>



                                <td style="
                                    padding: 12px;
                                    border-bottom: 1px solid #ddd;
                                ">


                                    <a href="${pageContext.request.contextPath}/CarrelloServlet?azione=rimuovi&id=${item.veicolo.id}"
                                       style="
                                            color: #dc3545;
                                            text-decoration: none;
                                            font-weight: bold;
                                       ">

                                        ❌ Rimuovi

                                    </a>


                                </td>


                            </tr>


                        </c:forEach>


                    </tbody>

                </table>



                <div style="
                    text-align: right;
                    margin-top: 20px;
                ">


                    <form action="${pageContext.request.contextPath}/CheckoutServlet"
                          method="POST">


                        <button type="submit"
                                style="
                                    background-color: #28a745;
                                    color: white;
                                    padding: 12px 25px;
                                    border: none;
                                    border-radius: 4px;
                                    font-weight: bold;
                                    cursor: pointer;
                                    font-size: 1.1rem;
                                ">

                            Conferma ed Invia Ordine

                        </button>


                    </form>


                </div>


            </c:when>



            <c:otherwise>


                <p style="
                    text-align: center;
                    color: gray;
                    font-style: italic;
                    padding: 20px;
                ">

                    Il tuo carrello è attualmente vuoto.

                </p>


            </c:otherwise>


        </c:choose>


    </main>


</div>


<jsp:include page="/WEB-INF/fragments/footer.jsp" />


</body>

</html>