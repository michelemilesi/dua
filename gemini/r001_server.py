# server.py

import socket

# --- Configurazione del Server ---
HOST = '127.0.0.1'  # Indirizzo IP locale (il tuo computer)
PORT = 4000         # Una porta libera su cui il server ascolterà

# --- Creazione del Server ---

# 1. Creiamo il "socket" del server. Pensa a questo come a una porta di comunicazione.
#    AF_INET si riferisce alla comunicazione via Internet (IPv4).
#    SOCK_STREAM si riferisce al tipo di protocollo (TCP), che garantisce che i dati arrivino in ordine.
server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# 2. Associamo il socket all'indirizzo IP e alla porta specificati.
#    Il server ora "possiede" questo indirizzo e porta.
server_socket.bind((HOST, PORT))

# 3. Mettiamo il server in modalità "ascolto".
#    Può gestire fino a 5 connessioni in coda prima di rifiutarne di nuove.
server_socket.listen(5)

print(f"[*] Il server MUD è in ascolto su {HOST}:{PORT}")

# --- Ciclo Principale del Server ---

# Questo è un ciclo infinito per accettare connessioni una dopo l'altra.
# Per ora, ne gestirà solo una alla volta.
while True:
    # 4. Accettiamo una nuova connessione.
    #    Questo comando è "bloccante": il programma si ferma qui finché un giocatore non si connette.
    #    Quando qualcuno si connette, ci restituisce due cose:
    #    - client_socket: un nuovo socket per comunicare solo con quel giocatore.
    #    - address: l'indirizzo IP e la porta del giocatore.
    client_socket, address = server_socket.accept()
    
    print(f"[*] Nuova connessione da {address}")

    # 5. Prepariamo e inviamo un messaggio di benvenuto al giocatore.
    #    I dati inviati tramite socket devono essere in formato "bytes".
    #    L'encoding 'utf-8' è uno standard comune per convertire stringhe in bytes.
    welcome_message = "Benvenuto nel nostro MUD! Per ora è un po' vuoto, ma grandi avventure ti attendono!\n"
    client_socket.send(welcome_message.encode('utf-8'))

    # 6. Per questo semplice esempio, chiudiamo subito la connessione.
    #    Nei prossimi passi, qui metteremo la logica di gioco.
    client_socket.close()
    print(f"[*] Connessione con {address} chiusa.")