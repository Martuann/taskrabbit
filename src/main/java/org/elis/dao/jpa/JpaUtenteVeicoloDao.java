package org.elis.dao.jpa;

import java.math.BigDecimal;
import java.util.List;

import org.elis.dao.definition.UtenteVeicoloDao;
import org.elis.progetto.model.UtenteVeicolo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaUtenteVeicoloDao implements UtenteVeicoloDao{
	private EntityManagerFactory emf;

	public JpaUtenteVeicoloDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaUtenteVeicoloDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void associaVeicolo(UtenteVeicolo uv) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            uv.setUtente(em.find(uv.getUtente().getClass(), uv.getUtente().getId()));
            uv.setVeicolo(em.find(uv.getVeicolo().getClass(), uv.getVeicolo().getId()));
            em.persist(uv);
            t.commit();
        }		
	}

	@Override
	public List<UtenteVeicolo> getDettagliVeicoliUtente(Long idUtente) throws Exception {
		return selectByUtente(idUtente);
	}

	@Override
	public void aggiornaPrezzoServizio(Long idUtente, Long idVeicolo, BigDecimal nuovoPrezzo) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            
            UtenteVeicolo uv = selectByUtenteEVeicolo(idUtente, idVeicolo);
            if (uv != null) {
                uv.setAggiuntaServizio(nuovoPrezzo);
                em.merge(uv); 
            }
            
            t.commit();
        }
		
	}

	@Override
	public void rimuoviAssociazione(Long idUtente, Long idVeicolo) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            
            UtenteVeicolo uv = em.createQuery(
                "SELECT uv FROM UtenteVeicolo uv WHERE uv.utente.id = :uId AND uv.veicolo.id = :vId", 
                UtenteVeicolo.class)
                .setParameter("uId", idUtente)
                .setParameter("vId", idVeicolo)
                .getResultStream().findFirst().orElse(null);
            
            if (uv != null) {
                em.remove(uv);
            }
            
            t.commit();
        }		
	}

	@Override
	public List<UtenteVeicolo> selectByUtente(Long idUtente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
           
            return em.createQuery(
                "SELECT uv FROM UtenteVeicolo uv JOIN FETCH uv.veicolo WHERE uv.utente.id = :id", 
                UtenteVeicolo.class)
                .setParameter("id", idUtente)
                .getResultList();
        }
	}

	@Override
	public UtenteVeicolo selectByUtenteEVeicolo(Long idUtente, Long idVeicolo) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                "SELECT uv FROM UtenteVeicolo uv WHERE uv.utente.id = :uId AND uv.veicolo.id = :vId", 
                UtenteVeicolo.class)
                .setParameter("uId", idUtente)
                .setParameter("vId", idVeicolo)
                .getResultStream().findFirst().orElse(null);
        }
	}

}
