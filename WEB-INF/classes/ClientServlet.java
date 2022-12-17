import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import com.mysql.cj.jdbc.Driver;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.mysql.cj.jdbc.exceptions.SQLError;
import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.StringTokenizer; 

/**
 * Servlet implementation class ClientServlet
 */
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Properties property;
	public FileInputStream filein;
	public MysqlDataSource dataSource;
	public String prop = "client.properties";
	public String pathToProps = "/WEB-INF/lib/";
       
    public String handleSqlQuery(String query) throws IOException
	{
		String displayMsg = "";
		try {

			String thisProp = pathToProps + prop;
			property = new Properties();
			property.load(getServletContext().getResourceAsStream(thisProp));
			dataSource = new MysqlDataSource();
			dataSource.setURL(property.getProperty("MYSQL_DB_URI"));
			dataSource.setUser(property.getProperty("username"));
			dataSource.setPassword(property.getProperty("password"));

			Connection connection = dataSource.getConnection();
					
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			ResultSetMetaData metaData = resultSet.getMetaData();
			int cols = metaData.getColumnCount();
			boolean isShipments = false;
			
			for (int i = 1; i <= cols; i++)
			{
				displayMsg += (String.format("%1$-31s\t", metaData.getColumnName(i)));
				// if (metaData.getColumnName(i).equals("quantity"))
				// {
				// 	isShipments = true;
				// }
			}
			displayMsg += "\n-------------------------------------------------------------------------------------------------"
			+"----------------------------------------------------------------------------------------------------------------"+"\n";
			while (resultSet.next())
			{
				for (int i = 1; i <= cols; i++)
				{
					displayMsg += String.format("%1$-31s\t", resultSet.getObject(i));

					// if (isShipments && i == 4)
					// {
					// 	if ((int)resultSet.getObject(i) >= 100)
					// 	{
					// 		statement.executeQuery("update shipments SET quantity = quantity + 5 where quantity >= 100");
					// 	}
					// }					
				}
				displayMsg += "\n";
			}
			connection.close();
			return "No business logic detected.\n" + displayMsg;
		} catch (IOException e) {
			return e.toString();
		} catch (SQLSyntaxErrorException sqlsee) {
			return sqlsee.toString();
		} catch (SQLException sqle) {
            String msg = sqle.toString();
            String [] ret = msg.split(" ");
            if (ret[1].equals("Statement.executeQuery()"))
            {
                return "Cannot update/insert as client-level user.";
            }
            return sqle.toString();
        }
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Holds the query that was in the text area in the jsp.
		String sqlQuery = request.getParameter("sqlQuery");

		// handle sql query with correct database info
		String result = handleSqlQuery(sqlQuery);

		String message = "<textarea readonly id=\"queryResults\" rows=\"10\" cols=\"200\">"+result+"</textarea>";

		HttpSession session = request.getSession();
		session.setAttribute("sqlQuery", message);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/clientHome.jsp");
		dispatcher.forward(request, response);
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
