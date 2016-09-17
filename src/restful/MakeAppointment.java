package restful;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import db.MysqlConn;

/**
 * Servlet implementation class MakeAppointment
 */
@WebServlet("/appointment")
public class MakeAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static MysqlConn conn = new MysqlConn(); 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeAppointment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (conn == null) {
			conn = new MysqlConn();
		}
		try {
			JSONObject input = RpcParser.parseInput(request);
			if (input.has("doctor_id") && input.has("date") && input.has("time")) {
				String doctorId = (String) input.get("doctor_id");
				int date = (int) input.get("date");
				int time = (int) input.get("time");
				String name = (String) input.get("patient_name");
				String gender = (String) input.get("gender");
				String phoneNo = (String) input.get("patient_phone_number");
				String problem = (String) input.get("description_of_problem");
				boolean success = conn.setSelectedAppointment(doctorId, date, time, name, gender, phoneNo, problem);
				RpcParser.writeOutput(response, new JSONObject().put("success", success).put("message", success ? "" : "The doctor is not available at selected time."));
			} else {
				RpcParser.writeOutput(response, new JSONObject().put("status", "InvalidParameter"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
