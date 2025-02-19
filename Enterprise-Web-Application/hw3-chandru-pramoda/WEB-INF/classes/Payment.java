import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.Random;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/Payment")

public class Payment extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        String msg = "good";
        String customername = "", user_id = "";
        ;
        HttpSession session = request.getSession(true);

        Utilities utility = new Utilities(request, pw);
        if (!utility.isLoggedin()) {
            session = request.getSession(true);
            session.setAttribute("login_msg", "Please Login to Pay");
            response.sendRedirect("Login");
            return;
        }
        // get the payment details like credit card no address processed from cart servlet

        String customeraddress = request.getParameter("address1");
        String creditCardNo = request.getParameter("creditCardNo");
        String deliveryPickup = request.getParameter("order");
        String storelocation = request.getParameter("storelocation");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double orderTotal = Double.parseDouble(request.getParameter("orderTotal"));
        double shippingCost = orderTotal * 0.05;
        customername = (String) session.getAttribute("username");
        user_id = (String) session.getAttribute("username");
        double discount = orderTotal * 0.05;
        String productType = request.getParameter("productType");
        if (session.getAttribute("usertype").equals("retailer")) {
            customername = (String) session.getAttribute("username");
            user_id = (String) session.getAttribute("username");
            try {
                HashMap<String, User> hm = new HashMap<String, User>();
                hm = MySqlDataStoreUtilities.selectUser();
                if (hm.containsKey(customername)) {
                    if (hm.get(customername).getUsertype().equals("customer")) {
                        msg = "good";
                    } else {
                        msg = "bad";
                    }

                } else {
                    msg = "bad";
                }

            } catch (Exception e) {

            }
        }

        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Order</a>");
        pw.print("</h2><div class='entry'>");

        String message = MySqlDataStoreUtilities.getConnection();
        if (message.equals("Successfull")) {
            if (msg.equals("good")) {
                int orderId = utility.getOrderPaymentSize() + 1;
                //iterate through each order

                for (OrderItem oi : utility.getCustomerOrders()) {

                    //set the parameter for each column and execute the prepared statement
                    utility.storePayment(orderId, user_id, customername, customeraddress, creditCardNo, oi.getName(), orderTotal, oi.getPrice(), shippingCost, oi.getType(), deliveryPickup, storelocation, quantity);

                }

                //remove the order details from cart after processing

                OrdersHashMap.orders.remove(utility.username());
                Utilities.updateQuanity(request.getParameter("productType"), request.getParameter("orderName"));

                pw.print("<h2>Your Order");
                pw.print("&nbsp&nbsp");
                pw.print("is stored ");
                pw.print("<br>Your Order No is " + (orderId) + "</h2>");
            } else {
                pw.print("<h2>Customer does not exits</h2>");
            }
        } else {
            pw.print("<h2>My Sql server is not up and running</h2>");
        }
        pw.print("</div></div></div>");
        utility.printHtml("Footer.html");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request, pw);
    }
}
