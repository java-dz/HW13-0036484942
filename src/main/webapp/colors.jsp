<%@page import="java.util.Random"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<% Random rnd = new Random(); %>
<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <ul>
      <li><a href="setcolor?color=white">WHITE</a>
      <li><a href="setcolor?color=red">RED</a>
      <li><a href="setcolor?color=green">GREEN</a>
      <li><a href="setcolor?color=cyan">CYAN</a>
      <li><a href="setcolor?color=rgb(<%=rnd.nextInt(255)%>,<%=rnd.nextInt(255)%>,<%=rnd.nextInt(255)%>)">RANDOM</a>
    </ul>
    <p>Go <a href="/webapp2">home</a>.</p>
  </body>
</html>