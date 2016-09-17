package restful;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;

import restful.RpcParser;
import db.MysqlConn;

/**
 * Servlet implementation class GetAppointment
 */
@WebServlet("/doctor/appointments")
public class GetAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static MysqlConn conn = new MysqlConn();   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAppointment() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (conn == null) {
			conn = new MysqlConn();
		}
		JSONArray array = new JSONArray();
		if (request.getParameterMap().containsKey("doctor_id")) {
			String doctorId = request.getParameter("doctor_id");
			try {
				array = conn.getAppointment(doctorId);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
