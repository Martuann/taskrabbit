package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.definition.CittaDao;
import org.elis.progetto.model.Citta;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class JpaCittaDao implements CittaDao {
	private EntityManagerFactory emf;
	public JpaCittaDao() {
		// TODO Auto-generated constructor stub
	}
	public JpaCittaDao(EntityManagerFactory emf) {
		super();
		this.emf = emf;
	}
	@Override
	public Long aggiungiCitta(Citta citta) throws Exception {
        try(EntityManager em=emf.createEntityManager()){
        	EntityTransaction transaction = em.getTransaction();
        	transaction.begin();
        	em.persist(citta);
        	transaction.commit();
        	return citta.getId();
        	
        	
        }
		
	}


	@Override
	public boolean esisteCitta(Citta citta) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(c) FROM Citta c WHERE c.nome = :nome", Long.class)
                           .setParameter("nome", citta.getNome())
                           .getSingleResult();
            return count > 0;
        }
	}
	@Override
	public List<Citta> getAllCitta() throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
	        return em.createQuery("SELECT c FROM Citta c", Citta.class).getResultList();
	    }
	}
	@Override
	public List<Citta> getCittaByProvincia(String provincia) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT c FROM Citta c WHERE c.provincia = :prov", Citta.class)
                     .setParameter("prov", provincia)
                     .getResultList();
        }
    }
	
	@Override
	public void aggiornaCitta(Citta citta) throws Exception {
		try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction t = em.getTransaction();
            t.begin();
            em.merge(citta); 
            t.commit();
        }		
	}
	@Override
	public void rimuoviCitta(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Citta selectById(Long id) throws Exception {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Citta.class, id);
        }
    }
	@Override
	public Citta getCitta(Citta citta) throws Exception {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT c FROM Citta c WHERE c.nome = :nome", Citta.class)
                     .setParameter("nome", citta.getNome())
                     .getResultStream().findFirst().orElse(null);
        }
	}
	@Override
	public Citta getByName(String cittaDaPulire) throws Exception {
		    try (EntityManager em = emf.createEntityManager()) {
		        
		        String jpql = "SELECT c FROM Citta c WHERE c.nome = :nome";
		        
		        return em.createQuery(jpql, Citta.class)
		                 .setParameter("nome", cittaDaPulire)
		                 .getResultStream()
		                 .findFirst()
		                 .orElse(null);
		                 
		    } 
		    
		}
	

}
