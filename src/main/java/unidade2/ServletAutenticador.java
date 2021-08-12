package unidade2;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletAutenticador
 */
@WebServlet("/ServletAutenticador")
public class ServletAutenticador extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static String url = "jdbc:mysql://localhost:3306/javaWeb";
	static String usuario = "root";
	static String senha = "admin";
	static Connection conexao;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAutenticador() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() throws ServletException {    	
    	loadDriver();
    }
    
    public void loadDriver() {
    	try {
    		Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(url, usuario, senha);
			conexao.setAutoCommit(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpfmask = request.getParameter("cpf");
		cpfmask = cpfmask.replaceAll("[.-]", "");
		long cpf = Long.parseLong(cpfmask);

		String senha = request.getParameter("senha");
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String consulta = "SELECT * FROM login WHERE cpf ='"+ cpf +"' AND senha='"+senha+"'";
		
		System.out.println(conexao);
		
		Statement statement;
		try {
			statement = conexao.createStatement();		
			ResultSet rs = statement.executeQuery(consulta);
			
			if(rs.next()) {
				out.println("<h2>Usuario autenticado.</h2>");
			}
			else {
				out.println("<h2>Usuario NAO autenticado.</h2>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
