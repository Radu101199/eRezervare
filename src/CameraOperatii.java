import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CameraOperatii {
	private Connection conexiune;

	public CameraOperatii(String databaseUrl, String username, String password) {
		DatabaseConnection dbConnection = new DatabaseConnection();
		dbConnection.connect();
		conexiune = dbConnection.getConnection();
	}

	public Camera getCameraByNr(String nr) {

		String query = "SELECT * FROM Camera WHERE Nr = ?";
		Camera camera = null;

		try (PreparedStatement statement = conexiune.prepareStatement(query)) {
			statement.setString(1, nr);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				String cameraNr = resultSet.getString("nr");
				int suplimentPret = resultSet.getInt("suplimentpret");
				String dotari = resultSet.getString("dotari");
				int tipId = resultSet.getInt("tipid");
				camera = new Camera(cameraNr, suplimentPret, dotari, tipId);
			}
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return camera;
	}

	public List<Camera> getCamere() {
		List<Camera> camere = new ArrayList<>();

		try {
			String query = "SELECT * FROM Camera";
			PreparedStatement statement = conexiune.prepareStatement(query);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				String nr = resultSet.getString("Nr");
				int supliment = resultSet.getInt("SuplimentPret");
				String dotari = resultSet.getString("Dotari");
				int tipId = resultSet.getInt("TipID");

				Camera room = new Camera(nr, supliment, dotari, tipId);
				camere.add(room);
			}
			resultSet.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return camere;
	}

	public boolean adaugaCamera(Camera camera) throws SQLException {
		try {
			String sql = "INSERT INTO Camera (Nr, suplimentpret, dotari, tipid) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setString(1, camera.getNr());
			statement.setInt(2, camera.getSupliment());
			statement.setString(3, camera.getDotari());
			statement.setInt(4, camera.getTipId());
			int rowsInserted = statement.executeUpdate();

			if (rowsInserted > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean editeazaCamera(Camera camera) throws SQLException {
		try {
			String sql = "UPDATE Camera SET suplimentpret = ?, dotari = ?, tipid = ? WHERE nr = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setInt(1, camera.getSupliment());
			statement.setString(2, camera.getDotari());
			statement.setInt(3, camera.getTipId());
			statement.setString(4, camera.getNr());
			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false; 
	}

	public boolean stergeCamera(int nr) throws SQLException {
		try {
			String sql = "DELETE FROM Camera WHERE nr = ?";
			PreparedStatement statement = conexiune.prepareStatement(sql);
			statement.setInt(1, nr);
			int rowsAffected = statement.executeUpdate();

			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false; 
	}
	
	public String getCameraTipNume(int tipID) {
	    try {
	        String query = "SELECT Denumire FROM Tip WHERE TipID = ?";
	        PreparedStatement statement = conexiune.prepareStatement(query);
	        statement.setInt(1, tipID);
	        ResultSet resultSet = statement.executeQuery();

	        if (resultSet.next()) {
	            return resultSet.getString("Denumire");
	        }

	        resultSet.close();
	        statement.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return ""; 
	}
	
}
