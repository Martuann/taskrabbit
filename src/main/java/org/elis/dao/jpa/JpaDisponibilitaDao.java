package org.elis.dao.jpa;

import java.time.LocalDate;
import java.util.List;

import org.elis.dao.definition.DisponibilitaDao;
import org.elis.progetto.model.Disponibilita;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaDisponibilitaDao implements DisponibilitaDao {
	private EntityManagerFactory emf;

	public JpaDisponibilitaDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaDisponibilitaDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void aggiungiDisponibilita(Disponibilita d) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.persist(d);
            t.commit();
        }		
	}

	@Override
	public List<Disponibilita> getDisponibilitaPerUtente(Long idUtente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            String jpql = "SELECT d FROM Disponibilita d WHERE d.utente.id = :uId";
            return em.createQuery(jpql, Disponibilita.class)
                     .setParameter("uId", idUtente)
                     .getResultList();
        }
	}

	@Override
	public void rimuoviDisponibilita(Long idDisponibilita) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            Disponibilita d = em.find(Disponibilita.class, idDisponibilita);
            if (d != null) em.remove(d);
            t.commit();
        }
	}

	@Override
	public void aggiornaDisponibilita(Disponibilita d) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(d);
            t.commit();
        }		
	}

	@Override
	public void cancellaTuttePerUtente(Long idUtente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.createQuery("DELETE FROM Disponibilita d WHERE d.utente.id = :uId")
              .setParameter("uId", idUtente)
              .executeUpdate();
            t.commit();
        }		
	}

	@Override
	public void salvaOAggiorna(Disponibilita d) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            
            String jpql = "SELECT d FROM Disponibilita d WHERE d.utente.id = :uId AND d.data = :data";
            Disponibilita esistente = em.createQuery(jpql, Disponibilita.class)
                .setParameter("uId", d.getUtente().getId())
                .setParameter("data", d.getData())
                .getResultStream().findFirst().orElse(null);

            if (esistente != null) {
                esistente.setInizio(d.getInizio());
                esistente.setFine(d.getFine());
                em.merge(esistente);
            } else {
            
                d.setUtente(em.find(d.getUtente().getClass(), d.getUtente().getId()));
                em.persist(d);
            }
            
            t.commit();
        }		
	}

	@Override
	public void rimuoviDisponibilitaByIdUtenteData(Long idUtente, LocalDate data) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            
            String jpql = "DELETE FROM Disponibilita d WHERE d.utente.id = :uId AND d.data = :data";
            em.createQuery(jpql)
              .setParameter("uId", idUtente)
              .setParameter("data", data)
              .executeUpdate();
            
            t.commit();
        }				
	}

}
