import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SmartThermostatList")

public class SmartThermostatList extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String, SmartThermostat> smartThermostats = new HashMap<String, SmartThermostat>();
		try {
			smartThermostats = MySqlDataStoreUtilities.getThermostats();
		} catch(Exception e) {
		}
		String name = null;
		String CategoryName = request.getParameter("maker");


		HashMap<String, SmartThermostat> hm = new HashMap<String, SmartThermostat>();
		if(CategoryName == null){
			hm.putAll(smartThermostats);
			name = "";
		}
		else
		{
			if(CategoryName.equals("Google"))
			{
				for(Map.Entry<String, SmartThermostat> entry : smartThermostats.entrySet())
				{
					if(entry.getValue().getRetailer().equals("Google"))
					{
						hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				name = "Google";
			}
			else if(CategoryName.equals("Emerson"))
			{
				for(Map.Entry<String, SmartThermostat> entry : smartThermostats.entrySet())
				{
					if(entry.getValue().getRetailer().equals("Emerson"))
					{
						hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				name = "Emerson";
			}
			else if(CategoryName.equals("ecobee"))
			{
				for(Map.Entry<String, SmartThermostat> entry : smartThermostats.entrySet())
				{
					if(entry.getValue().getRetailer().equals("ecobee"))
					{
						hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				name = "ecobee";
			}
			else if(CategoryName.equals("Honeywell"))
			{
				for(Map.Entry<String, SmartThermostat> entry : smartThermostats.entrySet())
				{
					if(entry.getValue().getRetailer().equals("Honeywell"))
					{
						hm.put(entry.getValue().getId(),entry.getValue());
					}
				}
				name = "Honeywell";
			}
		}
		
		/* Header, Left Navigation Bar are Printed.

		All the SmartThermostatList and SmartThermostatList information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" SmartThermostat's</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SmartThermostat> entry : hm.entrySet())
		{
			SmartThermostat thermostats = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+thermostats.getName()+"</h3>");
			pw.print("<strong>$"+thermostats.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/SmartThermostats/"+thermostats.getImage()+"' alt='' /></li>");
			pw.print("<h5>" + thermostats.getDescription() + "</h5></li>");

			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+thermostats.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");

		utility.printHtml("Footer.html");

	}
	}