package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.dao.definition.DisponibilitaDao;
import org.elis.progetto.model.Disponibilita;

public class MysqlDisponibilitaDao implements DisponibilitaDao {
	private DataSource dataSource;
	public MysqlDisponibilitaDao(DataSource dataSource) {
		this.dataSource = dataSource;	}

	@Override
	public void aggiungiDisponibilita(Disponibilita disponibilita) throws Exception {
String query = "INSERT INTO disponibilita (data, ora_inizio, ora_fine, id_utente) VALUES (?, ?, ?, ?)";
        
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setDate(1, Date.valueOf(disponibilita.getData()));
            preparedStatement.setTime(2, Time.valueOf(disponibilita.getInizio()));
            preparedStatement.setTime(3, Time.valueOf(disponibilita.getFine()));
            preparedStatement.setLong(4, disponibilita.getIdUtente());
            
            preparedStatement.executeUpdate();
        }		
	}

	@Override
	public List<Disponibilita> getDisponibilitaPerUtente(Long idUtente) throws Exception {
		List<Disponibilita> listaDisponibilita = new ArrayList<Disponibilita>();
	    
	    String query = "SELECT * FROM disponibilita WHERE id_utente = ? ORDER BY data ASC, ora_inizio ASC";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setLong(1, idUtente);
	        
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            while (resultSet.next()) {
	                Disponibilita disponibilita = MysqlToDisponibilita(resultSet);
	                listaDisponibilita.add(disponibilita);
	            }
	        }
	    } catch (Exception e) {
	        throw new Exception("Errore nel recupero delle disponibilità per l'utente con ID: " + idUtente, e);
	    }
	    
	    return listaDisponibilita;
	}

	@Override
	public void rimuoviDisponibilita(Long idDisponibilita) throws Exception {
		String query = "DELETE FROM disponibilita WHERE id = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setLong(1, idDisponibilita);
	        preparedStatement.executeUpdate();
	    }
	}		
	

	@Override
	public void aggiornaDisponibilita(Disponibilita disponibilita) throws Exception {
	    String query = "UPDATE disponibilita SET data = ?, ora_inizio = ?, ora_fine = ? WHERE id = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setDate(1, Date.valueOf(disponibilita.getData()));
	        preparedStatement.setTime(2, Time.valueOf(disponibilita.getInizio()));
	        preparedStatement.setTime(3, Time.valueOf(disponibilita.getFine()));
	        
	        preparedStatement.setLong(4, disponibilita.getId());
	        
	        preparedStatement.executeUpdate();
	    } catch (Exception e) {
	        throw new Exception("Errore durante l'aggiornamento della disponibilita con ID: " + disponibilita.getId(), e);
	    }		
	}

	@Override
	public void cancellaTuttePerUtente(Long idUtente) throws Exception {
		String query = "DELETE FROM disponibilita WHERE id_utente = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setLong(1, idUtente);
	        preparedStatement.executeUpdate();
	    }
	}		
	
	
	
	private Disponibilita MysqlToDisponibilita(ResultSet resultSet) throws Exception {
	    Long id = resultSet.getLong("id");
	    Long idUtente = resultSet.getLong("id_utente");
	    LocalDate data = resultSet.getDate("data").toLocalDate();
	    LocalTime inizio = resultSet.getTime("ora_inizio").toLocalTime();
	    LocalTime fine = resultSet.getTime("ora_fine").toLocalTime();
	    
	    return new Disponibilita(id, idUtente, data, inizio, fine);
	}
	
	@Override
	public void salvaOAggiorna(Disponibilita d) throws Exception {
	    String queryCerca = "SELECT id FROM disponibilita WHERE id_utente = ? AND data = ?";
	    
	    try (Connection c = dataSource.getConnection()) {
	        Long idEsistente =null;
	        
	        try (PreparedStatement psCerca = c.prepareStatement(queryCerca)) {
	            psCerca.setLong(1, d.getIdUtente());
	            psCerca.setDate(2, java.sql.Date.valueOf(d.getData()));
	            try (ResultSet rs = psCerca.executeQuery()) {
	                if (rs.next()) {
	                    idEsistente = rs.getLong("id");
	                }
	            }
	        }

	        if (idEsistente != null) {
	            String queryUpdate = "UPDATE disponibilita SET ora_inizio = ?, ora_fine = ? WHERE id = ?";
	            try (PreparedStatement psUpdate = c.prepareStatement(queryUpdate)) {
	                psUpdate.setTime(1, java.sql.Time.valueOf(d.getInizio()));
	                psUpdate.setTime(2, java.sql.Time.valueOf(d.getFine()));
	                psUpdate.setLong(3, idEsistente);
	                psUpdate.executeUpdate();
	            }
	        } else {
	            String queryInsert = "INSERT INTO disponibilita (id_utente, data, ora_inizio, ora_fine) VALUES (?, ?, ?, ?)";
	            try (PreparedStatement psInsert = c.prepareStatement(queryInsert)) {
	                psInsert.setLong(1, d.getIdUtente());
	                psInsert.setDate(2, java.sql.Date.valueOf(d.getData()));
	                psInsert.setTime(3, java.sql.Time.valueOf(d.getInizio()));
	                psInsert.setTime(4, java.sql.Time.valueOf(d.getFine()));
	                psInsert.executeUpdate();
	            }
	        }
	    }
	}
	
	
	
	
	
	@Override
	public void rimuoviDisponibilitaByIdUtenteData(Long idUtente, LocalDate data) throws Exception {
	    String query = "DELETE FROM disponibilita WHERE id_utente = ? AND data = ?";
	    
	    try (Connection connection = dataSource.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	        
	        preparedStatement.setLong(1, idUtente);
	        preparedStatement.setDate(2, Date.valueOf(data));
	        preparedStatement.executeUpdate();
	    }
	}
	
	
	
	
	
	
	
	
	
	
	
}
