package restful;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import db.MysqlConn;

/**
 * Servlet implementation class GetDoctorList
 */
@WebServlet({ "/GetDoctorList", "/doctor/list" })
public class GetDoctorList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static MysqlConn conn = new MysqlConn();   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetDoctorList() {
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
		JSONArray array = conn.getDoctorList();
		RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
