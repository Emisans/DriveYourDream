<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isErrorPage="true" %>

<!DOCTYPE html>

<html lang="it">

<head>

    <meta charset="UTF-8">

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <title>403 - Accesso negato | DriveYourDream</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

</head>


<body>

    <div style="
        max-width: 600px;
        margin: 100px auto;
        padding: 40px;
        text-align: center;
        background-color: white;
        border-radius: 10px;
        box-shadow: 0 3px 10px rgba(0,0,0,0.15);
    ">

        <h1 style="
            font-size: 60px;
            margin-bottom: 10px;
        ">
            403
        </h1>


        <h2>
            Accesso negato
        </h2>


        <p>
            Non hai i permessi necessari
            per accedere a questa risorsa.
        </p>


        <br>


        <a href="${pageContext.request.contextPath}/search">
            Torna al catalogo
        </a>

    </div>

</body>

</html>