import java.sql.*;
import java.time.LocalDateTime;

public class RezervareOperatii {

	private Connection conexiune;
	public RezervareOperatii(String databaseUrl, String username, String password) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		dbConnection.connect();
		conexiune = dbConnection.getConnection();
	}

	public boolean rezervaCamera(String CNP, int cameraId) {

		try {
			String sql = "INSERT INTO Rezervare (TuristID, CameraID) VALUES (?, ?)";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, CNP);
			statement.setInt(2, cameraId);

			int rowsInserted = statement.executeUpdate();
			statement.close();
			if (rowsInserted > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}

	public ResultSet cautaRezervare(String persoanaCNP) {

		try {
			String query = "SELECT r.*, p.PersoanaID FROM Rezervare r JOIN Persoana p ON r.PersonID = p.PersoanaID WHERE p.CNP = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setString(1, persoanaCNP);
			return statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	public ResultSet getRezervari() {
		try {
			String sql = "SELECT * FROM Rezervare";
			Statement statement = conexiune.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public boolean stergeRezervare(int rezervareId) {
		try {
			String sql = "DELETE FROM Rezervare WHERE NrInregistrare = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setInt(1, rezervareId);
			int rowsDeleted = statement.executeUpdate();
			statement.close();
			if (rowsDeleted > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public ResultSet getPersoane() {
		ResultSet resultSet = null;
		try {
			String query = "SELECT CNP FROM Persoana";
			PreparedStatement statement = conexiune.prepareStatement(query);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public ResultSet getCamere() {
		ResultSet resultSet = null;
		try {
			String query = "SELECT Id FROM Camera";
			PreparedStatement statement = conexiune.prepareStatement(query);
			resultSet = statement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultSet;
	}

	public boolean rezervaCamera(String persoanaCNP, int cameraId, LocalDateTime dataOraPlecare, LocalDateTime dataOraSosire, LocalDateTime dataCheckin, LocalDateTime dataCheckout, LocalDateTime dataRezervare, int angajatId) {
		try {
			int turistId = getIdPersoanaByCNP(persoanaCNP);
			if (turistId == -1) {
				return false;
			}
			String query = "INSERT INTO Rezervare (PersonId, CameraId, DataOraPlecare, DataOraSosire, DataCheckin, DataCheckout, DataOraRezervare, AngajatId) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setInt(1, turistId);
			statement.setInt(2, cameraId);
			statement.setTimestamp(3, Timestamp.valueOf(dataOraPlecare));
			statement.setTimestamp(4, Timestamp.valueOf(dataOraSosire));
			statement.setTimestamp(5, Timestamp.valueOf(dataCheckin));
			statement.setTimestamp(6, Timestamp.valueOf(dataCheckout));
			statement.setTimestamp(7, Timestamp.valueOf(dataRezervare));
			statement.setInt(8, angajatId);

			int rowsAffected = statement.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private int getIdPersoanaByCNP(String persoanaCNP) {

		try {
			String query = "SELECT PersoanaId FROM Persoana WHERE CNP = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setString(1, persoanaCNP);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getInt("PersoanaId");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1; 
	}

	public String getTuristCNP(int persoanaId) {
		try {
			String query = "SELECT CNP FROM Persoana WHERE PersoanaID = ?";
			PreparedStatement statement = conexiune.prepareStatement(query);
			statement.setInt(1, persoanaId);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				return resultSet.getString("CNP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

}
