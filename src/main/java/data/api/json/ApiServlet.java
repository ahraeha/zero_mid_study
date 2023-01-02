package data.api.json;

import com.example.zero_study_mid.dao.DbTest;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "getWifi", value = "/data.do")
public class ApiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        DbTest.deleteDb();

        int totalCount = DbTest.insertDB();

        PrintWriter out = response.getWriter();

        out.println("<center><h1>" + totalCount + "개의 WIFI 정보를 정상적으로 저장하였습니다.</h1>");
        out.println("<a href='./index.jsp'>홈 으로 가기 </a></center>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
