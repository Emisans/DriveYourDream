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

    <title>Dashboard Admin - DriveYourDream</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


    <style>

        .container-admin {
            max-width: 1200px;
            margin: 30px auto;
            padding: 20px;
        }


        /* MESSAGGIO OPERAZIONE RIUSCITA */

        .messaggio-successo {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
            padding: 12px 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: bold;
        }


        /* MESSAGGIO ERRORE */

        .messaggio-errore {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
            padding: 12px 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: bold;
        }


        .sezione-admin {
            background-color: white;
            padding: 25px;
            margin-bottom: 30px;
            border-radius: 8px;
            box-shadow: 0 3px 10px rgba(0,0,0,0.10);
        }


        .sezione-admin h2 {
            color: #0a3d91;
            margin-top: 0;
        }


        .form-admin {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            align-items: flex-end;
        }


        .campo-admin {
            display: flex;
            flex-direction: column;
            min-width: 160px;
            flex: 1;
        }


        .campo-admin label {
            font-weight: bold;
            margin-bottom: 5px;
        }


        .campo-admin input {
            padding: 8px;
        }


        .tabella-admin {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }


        .tabella-admin th,
        .tabella-admin td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
            text-align: left;
        }


        .tabella-admin th {
            background-color: #0a3d91;
            color: white;
        }


        .azioni-veicolo {
            display: flex;
            flex-wrap: wrap;
            gap: 8px;
        }


        .azioni-veicolo a {
            text-decoration: none;
            font-weight: bold;
        }


        @media (max-width: 768px) {

            .form-admin {
                flex-direction: column;
                align-items: stretch;
            }


            .campo-admin {
                width: 100%;
            }


            .tabella-admin {
                display: block;
                overflow-x: auto;
            }
        }

    </style>

</head>


<body>


    <jsp:include page="/WEB-INF/fragments/header.jsp" />

    <jsp:include page="/WEB-INF/fragments/nav.jsp" />


    <main class="container-admin">


        <h1>
            Pannello Amministratore
        </h1>


        <!-- ============================================= -->
        <!-- MESSAGGI DI CONFERMA                          -->
        <!-- ============================================= -->


        <c:if test="${param.messaggio eq 'aggiunto'}">

            <div class="messaggio-successo">
                ✓ Veicolo aggiunto correttamente.
            </div>

        </c:if>


        <c:if test="${param.messaggio eq 'modificato'}">

            <div class="messaggio-successo">
                ✓ Veicolo modificato correttamente.
            </div>

        </c:if>


        <c:if test="${param.messaggio eq 'attivato'}">

            <div class="messaggio-successo">
                ✓ Veicolo attivato correttamente.
            </div>

        </c:if>


        <c:if test="${param.messaggio eq 'disattivato'}">

            <div class="messaggio-successo">
                ✓ Veicolo disattivato correttamente.
            </div>

        </c:if>


        <!-- EVENTUALE ERRORE FILTRO -->

        <c:if test="${not empty erroreFiltro}">

            <div class="messaggio-errore">

                ${erroreFiltro}

            </div>

        </c:if>



        <!-- ============================================= -->
        <!-- MODIFICA VEICOLO                              -->
        <!-- ============================================= -->


        <c:if test="${not empty veicoloDaModificare}">


            <section class="sezione-admin">


                <h2>
                    Modifica Veicolo
                </h2>


                <form id="formVeicolo"
                      class="form-admin"
                      action="${pageContext.request.contextPath}/AdminServlet"
                      method="POST">


                    <input type="hidden"
                           name="azione"
                           value="modifica">


                    <input type="hidden"
                           name="id"
                           value="${veicoloDaModificare.id}">



                    <div class="campo-admin">

                        <label>Marca</label>

                        <input type="text"
                               id="marca"
                               name="marca"
                               value="${veicoloDaModificare.marca}"
                               required>

                        <small id="error-marca"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Modello</label>

                        <input type="text"
                               id="modello"
                               name="modello"
                               value="${veicoloDaModificare.modello}"
                               required>

                        <small id="error-modello"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Prezzo giornaliero</label>

                        <input type="number"
                               id="prezzo"
                               name="prezzo"
                               value="${veicoloDaModificare.prezzoGiornaliero}"
                               step="0.01"
                               min="0"
                               required>

                        <small id="error-prezzo"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Città</label>

                        <input type="text"
                               id="citta"
                               name="citta"
                               value="${veicoloDaModificare.citta}"
                               required>

                        <small id="error-citta"></small>

                    </div>



                    <div class="campo-admin">

                        <label>IVA</label>

                        <input type="number"
                               id="iva"
                               name="iva"
                               value="${veicoloDaModificare.iva}"
                               required>

                        <small id="error-iva"></small>

                    </div>


                    <button type="submit">
                        Salva Modifiche
                    </button>


                </form>


            </section>


        </c:if>



        <!-- ============================================= -->
        <!-- AGGIUNGI VEICOLO                              -->
        <!-- ============================================= -->


        <c:if test="${empty veicoloDaModificare}">


            <section class="sezione-admin">


                <h2>
                    Aggiungi Nuovo Veicolo
                </h2>


                <form id="formVeicolo"
                      class="form-admin"
                      action="${pageContext.request.contextPath}/AdminServlet"
                      method="POST">


                    <input type="hidden"
                           name="azione"
                           value="aggiungi">



                    <div class="campo-admin">

                        <label>Marca</label>

                        <input type="text"
                               id="marca"
                               name="marca"
                               placeholder="Es. Fiat"
                               required>

                        <small id="error-marca"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Modello</label>

                        <input type="text"
                               id="modello"
                               name="modello"
                               placeholder="Es. Panda"
                               required>

                        <small id="error-modello"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Prezzo giornaliero</label>

                        <input type="number"
                               id="prezzo"
                               name="prezzo"
                               step="0.01"
                               min="0"
                               placeholder="Es. 50"
                               required>

                        <small id="error-prezzo"></small>

                    </div>



                    <div class="campo-admin">

                        <label>Città</label>

                        <input type="text"
                               id="citta"
                               name="citta"
                               placeholder="Es. Salerno"
                               required>

                        <small id="error-citta"></small>

                    </div>



                    <div class="campo-admin">

                        <label>IVA</label>

                        <input type="number"
                               id="iva"
                               name="iva"
                               placeholder="22"
                               required>

                        <small id="error-iva"></small>

                    </div>


                    <button type="submit">
                        Aggiungi Veicolo
                    </button>


                </form>


            </section>


        </c:if>



        <!-- ============================================= -->
        <!-- LISTA VEICOLI                                 -->
        <!-- ============================================= -->


        <section class="sezione-admin">


            <h2>
                Gestione Veicoli
            </h2>


            <table class="tabella-admin">


                <thead>

                    <tr>

                        <th>ID</th>

                        <th>Marca</th>

                        <th>Modello</th>

                        <th>Prezzo</th>

                        <th>Città</th>

                        <th>IVA</th>

                        <th>Stato</th>

                        <th>Azioni</th>

                    </tr>

                </thead>


                <tbody>


                    <c:forEach var="veicolo"
                               items="${listaVeicoliAdmin}">


                        <tr>


                            <td>
                                ${veicolo.id}
                            </td>


                            <td>
                                ${veicolo.marca}
                            </td>


                            <td>
                                ${veicolo.modello}
                            </td>


                            <td>
                                ${veicolo.prezzoGiornaliero} €
                            </td>


                            <td>
                                ${veicolo.citta}
                            </td>


                            <td>
                                ${veicolo.iva}%
                            </td>


                            <td>

                                <c:choose>

                                    <c:when test="${veicolo.attivo}">
                                        Attivo
                                    </c:when>

                                    <c:otherwise>
                                        Non attivo
                                    </c:otherwise>

                                </c:choose>

                            </td>


                            <td>


                                <div class="azioni-veicolo">


                                    <a href="${pageContext.request.contextPath}/AdminServlet?azione=modifica&id=${veicolo.id}">

                                        Modifica

                                    </a>



                                    <c:choose>


                                        <c:when test="${veicolo.attivo}">

                                            <a href="${pageContext.request.contextPath}/AdminServlet?azione=toggleAttivo&id=${veicolo.id}&stato=false">

                                                Disattiva

                                            </a>

                                        </c:when>


                                        <c:otherwise>

                                            <a href="${pageContext.request.contextPath}/AdminServlet?azione=toggleAttivo&id=${veicolo.id}&stato=true">

                                                Attiva

                                            </a>

                                        </c:otherwise>


                                    </c:choose>


                                </div>


                            </td>


                        </tr>


                    </c:forEach>


                </tbody>


            </table>


        </section>



        <!-- ============================================= -->
        <!-- GESTIONE ORDINI                               -->
        <!-- ============================================= -->


        <section class="sezione-admin">


            <h2>
                Gestione Ordini
            </h2>



            <!-- FILTRI -->

            <form class="form-admin"
                  action="${pageContext.request.contextPath}/AdminServlet"
                  method="GET">


                <div class="campo-admin">


                    <label>
                        ID Cliente
                    </label>


                    <input type="number"
                           name="cliente"
                           value="${clienteFiltro}"
                           placeholder="Es. 3">


                </div>



                <div class="campo-admin">


                    <label>
                        Data ordine
                    </label>


                    <input type="date"
                           name="data"
                           value="${dataFiltro}">


                </div>



                <button type="submit">
                    Filtra
                </button>


                <a href="${pageContext.request.contextPath}/AdminServlet">

                    Mostra tutti

                </a>


            </form>



            <!-- TABELLA ORDINI -->


            <table class="tabella-admin">


                <thead>


                    <tr>

                        <th>
                            ID Ordine
                        </th>

                        <th>
                            ID Cliente
                        </th>

                        <th>
                            Data
                        </th>

                        <th>
                            Veicoli
                        </th>

                        <th>
                            Totale
                        </th>

                    </tr>


                </thead>


                <tbody>


                    <c:forEach var="ordine"
                               items="${listaOrdiniAdmin}">


                        <tr>


                            <td>

                                #${ordine.id}

                            </td>


                            <td>

                                ${ordine.idUtente}

                            </td>


                            <td>

                                ${ordine.dataOrdine}

                            </td>


                            <td>


                                <c:forEach var="item"
                                           items="${ordine.elementi}">


                                    <div>

                                        ${item.veicolo.marca}

                                        ${item.veicolo.modello}

                                        -

                                        ${item.giorni} giorni

                                    </div>


                                </c:forEach>


                            </td>


                            <td>

                                <strong>

                                    ${ordine.totaleComplessivo} €

                                </strong>

                            </td>


                        </tr>


                    </c:forEach>


                </tbody>


            </table>


            <c:if test="${empty listaOrdiniAdmin}">

                <p>
                    Nessun ordine trovato.
                </p>

            </c:if>


        </section>


    </main>



    <script src="${pageContext.request.contextPath}/js/main.js">
    </script>


</body>

</html>