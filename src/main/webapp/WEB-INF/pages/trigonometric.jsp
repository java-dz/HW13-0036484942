<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <body style="font-family: Consolas; background-color: <%= session.getAttribute("bgcolor") %>">
    <table border="1">
      <thead><tr align="center"><th>n&isin;N</th><th>sin(n)</th><th>cos(n)</th></tr></thead>
      <tbody>
      <c:forEach var="sinCos" items="${trigList}">
        <tr>
          <td>${sinCos[0]}</td>
          <td>${sinCos[1]}</td>
          <td>${sinCos[2]}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
    <p>Go <a href="/webapp2">home</a>.</p>
  </body>
</html>