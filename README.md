# DriveYourDream

DriveYourDream è un'applicazione web per il noleggio di automobili, realizzata in coppia per l'esame di **Tecnologie Software per il Web** del corso di laurea in Informatica dell'Università degli Studi di Salerno, nel 2026.

Il progetto comprende il percorso del cliente, dalla consultazione del catalogo alla conferma dell'ordine, e un'area riservata all'amministratore per la gestione dei veicoli. Il lavoro è stato condiviso sia sul frontend sia sul backend.

## Funzionalità

- Consultazione del catalogo, ricerca e visualizzazione dei dettagli delle auto.
- Registrazione, accesso, logout e area personale.
- Carrello con riepilogo e conferma dell'ordine.
- Consultazione degli ordini nell'area personale.
- Gestione dei veicoli nell'area amministrativa.
- Controllo degli accessi in base al ruolo dell'utente.
- Validazione dei moduli e verifica asincrona delle email già registrate.

## Tecnologie utilizzate

| Parte del progetto | Tecnologie |
| --- | --- |
| Backend | Java 11, Servlet 4.0, JDBC |
| Pagine e interfaccia | JSP, JSTL, HTML, CSS, JavaScript |
| Richieste asincrone | Fetch API e JSON |
| Database | MySQL 8 |
| Ambiente di sviluppo | Eclipse, Apache Tomcat 9 |

Il progetto utilizza le API `javax.servlet` ed è configurato come **Eclipse Dynamic Web Project**.

## Organizzazione del codice

| Percorso | Contenuto |
| --- | --- |
| `src/main/java/control` | Servlet e filtro di accesso all'area amministrativa |
| `src/main/java/dao` | Query e accesso al database |
| `src/main/java/model` | Classi del modello e gestione delle connessioni |
| `src/main/webapp` | Pagine JSP, fogli di stile, script e immagini |
| `src/main/webapp/WEB-INF/lib` | Librerie JDBC e JSTL |
| `database/progettotsw.sql` | Schema del database e catalogo dimostrativo |

## Avvio in locale

### 1. Preparare l'ambiente

Servono un JDK 11, Eclipse con gli strumenti per lo sviluppo web Java, Apache Tomcat 9 e MySQL 8.

Clonare il repository:

```bash
git clone https://github.com/Emisans/DriveYourDream.git
```

In Eclipse selezionare **File → Import → General → Existing Projects into Workspace** e scegliere la cartella `DriveYourDream`. Nelle proprietà del progetto associare il JDK e il runtime Tomcat 9 installati sul proprio computer.

Tomcat 10 e le versioni successive utilizzano le API `jakarta.servlet`: il codice attuale richiede Tomcat 9.

### 2. Preparare il database

In MySQL Workbench creare un database locale chiamato `progettotsw`:

```sql
CREATE DATABASE progettotsw
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;
```

Selezionarlo come schema predefinito, aprire `database/progettotsw.sql` ed eseguire lo script. Il catalogo contiene dati dimostrativi; le tabelle degli utenti e degli ordini sono vuote.

**Usare un database dedicato alla prova:** lo script contiene istruzioni `DROP TABLE` che ricreano le tabelle.

### 3. Configurare la connessione

Creare un utente MySQL locale con accesso al database `progettotsw`. Nella configurazione di avvio di Tomcat impostare queste variabili d'ambiente:

| Variabile | Valore |
| --- | --- |
| `DB_USER` | Nome dell'utente MySQL locale |
| `DB_PASSWORD` | Password dell'utente MySQL locale |
| `DB_URL` | Facoltativa: URL JDBC per un database diverso da quello locale predefinito |

In Eclipse le variabili si impostano nella scheda **Environment** della configurazione di avvio del server, accessibile da **Run → Run Configurations**.

Se `DB_USER` non è impostata, viene usato il nome `driveyourdream`. La password deve essere impostata esplicitamente. L'URL predefinito è:

```text
jdbc:mysql://localhost:3306/progettotsw?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
```

Il file `.env.example` riporta la configurazione di esempio, ma non viene letto automaticamente dall'applicazione. Riavviare Tomcat dopo aver modificato le variabili.

### 4. Avviare l'applicazione

Selezionare il progetto in Eclipse e scegliere **Run As → Run on Server**, usando Tomcat 9. Con porta e context path predefiniti, l'indirizzo è:

[http://localhost:8080/DriveYourDream/](http://localhost:8080/DriveYourDream/)

Le librerie JDBC e JSTL sono già incluse in `WEB-INF/lib`; l'API Servlet è fornita da Tomcat.

## Accesso cliente e amministratore

Per provare il percorso cliente, creare un account dalla pagina di registrazione.

Per accedere all'area amministrativa, registrare un account locale e impostare nel database il valore `admin` nel campo `ruolo` della relativa riga della tabella `utente`. Non sono inclusi account amministrativi preconfigurati.

## Note sul progetto

DriveYourDream è un elaborato didattico. Le password vengono trasformate in hash SHA-256 in Java e le query di autenticazione utilizzano `PreparedStatement`.

Per un impiego reale restano da rafforzare la gestione delle password, adottando un algoritmo dedicato con salt e fattore di lavoro, e la validazione lato server: i controlli JavaScript possono essere aggirati. Il repository non contiene credenziali del database o dati esportati di utenti e ordini.
