package org.elis.dao.jpa;

import java.time.DayOfWeek;
import java.util.List;

import org.elis.dao.definition.OrarioBaseDao;
import org.elis.progetto.model.OrarioBase;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaOrarioBaseDao implements OrarioBaseDao {
	private EntityManagerFactory emf;

	public JpaOrarioBaseDao() {
		// TODO Auto-generated constructor stub
	}

	public JpaOrarioBaseDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}

	@Override
	public void salvaOrario(OrarioBase orario) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
	        em.getTransaction().begin();
	        
	        String jpql = "SELECT o FROM OrarioBase o WHERE o.utente.id = :uId AND o.giornoSettimana = :giorno";
	        OrarioBase esistente = em.createQuery(jpql, OrarioBase.class)
	            .setParameter("uId", orario.getUtente().getId())
	            .setParameter("giorno", orario.getGiornoSettimana())
	            .getResultStream().findFirst().orElse(null);

	        if (esistente != null) {
	            esistente.setOraInizio(orario.getOraInizio());
	            esistente.setOraFine(orario.getOraFine());
	            em.merge(esistente);
	        } else {
	            em.persist(orario);
	        }
	        
	        em.getTransaction().commit();
	    }		
	}

	@Override
	public List<OrarioBase> getOrariByUtente(long idUtente) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT o FROM OrarioBase o WHERE o.utente.id = :uId", OrarioBase.class)
                     .setParameter("uId", idUtente)
                     .getResultList();
        }
	}

	@Override
	public void eliminaOrario(long idUtente, int giornoSettimana) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction();
            et.begin();
            
            DayOfWeek giorno = DayOfWeek.of(giornoSettimana);
            
            em.createQuery("DELETE FROM OrarioBase o WHERE o.utente.id = :uId AND o.giornoSettimana = :giorno")
              .setParameter("uId", idUtente)
              .setParameter("giorno", giorno)
              .executeUpdate();
              
            et.commit();
        }
    }
		
	

	@Override
	public OrarioBase getOrariByUtenteEGiorno(long idUtente, DayOfWeek giornodellasettimana) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT o FROM OrarioBase o WHERE o.utente.id = :uId AND o.giornoSettimana = :giorno", OrarioBase.class)
                     .setParameter("uId", idUtente)
                     .setParameter("giorno", giornodellasettimana)
                     .getResultStream().findFirst().orElse(null);
        }
	}

}
