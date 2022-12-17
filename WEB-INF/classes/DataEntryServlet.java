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
 * Servlet implementation class DataEntryServlet
 */
public class DataEntryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Properties property;
	public FileInputStream filein;
	public MysqlDataSource dataSource;
	public String prop = "entry.properties";
	public String pathToProps = "/WEB-INF/lib/";

    public String handleSqlQuery(String recordType, String item1, String item2, String item3, String item4, String item5) throws IOException
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

            String query = "";

            if (recordType.equals("suppliers"))
            {
                query = "insert into suppliers values ('"+item1+"', '"+item2+"', "+item3+", '"+item4+"')";
                displayMsg = "New jobs record ("+item1+", "+item2+", "+item3+", "+item4+")";
            }
            else if (recordType.equals("parts"))
            {
                query = "insert into parts values ('"+item1+"', '"+item2+"', '"+item3+"', "+item4+", '"+item5+"')";
                displayMsg = "New jobs record ("+item1+", "+item2+", "+item3+", "+item4+", "+item5+")";
            }
            else if (recordType.equals("jobs"))
            {
                query = "insert into jobs values ('"+item1+"', '"+item2+"', "+item3+", '"+item4+"')";
                displayMsg = "New jobs record ("+item1+", "+item2+", "+item3+", "+item4+")";
            }
            else if (recordType.equals("shipments"))
            {
                int qty = Integer.parseInt(item4);
                // Checking for business logic:
                if (qty >= 100)
                {
                    int rs = statement.executeUpdate("update suppliers set status = status + 5 where snum = '" + item1 + "'");
                    displayMsg += "Business logic detected.\n" + rs+" rows affected.\n";
                }
                query = "insert into shipments values ('"+item1+"', '"+item2+"', '"+item3+"', "+item4+")";
                displayMsg += "New shipments record ("+item1+", "+item2+", "+item3+", "+item4+")";
            }

			int resultSet = statement.executeUpdate(query);
			
			connection.close();
			return displayMsg;
		} catch (IOException e) {
			return e.toString();
		} catch (SQLSyntaxErrorException sqlsee) {
			return sqlsee.toString();
		} catch (SQLException sqle) {
			return sqle.toString();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Holds the query that was in the text area in the jsp.

        // Check which insertion is happening:
        String shipments = request.getParameter("quantity");
        String jobs = request.getParameter("jnum");
        String suppliers = request.getParameter("snum");
        String parts = request.getParameter("pnum");

		String result = "";

        if (jobs != null && shipments == null)
        {
            result = handleSqlQuery("jobs", request.getParameter("jnum"), request.getParameter("jname"), request.getParameter("jnumworkers"), request.getParameter("jcity"), "");
        }
        else if (suppliers != null && shipments == null)
        {
            result = handleSqlQuery("suppliers", request.getParameter("snum"), request.getParameter("sname"), request.getParameter("status"), request.getParameter("scity"), "");
        }
        else if (parts != null && shipments == null)
        {
            result = handleSqlQuery("parts", request.getParameter("pnum"), request.getParameter("pname"), request.getParameter("pcolor"), request.getParameter("pweight"), "pcity");
        }
        else if (shipments != null)
        {
            result = handleSqlQuery("shipments", request.getParameter("snum"), request.getParameter("pnum"), request.getParameter("jnum"), request.getParameter("quantity"), "");
        }
        else
        {
            result = "Error: didnt get record name from form.";
        }

		String message = "<textarea readonly id=\"queryResults\" rows=\"10\" cols=\"200\">"+result+"</textarea>";

		HttpSession session = request.getSession();
		session.setAttribute("sqlQuery", message);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/dataEntryHome.jsp");
		dispatcher.forward(request, response); 
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
    }

}
