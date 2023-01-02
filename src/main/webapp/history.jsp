<%@ page import="java.util.List" %>
<%@ page import="com.example.zero_study_mid.dto.HistoryValue" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.zero_study_mid.dao.Historydb" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<style>
    #customers{
        font-family: Arial, Helvetica, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }
    #customers td, #customers th{
        border: 1px solid #ddd;
        padding: 8px;
    }
    #customers th {
        padding-top: 12px;
        padding-bottom: 12px;
        text-align: center;
        background-color: #04AA6D;
        color: white
    }
    #customers tr:nth-child(even){background-color: #f2f2f2;}
    #customers tr:hover {background-color: #ddd}
</style>
<body>
<%
    String req = request.getParameter("num");
    Historydb.withdrawDb(req);
    List<HistoryValue> list = Historydb.selectDb();
%>
<h1>위치 히스토리 목록</h1>
<nav>
    <a href="/index.jsp">홈</a> |
    <a href="/history.jsp">위치 히스토리 목록</a> |
    <a href="">Open API 와이파이 정보 가져오기</a>
</nav>
<br>
<table id="customers">
    <tr>
        <th>ID</th>
        <th>X좌표</th>
        <th>Y좌표</th>
        <th>조회일자</th>
        <th>비고</th>
    </tr>
    <%
        for (HistoryValue historyValue : list) {
    %>
    <tr>
        <td> <%= historyValue.getId() %> </td>
        <td> <%= historyValue.getLAT() %> </td>
        <td> <%= historyValue.getLNT() %> </td>
        <td> <%= historyValue.getDate() %> </td>
        <% String num = String.valueOf(historyValue.getId()); %>
        <form action="/history.jsp" method="post">
            <td style="text-align: center"><button id="num" name="num" type="submit" value="<%= num%>">삭제</button></td>

        </form>
    </tr>
    <%
        }
    %>
</table>

</body>
</html>