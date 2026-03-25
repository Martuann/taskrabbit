package org.elis.dao.jpa;

import java.util.ArrayList;
import java.util.List;

import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;

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
	public void insert(Richiesta r) {
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
	public Richiesta selectById(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT r FROM Richiesta r " +
                          "JOIN FETCH r.utenteRichiedente " +
                          "JOIN FETCH r.utenteRichiesto " +
                          "JOIN FETCH r.professione " +
                          "LEFT JOIN FETCH r.veicolo " +
                          "WHERE r.id = :id";
            return em.createQuery(jpql, Richiesta.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (Exception e) {
            return null;
        }
	}

	@Override
	public List<Richiesta> selectAll() {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Richiesta r", Richiesta.class).getResultList();
        }
	}

	@Override
	public void update(Richiesta r) {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(r);
            t.commit();
        }		
	}

	@Override
	public void delete(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Richiesta r = em.find(Richiesta.class, id);
            if (r != null) em.remove(r);
            t.commit();
        }
		
	}

	

	@Override
	public List<Richiesta> selectByIdUtenteRichiedente(Long idUtenteRichiedente) {
		  try (EntityManager em = emf.createEntityManager()) {
		       
		        String jpql = "SELECT r FROM Richiesta r " +
		                      "JOIN FETCH r.utenteRichiesto u " + 
		                      "JOIN FETCH r.professione p " +
		                      "LEFT JOIN FETCH u.immagini i ON i.isFotoProfilo = true " +
		                      "WHERE r.utenteRichiedente.id = :id " +
		                      "ORDER BY r.data DESC";

		        return em.createQuery(jpql, Richiesta.class)
		                 .setParameter("id", idUtenteRichiedente)
		                 .getResultList();
		    } catch (Exception e) {
		        e.printStackTrace();
		        return new ArrayList<>();
		    }
	}

	@Override
	public List<Richiesta> selectByIdUtenteRichiesto(Long idUtenteRichiesto) {
		try (EntityManager em = emf.createEntityManager()) {
	        
	        String jpql = "SELECT r FROM Richiesta r " +
	                      "JOIN FETCH r.utenteRichiedente ur " + 
	                      "JOIN FETCH r.professione p " +
	                      "LEFT JOIN FETCH ur.immagini i ON i.isFotoProfilo = true " +
	                      "WHERE r.utenteRichiesto.id = :id " +
	                      "ORDER BY r.data DESC";

	        return em.createQuery(jpql, Richiesta.class)
	                 .setParameter("id", idUtenteRichiesto)
	                 .getResultList();
	    } catch (Exception e) {
	        e.printStackTrace();
	        return new ArrayList<>();
	    }
	}
	
	
	
}
