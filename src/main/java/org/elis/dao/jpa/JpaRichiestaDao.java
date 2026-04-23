package org.elis.dao.jpa;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaRichiestaDao implements RichiestaDao{
	private EntityManagerFactory emf;

	public JpaRichiestaDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaRichiestaDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void insert(Richiesta r) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            r.setUtenteRichiedente(em.find(r.getUtenteRichiedente().getClass(), r.getUtenteRichiedente().getId()));
            r.setUtenteRichiesto(em.find(r.getUtenteRichiesto().getClass(), r.getUtenteRichiesto().getId()));
            r.setProfessione(em.find(r.getProfessione().getClass(), r.getProfessione().getId()));
            if(r.getVeicolo() != null) {
                r.setVeicolo(em.find(r.getVeicolo().getClass(), r.getVeicolo().getId()));
            }
            
            em.persist(r);
            t.commit();
        }		
	}

	@Override
	public Richiesta selectById(Long id)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT r FROM Richiesta r " +
                          "JOIN FETCH r.utenteRichiedente " +
                          "JOIN FETCH r.utenteRichiesto " +
                          "JOIN FETCH r.professione " +
                          "LEFT JOIN FETCH r.veicolo " +
                          "WHERE r.id = :id";
           Richiesta richiesta= em.createQuery(jpql, Richiesta.class)
                     .setParameter("id", id)
                     .getSingleResult();
           gestisciScadenza(richiesta);
           return richiesta;
           
           
        } 
	}

	@Override
	public List<Richiesta> selectAll() throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            List<Richiesta> richieste= em.createQuery("SELECT r FROM Richiesta r", Richiesta.class).getResultList();
           
            richieste.forEach(this::gestisciScadenza);
            
            
            return richieste;
            
            
        }
	}

	@Override
	public void update(Richiesta r)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(r);
            t.commit();
        }		
	}

	@Override
	public void delete(Long id)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Richiesta r = em.find(Richiesta.class, id);
            if (r != null) em.remove(r);
            t.commit();
        }
		
	}

	

	@Override
	public List<Richiesta> selectByIdUtenteRichiedente(Long idUtenteRichiedente) throws Exception{
		  try (EntityManager em = emf.createEntityManager()) {
		       
		        String jpql = "SELECT DISTINCT r FROM Richiesta r " +
			        		  "JOIN FETCH r.utenteRichiesto u " + 
			        		  "JOIN FETCH r.professione p " +
			        		  "LEFT JOIN FETCH u.immagini i " +
			        		  "WHERE r.utenteRichiedente.id = :id " +
			        		  "AND (i IS NULL OR i.isFotoProfilo = true) " +
			        		  "ORDER BY r.data DESC";

		        
		        
		     
		        List<Richiesta> richieste= em.createQuery(jpql, Richiesta.class)
		                 .setParameter("id", idUtenteRichiedente)
		                 .getResultList();
		        
		        richieste.forEach(this::gestisciScadenza);
		        return richieste;
		    }
	}

	@Override
	public List<Richiesta> selectByIdUtenteRichiesto(Long idUtenteRichiesto) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
	        
	        String jpql = "SELECT DISTINCT r FROM Richiesta r " +
				          "JOIN FETCH r.utenteRichiesto u " + 
				          "JOIN FETCH r.professione p " +
				          "LEFT JOIN FETCH u.immagini i " +
				          "WHERE r.utenteRichiesto.id = :id " +
				          "AND (i IS NULL OR i.isFotoProfilo = true) " +
				          "ORDER BY r.data DESC";

	        List<Richiesta> richieste= em.createQuery(jpql, Richiesta.class)
	                 .setParameter("id", idUtenteRichiesto)
	                 .getResultList();
	        richieste.forEach(this::gestisciScadenza);
	        return richieste;
	    } 
	}
	private void gestisciScadenza(Richiesta r) {
	    if (r != null && r.getStato() == StatoRichiesta.in_attesa) {
	        
	        LocalDateTime momentoInizio = LocalDateTime.of(r.getData(), r.getOrarioInizio());
	        
	        LocalDateTime limiteScadenza = momentoInizio.minusHours(2);
	        
	        if (LocalDateTime.now().isAfter(limiteScadenza)) {
	            r.setStato(StatoRichiesta.scaduta);
	        }
	    }
	
}

	@Override
	public Boolean haLavorato(Long idUtenteRichiedente, Long idUtenteRichiesto) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
			String jpql = "SELECT COUNT(r) FROM Richiesta r WHERE r.utenteRichiedente.id = :idUtenteRichiedente AND r.utenteRichiesto.id = :idUtenteRichiesto AND r.stato = :statoRichiesta";
			Long count= em.createQuery(jpql, Long.class)
					.setParameter("idUtenteRichiedente", idUtenteRichiedente)
					.setParameter("idUtenteRichiesto", idUtenteRichiesto)
					.setParameter("statoRichiesta", StatoRichiesta.completato)
					.getSingleResult();
			return count>0;
		}
	}
	

}
