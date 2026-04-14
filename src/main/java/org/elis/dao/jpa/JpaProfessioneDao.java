package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.definition.ProfessioneDao;
import org.elis.progetto.model.Professione;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaProfessioneDao implements ProfessioneDao{
	private EntityManagerFactory emf;

	public JpaProfessioneDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaProfessioneDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void insert(Professione p) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(p);
            t.commit();
        }		
	}

	@Override
	public Professione selectById(Long id) throws Exception{
		try (EntityManager em = emf.createEntityManager()) {
            return em.find(Professione.class, id);
        }
	}

	@Override
	public List<Professione> selectAll()throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT p FROM Professione p", Professione.class).getResultList();
        }
	}

	@Override
	public void update(Professione p) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(p);
            t.commit();
        }
		
	}

	@Override
	public void delete(Long id)throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Professione p = em.find(Professione.class, id);
            if (p != null) em.remove(p);
            t.commit();
        }		
	}

	@Override
	public List<Professione> selectbyUtente(Long id_utente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
      
            String jpql = "SELECT p FROM Professione p " +
                          "JOIN UtenteProfessione up ON p.id = up.professione.id " +
                          "WHERE up.utente.id = :uId";
            
            return em.createQuery(jpql, Professione.class)
                     .setParameter("uId", id_utente)
                     .getResultList();
        }
    }
	}


