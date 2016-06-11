<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>--%>
<%@page import="java.io.BufferedWriter" %>
<%@page import="java.io.FileWriter" %>
<%@page import="java.util.Date" %>
<%@page import="java.util.Scanner" %>
<%
    Long count = (Long) session.getAttribute("count");
    if (count == null)
        count = 0L;
    count += 1L;
    session.setAttribute("count", count);
    /** Log POSTs at / to a file **/
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        BufferedWriter writer = new BufferedWriter(new FileWriter("/tmp/sample-app.log", true));
        Scanner scanner = new Scanner(request.getInputStream()).useDelimiter("\\A");
        if (scanner.hasNext()) {
            String reqBody = scanner.next();
            writer.write(String.format("%s Received message: %s.\n", (new Date()).toString(), reqBody));
        }
        writer.flush();
        writer.close();

    } else {
%>
<jsp:useBean id="appversion" class="org.funtime.Version" scope="page"/>
<jsp:useBean id="today" class="java.util.Date" scope="page"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <!--
      Copyright 2010-2011 Amazon.com, Inc. or its affiliates. All Rights Reserved.

      Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance with the License. A copy of the License is located at

          http://aws.Amazon/apache2.0/

      or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
    -->
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Welcome</title>
    <style>
        body {
            color: #ffffff;
            background-color: #ffc7c7;
            font-family: Arial, sans-serif;
            font-size: 14px;
            -moz-transition-property: text-shadow;
            -moz-transition-duration: 4s;
            -webkit-transition-property: text-shadow;
            -webkit-transition-duration: 4s;
            text-shadow: none;
        }

        body.blurry {
            -moz-transition-property: text-shadow;
            -moz-transition-duration: 4s;
            -webkit-transition-property: text-shadow;
            -webkit-transition-duration: 4s;
            text-shadow: #fff 0px 0px 25px;
        }

        a {
            color: #1827cc;
        }

        .textColumn, .linksColumn, #footer {
            padding: 2em;
        }

        .textColumn {
            position: absolute;
            top: 0px;
            right: 50%;
            bottom: 60px;
            left: 0px;

            text-align: right;
            padding-top: 11em;
            background-color: #0188cc;
            background-image: -moz-radial-gradient(left top, circle, #6ac9f9 0%, #cc00c0 60%);
            background-image: -webkit-gradient(radial, 0 0, 1, 0 0, 500, from(#6ac9f9), to(#0188cc));
        }

        .textColumn p {
            width: 75%;
            float: right;
        }

        .linksColumn {
            position: absolute;
            top: 0px;
            right: 0px;
            bottom: 60px;
            left: 50%;

            background-color: #c79cb2;
        }

        #footer {
            margin: 8px;
            position:absolute;
            clear: both;
            bottom:0;
            width:100%;
            height:2em;   /* Height of the footer */
            background: #6cf;

        }

        h1 {
            font-size: 500%;
            font-weight: normal;
            margin-bottom: 0em;
        }

        h2 {
            font-size: 200%;
            font-weight: normal;
            margin-bottom: 0em;
        }

        ul {
            padding-left: 1em;
            margin: 0px;
        }

        li {
            margin: 4px 0em;
        }

    </style>
</head>
<body id="sample">
<div class="textColumn">
    <h1>Congratulations UV you made it ${count} Times !!!</h1>

    <p>Your first AWS Elastic Beanstalk Application is now running on your own dedicated environment in the AWS Cloud</p>
</div>

<div class="linksColumn">
    <h2>What's Next? (${today.time})</h2>
    <ul>
        <li><a href="./dataset/0">Get Dataset timestamp 0</a></li>
        <li><a href="./dataset/1">Get Dataset timestamp 1</a></li>
        <li><a href="./getLast">Get Dataset timestamp Latest </a></li>
        <li><a href="./dataset/${today.time}">Get Dataset timestamp "${today.time}"</a></li>
        <li/>
        <li><a href="./createNext">Set Dataset timestamp</a></li>
        <li/>
        <li><a href="./dataset">Get All Datasets</a></li>
        <li/>
        <li><a href="./home">Home</a></li>
        <li/>
        <li><a href="./delay">Delay</a></li>
        <li><a href="./delay/1234">Delay 1234</a></li>
        <li/>
        <li><a href="./health">Health</a></li>
    </ul>
</div>
<div id="footer">
    <table>
        <tr>
            <td>Version:</td>
            <td><spring:eval expression="@version.buildNumber" /></td>
        </tr>
        <tr>
            <td>TimeStamp:</td>
            <td><spring:eval expression="@version.timeStamp" /></td>
        </tr>
    </table>
</div>
</body>
</html>
<% } %>
