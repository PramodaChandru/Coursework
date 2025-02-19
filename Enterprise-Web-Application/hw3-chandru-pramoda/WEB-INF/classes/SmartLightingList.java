import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SmartLightingList")

public class SmartLightingList extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();
		HashMap<String, SmartLighting> smartLights = new HashMap<String, SmartLighting>();
		try {
			smartLights = MySqlDataStoreUtilities.getLights();
		} catch(Exception e) {
		}

        String name = null;
        String CategoryName = request.getParameter("maker");
        HashMap<String, SmartLighting> hm = new HashMap<String, SmartLighting>();

        if (CategoryName == null) {
            hm.putAll(smartLights);
            name = "";
        } else {
            if (CategoryName.equals("Philips")) {
                for (Map.Entry<String, SmartLighting> entry : smartLights.entrySet()) {
                    if (entry.getValue().getRetailer().equals("Philips")) {
                        hm.put(entry.getValue().getId(), entry.getValue());
                    }
                }
                name = "Philips";
            } else if (CategoryName.equals("Govee")) {
                for (Map.Entry<String, SmartLighting> entry : smartLights.entrySet()) {
                    if (entry.getValue().getRetailer().equals("Govee")) {
                        hm.put(entry.getValue().getId(), entry.getValue());
                    }
                }
                name = "Govee";
            } else if (CategoryName.equals("Ring")) {
                for (Map.Entry<String, SmartLighting> entry : smartLights.entrySet()) {
                    if (entry.getValue().getRetailer().equals("Ring")) {
                        hm.put(entry.getValue().getId(), entry.getValue());
                    }
                }
                name = "Ring";
            }
        }

		/* Header, Left Navigation Bar are Printed.

		All the lights and light information are dispalyed in the Content Section

		and then Footer is Printed*/

        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>" + name + " SmartLight's</a>");
        pw.print("</h2><div class='entry'><table id='bestseller'>");
        int i = 1;
        int size = hm.size();
        for (Map.Entry<String, SmartLighting> entry : hm.entrySet()) {
            SmartLighting light = entry.getValue();
            if (i % 3 == 1) pw.print("<tr>");
            pw.print("<td><div id='shop_item'>");
            pw.print("<h3>" + light.getName() + "</h3>");
            pw.print("<strong>$" + light.getPrice() + "</strong><ul>");
            pw.print("<li id='item'><img src='images/SmartLightings/" + light.getImage() + "' alt='' /></li>");
            pw.print("<h5>" + light.getDescription() + "</h5></li>");

			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lights'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lights'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+light.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lights'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
            if (i % 3 == 0 || i == size)
                pw.print("</tr>");
            i++;
        }
        pw.print("</table></div></div></div>");
        utility.printHtml("Footer.html");
    }
}
