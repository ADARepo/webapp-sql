<!DOCTYPE html>
<%
    String sqlQuery = (String) session.getAttribute("sqlQuery");
    if (sqlQuery == null) sqlQuery="";
%>
<html>
<head>
<meta charset="UTF-8">
<title>
    Client Home
</title>
<style>
    body {
        background: rgb(4, 3, 3);
    }
    .topContent {
        max-width: 900px;
        margin: auto;
        /* background: white; */
        padding: 15px;
    }
    .bottomContent {
        max-width: 700px;
        margin: auto;
        padding-top: 20px;
        text-align: center;
    }
    textarea {
        display: block;
        margin: auto;
    }
    .text-center {
        text-align: center;
    }
    input {
        background-color: rgb(67, 61, 61);
    }
    .resultArea {
        max-width: 150px;
        margin: auto;
        padding: 10px;
        color: white;
    }
    
</style>
</head>
<body>
    <div class="topContent">
        <h1 style="color:red">
            Welcome to the Fall 2022 Project 4 Enterprise Database System
        </h1>
        <h2 style="color:cyan">
            A Servlet/JSP-based Multi-tiered Enterprise Application Using A Tomcat Container
        </h2>
    </div>
    <div class="bottomContent">
        <h3 style="color:white">
            You are connected to the Project 4 Enterprise System database as a <span style="color:red">client-level</span> user.
        </h3>
        <h3 style="color:white">
            Please enter any valid SQL query or update command in the box below.
        </h3>
    </div>
    <br>
    <div class="text-center">
        <form method="post" action="/Project4/client1">
            <textarea name="sqlQuery" rows="10" cols="80"></textarea>
            <input style="color:rgb(115, 216, 115)" type="submit" value="Execute Command">
            <input style="color:rgb(247, 56, 56)" type="reset" value="Reset Form">
        </form>
    </div>
    <div class="text-center">
        <div class="resultArea">
            <h3>Database Results:</h3>
        </div>
        <div>
            <form>
                <input style="color:rgb(198, 198, 28)" type="reset" value="Clear Results">
                <%=sqlQuery%>
            </form>
        </div>
</div>
</body>
</html>