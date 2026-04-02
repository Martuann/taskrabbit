package org.elis.utilities;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Disponibilita;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.OrarioBase;
import org.elis.progetto.model.Professione;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.StatoRichiesta;
import org.elis.progetto.model.Utente;
import org.elis.progetto.model.UtenteProfessione;
import org.elis.progetto.model.UtenteVeicolo;
import org.elis.progetto.model.Veicolo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class JpaDbFiller {

    public static void main(String[] args) {
        System.out.println("=== AVVIO DEL PROGRAMMA DI SEEDING ===");
        
        EntityManagerFactory emf = null;
        
        try {
            System.out.println("Inizializzazione di Hibernate...");
            emf = Persistence.createEntityManagerFactory("taskly");
            System.out.println("EntityManagerFactory creato con successo!");
            
            // --- STEP 1: FORZIAMO LA CREAZIONE DELLE TABELLE ---
            EntityManager emSchema = emf.createEntityManager();
            emSchema.getTransaction().begin();
            // Aprendo e chiudendo la transazione subito, 
            // costringiamo Hibernate a eseguire l'azione "drop-and-create"
            emSchema.getTransaction().commit();
            emSchema.close();
            
            System.out.println("Tabelle create fisicamente. Ora popolo i dati...");

            // --- STEP 2: ORA CHE LE TABELLE ESISTONO DI SICURO, INSERIAMO I DATI ---
            EntityManager emDati = emf.createEntityManager();
            EntityTransaction tx = emDati.getTransaction();
            
            try {
                tx.begin();

                System.out.println("Inizio popolamento database...");

                // 1. POPOLAMENTO CITTÀ
                Citta roma = new Citta("Roma", "RM");
                Citta milano = new Citta("Milano", "MI");
                Citta napoli = new Citta("Napoli", "NA");
                emDati.persist(roma);
                emDati.persist(milano);
                emDati.persist(napoli);

                // 2. POPOLAMENTO PROFESSIONI
                Professione idraulico = new Professione("Idraulico");
                Professione elettricista = new Professione("Elettricista");
                Professione giardiniere = new Professione("Giardiniere");
                emDati.persist(idraulico);
                emDati.persist(elettricista);
                emDati.persist(giardiniere);

                // 3. POPOLAMENTO VEICOLI
                Veicolo furgone = new Veicolo("Furgone");
                Veicolo auto = new Veicolo("Automobile");
                Veicolo moto = new Veicolo("Motocicletta");
                emDati.persist(furgone);
                emDati.persist(auto);
                emDati.persist(moto);

                // 4. POPOLAMENTO UTENTI
                Utente admin = new Utente("Marco", "Rossi", "admin@taskly.com", "3331111111", "admin123", 
                        LocalDate.of(1985, 5, 20), "RSSMRC85E20H501A", Ruolo.ADMIN, roma);
                
                Utente professionista = new Utente("Luca", "Bianchi", "luca.pro@taskly.com", "3332222222", "pro123", 
                        LocalDate.of(1990, 8, 15), "BNCLCU90M15F205Z", Ruolo.PROFESSIONISTA, milano);
                
                Utente cliente = new Utente("Mario", "Verdi", "mario.cliente@mail.com", "3333333333", "cliente123", 
                        LocalDate.of(1995, 3, 10), "VRDMRA95C10F839U", Ruolo.UTENTE_BASE, roma);

                emDati.persist(admin);
                emDati.persist(professionista);
                emDati.persist(cliente);

                // 5. IMMAGINI (Foto Profilo)
                Immagine avatarCliente = new Immagine("avatar_mario.png", "immagini/avatar_mario.png", true, cliente);
                Immagine avatarPro = new Immagine("avatar_luca.png", "immagini/avatar_luca.png", true, professionista);
                emDati.persist(avatarCliente);
                emDati.persist(avatarPro);

                // 6. UTENTE_PROFESSIONE (Tariffe orarie dei professionisti)
                UtenteProfessione proIdraulico = new UtenteProfessione(professionista, idraulico, new BigDecimal("35.50"));
                UtenteProfessione proElettricista = new UtenteProfessione(professionista, elettricista, new BigDecimal("40.00"));
                emDati.persist(proIdraulico);
                emDati.persist(proElettricista);

                // 7. UTENTE_VEICOLO (Sovrapprezzo per uso mezzo)
                UtenteVeicolo proFurgone = new UtenteVeicolo(professionista, furgone, new BigDecimal("15.00"));
                emDati.persist(proFurgone);

             // 8. ORARIO BASE (Usiamo giovedì e venerdì che nell'ordine di Hibernate sforano lo 0)
                OrarioBase lunediPro = new OrarioBase(DayOfWeek.THURSDAY, LocalTime.of(9, 0), LocalTime.of(18, 0), professionista);
                OrarioBase martediPro = new OrarioBase(DayOfWeek.FRIDAY, LocalTime.of(9, 0), LocalTime.of(13, 0), professionista);
                emDati.persist(lunediPro);
                emDati.persist(martediPro);

                // 9. DISPONIBILITÀ (Disponibilità per un giorno specifico)
                Disponibilita dispStraordinaria = new Disponibilita(professionista, LocalDate.now().plusDays(2), 
                        LocalTime.of(10, 0), LocalTime.of(16, 0));
                emDati.persist(dispStraordinaria);

                // 10. RICHIESTE (Interazioni tra utenti e professionisti)
                Richiesta richiestaRiparazione = new Richiesta(
                        "Ho un rubinetto che perde in cucina.",
                        LocalDate.now().plusDays(1),
                        LocalTime.of(10, 0),
                        LocalTime.of(12, 0),
                        new BigDecimal("86.00"), // 2 ore a 35.50€ + 15€ furgone
                        "Via Roma 12, Milano",
                        StatoRichiesta.in_attesa,
                        cliente,          // Richiedente
                        professionista,   // Richiesto
                        idraulico,        // Professione
                        furgone           // Veicolo
                );
                emDati.persist(richiestaRiparazione);

                // 11. RECENSIONI
                Recensione recensionePro = new Recensione(
                        5, 
                        "Ottimo lavoro, veloce e super professionale!", 
                        LocalDate.now(), 
                        cliente,          // Chi scrive
                        professionista    // Chi riceve
                );
                emDati.persist(recensionePro);

                // Forza l'invio fisico dei dati al database
                emDati.flush();
                
                // Chiude la transazione confermando i dati
                tx.commit();
                System.out.println("Database popolato con successo!");

            } catch (Exception e) {
                if (tx.isActive()) {
                    System.err.println("Errore rilevato! Eseguo il rollback della transazione...");
                    tx.rollback();
                }
                System.err.println("Errore durante il popolamento: " + e.getMessage());
                e.printStackTrace();
            } finally {
                emDati.close();
            }
            
        } catch (Throwable t) {
            System.err.println("ERRORE FATALE ALL'AVVIO DI HIBERNATE!");
            t.printStackTrace();
        } finally {
            if (emf != null && emf.isOpen()) {
                emf.close();
            }
        }
    }
}