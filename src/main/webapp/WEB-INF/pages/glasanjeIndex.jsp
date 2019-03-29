<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <h1>Glasanje za omiljeni bend:</h1>
    <p>Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!</p>
    <ol>
      <li><a href="glasanje-glasaj?id=1">The Beatles</a></li>
      <li><a href="glasanje-glasaj?id=2">The Platters</a></li>
      <li><a href="glasanje-glasaj?id=3">The Beach Boys</a></li>
      <li><a href="glasanje-glasaj?id=4">The Four Seasons</a></li>
      <li><a href="glasanje-glasaj?id=5">The Marcels</a></li>
      <li><a href="glasanje-glasaj?id=6">The Everly Brothers</a></li>
      <li><a href="glasanje-glasaj?id=7">The Mamas And The Papas</a></li>
    </ol>
    <p>Idi <a href="/webapp2">kući</a>.</p>
  </body>
</html>
