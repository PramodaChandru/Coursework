

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/WriteReview")
//once the user clicks writereview button from products page he will be directed
//to write review page where he can provide reqview for item rating reviewText

public class WriteReview extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
        review(request, response);
    }

    protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            response.setContentType("text/html");
            PrintWriter pw = response.getWriter();
            Utilities utility = new Utilities(request, pw);
            if (!utility.isLoggedin()) {
                HttpSession session = request.getSession(true);
                session.setAttribute("login_msg", "Please Login to Write a Review");
                response.sendRedirect("Login");
                return;
            }
            String productname = request.getParameter("name");
            String producttype = request.getParameter("type");
            String productmaker = request.getParameter("maker");
            String productprice = request.getParameter("price");

            // on filling the form and clicking submit button user will be directed to submit review page
            utility.printHtml("Header.html");
            utility.printHtml("LeftNavigationBar.html");
            pw.print("<form name ='WriteReview' action='SubmitReview' method='post'>");
            pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
            pw.print("<a style='font-size: 24px;'>Review</a>");
            pw.print("</h2><div class='entry'>");
            pw.print("<table class='gridtable'>");
            pw.print("<tr><td> Product Name: </td><td>");
            pw.print(productname);
            pw.print("<input type='hidden' name='productname' value='" + productname + "'>");
            pw.print("</td></tr>");
            pw.print("<tr><td> Product Type:</td><td>");
            pw.print(producttype);
            pw.print("<input type='hidden' name='producttype' value='" + producttype + "'>");
            pw.print("</td></tr>");
            pw.print("<tr><td> Product Price:</td><td>");
            pw.print(productprice);
            pw.print("<input type='hidden' name='productprice' value='" + productprice + "'>");
            pw.print("</td></tr>");
            pw.print("<tr><td> Product Maker: </td><td>");
            pw.print(productmaker);
            pw.print("<input type='hidden' name='productmaker' value='" + productmaker + "'>");
            pw.print("</td></tr><table>");
            pw.print("<br>");
            pw.print("<table><tr></tr><tr></tr>");
            pw.print("<tr>");
            pw.print("<td> User Age: </td>");
            pw.print("<td> <input type='text' name='age'> </td>");
            pw.print("</tr>");

            pw.print("<tr><td> User Gender: </td>");
            pw.print("<td>");
            pw.print("<select name='gender'>");
            pw.print("<option value='male' selected>male</option>");
            pw.print("<option value='female'>female</option>");
            pw.print("<option value='Nonbinary'>Nonbinary</option>");
            pw.print("<option value='Decline to state'>Decline to state</option>");
            pw.print("</td></tr>");

            pw.print("<td> User Occupation: </td>");
            pw.print("<td> <input type='text' name='Occupation'> </td>");

            pw.print("<tr><td> Review Rating: </td>");
            pw.print("<td>");
            pw.print("<select name='reviewRating'>");
            pw.print("<option value='1' selected>1</option>");
            pw.print("<option value='2'>2</option>");
            pw.print("<option value='3'>3</option>");
            pw.print("<option value='4'>4</option>");
            pw.print("<option value='5'>5</option>");
            pw.print("</td></tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer Id: </td>");
            pw.print("<td> <input type='text' name='retailerId'> </td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer Zip Code: </td>");
            pw.print("<td> <input type='text' name='zipcode'> </td>");
            pw.print("</tr>");


            pw.print("<tr>");
            pw.print("<td> Retailer City: </td>");
            pw.print("<td> <input type='text' name='StoreCity'> </td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Retailer State: </td>");
            pw.print("<td> <input type='text' name='retailerState'> </td>");
            pw.print("</tr>");

            pw.print("<tr><td> Product On Sale: </td>");
            pw.print("<td>");
            pw.print("<select name='onSale'>");
            pw.print("<option value='yes' selected>Yes</option>");
            pw.print("<option value='no'>No</option>");
            pw.print("</td></tr>");

            pw.print("<tr><td> Manufacturer Rebate: </td>");
            pw.print("<td>");
            pw.print("<select name='rebate'>");
            pw.print("<option value='yes' selected>Yes</option>");
            pw.print("<option value='no'>No</option>");
            pw.print("</td></tr>");

            pw.print("<tr>");
            pw.print("<td> Review Date: </td>");
            pw.print("<td> <input type='date' name='reviewdate'> </td>");
            pw.print("</tr>");

            pw.print("<tr>");
            pw.print("<td> Review Text: </td>");
            pw.print("<td><textarea name='reviewText' rows='4' cols='50'> </textarea></td></tr>");
            pw.print("<tr><td colspan='2'><input type='submit' class='btnbuy' name='SubmitReview' value='SubmitReview'></td></tr></table>");

            pw.print("</h2></div></div></div>");
            utility.printHtml("Footer.html");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

    }
}
