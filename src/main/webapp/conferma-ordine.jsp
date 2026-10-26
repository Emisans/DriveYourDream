<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html lang="it">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>
        Prenotazione Confermata - DriveYourDream
    </title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


    <style>

        /* CONTENITORE PRINCIPALE */

        .pagina-conferma {

            min-height: calc(100vh - 170px);

            display: flex;

            justify-content: center;

            align-items: center;

            padding: 40px 20px;

        }


        /* RICEVUTA */

        .fattura {

            width: 100%;

            max-width: 750px;

            background-color: white;

            padding: 40px;

            border-radius: 12px;

            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.15);

            text-align: center;

        }


        .titolo-fattura {

            color: #0a3d91;

            margin-bottom: 5px;

        }


        .sottotitolo {

            color: #666;

            margin-top: 0;

        }


        .messaggio-successo {

            color: #28a745;

            margin-top: 25px;

        }


        /* DATI CLIENTE */

        .dati-cliente {

            text-align: left;

            margin-top: 30px;

            padding: 20px;

            border: 1px solid #ddd;

            border-radius: 8px;

            background-color: #f8f9fa;

        }


        .dati-cliente h3 {

            color: #0a3d91;

            margin-top: 0;

            border-bottom: 1px solid #ddd;

            padding-bottom: 10px;

        }


        .dati-cliente p {

            margin: 10px 0;

        }


        /* PULSANTI */

        .azioni-conferma {

            display: flex;

            justify-content: center;

            gap: 12px;

            margin-top: 30px;

            flex-wrap: wrap;

        }


        .azioni-conferma button,
        .azioni-conferma a {

            display: inline-block;

            padding: 11px 18px;

            border-radius: 5px;

            font-weight: bold;

            text-decoration: none;

            cursor: pointer;

            font-size: 14px;

        }


        .btn-principale {

            background-color: #0a3d91;

            color: white;

            border: 1px solid #0a3d91;

        }


        .btn-secondario {

            background-color: white;

            color: #0a3d91;

            border: 1px solid #0a3d91;

        }


        /* ======================================= */
        /* STAMPA                                  */
        /* ======================================= */

        @media print {

            .no-print {

                display: none !important;

            }


            body {

                background: white;

            }


            .pagina-conferma {

                min-height: auto;

                padding: 0;

            }


            .fattura {

                max-width: 100%;

                box-shadow: none;

                border: none;

                margin: 0;

            }


            .dati-cliente {

                background-color: white;

            }

        }


    </style>

</head>


<body>


    <!-- HEADER E NAV -->

    <div class="no-print">

        <jsp:include page="/WEB-INF/fragments/header.jsp" />

        <jsp:include page="/WEB-INF/fragments/nav.jsp" />

    </div>



    <!-- PAGINA CONFERMA -->

    <div class="pagina-conferma">


        <main class="fattura">


            <h1 class="titolo-fattura">

                DriveYourDream

            </h1>


            <p class="sottotitolo">

                Riepilogo Prenotazione

            </p>


            <hr>


            <h2 class="messaggio-successo">

                Prenotazione Completata con Successo!

            </h2>


            <p>

                Il tuo ordine è stato registrato
                correttamente.

            </p>


            <p>

                Puoi verificare i dettagli e lo storico
                dei tuoi noleggi nella tua Area Personale.

            </p>



            <!-- DATI CLIENTE -->

            <div class="dati-cliente">


                <h3>

                    Dati Cliente

                </h3>


                <p>

                    <strong>Nome:</strong>

                    ${sessionScope.utente.nome}

                </p>


                <p>

                    <strong>Cognome:</strong>

                    ${sessionScope.utente.cognome}

                </p>


                <p>

                    <strong>Email:</strong>

                    ${sessionScope.utente.email}

                </p>


            </div>



            <!-- PULSANTI -->

            <div class="azioni-conferma no-print">


                <button type="button"
                        class="btn-principale"
                        onclick="window.print()">

                    Stampa Ricevuta

                </button>


                <a href="${pageContext.request.contextPath}/AreaPersonaleServlet"
                   class="btn-principale">

                    Area Personale

                </a>


                <a href="${pageContext.request.contextPath}/search"
                   class="btn-secondario">

                    Torna al Catalogo

                </a>


            </div>


        </main>


    </div>


</body>

</html>