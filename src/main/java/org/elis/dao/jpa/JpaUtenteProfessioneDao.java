package org.elis.dao.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.progetto.model.UtenteProfessione;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaUtenteProfessioneDao implements UtenteProfessioneDao{
	private EntityManagerFactory emf;

	public JpaUtenteProfessioneDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaUtenteProfessioneDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void insert(UtenteProfessione u)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                em.persist(u);
                et.commit();
            } catch (Exception e) {
                if (et.isActive()) et.rollback();
                e.printStackTrace();
            }
        }	
	}

	@Override
	public UtenteProfessione selectById(Long id)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.find(UtenteProfessione.class, id);
        }
    }
	

	@Override
	public List<UtenteProfessione> selectAll()throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT up FROM UtenteProfessione up", UtenteProfessione.class)
                     .getResultList();
        }
	}

	@Override
	public void update(UtenteProfessione u)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                em.merge(u);
                et.commit();
            } catch (Exception e) {
                if (et.isActive()) et.rollback();
                e.printStackTrace();
            }
        }		
	}

	@Override
	public void delete(Long id) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction();
            try {
                et.begin();
                UtenteProfessione u = em.find(UtenteProfessione.class, id);
                if (u != null) em.remove(u);
                et.commit();
            } catch (Exception e) {
                if (et.isActive()) et.rollback();
                e.printStackTrace();
            }
        }		
	}

	@Override
	public UtenteProfessione selectByIdUtenteIdProfessione(Long idUtente, Long idProfessione)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                "SELECT up FROM UtenteProfessione up WHERE up.utente.id = :idU AND up.professione.id = :idP", 
                UtenteProfessione.class)
                .setParameter("idU", idUtente)
                .setParameter("idP", idProfessione)
                .getSingleResult();
        } catch (Exception e) {
            return null;
        }
	}

	@Override
	public List<UtenteProfessione> selectByUtente(long idUtente)throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                "SELECT up FROM UtenteProfessione up WHERE up.utente.id = :idU", 
                UtenteProfessione.class)
                .setParameter("idU", idUtente)
                .getResultList();
        }	
	}

	@Override
	public Boolean checkTariffeCritiche(Long utenteId)throws Exception{ 
		try (EntityManager em = emf.createEntityManager()) {

		 return em.createQuery("SELECT COUNT(up) FROM UtenteProfessione up " +
                 "WHERE up.utente.id = :idU " +
                 "AND up.tariffaH = 0 ", Long.class).setParameter("idU", utenteId).getSingleResult() > 0;
	   
	}
	}
	
	
	
	
	
	
	
}
