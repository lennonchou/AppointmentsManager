package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Create DB tables in MySQL.
 *
 */
public class DBImport {

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn = null;

			try {
				System.out.println("Connecting to \n" + DBUtil.URL);
				conn = DriverManager.getConnection(DBUtil.URL);
			} catch (SQLException e) {
				System.out.println("SQLException " + e.getMessage());
				System.out.println("SQLState " + e.getSQLState());
				System.out.println("VendorError " + e.getErrorCode());
			}
			if (conn == null) {
				return;
			}
			// Step 1 Drop tables in case they exist.
			Statement stmt = conn.createStatement();

			String sql = "DROP TABLE IF EXISTS appointment";
			stmt.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS doctor";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE doctor " + "(doctor_id VARCHAR(255) NOT NULL, " + " name VARCHAR(255), "
					+ "pic_url VARCHAR(255), " + "title VARCHAR(255), " + "department VARCHAR(255), " + "focus VARCHAR(255),"
					+ "service_hospital VARCHAR(255), " + "resume VARCHAR(255), " + " price FLOAT, "
					+ " PRIMARY KEY ( doctor_id ))";
			stmt.executeUpdate(sql);

			sql = "CREATE TABLE appointment "
					+ " (doctor_id VARCHAR(255) NOT NULL, " + " patient_name VARCHAR(255), "
					+ " date INTEGER NOT NULL, " + " time INTEGER NOT NULL," + " gender VARCHAR(255)," + " patient_phone_number VARCHAR(255),"
					+ " description_of_problem VARCHAR(255)," + " PRIMARY KEY (doctor_id, date, time),"
					+ " FOREIGN KEY (doctor_id) REFERENCES doctor(doctor_id))";
			stmt.executeUpdate(sql);

			// Step 3: insert data
			// Create a fake user
			sql = "INSERT IGNORE INTO doctor VALUES(\"D001\",\"Adam\",\"http://www.writergirl.com/wp-content/uploads/2014/11/Doctor-790X1024.jpg\",\"Attending physician\",\"ENT\",\"\",\"Mario Hospital\",\"\",\"600\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D002\",\"Maria\",\"http://dreamatico.com/data_images/doctor/doctor-6.jpg\",\"Attending physician\",\"ENT\",\"\",\"Mario Hospital\",\"\",\"700\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D003\",\"Odom\",\"http://fourwaysdoctor.co.za/wp-content/uploads/2013/08/doctor1.png\",\"Attending physician\",\"ENT\",\"\",\"Mario Hospital\",\"\",\"800\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D004\",\"Walton\",\"http://freedesignfile.com/upload/2013/07/Doctor-4.jpg\",\"Physician assistant\",\"ENT\",\"\",\"Mario Hospital\",\"\",\"850\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D011\",\"Kobe\",\"https://thumbs.dreamstime.com/x/female-doctor-22929877.jpg\",\"Attending physician\",\"General surgery\",\"\",\"Mario Hospital\",\"\",\"1100\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D012\",\"Jackson\",\"http://feelgrafix.com/data_images/out/28/1008771-doctor.jpg\",\"Attending physician\",\"General surgery\",\"\",\"Mario Hospital\",\"\",\"1100\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D013\",\"Phil\",\"http://feelgrafix.com/data_images/out/28/1008771-doctor.jpg\",\"Attending physician\",\"General surgery\",\"\",\"Mario Hospital\",\"\",\"1100\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D021\",\"James\",\"http://kingofwallpapers.com/doctor/doctor-015.jpg\",\"Attending physician\",\"Neonatal unit\",\"\",\"Mario Hospital\",\"\",\"1100\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D022\",\"Porter\",\"https://a2ua.com/doctor/doctor-001.jpg\",\"Attending physician\",\"Neonatal unit\",\"\",\"Mario Hospital\",\"\",\"1100\")";
			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);
			sql = "INSERT IGNORE INTO doctor VALUES(\"D023\",\"Harry\",\"http://healthathomes.com/wp-content/uploads/bb_florida-ent-doctor.png\",\"Attending physician\",\"Neonatal unit\",\"\",\"Mario Hospital\",\"\",\"1100\")";

			System.out.println("\nDBImport executing query:\n" + sql);
			stmt.executeUpdate(sql);

			System.out.println("DBImport: import is done successfully.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
