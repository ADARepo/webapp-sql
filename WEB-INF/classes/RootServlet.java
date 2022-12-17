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
 * Servlet implementation class RootServlet
 */
public class RootServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Properties property;
	public FileInputStream filein;
	public MysqlDataSource dataSource;
	public String prop = "root.properties";
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
//							System.out.println(metaData.getColumnName(i + 1));
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
			try {
				Connection connection = dataSource.getConnection();

				String[] queryTokens = query.split(" ");
				String businessLogic = "";
				int quantity = 0;
				int affected = 0;

				for (int i=0; i<queryTokens.length; i++)
				{
					if (queryTokens[i].equals("suppliers"))
					{
						businessLogic = "Business logic not detected.";
						break;
					}
					if (queryTokens[i].equals("update"))
					{
						businessLogic = "Business logic not detected.";
						break;
					}
				
					if(queryTokens[i].equals("values"))
					{
						businessLogic = "Business logic detected.";
						i++;
						
						String snum = queryTokens[i];
						snum = snum.replace("(", "");
						snum = snum.replace("'", "");
						snum = snum.replace(",", "");
						snum = snum.replace(")", "");
						i++;
						String pnum = queryTokens[i];
						pnum = pnum.replace("(", "");
						pnum = pnum.replace("'", "");
						pnum = pnum.replace(",", "");
						pnum = pnum.replace(")", "");
						i++;
						String jnum = queryTokens[i];
						jnum = jnum.replace("(", "");
						jnum = jnum.replace("'", "");
						jnum = jnum.replace(",", "");
						jnum = jnum.replace(")", "");
						i++;
						String qty = queryTokens[i];
						qty = qty.substring(0, qty.length() - 1);

						quantity = Integer.parseInt(qty);

						if (quantity >= 100)
						{
							String command = "update suppliers set status = status + 5 where snum = '" + snum + "'";
							Statement st = connection.createStatement();
							affected += st.executeUpdate(command);
						}
					}
				}
				
				Statement statement = connection.createStatement();
				int resultSet = statement.executeUpdate(query) + affected;
				// ResultSet keys = statement.getGeneratedKeys();
				displayMsg = Integer.toString(resultSet);
				
				connection.close();
				return businessLogic + "\n" + affected + " rows affected.\n";
			} catch (SQLException sqle2) {
				return sqle2.toString();
			}
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
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/rootHome.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}