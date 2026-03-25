package org.elis.dao.jpa;

import java.util.List;

import org.elis.dao.definition.UtenteProfessioneDao;
import org.elis.progetto.model.UtenteProfessione;

import jakarta.persistence.EntityManagerFactory;

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
	public void insert(UtenteProfessione u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UtenteProfessione selectById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UtenteProfessione> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(UtenteProfessione u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UtenteProfessione selectByIdUtenteIdProfessione(Long idUtente, Long idProfessione) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UtenteProfessione> selectByUtente(long idUtente) {
		// TODO Auto-generated method stub
		return null;
	}

}
