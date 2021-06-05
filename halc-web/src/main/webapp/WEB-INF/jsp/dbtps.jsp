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
    	<h2 style="color:blue" align="Center">HALC PostgreSQL DB Throughput</h2>
    	<br>
    	<br>
    	
    	<table class=styled-table2>
		            <thead>
		                <tr>
		                    <th></th>
		                </tr>
		            </thead>
	            
		            <tbody>
		         			<tr class="active-row">
		         				<td><p style="color:green;">
		         							<c:out value="${dbtps}"/> 	         							
		         					</p>
		         				</td>
		     				</tr>
		     				<tr>
	                 		<td colspan="1">
	                 				<input type="Button" class="submit" value="Main" onClick="redirectHome()">
	                 		</td>	
	                   </tr>
		     				
		            </tbody>
	         </table>  	   
        
    </body>
</html>