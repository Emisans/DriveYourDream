// =========================================================
// VALIDAZIONE FORM VEICOLO
// =========================================================

document.addEventListener("DOMContentLoaded", function() {

    const form =
        document.getElementById("formVeicolo");

    if (form) {

        form.addEventListener("submit", function(event) {

            let isValid = true;


            document.getElementById("error-marca").textContent = "";
            document.getElementById("error-modello").textContent = "";
            document.getElementById("error-prezzo").textContent = "";
            document.getElementById("error-citta").textContent = "";
            document.getElementById("error-iva").textContent = "";


            const marca =
                document.getElementById("marca").value.trim();

            const modello =
                document.getElementById("modello").value.trim();

            const prezzo =
                document.getElementById("prezzo").value.trim();

            const citta =
                document.getElementById("citta").value.trim();

            const iva =
                document.getElementById("iva").value.trim();


            const regexTesto =
                /^[A-Za-z0-9\s\-]{2,30}$/;

            const regexPrezzo =
                /^\d+(\.\d{1,2})?$/;

            const regexIva =
                /^(4|5|10|22)$/;


            if (!regexTesto.test(marca)) {

                document.getElementById("error-marca").textContent =
                    "La marca deve essere compresa tra 2 e 30 caratteri.";

                document.getElementById("marca").focus();

                isValid = false;
            }


            if (!regexTesto.test(modello)) {

                document.getElementById("error-modello").textContent =
                    "Il modello deve essere compreso tra 2 e 30 caratteri.";

                if (isValid) {
                    document.getElementById("modello").focus();
                }

                isValid = false;
            }


            if (!regexPrezzo.test(prezzo)
                    || parseFloat(prezzo) <= 0) {

                document.getElementById("error-prezzo").textContent =
                    "Inserisci un prezzo valido maggiore di 0.";

                if (isValid) {
                    document.getElementById("prezzo").focus();
                }

                isValid = false;
            }


            if (!regexTesto.test(citta)) {

                document.getElementById("error-citta").textContent =
                    "Inserisci una città valida.";

                if (isValid) {
                    document.getElementById("citta").focus();
                }

                isValid = false;
            }


            if (!regexIva.test(iva)) {

                document.getElementById("error-iva").textContent =
                    "Inserisci un'aliquota IVA corretta.";

                if (isValid) {
                    document.getElementById("iva").focus();
                }

                isValid = false;
            }


            if (!isValid) {
                event.preventDefault();
            }

        });
    }
});



// =========================================================
// VALIDAZIONE REGISTRAZIONE + AJAX EMAIL
// =========================================================

document.addEventListener("DOMContentLoaded", function() {

    const formRegistrazione =
        document.getElementById("formRegistrazione");


    if (formRegistrazione) {


        const emailInput =
            document.getElementById("email");

        const erroreEmail =
            document.getElementById("error-email");


        let emailEsistente = false;



        // AJAX - CONTROLLO EMAIL

        emailInput.addEventListener("blur", function() {

            const email =
                emailInput.value.trim();


            if (email === "") {

                emailEsistente = false;

                erroreEmail.textContent = "";

                return;
            }


            fetch(
                "CheckEmailServlet?email="
                + encodeURIComponent(email)
            )

            .then(response =>
                response.json()
            )

            .then(data => {

                if (data.esiste) {

                    emailEsistente = true;

                    erroreEmail.textContent =
                        "Questa email è già registrata.";

                } else {

                    emailEsistente = false;

                    erroreEmail.textContent = "";
                }

            })

            .catch(error => {

                console.error(
                    "Errore controllo email:",
                    error
                );

            });

        });



        // VALIDAZIONE FORM

        formRegistrazione.addEventListener(
            "submit",
            function(event) {


                let isValid = true;


                document.getElementById("error-nome").textContent = "";
                document.getElementById("error-cognome").textContent = "";
                document.getElementById("error-email").textContent = "";
                document.getElementById("error-password").textContent = "";


                const nome =
                    document.getElementById("nome").value.trim();

                const cognome =
                    document.getElementById("cognome").value.trim();

                const email =
                    document.getElementById("email").value.trim();

                const password =
                    document.getElementById("password").value;


                const regexNome =
                    /^[A-Za-zÀ-ÿ' ]{2,30}$/;

                const regexEmail =
                    /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

                const regexPassword =
                    /^(?=.*[A-Za-z])(?=.*\d).{8,}$/;



                if (!regexNome.test(nome)) {

                    document.getElementById("error-nome").textContent =
                        "Inserisci un nome valido (2-30 caratteri).";

                    document.getElementById("nome").focus();

                    isValid = false;
                }


                if (!regexNome.test(cognome)) {

                    document.getElementById("error-cognome").textContent =
                        "Inserisci un cognome valido (2-30 caratteri).";

                    if (isValid) {
                        document.getElementById("cognome").focus();
                    }

                    isValid = false;
                }


                if (!regexEmail.test(email)) {

                    document.getElementById("error-email").textContent =
                        "Inserisci un indirizzo email valido.";

                    if (isValid) {
                        document.getElementById("email").focus();
                    }

                    isValid = false;
                }


                if (emailEsistente) {

                    document.getElementById("error-email").textContent =
                        "Questa email è già registrata.";

                    if (isValid) {
                        document.getElementById("email").focus();
                    }

                    isValid = false;
                }


                if (!regexPassword.test(password)) {

                    document.getElementById("error-password").textContent =
                        "Minimo 8 caratteri, con almeno una lettera e un numero.";

                    if (isValid) {
                        document.getElementById("password").focus();
                    }

                    isValid = false;
                }


                if (!isValid) {
                    event.preventDefault();
                }

            }
        );
    }
});



// =========================================================
// AJAX - SUGGERIMENTI MARCA
// =========================================================

document.addEventListener("DOMContentLoaded", function() {


    const marcaInput =
        document.getElementById("marcaRicerca");


    const contenitore =
        document.getElementById("suggerimentiMarca");


    // Questo codice viene eseguito solo in index.jsp
    if (marcaInput && contenitore) {


        marcaInput.addEventListener(
            "input",
            function() {


                const testo =
                    marcaInput.value.trim();


                // Se il campo è vuoto,
                // nascondiamo i suggerimenti
                if (testo.length === 0) {

                    contenitore.innerHTML = "";

                    contenitore.style.display =
                        "none";

                    return;
                }


                // RICHIESTA AJAX
                fetch(
                    contextPath
                    + "/SuggerimentiServlet?testo="
                    + encodeURIComponent(testo)
                )


                // JSON -> oggetto JavaScript
                .then(response =>
                    response.json()
                )


                .then(suggerimenti => {


                    contenitore.innerHTML = "";


                    // Nessun risultato
                    if (suggerimenti.length === 0) {

                        contenitore.style.display =
                            "none";

                        return;
                    }


                    // Creiamo un elemento
                    // per ogni suggerimento
                    suggerimenti.forEach(
                        function(marca) {


                            const elemento =
                                document.createElement("div");


                            elemento.textContent =
                                marca;


                            elemento.style.padding =
                                "8px";


                            elemento.style.cursor =
                                "pointer";


                            elemento.style.borderBottom =
                                "1px solid #eee";


                            // CLICK SUL SUGGERIMENTO
                            elemento.addEventListener(
                                "click",
                                function() {


                                    marcaInput.value =
                                        marca;


                                    contenitore.innerHTML =
                                        "";


                                    contenitore.style.display =
                                        "none";

                                }
                            );


                            contenitore.appendChild(
                                elemento
                            );

                        }
                    );


                    contenitore.style.display =
                        "block";

                })


                .catch(error => {

                    console.error(
                        "Errore suggerimenti:",
                        error
                    );

                });

            }
        );
    }
});