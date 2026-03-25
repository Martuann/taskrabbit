package org.elis.dao.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Citta;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

public class JpaUtenteDao implements UtenteDao{
	private EntityManagerFactory emf;

	public JpaUtenteDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaUtenteDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public Long aggiungiUtente(Utente utente) throws Exception {
try(EntityManager em=emf.createEntityManager()){
	EntityTransaction transaction =em.getTransaction();
	transaction.begin();
    em.persist(utente);
    transaction.commit();
}		return utente.getId();
	}

	@Override
	public Utente aggiornaUtente(Utente utente) throws Exception {
try(EntityManager em =emf.createEntityManager()){
EntityTransaction et=em.getTransaction();

et.begin();
Utente utenteAgg=em.merge(utente);
et.commit();
return utenteAgg;
}
	}

	@Override
	public void rimuoviUtente(Long id) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
	        EntityTransaction et = em.getTransaction();
	        et.begin();
	        Utente u = em.find(Utente.class, id);
	        if (u != null) {
	            em.remove(u);
	        }
	        et.commit();
	    }		
	}

	@Override
	public Utente login(String email, String password) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u where u.email =:email and u.password =:password", Utente.class);
			query.setParameter("email", email); 
	        query.setParameter("password", password);	    

		return query.getSingleResult();
		
		
	}catch(NoResultException e){
		return null;
		
	}	}
	
	

	@Override
	public boolean presenzaUtente(String email) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u where u.email =:email", Utente.class);
			query.setParameter("email", email); 
	           

		 return query.getSingleResult()!=null;
		
		
	}catch(NoResultException e){
		return false;

	}	}

	

	@Override
	public Utente ricercaTramiteEmail(String email) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u where u.email =:email", Utente.class);
			query.setParameter("email", email); 
	           
	
		 return query.getSingleResult();
		
		
	}catch(NoResultException e){
		return null;

	}	}

	
	

	@Override
	public Utente ricercaPerId(Long id) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			return em.find(Utente.class,id);
		}

	}

	@Override
	public List<Utente> getUtentiProfessionisti() throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT u FROM Utente u where u.ruolo =:r", Utente.class);
			query.setParameter("r", Ruolo.PROFESSIONISTA);
	           
	
		 return query.getResultList();
		
		
	}catch(NoResultException e){
		return null;

	}	}

	

	@Override
	public List<Utente> ricercaTramiteProfessione(String professione) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT up.utente FROM UtenteProfessione up WHERE up.professione.nome = :professione", Utente.class);
			query.setParameter("professione", professione);
	        

			
	
		 return query.getResultList();
		
		
	}catch (Exception e) {
        throw new Exception("Errore nella ricerca utenti per professione: " + professione, e);
    }

	}	

	
	

		@Override
		public Map<Utente, String> getRecensoriConFoto(Long id_utenteRicevente) throws Exception {
			return null;
	//
		}

	@Override
	public List<Utente> ricercaLikeProfessione(String professione) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT up.utente from UtenteProfessione up where up.professione.nome like :professione", Utente.class);
			query.setParameter("professione","%"+ professione+"%");
	        
		
		
		
				 return query.getResultList();
				
				
			}catch (Exception e) {
		        throw new Exception("Errore nella ricerca utenti per professione: " + professione, e);
		    }

			}	
		
		
	
		
		
	

	@Override
	public boolean presenzaCodiceFiscale(String codiceFiscale) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT u from Utente u where cf = :codiceFiscale", Utente.class);
			query.setParameter("codiceFiscale",codiceFiscale);

	        
		
		
		
				 return query.getSingleResult()!=null;
				
				
			}catch (NoResultException e) {
return false;		    }

			}	
		
	

	@Override
	public boolean presenzaTelefono(String telefono) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
	        Long count = em.createQuery("SELECT COUNT(u) FROM Utente u WHERE u.telefono = :tel", Long.class)
	                       .setParameter("tel", telefono)
	                       .getSingleResult();
	        return count > 0;
	    }

			}
	

	@Override
	public List<Utente> listaUtentiRichiestiDaRichiedente(Long id_utenteRichiedente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			TypedQuery<Utente> query = em.createQuery("SELECT r.utenteRichiesto from Richiesta r where r.richiedente.id = :id_utenteRichiedente", Utente.class);
			query.setParameter("id_utenteRichiedente",id_utenteRichiedente);

	        
		
		
		
				 return query.getResultList();
				
				
			}catch (Exception e) {
			throw new Exception("Errore nella ricerca utenti", e);
			}
	
	
	
	
	}

	
	

	
	
	
}
