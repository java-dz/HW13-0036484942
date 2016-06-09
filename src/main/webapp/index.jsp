<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <ul>
      <li><a href="colors.jsp">Background color chooser</a>
      <li><a href="trigonometric?a=0&b=90">Trigonometric</a>
      <li><a href="stories/funny.jsp">Funny stories</a>
      <li><a href="report.jsp">OS Usage</a>
      <li><a href="powers?a=1&b=100&n=3">Excel powers</a>
      <li><a href="appinfo.jsp">App Info</a>
      <li><a href="glasanje">Glasanje</a>
    </ul>
  </body>
</html>