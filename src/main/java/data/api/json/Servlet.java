package data.api.json;

import com.example.zero_study_mid.dao.DbTest;
import okhttp3.Request;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SearchWifi", value = "/Search.do")
public class Servlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double LAT = Double.parseDouble(request.getParameter("LAT"));
        double LNT = Double.parseDouble(request.getParameter("LNT"));

        DbTest.selectDb(LAT, LNT);


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        double LAT = Double.parseDouble(request.getParameter("LAT"));
        double LNT = Double.parseDouble(request.getParameter("LNT"));

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/");
        requestDispatcher.forward(request, response);
    }

    public static void main(String[] args) {

    }
}
