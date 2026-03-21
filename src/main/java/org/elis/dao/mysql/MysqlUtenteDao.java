package org.elis.dao.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.elis.dao.definition.UtenteDao;
import org.elis.progetto.model.Ruolo;
import org.elis.progetto.model.Utente;
import org.elis.utilities.DataSourceConfig;

public class MysqlUtenteDao implements UtenteDao {
	private DataSource dataSource;

	public MysqlUtenteDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	// Long id, String nome,String cognome ,String email, String telefono, String password, LocalDate ddn, String cf,
	//	Ruolo ruolo, Long idCitta

	@Override
	public Long aggiungiUtente(Utente utente) throws Exception {
		String query = "INSERT INTO utente (nome, cognome, email, telefono, password, ddn, cf, ruolo, id_citta) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";


		try (Connection connection = dataSource.getConnection();
				PreparedStatement insertStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

			insertStatement.setString(1, utente.getNome());
			insertStatement.setString(2, utente.getCognome());
			insertStatement.setString(3, utente.getEmail());
			insertStatement.setString(4, utente.getTelefono());
			insertStatement.setString(5, utente.getPassword());
			insertStatement.setDate(6, Date.valueOf(utente.getDdn()));
			insertStatement.setString(7, utente.getCf());
			insertStatement.setInt(8, utente.getRuolo().ordinal());
			insertStatement.setLong(9, utente.getIdCitta());

			int colonneInserite = insertStatement.executeUpdate();

			if (colonneInserite == 0) {
				throw new Exception("La creazione dell'utente è fallita, nessuna riga aggiunta nel DB.");
			}

			try (ResultSet IdGenerator = insertStatement.getGeneratedKeys()) {
				if (IdGenerator.next()) {
					Long nuovoId = IdGenerator.getLong(1);

					utente.setId(nuovoId);

					return nuovoId;
				} else {
					throw new Exception("La creazione dell'utente è fallita, non è stato possibile ottenere l'ID.");
				}
			}
		}
	}


	@Override
	public void aggiornaUtente(Utente utente) throws Exception {
		String query = "UPDATE utente SET nome = ?, cognome = ?, email = ?, telefono = ?, password = ?, ddn = ?, cf = ?, ruolo = ?, id_citta = ? WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, utente.getNome());
			preparedStatement.setString(2, utente.getCognome());
			preparedStatement.setString(3, utente.getEmail());
			preparedStatement.setString(4, utente.getTelefono());
			preparedStatement.setString(5, utente.getPassword());
			preparedStatement.setDate(6, Date.valueOf(utente.getDdn()));
			preparedStatement.setString(7, utente.getCf());
			preparedStatement.setInt(8, utente.getRuolo().ordinal());
			preparedStatement.setLong(9, utente.getIdCitta());
			preparedStatement.setLong(10, utente.getId());

			preparedStatement.executeUpdate();
		}
	}


	@Override
	public void rimuoviUtente(Long id) throws Exception {

	}



	@Override
	public Utente login(String email, String password) throws Exception {

		String query = "SELECT * FROM utente WHERE email = ? AND BINARY password = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, email);
			preparedStatement.setString(2, password);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return MysqlToUtente(resultSet);
				}
			}
		} catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Errore durante il login", e);
	    }
	    return null;
	}


	@Override
	public boolean presenzaUtente(String email) throws Exception {
		String query = "SELECT COUNT(*) FROM utente WHERE email = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return resultSet.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	@Override
	public Utente ricercaTramiteEmail(String email) throws Exception {
		String query = "SELECT * FROM utente WHERE email = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, email);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return MysqlToUtente(resultSet);

				}
			}
		} catch (Exception e) {
			throw new Exception("Errore durante la ricerca dell'utente tramite email: " + email, e);
		}


		return null;
	}

	@Override
	public Utente ricercaPerId(Long id) throws Exception {
		String query = "SELECT * FROM utente WHERE id = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setLong(1, id);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return MysqlToUtente(resultSet);
				}
			}
		}
		return null;
	}

	@Override
	public List<Utente> getUtentiProfessionisti() throws Exception {
		List<Utente> listaProfessionisti = new ArrayList<Utente>();

		String query = "SELECT * FROM utente WHERE ruolo = 1";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query);
				ResultSet resultSet = preparedStatement.executeQuery()) {

			while (resultSet.next()) {
				Utente professionista = MysqlToUtente(resultSet);

				listaProfessionisti.add(professionista);
			}
		} catch (Exception e) {
			throw new Exception("Errore durante il recupero dei professionisti dal database", e);
		}

		return listaProfessionisti;
	}

	@Override
	public List<Utente> ricercaTramiteProfessione(String professione) throws Exception {
		List<Utente> listaRisultato = new ArrayList<>();

		String query = "SELECT u.* FROM utente u " +
				"JOIN utente_professione up ON u.id = up.id_utente " +
				"JOIN professione p ON p.id = up.id_professione " +
				"WHERE p.nome = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, professione);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					Utente professionista = MysqlToUtente(resultSet);
					listaRisultato.add(professionista);
				}
			}
		} catch (Exception e) {
			throw new Exception("Errore nella ricerca per professione: " + professione, e);
		}

		return listaRisultato;	}
	private Utente MysqlToUtente(ResultSet resultSet) throws Exception {
		Utente utente = new Utente();

		Long id = resultSet.getLong("id");
		String nome = resultSet.getString("nome");
		String cognome = resultSet.getString("cognome");
		String email = resultSet.getString("email");
		String telefono = resultSet.getString("telefono");
		String password = resultSet.getString("password");

		Date dataSql = resultSet.getDate("ddn");
		LocalDate dataDiNascita = dataSql.toLocalDate();

		String codiceFiscale = resultSet.getString("cf");

		int indiceRuolo = resultSet.getInt("ruolo");
		Ruolo ruoloEnum = Ruolo.values()[indiceRuolo];

		Long idCitta = resultSet.getLong("id_citta");

		utente.setId(id);
		utente.setNome(nome);
		utente.setCognome(cognome);
		utente.setEmail(email);
		utente.setTelefono(telefono);
		utente.setPassword(password);
		utente.setDdn(dataDiNascita);
		utente.setCf(codiceFiscale);
		utente.setRuolo(ruoloEnum);
		utente.setIdCitta(idCitta);

		return utente;
	}


	@Override
	public Map<Utente, String> getRecensoriConFoto(Long id_utenteRicevente) throws Exception {
		Map<Utente, String> mappaRecensori = new LinkedHashMap<>();

		String sql = "SELECT u.*, i.percorso AS foto_percorso " +
				"FROM utente u " +
				"JOIN recensione r ON u.id = r.id_utenteScrittore " +
				"LEFT JOIN immagine i ON u.id = i.id_utente AND i.isfotoprofilo = 1 " +
				"WHERE r.id_utenteRicevente = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(sql)) {

			ps.setLong(1, id_utenteRicevente);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					Utente u = MysqlToUtente(rs);

					String percorso = rs.getString("foto_percorso");
					if (percorso == null || percorso.isEmpty()) {
						percorso = "img/default-avatar.png";
					}

					mappaRecensori.put(u, percorso);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore nel recupero della mappa recensori", e);
		}
		return mappaRecensori;
	}



	@Override
	public List<Utente> ricercaLikeProfessione(String professione) throws Exception {
		List<Utente> listaRisultato = new ArrayList<>();

		String query = "SELECT u.* FROM utente u " +
				"JOIN utente_professione up ON u.id = up.id_utente " +
				"JOIN professione p ON p.id = up.id_professione " +
				"WHERE p.nome LIKE  ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(query)) {

			preparedStatement.setString(1, "%" + professione + "%");
			
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {

					Utente professionista = MysqlToUtente(resultSet);
					listaRisultato.add(professionista);
				}
			}
		} catch (Exception e) {
			throw new Exception("Errore nella ricerca per professione: " + professione, e);
		}

		return listaRisultato;	}

	@Override
	public boolean presenzaCodiceFiscale(String codiceFiscale) throws Exception {

	    String query = "SELECT COUNT(*) FROM utente WHERE cf = ?";

	    try (Connection connection = dataSource.getConnection();
	            PreparedStatement ps = connection.prepareStatement(query)) {

	        ps.setString(1, codiceFiscale);

	        try (ResultSet rs = ps.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt(1) > 0;
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new Exception("Errore durante il controllo del Codice Fiscale", e);
	    }
	    return false;
	}

	@Override
	public boolean presenzaTelefono(String telefono) throws Exception {

		String query = "SELECT COUNT(*) FROM utente WHERE telefono = ?";

		try (Connection connection = dataSource.getConnection();
				PreparedStatement ps = connection.prepareStatement(query)) {

			ps.setString(1, telefono);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Errore durante il controllo del telefono", e);
		}
		return false;
	}

}



