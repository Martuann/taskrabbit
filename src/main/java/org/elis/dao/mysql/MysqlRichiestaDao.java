package org.elis.dao.mysql;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import org.elis.dao.definition.RichiestaDao;
import org.elis.progetto.model.Richiesta;
import org.elis.progetto.model.StatoRichiesta;

public class MysqlRichiestaDao implements RichiestaDao{
	Connection connection;

	public MysqlRichiestaDao(Connection connection) {
		super();
		this.connection = connection;
	}
	
	@Override
	public void insert(Richiesta r) {
		String sql = "INSERT INTO richiesta (descrizione,data,orario_inizio,orario_fine,costoeffettivo,indirizzo,"+ 
				 	 "stato,id_utenteRichiedente,id_utenteRichiesto,id_professione,id_veicolo) "+
				 	 "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
		try (PreparedStatement insert = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			insert.setString(1, r.getDescrizione());
			insert.setObject(2, Date.valueOf(r.getData()));
			insert.setObject(3, Time.valueOf(r.getOrarioInizio()));
			insert.setObject(4, Time.valueOf(r.getOrarioFine()));
			insert.setObject(5, r.getCostoEffettivo());
			insert.setString(6, r.getIndirizzo());
			insert.setInt(7, r.getStato().ordinal());
			insert.setLong(8, r.getIdUtenteRichiedente());
			insert.setLong(9, r.getIdUtenteRichiesto());
			insert.setLong(10, r.getIdProfessione());
			insert.setLong(11, r.getIdVeicolo());
			insert.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Richiesta selectById(Long id) {
		String sql = "SELECT * FROM richiesta WHERE id="+id;
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 if(rs.next()) {
				 return new Richiesta(
					rs.getLong("id"),
					rs.getString("descrizione"),
					((Date) rs.getObject("data")).toLocalDate(),
					((Time) rs.getObject("ora_inizio")).toLocalTime(),
					((Time) rs.getObject("ora_fine")).toLocalTime(),
					(BigDecimal) rs.getObject("costoeffettivo"),
					rs.getString("indirizzo"),
					StatoRichiesta.values()[rs.getInt("stato")],
					rs.getLong("id_utenteRichiedente"),
					rs.getLong("id_utenteRichiesto"),
					rs.getLong("id_professione"),
					rs.getLong("id_veicolo")
				 );
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Richiesta> selectAll() {
		String sql = "SELECT * FROM richiesta";
		List<Richiesta> richieste = new ArrayList<>();
		try (PreparedStatement select = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			 ResultSet rs = select.executeQuery();
			 while(rs.next()) {
				 richieste.add(new Richiesta(
					rs.getLong("id"),
					rs.getString("descrizione"),
					((Date) rs.getObject("data")).toLocalDate(),
					((Time) rs.getObject("ora_inizio")).toLocalTime(),
					((Time) rs.getObject("ora_fine")).toLocalTime(),
					(BigDecimal) rs.getObject("costoeffettivo"),
					rs.getString("indirizzo"),
					StatoRichiesta.values()[rs.getInt("stato")],
					rs.getLong("id_utenteRichiedente"),
					rs.getLong("id_utenteRichiesto"),
					rs.getLong("id_professione"),
					rs.getLong("id_veicolo")
				 ));
			 }
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
		return richieste;
	}

	@Override
	public void update(Richiesta r) {
		String sql = "UPDATE richiesta SET descrizione=?,data=?,ora_inizio=?,ora_inizio=?,ora_fine=?,costoeffettivo=?,"+
					 "indirizzo=?,stato=?,id_utenteRichiedente=?,id_utenteRichiesto=?,id_professione=?,id_veicolo=? "+
					 "WHERE id="+r.getId();
		try (PreparedStatement update = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			update.setString(1, r.getDescrizione());
			update.setObject(2, Date.valueOf(r.getData()));
			update.setObject(3, Time.valueOf(r.getOrarioInizio()));
			update.setObject(4, Time.valueOf(r.getOrarioFine()));
			update.setObject(5, r.getCostoEffettivo());
			update.setString(6, r.getIndirizzo());
			update.setInt(7, r.getStato().ordinal());
			update.setLong(8, r.getIdUtenteRichiedente());
			update.setLong(9, r.getIdUtenteRichiesto());
			update.setLong(10, r.getIdProfessione());
			update.setLong(11, r.getIdVeicolo());
			update.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		String sql = "DELETE FROM richiesta WHERE id="+id;
		try (PreparedStatement delete = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			delete.executeUpdate();
	     }
		catch (Exception e) {
			 e.printStackTrace();
		}
	}
}
