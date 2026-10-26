# DriveYourDream

Progetto universitario realizzato in coppia per l'esame di Tecnologie Software per il Web, Università degli Studi di Salerno, 2026.

Applicazione web didattica per la consultazione e il noleggio di automobili. Entrambi gli studenti hanno contribuito al frontend e al backend. Questa pubblicazione non attribuisce a un solo autore il lavoro dell'intero gruppo.

## Funzionalità presenti nel codice

- Home page, catalogo, ricerca e dettaglio dei veicoli.
- Registrazione, login, logout e area personale.
- Distinzione tra cliente e amministratore, con filtro di accesso all'area amministrativa.
- Carrello, checkout, conferma e consultazione degli ordini.
- Gestione dei veicoli nell'area amministrativa.
- Controlli dei campi lato browser e controllo asincrono dell'email già registrata.

## Tecnologie

Java 11, Servlet 4.0 (`javax.servlet`), JSP, JSTL, JDBC, MySQL 8, HTML, CSS, JavaScript, AJAX/JSON e XML. Progetto Eclipse Dynamic Web Project, configurato per Apache Tomcat 9.

## Struttura

- `src/main/java/control`: servlet e filtro amministrativo.
- `src/main/java/dao`: accesso ai dati tramite JDBC.
- `src/main/java/model`: modelli e gestione delle connessioni.
- `src/main/webapp`: JSP, JavaScript, CSS, immagini e configurazione web.
- `database/progettotsw.sql`: schema e catalogo dimostrativo; utenti e ordini rimossi dalla copia pubblica.
- `.env.example`: esempio dei nomi delle variabili di configurazione.

## Avvio locale con Eclipse

1. Installare un JDK 11 o compatibile, Eclipse con gli strumenti Enterprise Java/Web, Apache Tomcat 9 e MySQL 8. Il progetto usa `javax.servlet`: non sostituire direttamente Tomcat 9 con Tomcat 10+ senza migrazione.
2. In MySQL creare un database dimostrativo vuoto chiamato `progettotsw`. Importare `database/progettotsw.sql` selezionando prima quel database. **Lo script contiene DROP TABLE**: usarlo solo sul database dimostrativo, non su dati da conservare.
3. Creare un utente MySQL locale dedicato e assegnargli i permessi necessari sul database dimostrativo.
4. In Eclipse importare la cartella come progetto esistente; selezionare Java 11 e configurare il runtime Tomcat 9 disponibile sul proprio computer.
5. Nella configurazione che avvia Tomcat impostare `DB_USER`, `DB_PASSWORD` e, se necessario, `DB_URL`. L'URL predefinito è quello in `.env.example`. Il nome utente predefinito è `driveyourdream`; la password non è incorporata nel codice. Se il server era già avviato, riavviarlo dopo la configurazione.
6. Avviare il progetto con **Run on Server** e aprire `http://localhost:8080/DriveYourDream/`, adattando porta e context path alla propria configurazione.

Il file `.env.example` è soltanto documentazione: non viene caricato automaticamente. Il progetto non contiene un `pom.xml`; le dipendenze JDBC/JSTL sono in `WEB-INF/lib` e l'API Servlet è fornita dal runtime Tomcat.

## Account dimostrativi

La copia pubblica non contiene account o ordini esportati. Registrare un nuovo utente dall'interfaccia. Per provare l'area amministrativa, il gestore del database può impostare il ruolo `admin` per il solo account dimostrativo desiderato, dopo averlo registrato. Non pubblicare credenziali o dati personali di account reali.

## Ambito didattico e limiti

Nel codice originale le password sono trasformate con SHA-256 in Java e le query di autenticazione usano `PreparedStatement`. SHA-256 semplice non equivale a un sistema moderno di password hashing con salt e fattore di lavoro. La validazione JavaScript non sostituisce i controlli lato server. L'applicazione è un elaborato didattico e non è proposta come servizio pronto per la produzione.

## Preparazione della copia pubblica

- Rimossi output compilati e dati esportati di utenti e ordini.
- Sostituite le credenziali MySQL incorporate con variabili d'ambiente.
- Aggiunti README, `.gitignore` ed esempio della configurazione.
- Conservate le funzionalità del progetto; non effettuata una riscrittura della sicurezza.

Verifica effettuata: lettura statica dei sorgenti e controllo della struttura dell'archivio. Avvio completo con Tomcat/MySQL non eseguito in questo ambiente. Verificare l'avvio locale prima di presentare una demo.

## Autori e utilizzo

Progetto di gruppo: aggiungere i nomi dei due autori concordandoli con il collega. Non è stata assegnata una nuova licenza al lavoro del gruppo. Non rimuovere eventuali attribuzioni relative a codice, immagini o librerie di terzi.
