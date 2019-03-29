<%@page import="java.util.concurrent.TimeUnit"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
  long startTime = (Long) application.getAttribute("startTime");
  long now = System.currentTimeMillis();
  long diff = now - startTime;

  long days = TimeUnit.MILLISECONDS.toDays(diff);
  diff -= TimeUnit.DAYS.toMillis(days);
  long hours = TimeUnit.MILLISECONDS.toHours(diff);
  diff -= TimeUnit.HOURS.toMillis(hours);
  long minutes = TimeUnit.MILLISECONDS.toMinutes(diff);
  diff -= TimeUnit.MINUTES.toMillis(minutes);
  long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
  diff -= TimeUnit.SECONDS.toMillis(seconds);
  long milliseconds = diff;

  String uptime = days + " Days " + hours + " Hours " + minutes + " Minutes "
                + seconds + " Seconds " + milliseconds + " Milliseconds";
%>

<html>
  <body style="background-color: <%= session.getAttribute("bgcolor") %>">
    <p>Server uptime: <%= uptime %></p>
    <p>Go <a href="/webapp2">home</a>.</p>
  </body>
</html>
