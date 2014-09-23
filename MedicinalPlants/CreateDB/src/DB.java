
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class DB {

	Connection connection = null;
	Statement stmt = null;

	public void connect(){


		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your MySQL JDBC Driver?");
			e.printStackTrace();
			return;
		}

		try {
			connection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/medicinal_plants","root", "root");

		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		}

		if (connection != null) {
			System.out.println("Database connection sucessfull.");
		} else {
			System.out.println("Failed to make connection!");
		}
	}

	public void insert(String query){
		try 
		{
			Statement statement = connection.createStatement();
			statement.executeUpdate(query);
		}
		catch (Exception se)
		{
			se.printStackTrace();
		}
	}

	public Plant select(String query){
		Plant p = new Plant();
		try 
		{
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);

			while (rs.next()) {
				p.id = rs.getString("id");
				p.scientific_name = rs.getString("scientific_name");
				p.family_name = rs.getString("family_name");
				p.english_name = rs.getString("english_name");
				p.description = rs.getString("description");
				p.edible_parts = rs.getString("edible_parts");
				p.treatment_for = rs.getString("treatment_for");
				p.treatment_parts = rs.getString("parts_used");
				p.medical_properties = rs.getString("medical_properties");
			}
		}
		catch (Exception se)
		{
			se.printStackTrace();
		}
		return p;
	}



}
