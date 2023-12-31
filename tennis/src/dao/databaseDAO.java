package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.campo;
import model.gestore;
import model.istruttore;
import model.prenotazione;
import model.utente;

/**
 * AbstractDAO for API CRUD
 */
public class databaseDAO {
	private String url = "jdbc:mysql://localhost:3306/tennisApp";
	private String jdbcUsername = "root";
	private String jdbcPassword = "";//per gilbe root

	// select all tables SQL
	private static final String SELECT_ALL_USERS = "select * from utente";
	private static final String SELECT_ALL_FIELDS = "select * from campo";
	private static final String SELECT_ALL_RESERV = "select * from prenotazione order by dataOra";
	private static final String SELECT_ALL_INSTRU = "select * from istruttore";
	private static final String SELECT_MANAGER = "select * from gestore";
	private static final String SELECT_INSTRU = "select * from istruttore where id = ?;";

	// insert in all tables SQL
	private static final String INSERT_USERS_SQL = "INSERT INTO utente"
			+ "  (nome, cognome, eta, sesso, email, telefono, username, password) VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_FIELDS_SQL = "INSERT INTO campo"
			+ "  (tipo, prezzo, valutazione, coperto, codice) VALUES " + " (?, ?, ?, ?, ?);";
	private static final String INSERT_INSTRU_SQL = "INSERT INTO istruttore"
			+ "  (nome, cognome, eta, sesso, email, telefono, username, password," + " esperienza, oreLezione, pagaOraria) "
			+ "VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static final String INSERT_RESERV_NO_ISTR_SQL = "INSERT INTO prenotazione"
			+ "  (dataOra, durata, prezzo, partecipanti, campo, tipo, istruttore) VALUES "
			+ " (?, ?, ?, ?, ?, ?, NULL);";
	private static final String INSERT_RESERV_SQL = "INSERT INTO prenotazione"
			+ "  (dataOra, durata, prezzo, partecipanti, campo, istruttore," + " tipo) VALUES "
			+ " (?, ?, ?, ?, ?, ?, ?);";

	// delete from all tables SQL
	private static final String DELETE_USERS_SQL = "delete from utente where id = ?;";
	private static final String DELETE_FIELDS_SQL = "delete from campo where id = ?;";
	private static final String DELETE_RESERV_SQL = "delete from prenotazione where id = ?;";
	private static final String DELETE_INSTRU_SQL = "delete from istruttore where id = ?;";

	// update all tables SQL
	private static final String UPDATE_USERS_SQL = "update utente set sesso = ?, email = ?, telefono = ?, username = ?, password = ? where id = ?;";
	private static final String UPDATE_FIELDS_SQL = "update campo set prezzo = ?, valutazione = ?, coperto = ?, codice = ? where id = ?;";
	private static final String UPDATE_RESERV_SQL = "update prenotazione set  dataOra = ?, durata = ?, prezzo = ? where id = ?;";
	private static final String UPDATE_INSTRU_SQL = "update istruttore set sesso = ?, email = ?, telefono = ?, username = ?, password = ?, esperienza = ?,"
			+ " oreLezione = ?, pagaOraria = ? where id = ?;";

	public databaseDAO() {}
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}

	// CRUD API GET
	public List<utente> selectUsers() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<utente> users = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nome");
				String surname = rs.getString("cognome");
				int age = rs.getInt("eta");
				char sesso = rs.getString("sesso").charAt(0);
				String email = rs.getString("email");
				String telephone = rs.getString("telefono");
				String user = rs.getString("username");
				String password = rs.getString("password");
				users.add(new utente(id, name, surname, age, email, telephone, user, password, sesso));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return users;
	}

	public List<campo> selectFields() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<campo> fields = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FIELDS);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String type = rs.getString("tipo");
				float price = rs.getFloat("prezzo");
				int evaluation = rs.getInt("valutazione");
				boolean cover = (rs.getBoolean("coperto"));
				String code = rs.getString("codice");
				fields.add(new campo(id, type, cover, price, evaluation, code));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return fields;
	}

	public List<istruttore> selectInstructors() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<istruttore> istr = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_INSTRU);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nome");
				String surname = rs.getString("cognome");
				int age = rs.getInt("eta");
				char sesso = rs.getString("sesso").charAt(0);
				String email = rs.getString("email");
				String telephone = rs.getString("telefono");
				String user = rs.getString("username");
				String password = rs.getString("password");
				int experience = rs.getInt("esperienza");
				int hour = rs.getInt("oreLezione");
				float paid = rs.getFloat("pagaOraria");
				istr.add(new istruttore(id, name, surname, age, sesso, email, telephone, user, password, experience, hour,
						paid));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return istr;
	}
	
	public istruttore selectInstructor(int id) {
		istruttore istr = null;
		// using try-with-resources to avoid closing resources (boiler plate code)
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_INSTRU);) {
			// Step 3: Execute the query or update query
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			// Step 4: Process the ResultSet object.
			
			String name = rs.getString("nome");
			String surname = rs.getString("cognome");
			int age = rs.getInt("eta");
			char sesso = rs.getString("sesso").charAt(0);
			String email = rs.getString("email");
			String telephone = rs.getString("telefono");
			String user = rs.getString("username");
			String password = rs.getString("password");
			int experience = rs.getInt("esperienza");
			int hour = rs.getInt("oreLezione");
			float paid = rs.getFloat("pagaOraria");
			
			istr = new istruttore(id, name, surname, age, sesso, email, telephone, user, password, experience, hour,
					paid);
		} catch (SQLException e) {
			System.err.println(e);
		}
		return istr;
	}
	

	public List<prenotazione> selectReserv() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<prenotazione> reserv = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESERV);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String date = rs.getString("dataOra");
				int durata = rs.getInt("durata");
				float price = rs.getFloat("prezzo");
				String part = rs.getString("partecipanti");
				int field = rs.getInt("campo");
				int istr = rs.getInt("istruttore");
				int type = rs.getInt("tipo");
				reserv.add(new prenotazione(id, date, durata, price, part, field, istr, type));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return reserv;
	}

	public List<gestore> selectManager() {

		// using try-with-resources to avoid closing resources (boiler plate code)
		List<gestore> manager = new ArrayList<>();
		// Step 1: Establishing a Connection
		try (Connection connection = getConnection();

				// Step 2:Create a statement using connection object
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MANAGER);) {
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();

			// Step 4: Process the ResultSet object.
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("nome");
				String surname = rs.getString("cognome");
				int age = rs.getInt("eta");
				char sesso = rs.getString("sesso").charAt(0);
				String email = rs.getString("email");
				String telephone = rs.getString("telefono");
				String user = rs.getString("username");
				String password = rs.getString("password");
				String vat = rs.getString("partitaIva");
				manager.add(new gestore(id, name, surname, age, email, telephone, user, password, sesso, vat));
			}
		} catch (SQLException e) {
			System.err.println(e);
		}
		return manager;
	}
	
	// CRUD API POST/PUT
	public boolean insertUser(utente user) throws SQLException {
		boolean insertRow = false;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getNome());
			preparedStatement.setString(2, user.getCognome());
			preparedStatement.setInt(3, user.getEta());
			preparedStatement.setString(4, Character.toString(user.getSesso()));
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.setString(6, user.getNumero());
			preparedStatement.setString(7, user.getUsername());
			preparedStatement.setString(8, user.getPassword());
			insertRow = preparedStatement.executeUpdate()>0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return insertRow;
	}

	public boolean insertInstru(istruttore istr) throws SQLException {
		boolean insertRow = false;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INSTRU_SQL)) {
			preparedStatement.setString(1, istr.getNome());
			preparedStatement.setString(2, istr.getCognome());
			preparedStatement.setInt(3, istr.getEta());
			preparedStatement.setString(4, Character.toString(istr.getSesso()));
			preparedStatement.setString(5, istr.getEmail());
			preparedStatement.setString(6, istr.getNumero());
			preparedStatement.setString(7, istr.getUsername());
			preparedStatement.setString(8, istr.getPassword());
			preparedStatement.setInt(9, istr.getEsperienza());
			preparedStatement.setInt(10, istr.getOreLezione()); 
			preparedStatement.setFloat(11, istr.getPagaOraria());
			insertRow = preparedStatement.executeUpdate()>0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return insertRow;
	}

	public boolean insertField(campo c) throws SQLException {
		boolean insertRow = false;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FIELDS_SQL)) {
			preparedStatement.setString(1, c.getTipo());
			preparedStatement.setFloat(2, c.getPrezzo());
			preparedStatement.setInt(3, c.getValutazione());
			preparedStatement.setBoolean(4, c.isCoperto());
			preparedStatement.setString(5, c.getCodice());
			insertRow = preparedStatement.executeUpdate()>0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return insertRow;
	}

	public boolean insertReserv(prenotazione p) throws SQLException {
		boolean insertRow = false;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERV_SQL)) {
			preparedStatement.setString(1, p.getDataOra());
			preparedStatement.setInt(2, p.getDurata());
			preparedStatement.setFloat(3, p.getPrezzo());
			preparedStatement.setString(4, p.getPartecipanti());
			preparedStatement.setInt(5, p.getCampo());
			preparedStatement.setInt(6, p.getIstruttore());
			preparedStatement.setInt(7, p.getTipo());
			insertRow = preparedStatement.executeUpdate()>0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return insertRow;
	}
	
	public boolean insertReservNoIstr(prenotazione p) throws SQLException {
		boolean insertRow = false;
		// try-with-resource statement will auto close the connection.
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERV_NO_ISTR_SQL)) {
			preparedStatement.setString(1, p.getDataOra());
			preparedStatement.setInt(2, p.getDurata());
			preparedStatement.setFloat(3, p.getPrezzo());
			preparedStatement.setString(4, p.getPartecipanti());
			preparedStatement.setInt(5, p.getCampo());
			preparedStatement.setInt(6, p.getTipo());
			insertRow = preparedStatement.executeUpdate()>0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return insertRow;
	}

//CRUD API DELETE 

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean deleteField(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_FIELDS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean deleteReserv(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_RESERV_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

	public boolean deleteInstru(int id) throws SQLException {
		boolean rowDeleted = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_INSTRU_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}

//CRUD API PATCH
	
	public boolean updateUser(utente user) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			statement.setString(1, Character.toString(user.getSesso()));
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getNumero());
			statement.setString(4, user.getUsername());
			statement.setString(5, user.getPassword());
			statement.setInt(6, user.getId());
			rowUpdated = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rowUpdated;
	}
	
	public boolean updateField(campo c) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_FIELDS_SQL);) {
			statement.setFloat(1, c.getPrezzo());
			statement.setInt(2, c.getValutazione());
			statement.setBoolean(3, c.isCoperto());
			statement.setString(4, c.getCodice());
			statement.setInt(5, c.getId());
			rowUpdated = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rowUpdated;
	}

	public boolean updateReserv(prenotazione p) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_RESERV_SQL);) {
			statement.setString(1, p.getDataOra());
			statement.setInt(2, p.getDurata());
			statement.setFloat(3, p.getPrezzo());
			statement.setInt(4, p.getId());
			rowUpdated = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rowUpdated;
	}

	public boolean updateInstru(istruttore istr) throws SQLException {
		boolean rowUpdated = false;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_INSTRU_SQL);) {
			statement.setString(1, Character.toString(istr.getSesso()));
			statement.setString(2, istr.getEmail());
			statement.setString(3, istr.getNumero());
			statement.setString(4, istr.getUsername());
			statement.setString(5, istr.getPassword());
			statement.setInt(6, istr.getEsperienza());
			statement.setInt(7, istr.getOreLezione());
			statement.setFloat(8, istr.getPagaOraria());
			statement.setInt(9, istr.getId());
			rowUpdated = statement.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println(e);
		}
		return rowUpdated;
	}

	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}