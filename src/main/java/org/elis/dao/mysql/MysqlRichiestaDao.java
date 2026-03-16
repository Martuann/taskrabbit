package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;

public class MysqlRichiestaDao implements RichiestaDao {
	
	private DataSource dataSource;

	public MysqlRichiestaDao(DataSource dataSource) {
		super();
		this.dataSource = dataSource;
	}
	
	@Override
	public void insert(Richiesta r) {
		String sql = "INSERT INTO richiesta (descrizione, data, orario_inizio, orario_fine, costoeffettivo, indirizzo, stato, id_utenteRichiedente, id_utenteRichiesto, id_professione, id_veicolo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		if(r.getIdVeicolo()==null) {
			sql = "INSERT INTO richiesta (descrizione, data, orario_inizio, orario_fine, costoeffettivo, indirizzo, stato, id_utenteRichiedente, id_utenteRichiesto, id_professione) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		}
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement insert = connection.prepareStatement(sql)) {
			
			insert.setString(1, r.getDescrizione());
			insert.setDate(2, Date.valueOf(r.getData()));
			insert.setTime(3, Time.valueOf(r.getOrarioInizio()));
			insert.setTime(4, Time.valueOf(r.getOrarioFine()));
			insert.setBigDecimal(5, r.getCostoEffettivo());
			insert.setString(6, r.getIndirizzo());
			insert.setInt(7, r.getStato().ordinal());
			insert.setLong(8, r.getIdUtenteRichiedente());
			insert.setLong(9, r.getIdUtenteRichiesto());
			insert.setLong(10, r.getIdProfessione());
			if(r.getIdVeicolo()!=null) {
				insert.setLong(11, r.getIdVeicolo());
			}
			
			insert.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Richiesta selectById(Long id) {
		String sql = "SELECT * FROM richiesta WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql)) {
			
			select.setLong(1, id);
			
			try (ResultSet rs = select.executeQuery()) {
				if(rs.next()) {
					Richiesta richiesta = new Richiesta(
						rs.getLong("id"),
						rs.getString("descrizione"),
						rs.getDate("data").toLocalDate(),
						rs.getTime("orario_inizio").toLocalTime(),
						rs.getTime("orario_fine").toLocalTime(),
						rs.getBigDecimal("costoeffettivo"),
						rs.getString("indirizzo"),
						StatoRichiesta.values()[rs.getInt("stato")],
						rs.getLong("id_utenteRichiedente"),
						rs.getLong("id_utenteRichiesto"),
						rs.getLong("id_professione"),
						rs.getLong("id_veicolo"));
					if(rs.wasNull()) {
						richiesta.setIdVeicolo(null);
					}
					return richiesta;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<Richiesta> selectAll() {
		String sql = "SELECT * FROM richiesta";
		List<Richiesta> richieste = new ArrayList<>();
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql);
			 ResultSet rs = select.executeQuery()) {
			
			while(rs.next()) {
				Richiesta richiesta = new Richiesta(
					rs.getLong("id"),
					rs.getString("descrizione"),
					rs.getDate("data").toLocalDate(),
					rs.getTime("orario_inizio").toLocalTime(),
					rs.getTime("orario_fine").toLocalTime(),
					rs.getBigDecimal("costoeffettivo"),
					rs.getString("indirizzo"),
					StatoRichiesta.values()[rs.getInt("stato")],
					rs.getLong("id_utenteRichiedente"),
					rs.getLong("id_utenteRichiesto"),
					rs.getLong("id_professione"),
					rs.getLong("id_veicolo"));
				if(rs.wasNull()) {
					richiesta.setIdVeicolo(null);
				}
				richieste.add(richiesta);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return richieste;
	}

	@Override
	public void update(Richiesta r) {
		String sql = "UPDATE richiesta SET descrizione=?, data=?, orario_inizio=?, orario_fine=?, costoeffettivo=?, indirizzo=?, stato=?, id_utenteRichiedente=?, id_utenteRichiesto=?, id_professione=?, id_veicolo=? WHERE id="+r.getId();
		if(r.getIdVeicolo()==null) {
			sql = "UPDATE richiesta SET descrizione=?, data=?, orario_inizio=?, orario_fine=?, costoeffettivo=?, indirizzo=?, stato=?, id_utenteRichiedente=?, id_utenteRichiesto=?, id_professione=? WHERE id="+r.getId();
		}
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement update = connection.prepareStatement(sql)) {
			
			update.setString(1, r.getDescrizione());
			update.setDate(2, Date.valueOf(r.getData()));
			update.setTime(3, Time.valueOf(r.getOrarioInizio()));
			update.setTime(4, Time.valueOf(r.getOrarioFine()));
			update.setBigDecimal(5, r.getCostoEffettivo());
			update.setString(6, r.getIndirizzo());
			update.setInt(7, r.getStato().ordinal());
			update.setLong(8, r.getIdUtenteRichiedente());
			update.setLong(9, r.getIdUtenteRichiesto());
			update.setLong(10, r.getIdProfessione());
			if(r.getIdVeicolo()!=null) {
				update.setLong(11, r.getIdVeicolo());
			}
			
			update.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM richiesta WHERE id = ?";
		
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement delete = connection.prepareStatement(sql)) {
			
			delete.setLong(1, id);
			delete.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Richiesta> selectByIdUtenteRichiesto(Long idUtenteRichiesto) {
		String sql = "SELECT * FROM richiesta WHERE id_utenteRichiesto="+idUtenteRichiesto;
		List<Richiesta> richieste = new ArrayList<>();
		try (Connection connection = dataSource.getConnection();
			 PreparedStatement select = connection.prepareStatement(sql);
			 ResultSet rs = select.executeQuery()) {
			
			while(rs.next()) {
				Richiesta richiesta = new Richiesta(
					rs.getLong("id"),
					rs.getString("descrizione"),
					rs.getDate("data").toLocalDate(),
					rs.getTime("orario_inizio").toLocalTime(),
					rs.getTime("orario_fine").toLocalTime(),
					rs.getBigDecimal("costoeffettivo"),
					rs.getString("indirizzo"),
					StatoRichiesta.values()[rs.getInt("stato")],
					rs.getLong("id_utenteRichiedente"),
					rs.getLong("id_utenteRichiesto"),
					rs.getLong("id_professione"),
					rs.getLong("id_veicolo"));
				if(rs.wasNull()) {
					richiesta.setIdVeicolo(null);
				}
				richieste.add(richiesta);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return richieste;
	}
}
