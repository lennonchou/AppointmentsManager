package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MysqlConn {
	
	private Connection conn;

	public MysqlConn() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DBUtil.URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unused")
	private boolean executeUpdateStatement(String query) {
		// write query to database.
		if (conn == null) {
			return false;
		}
		try {
			Statement stmt = conn.createStatement();
			System.out.println("\nDBConnection executing query:\n" + query);
			stmt.executeUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private ResultSet executeFetchStatement(String query) {
		// read query from database and return selected ones.
		if (conn == null) {
			return null;
		}
		try {
			Statement stmt = conn.createStatement();
			System.out.println("\nDBConnection executing query:\n" + query);
			return stmt.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean checkAppointmentAvailability(String doctorId, int date, int time) {
		try {
			String sql = "SELECT * FROM appointment where date=? and time=? and doctor_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, date);
			ps.setInt(2, time);
			ps.setString(3, doctorId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public JSONObject getDoctorDetail(String doctorId) {		
		// TODO Auto-generated method stub
		try {
			JSONObject obj = new JSONObject();
			String query = "SELECT * FROM doctor where doctor_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, doctorId);
			ResultSet rs = ps.executeQuery();
			
			//get doctor's personal info
			if (rs.next()) {
				obj.put("name", rs.getString("name")).put("picture", rs.getString("pic_url")).put("title", rs.getString("title"))
				.put("department", rs.getString("department")).put("focus", rs.getString("focus")).put("service_hospital", rs.getString("service_hospital"))
				.put("resume", rs.getString("resume")).put("price", rs.getFloat("price"));
			}
			
			//get doctor's schedule
			JSONArray calendar = new JSONArray();
			for (int i = 0; i < 2; i++) {
				for (int j = 8; j < 17; j++) {
					boolean status = checkAppointmentAvailability(doctorId, i, j);
					JSONObject slot = new JSONObject();
					slot.put("date", i).put("time", j).put("availability", status);
					calendar.put(slot);
				}
			}
			obj.put("calendar", calendar);
			return obj;
		} catch (JSONException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private JSONArray getDoctorByDepartment(String department) {	
		try {
			JSONArray array = new JSONArray();
			String sql = "SELECT * FROM doctor where department=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, department);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				JSONObject doctor = new JSONObject();
				doctor.put("doctor_name", rs.getString("name")).put("doctor_pic", rs.getString("pic_url")).put("doctor_id", rs.getString("doctor_id"));
				array.put(doctor);
			}
			return array;
		} catch (SQLException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public JSONArray getDoctorList() {
		// TODO Auto-generated method stub
		try {
			List<String> departments = new ArrayList<>();
			String query = "SELECT DISTINCT department FROM doctor";
			ResultSet rs = executeFetchStatement(query);
			
			//get all the department name
			while (rs.next()) {
				String department = rs.getString("department");
				departments.add(department);
			}
			
			//for each department, get all the doctors in it
			JSONArray array = new JSONArray();
			for (int i = 0; i < departments.size(); i++) {
				JSONObject obj = new JSONObject().put("department", departments.get(i));
				JSONArray doctors = getDoctorByDepartment(departments.get(i));
				//put all the doctors' info in a specific department in a JSONArray
				obj.put("doctors", doctors);
				//put one department JSONObject into result
				array.put(obj);
			}
			return array;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.print("no list");
		return null;
	}
	
	private boolean insertAppointment(String doctorId, int date, int time, String name, String gender, String phoneNo, String problem) {
		//wrap the appointment into a preparedstatement
		try {
			String sql = "INSERT INTO appointment VALUES(?,?,?,?,?,?,?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, doctorId);
			ps.setInt(3, date);
			ps.setInt(4, time);
			ps.setString(2, name);
			ps.setString(5, gender);
			ps.setString(6, phoneNo);
			ps.setString(7, problem);
			System.out.println("\nDBConnection executing query:\n" + sql);
			ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean setSelectedAppointment(String doctorId, int date, int time, String name, String gender, String phoneNo, String problem) {
		if (!insertAppointment(doctorId, date, time, name, gender, phoneNo, problem)) {
			return false;
		}
		return true;
	}
	
	private ResultSet getAppointmentByDoctorId(String doctorId) {
		//use preparedstatement for security concerns
		try {
			String query = "SELECT * FROM appointment where doctor_id=?";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, doctorId);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public JSONArray getAppointment(String doctorId) throws JSONException {
		// TODO Auto-generated method stub		
		try {
			JSONArray array = new JSONArray();
			ResultSet rs = getAppointmentByDoctorId(doctorId);
			while (rs.next()) {
				
				//get each appointment slot
				JSONObject slot = new JSONObject();
				slot.put("date", rs.getInt("date")).put("time", rs.getInt("time"))
				.put("patient_name", rs.getString("patient_name")).put("description_of_problem", rs.getString("description_of_problem"));
				array.put(slot);
			}
			return array;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static void main(String[] args) throws JSONException {
		MysqlConn c = new MysqlConn();
//		System.out.print(c.checkAppointmentAvailability("D001", 0, 9));
//		if (c.setSelectedAppointment("D021", 0, 8, "", "", "", "")) {
//			System.out.println("YES");
//		}
//		System.out.println(c.getDoctorDetail("D021").toString());
//		System.out.println(c.getDoctorList().toString());
	}
}
