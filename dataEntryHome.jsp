<!DOCTYPE html>
<%
    String sqlQuery = (String) session.getAttribute("sqlQuery");
    if (sqlQuery == null) sqlQuery="";
%>
<html>
<head>
<meta charset="UTF-8">
<title>
    Data-Entry Home
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
    .tr-inputs {
        /* position: absolute; */
        /* display: inline-block; */
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
    }
    
</style>
</head>
<body>
    <div class="topContent">
        <h1 style="color:red">
            Welcome to the Fall 2022 Project 4 Enterprise Database System
        </h1>
        <h2 style="color:cyan; margin-left:330px">
            Data Entry Application
        </h2>
    </div>
    <div class="bottomContent">
        <h3 style="color:white">
            You are connected to the Project 4 Enterprise System database as a <span style="color:red">data-entry-level</span> user.
        </h3>
        <h3 style="color:white; max-width:900px">
            Enter the data values in a form below to add a new record to the corresponding database table.
        </h3>
    </div>
    <br>
    <!------------------------ Begin table entries ----------------->
    <!------------------------ Suppliers Record insert ----------------->
    <div class="bottomContent">
        <h3 style="color:coral">Suppliers Record Insert</h3>
    </div>
    <table style="margin:auto">
        <tr>
            <th style="color:white">snum</th>
            <th style="color:white">sname</th>
            <th style="color:white">status</th>
            <th style="color:white">city</th>
        </tr>
        <form action="" method="post">
            <tr class="tr-inputs">
                <th>
                    <input name="snum" type="text" style="color:white">
                </th>
                <th>
                    <input name="sname" type="text" style="color:white">
                </th>
                <th>
                    <input name="status" type="text" style="color:white">
                </th>
                <th>
                    <input name="scity" type="text" style="color:white">
                </th>
            </tr>
        </table>
        <div class="text-center">
            <input name="suppliers" style="color:rgb(115, 216, 115)" type="submit" value="Execute Command">
            <input style="color:rgb(198, 198, 28)" type="reset" value="Clear Entries">
        </div>
    </form>

<br><br>
<!------------------------Parts Record insert ----------------->
<div class="bottomContent">
    <h3 style="color:coral">Parts Record Insert</h3>
</div>
<table style="margin:auto">
    <tr>
        <th style="color:white">pnum</th>
        <th style="color:white">pname</th>
        <th style="color:white">pcolor</th>
        <th style="color:white">pweight</th>
        <th style="color:white">city</th>
    </tr>
    <form action="/Project4/dataEntry1" method="post">
    <tr class="tr-inputs">
            <th>
                <input name="pnum" type="text" style="color:white">
            </th>
            <th>
                <input name="pname" type="text" style="color:white">
            </th>
            <th>
                <input name="pcolor" type="text" style="color:white">
            </th>
            <th>
                <input name="pweight" type="text" style="color:white">
            </th>
            <th>
                <input name="pcity" type="text" style="color:white">
            </th>
        </tr>
    </table>
    <div class="text-center">
        <input name="parts" style="color:rgb(115, 216, 115)" type="submit" value="Execute Command">
        <input style="color:rgb(198, 198, 28)" type="reset" value="Clear Entries">
    </div>
</form>
<br><br>
<!------------------------ Jobs Record insert ----------------->
    <div class="bottomContent">
        <h3 style="color:coral">Jobs Record Insert</h3>
    </div>
    <table style="margin:auto">
        <tr>
            <th style="color:white">jnum</th>
            <th style="color:white">jname</th>
            <th style="color:white">numworkers</th>
            <th style="color:white">city</th>
        </tr>
        <form action="/Project4/dataEntry1" method="post">
        <tr class="tr-inputs">
                <th>
                    <input name="jnum" type="text" style="color:white">
                </th>
                <th>
                    <input name="jname" type="text" style="color:white">
                </th>
                <th>
                    <input name="jnumworkers" type="text" style="color:white">
                </th>
                <th>
                    <input name="jcity" type="text" style="color:white">
                </th>
            </tr>
        </table>
        <div class="text-center">
            <input name="jobs" style="color:rgb(115, 216, 115)" type="submit" value="Execute Command">
            <input style="color:rgb(198, 198, 28)" type="reset" value="Clear Entries">
        </div>
    </form>

<br><br>
<!------------------------ Shipments Record insert ----------------->
    <div class="bottomContent">
        <h3 style="color:coral">Shipments Record Insert</h3>
    </div>
    <table style="margin:auto">
        <tr>
            <th style="color:white">snum</th>
            <th style="color:white">pnum</th>
            <th style="color:white">jnum</th>
            <th style="color:white">quantity</th>
        </tr>
        <form action="/Project4/dataEntry1" method="post">
        <tr class="tr-inputs">
            <th>
                    <input name="snum" type="text" style="color:white">
                </th>
                <th>
                    <input name="pnum" type="text" style="color:white">
                </th>
                <th>
                    <input name="jnum" type="text" style="color:white">
                </th>
                <th>
                    <input name="quantity" type="text" style="color:white">
                </th>
            </tr>
        </table>
        <div class="text-center">
            <input name="shipment" style="color:rgb(115, 216, 115)" type="submit" value="Execute Command">
            <input style="color:rgb(198, 198, 28)" type="reset" value="Clear Entries">
        </div>
    </form>
    <div class="text-center">
        <div class="resultArea">
            <h3>Database Results:</h3>
        </div>
        <div>
            <form action="">
                <input style="color:rgb(198, 198, 28)" type="reset" name="resetResult" value="Clear Results">                
                <%=sqlQuery%>
            </form>
        </div>
    </div>
<br><br>
</div>
</body>
</html>