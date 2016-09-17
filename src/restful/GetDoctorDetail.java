package restful;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.json.JSONObject;

import db.MysqlConn;

/**
 * Servlet implementation class GetDoctorDetail
 */
@WebServlet("/doctor/detail")
public class GetDoctorDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static MysqlConn conn = new MysqlConn();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDoctorDetail() {
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
		JSONObject obj = new JSONObject();
		if (request.getParameterMap().containsKey("doctor_id")) {
			String doctorId = request.getParameter("doctor_id");
			obj = conn.getDoctorDetail(doctorId);
		}
		RpcParser.writeOutput(response, obj);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
