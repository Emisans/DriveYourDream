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

    <title>DriveYourDream - Login</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">


    <style>

        .login-container {

            max-width: 400px;

            margin: 40px auto;

            padding: 25px;

            background-color: white;

            border-radius: 8px;

            box-shadow: 0 4px 8px rgba(0,0,0,0.1);

        }


        .login-container h2 {

            text-align: center;

            color: #0a3d91;

            margin-bottom: 20px;

        }


        .login-form {

            display: flex;

            flex-direction: column;

            gap: 15px;

        }


        .campo-login {

            display: flex;

            flex-direction: column;

        }


        .campo-login label {

            font-weight: bold;

            margin-bottom: 5px;

        }


        .campo-login input {

            padding: 8px;

            border-radius: 4px;

            border: 1px solid #ccc;

        }


        .messaggio-errore {

            background-color: #f8d7da;

            color: #721c24;

            border: 1px solid #f5c6cb;

            padding: 10px;

            margin-bottom: 15px;

            border-radius: 5px;

            text-align: center;

        }


        .login-form button {

            padding: 10px;

            background-color: #0a3d91;

            color: white;

            border: none;

            border-radius: 4px;

            font-weight: bold;

            cursor: pointer;

        }


        .login-form button:hover {

            background-color: #062c6b;

        }

    </style>

</head>


<body>


    <jsp:include page="/WEB-INF/fragments/header.jsp" />

    <jsp:include page="/WEB-INF/fragments/nav.jsp" />


    <main class="login-container">


        <h2>
            Accedi
        </h2>


        <!-- MESSAGGIO DI ERRORE -->

        <c:if test="${not empty errore}">

            <div class="messaggio-errore">

                ${errore}

            </div>

        </c:if>


        <form class="login-form"
              action="${pageContext.request.contextPath}/LoginServlet"
              method="POST">


            <div class="campo-login">

                <label for="email">
                    Email
                </label>

                <input type="email"
                       id="email"
                       name="email"
                       placeholder="Inserisci la tua email"
                       required>

            </div>


            <div class="campo-login">

                <label for="password">
                    Password
                </label>

                <input type="password"
                       id="password"
                       name="password"
                       placeholder="Inserisci la password"
                       required>

            </div>


            <button type="submit">

                Entra

            </button>


        </form>


    </main>


</body>

</html>