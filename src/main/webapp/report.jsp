<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <h1>OS usage</h1>
    <p>Here are the results of OS usage in survey that we completed.</p>
    <img src="reportImage" alt="OS usage" />
    <p>Go <a href="/webapp2">home</a>.</p>
  </body>
</html>
