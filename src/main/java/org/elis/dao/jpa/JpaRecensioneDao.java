package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.definition.RecensioneDao;
import org.elis.progetto.model.Recensione;
import org.elis.progetto.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaRecensioneDao implements RecensioneDao{
	private EntityManagerFactory emf;

	public JpaRecensioneDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaRecensioneDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void insert(Recensione r) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            r.setUtenteScrittore(em.find(Utente.class, r.getUtenteScrittore().getId()));
            r.setUtenteRicevente(em.find(Utente.class, r.getUtenteRicevente().getId()));
            
            em.persist(r);
            t.commit();
        }		
	}

	@Override
	public Recensione selectById(Long id) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            return em.find(Recensione.class, id);
        }
    }

	@Override
	public List<Recensione> selectAll() throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Recensione r", Recensione.class).getResultList();
        }
    }

	@Override
	public void update(Recensione r) throws Exception{
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
            Recensione r = em.find(Recensione.class, id);
            if (r != null) em.remove(r);
            t.commit();
        }
    }

	@Override
	public List<Recensione> selectByIdUtenteRicevente(Long idUtenteRicevente)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Recensione r WHERE r.utenteRicevente.id = :uId", Recensione.class)
                     .setParameter("uId", idUtenteRicevente)
                     .getResultList();
        }
    }

	@Override
	public Boolean esisteRecensionePerRichiesta(Long idScrittore, Long idRicevente) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery(
                "SELECT COUNT(r) FROM Recensione r WHERE r.utenteScrittore.id = :sId AND r.utenteRicevente.id = :rId", Long.class)
                .setParameter("sId", idScrittore)
                .setParameter("rId", idRicevente)
                .getSingleResult();
            
            return count > 0;
        }
    }
	

	@Override
	public List<Recensione> selectByIdUtenteScrittore(Long idUtenteScrittore)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT r FROM Recensione r WHERE r.utenteScrittore.id = :uId", Recensione.class)
                     .setParameter("uId", idUtenteScrittore)
                     .getResultList();
        }
	}

	@Override
	public Double selectAvgByUtente(Long idUtente) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
			 return em.createQuery("SELECT avg(r.voto) FROM Recensione r WHERE r.utenteRicevente.id = :uId", Double.class)
                     .setParameter("uId", idUtente)
					 .getSingleResult();	
	
	
	
		}
		}
	
	
}
