# Domotik - Applicazione Android per la Gestione di Dispositivi Domotici
Benvenuto in Domotik, un'applicazione Android sviluppata con Android Studio per semplificare la gestione dei dispositivi domotici. Questa app ti permette di controllare e monitorare facilmente i tuoi dispositivi da remoto.

## Funzionalità Principali
### 1) Login:
La login dovrà permettere agli utenti di autenticarsi e di mantenere lo stato dei dispositivi tra i vari dispositivi "della famiglia". la famiglia la si identificherà con un id univoco che accomunerà i componenti che dovranno andare a modificare le stesse risorse.       Gli utenti saranno divisi come amministratore e guest, un amministratore potrà scegliere quali utenti potranno visualizzare i dati relativi alla sua casa e comandarne i dispositivi.
L'app traccia chi effettua il login, e permette di ricostruire tutte le azioni svolte da ciascun membro (es: modifica di un parametro di un attuatore). I log di tutte le azioni fatte dovrebbero essere visibili all'amministratore nonché all'utente che ha effettuato le azioni
L'amministratore può dare permessi di lettura e di configurazione in modo puntuale a ciascun individuo della famiglia e per ciascun sensore/attuatore (ad esempio, "Mario" può leggere il valore di temperatura della sua camera e del bagno ma non quello della cucina, e può modificare solo il valore del termostato della sua camera). Naturalmente mi aspetto che si possano controllare svariati sensori (non 1 per tipo, ma N per tipo, divisi in diverse stanze della casa)

### 2) controllo dispositivi smart (luci, riscaldamento, telo da proiezione, etc):
il controllo deve essere fittizio: si creeranno delle classi modello ad-hoc per ognuno dei dispositivi in descrizione, ogni modello dovrà avere le caratteristiche dell'oggetto in questione  
-   luci = interruttore On/Off
-   riscaldamento = interruttore On/Off, campo testuale modificabile per impostare la Temperatura
-   telo da proiezione = interruttore Su/Giu ogni modello dovrà avere la sua realizzazione sul firebase per poterne registrare lo stato e dorvà avere anche una sua finestra/pagina per il controllo.
I parametri degli attuatori si devono naturalmente poter settare in modo più user-friendly rispetto a cambiare un "campo di testo"

### 3) Chat: 
La chat ha la funzione di bacheca per i componenti della famiglia dove lasciare note agli altri famigliari. inoltre sarà oggetto di messaggi dalla applicazione stessa che invierà delle statistiche che le verranno inviate dopo le analisi sulle condizioni indoor ogni volta che si fa una elaborazione.
La chat dovrebbe essere possibile sia in una forma condivisa, per i messaggi a tutto il nucleo familiare, ma anche ad individui specifici

### 4) Statistiche su dati Indoor: 
possibilità di filtrare dati in base alla data e ottenere statistiche in un determinato intervallo di tempo. le statistiche sarannno composte da massimi, minimi e medie dei valori nell'intervallo specificato.
verrà fatta una analisi dei rischi associati a cambiamenti estremi nei livelli di temperatura e CO2, ad esempio per la salute umana e verranno inviati i risultati testuali delle operazioni che il software consiglia fare per chat.
Le analisi dovrebbero poter essere fatte: a livello temporale, per tipologia di sensore (es: aggrego tutti i valori di temperatura della casa), per specifico sensore (es: il sensore di temperatura che si trova in camera di Mario), etc... Naturalmente, devono tener conto dei permessi di lettura precedentemente definiti
Le analisi rischi devono essere automatizzate in qualche misura (non fatte "a mano" ed inserite sul db)

## Requisiti di Sistema
- Android 5.0 (Lollipop) e versioni successive.
- Connessione Internet per il controllo remoto.
## Installazione
- Clona il repository sul tuo ambiente di sviluppo locale.
- Apri il progetto in Android Studio.
- Configura le tue chiavi API e altre configurazioni secondo le istruzioni fornite.
## Configurazione
- Modifica il file di configurazione con le informazioni necessarie per l'utilizzo dell'app.

## Esecuzione del Progetto
Apri il progetto in Android Studio e eseguilo.

## Contribuisci
Siamo felici di accettare contributi! Se desideri contribuire a Domotik, segui questi passaggi:

##Fai il fork del repository.
- Crea un branch con il nome della tua funzionalità.
- Fai commit delle tue modifiche.
- Pusha il tuo branch.
- Apri una pull request.
## Segnalazione Problemi
Per segnalare un problema, utilizza il tracker delle issue su GitHub.

Grazie per l'interesse e la partecipazione a Domotik!
