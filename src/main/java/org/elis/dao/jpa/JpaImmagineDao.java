package org.elis.dao.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elis.dao.definition.ImmagineDao;
import org.elis.progetto.model.Immagine;
import org.elis.progetto.model.Utente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaImmagineDao implements ImmagineDao{
	private EntityManagerFactory emf;

	public JpaImmagineDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaImmagineDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void insert(Immagine i) {
		try (EntityManager em = emf.createEntityManager()) {
	        EntityTransaction t = em.getTransaction();
	        t.begin();

	        if (i.getIsFotoProfilo()) {
	            List<Immagine> vecchiaFoto = em.createQuery(
	                "SELECT i FROM Immagine i WHERE i.utente.id = :uId AND i.isFotoProfilo = true", Immagine.class)
	                .setParameter("uId", i.getUtente().getId())
	                .getResultList();

	            for (Immagine c : vecchiaFoto) {
	                em.remove(c); 
	                
//eliminazioneFile TODO	             
	            }
	        }

	        i.setUtente(em.find(Utente.class, i.getUtente().getId()));

	        em.persist(i);

	        t.commit();
	    } catch (Exception e) {
	        e.printStackTrace();
	        throw e;
	    }}

	@Override
	public Immagine selectById(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
            return em.find(Immagine.class, id);
        }
	}

	@Override
	public List<Immagine> selectAll() {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT i FROM Immagine i", Immagine.class).getResultList();
        }
	}

	@Override
	public void update(Immagine i) {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(i);
            t.commit();
        }
	}

	@Override
	public void delete(Long id) {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Immagine i = em.find(Immagine.class, id);
            if (i != null) em.remove(i);
            t.commit();
        }
		
	}

	@Override
	public List<Immagine> selectByIdUtente(Long idUtente) {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT i FROM Immagine i WHERE i.utente.id = :uId", Immagine.class)
                     .setParameter("uId", idUtente)
                     .getResultList();
        }
	}

	@Override
	public Map<Long, String> getMappaFotoProfilo(List<Long> id_utenti) throws Exception {
		Map<Long, String> mappa = new HashMap<>();
        if (id_utenti == null || id_utenti.isEmpty()) return mappa;

        try (EntityManager em = emf.createEntityManager()) {
            List<Immagine> foto = em.createQuery(
                "SELECT i FROM Immagine i WHERE i.utente.id IN :ids AND i.isFotoProfilo = true", Immagine.class)
                .setParameter("ids", id_utenti)
                .getResultList();

            for (Immagine i : foto) {
                mappa.put(i.getUtente().getId(), i.getPercorso());
            }
        }
        return mappa;
    }
	
	
	
	
	}


