import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/SmartSpeakerList")

public class SmartSpeakerList extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String, SmartSpeaker> smartSpeakers = new HashMap<String, SmartSpeaker>();
		try {
			smartSpeakers = MySqlDataStoreUtilities.getSpeakers();
		} catch(Exception e) {
		}

		String name = null;
		String CategoryName = request.getParameter("maker");


		HashMap<String, SmartSpeaker> hm = new HashMap<String, SmartSpeaker>();
		if(CategoryName==null){
			hm.putAll(smartSpeakers);
			name = "";
		}
		else
		{
		   if(CategoryName.equals("Amazon"))
		   {
			 for(Map.Entry<String, SmartSpeaker> entry : smartSpeakers.entrySet())
			 {
				if(entry.getValue().getRetailer().equals("Amazon"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
			 }
				name = "Amazon";
		   }
		   else if(CategoryName.equals("Apple"))
		    {
			for(Map.Entry<String, SmartSpeaker> entry : smartSpeakers.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Apple"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Apple";
			}
			else if(CategoryName.equals("Bose"))
			{
				for(Map.Entry<String, SmartSpeaker> entry : smartSpeakers.entrySet())
				{
				 if(entry.getValue().getRetailer().equals("Bose"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Bose";
			}
		}
		
		/* Header, Left Navigation Bar are Printed.

		All the SmartSpeakerList and SmartSpeakerList information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" SmartSpeaker's</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, SmartSpeaker> entry : hm.entrySet())
		{
			SmartSpeaker speakers = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+speakers.getName()+"</h3>");
			pw.print("<strong>$"+speakers.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/SmartSpeakers/"+speakers.getImage()+"' alt='' /></li>");
			pw.print("<h5>" + speakers.getDescription() + "</h5></li>");

			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='speakers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='speakers'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+speakers.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='speakers'>"+
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
