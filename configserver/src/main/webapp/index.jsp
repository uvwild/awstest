<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Root</title>
</head>
<body background="#ffddee">
<h1>you made it to the <b>configserver</b>! <% new Date().toString(); %></h1>

<h2><A href="./health">Healthcheck</A></h2>

<h2><A href="./admin/env">Environment</A></h2>
</body>
</html>
