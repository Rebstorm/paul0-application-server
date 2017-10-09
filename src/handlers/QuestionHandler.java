package handlers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class QuestionHandler extends HttpServlet {


    @Override
    protected void doGet( HttpServletRequest request,
                          HttpServletResponse response ) throws ServletException, IOException {

        System.out.print(request.getQueryString());

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
