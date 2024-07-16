import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AngajatOperatii {
	private Connection conexiune;

	public AngajatOperatii(String databaseUrl, String username, String password) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		dbConnection.connect();
		conexiune = dbConnection.getConnection();
	}

	public boolean inregistrare(String username, String CNP, String parola, String functie) {

		try {
			String checkFunctionSql = "SELECT FunctieID FROM Functie WHERE Denumirea = ?";
			PreparedStatement checkFunctionStatement = conexiune.prepareStatement(checkFunctionSql);
			checkFunctionStatement.setString(1, functie);
			ResultSet functionResult = checkFunctionStatement.executeQuery();
			if (!functionResult.next()) {
				System.out.println("Functie nevalida: " + functie);
				return false;
			}
			int functieID = functionResult.getInt("FunctieID");

			String sql = "INSERT INTO Angajat (Username, CNP, Parola, FunctieID) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, CNP);
			statement.setString(3, parola);
			statement.setInt(4, functieID);

			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public Angajat login(String username, String parola) {

		try {
			String sql = "SELECT * FROM Angajat WHERE Username = ? AND Parola = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, parola);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				//int angajatId = resultSet.getInt("AngajatID");
				String CNP = resultSet.getString("CNP");
				int functie = resultSet.getInt("FunctieId");
				return new Angajat( username, CNP, parola, functie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean stergeAngajat(String angajatCNP) {

		try {
			String sql = "DELETE FROM Angajat WHERE CNP = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, angajatCNP);

			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Angajat> getListAngajati() {

		List<Angajat> angajati = new ArrayList<>();
		try {
			String sql = "SELECT * FROM Angajat";
			PreparedStatement statement = conexiune.prepareStatement(sql);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				//int employeeId = resultSet.getInt("AngajatID");
				String username = resultSet.getString("Username");
				String CNP = resultSet.getString("CNP");
				String password = resultSet.getString("Parola");
				int functie = resultSet.getInt("FunctieId");

				Angajat angajat = new Angajat(username, CNP, password, functie);
				angajati.add(angajat);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return angajati;
	}

	public Angajat getAngajatCNP(String Cnp) {

		Angajat angajat = null;
		try {
			String sql = "SELECT * FROM Angajat WHERE CNP = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, Cnp);

			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String username = resultSet.getString("Username");
				String CNP = resultSet.getString("CNP");
				String parola = resultSet.getString("Parola");
				int functie = resultSet.getInt("FunctieId");

				angajat = new Angajat(username, CNP, parola, functie);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return angajat;
	}

	public boolean adaugaAngajatBD(Angajat angajat) {

		try {
			String sql = "INSERT INTO Angajat (username, cnp, parola, FunctieId) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, angajat.getUsername());
			statement.setString(2, angajat.getCNP());
			statement.setString(3, angajat.getParola());
			statement.setInt(4, angajat.getFunctie());

			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean adaugaPersoanaBD(Persoana persoana) {

		String query = "INSERT INTO Persoana (Nume, SerieNr, CNP, StradaNr, Telefon) VALUES (?, ?, ?, ?, ?)";
		try (PreparedStatement statement = conexiune.prepareStatement(query)) {
			statement.setString(1, persoana.getNume());
			statement.setString(2, persoana.getSerieNr());
			statement.setString(3, persoana.getCnp());
			statement.setString(4, persoana.getStradaNr());
			statement.setString(5, persoana.getTelefon());

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				return true; 
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateAngajat(int angajatId, String username, String parola) {

		try {
			String query = "UPDATE Angajat SET Username = ?, Parola = ? WHERE AngajatID = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, parola);
			statement.setInt(3, angajatId);
			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateAngajatManager(Angajat angajat) {

		try {
			String sql = "UPDATE Angajat SET Username = ?, CNP = ?, Parola = ?, FunctieId = ? WHERE CNP = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, angajat.getUsername());
			statement.setString(2, angajat.getCNP());
			statement.setString(3, angajat.getParola());
			statement.setInt(4, angajat.getFunctie());
			statement.setString(5, angajat.getCNP());

			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}


	public int getAngajatId(String username, String parola) {

		try {
			String query = "SELECT AngajatID FROM Angajat WHERE Username = ? AND Parola = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, parola);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("AngajatID");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public String getFunctieNume(int functieId) {

		try {
			String query = "SELECT Denumirea FROM Functie WHERE FunctieID = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setInt(1, functieId);

			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("Denumirea");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getFunctieDefinitie(int functieId) {

		try {
			String query = "SELECT Descriere FROM Functie WHERE FunctieID = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setInt(1, functieId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("Descriere");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null; 
	}

	public String getAngajatFunctieDenumire(int functieID) {
	    try {
	        String query = "SELECT Denumirea FROM Functie WHERE FunctieID = ?";
	        PreparedStatement statement = conexiune.prepareStatement(query);
	        statement.setInt(1, functieID);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            return resultSet.getString("Denumirea");
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ""; 
	}
	
	public void inchideConexiune() {
		try {
			if (conexiune != null) {
				conexiune.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

