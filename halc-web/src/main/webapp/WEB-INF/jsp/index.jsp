<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file = "/WEB-INF/jsp/taginclude.jsp"%>
<html>
    <head>
        <title>Home page</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <script src="/js/common.js"></script>
    </head>
    <body>  
    	<br>
    	<h2 style="color:blue" align="Center">HALC Kafka (Confluent io) experiment</h2>
    	<br>
    	<br>
    	
    	<table class=styled-table2>
		            <thead>
		                <tr>
		                    <th>Link</th>
		                    <th>Description</th>
		                </tr>
		            </thead>
	            
		            <tbody>
		            	
		            	<c:forEach items="${halcLinks}" var="entry">
	    					<tr class="active-row">
		         					<td><p style="color:green;">
		         							<a href="<c:out value="${entry.key}"/>">Click Here</a>		         							
		         						</p>
		         					</td>
		         					<td>
		         						<p style="color:green;"><c:out value="${entry.value}"/></p>
		         					</td>
		     					</tr>
						</c:forEach>
	   						
		            </tbody>
	         </table>  	   
        
    </body>
</html>