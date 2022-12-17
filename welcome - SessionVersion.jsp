<!DOCTYPE html>
<!-- welcomesession.jsp -->
<!-- JSP front-end that processes a "get" request containing data. -->
<!-- and sends it to a servlet for processing and returning result to -->
<!-- the original user page -->

<%-- start scriptlet --%>
<%
   String firstName = (String) session.getAttribute("firstName");

   String message = (String) session.getAttribute("message");
   if (message ==null) message = " ";
%>

<html lang="en">
   <!-- head section of document -->
   <head>
      <title>A JSP that processes "get" requests with data</title>
	  <meta charset="utf-8">
	  <style type="text/css">
   	  <!--
   		body { background-color: black; color:lime; font-family: verdana, arial, sans-serif; font-size: 1.4em;  }
         h1 { color:yellow; font-size: 1.3em; }
         input[type="submit"] {background-color: yellow; font-weight:bold;}
         input[type="text"]{background-color:blue; color:white;}
         span {color: red;}
         #servlet {color:purple;}
         #jsp {color:cyan;}
   	  -->
	  </style>
   </head>
   <!-- body section of document -->
   <body>
      <form action = "/JSPServletApp/welcomesession" method = "post">
               <p>Type your first name and press Submit</p>
               <p><input type = "text" name = "firstName">  </input>
                  <input type = "submit" value = "Submit Your Name" />
               </p>
      </form>
      <br><br><br>
      <h2>
      	<%-- JSP expression to access the message sent from the servlet --%>
      	<%=message%>
      </h2>
   </body>
</html>  <!-- end HTML document -->


