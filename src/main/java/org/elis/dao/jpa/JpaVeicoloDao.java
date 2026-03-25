package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.definition.VeicoloDao;
import org.elis.progetto.model.Veicolo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaVeicoloDao implements VeicoloDao{
	private EntityManagerFactory emf;

	public JpaVeicoloDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaVeicoloDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void aggiungiVeicolo(Veicolo v) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(v);
            t.commit();
        }		
	}

	@Override
	public Veicolo ricercaPerId(long id) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.find(Veicolo.class, id);
        }
	}

	@Override
	public List<Veicolo> getAllVeicoli() throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT v FROM Veicolo v", Veicolo.class).getResultList();
        }
	}

	@Override
	public List<Veicolo> getVeicolibyUtente(Long id_utente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT v FROM Veicolo v " +
                          "JOIN UtenteVeicolo uv ON v.id = uv.veicolo.id " +
                          "WHERE uv.utente.id = :uId";
            
            return em.createQuery(jpql, Veicolo.class)
                     .setParameter("uId", id_utente)
                     .getResultList();
        }
    }
	}


