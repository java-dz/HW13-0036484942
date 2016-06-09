<%@page import="java.util.Random" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% Random rnd = new Random(); %>
<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <p style="color: rgb(<%=rnd.nextInt(255)%>,<%=rnd.nextInt(255)%>,<%=rnd.nextInt(255)%>)">
      So, a bear walks into a bar... <br/>
      See the rest of <a href="http://www.jkcinema.com/runflash.aspx?id=bear&name=The%20Infamous%20Bear%20Joke&width=450&height=300&quality=best">The Infamous Bear Joke</a>.
    </p>
    <p>Go <a href="/webapp2">home</a>.</p>
  </body>
</html>