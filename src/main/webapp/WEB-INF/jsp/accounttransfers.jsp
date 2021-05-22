<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <title>Account Transfers</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>  
    	<br>
    	<h2 style="color:blue" align="left">HALC Kafka Single Account Transfer</h2>
    	<br>
    	<br>
        <table class="styled-table">
            <thead>
                <tr>
                    <th>From Account</th>
                    <th>To Account</th>
                    <th>Amount</th>
                    <th>Ref</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${books}" var="book">
                    <tr class="active-row">
                        <td>${book.isbn}</td>
                        <td>${book.name}</td>
                        <td>${book.author}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>