
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.*;
import java.util.List;

/**
 * Servlet implementation class Recommender
 */

@WebServlet("/Recommenderr")

public class Recommenderr extends HttpServlet {

	
	
	static Connection conn;

	public static boolean conn() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/bd", "root", "cloudera");
			return true;
		} catch (Exception e) {

			System.out.println(e);
			return false;
		}
	}

	public static boolean tableExist(Connection conn, String tableName) throws SQLException {
		boolean tExists = false;
		try (ResultSet rs = conn.getMetaData().getTables(null, null, tableName, null)) {
			while (rs.next()) {
				String tName = rs.getString("TABLE_NAME");
				if (tName != null && tName.equals(tableName)) {
					tExists = true;
					break;
				}
			}
		}
		return tExists;
	}

	public static void createTable() {
		try {
			Statement stmt = conn.createStatement();
			if (tableExist(conn, "Movies")) {
				System.out.println("Tables already present... stepping further --> --> -->");

			} else {
				String sql = "CREATE TABLE Movies(Movie_No int, Movie_Name VARCHAR(255), Movie_Category VARCHAR(255));";
				String sql2= "CREATE TABLE Ratings(User_ID int, Movie_ID int, Movie_Ratings DECIMAL(2,1), Time_stamp Varchar(255));";
				String sql3= "CREATE TABLE Users(User_ID int, Sex VARCHAR(255), Age int, Occupation varchar(255), Zip_Code varchar(255));";
				String sql4 = "LOAD DATA INFILE '/home/cloudera/Desktop/movies.csv' INTO TABLE Movies CHARACTER SET 'utf8' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;";
				String sql5 = "LOAD DATA INFILE '/home/cloudera/Desktop/ratings.csv' INTO TABLE Ratings CHARACTER SET 'utf8' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;";
				String sql6 = "LOAD DATA INFILE '/home/cloudera/Desktop/users.csv' INTO TABLE Users CHARACTER SET 'utf8' FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"' LINES TERMINATED BY '\r\n' IGNORE 1 LINES;";
				
				stmt.executeUpdate(sql);
				System.out.println("Created table Movie...");
				stmt.executeUpdate(sql2);
				System.out.println("Created table Ratings...");
				stmt.executeUpdate(sql3);
				System.out.println("Created table Users...");
				stmt.executeUpdate(sql4);
				System.out.println("Data of movies.csv has been entered in database...");
				stmt.executeUpdate(sql5);
				System.out.println("Data of Ratings.csv has been entered in database...");
				stmt.executeUpdate(sql6);
				System.out.println("Data of Users.csv has been entered in database...");
				
			}
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Recommenderr() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	private void rr() throws IOException, TasteException{
		
		DataModel model = new FileDataModel(new File("dataset.csv")); 
        UserSimilarity similarity = new EuclideanDistanceSimilarity(model); 
       UserNeighborhood neighborhood = new ThresholdUserNeighborhood 
                       (0.1, similarity, model); 
       Recommender recommender = new GenericUserBasedRecommender(model, 
                       neighborhood, similarity); 
       List<RecommendedItem> recommendations = recommender.recommend(2, 3); 
       for (RecommendedItem recommendation : recommendations) { 
               System.out.println(" recommendation: " + recommendation); 
       } 
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (conn()) {
			createTable();
			try {
				conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				rr();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			response.sendRedirect("result.jsp");
		} else {
			response.sendRedirect("error.jsp");
		}
		// TODO Auto-generated method stub
	}

}
