<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file = "/WEB-INF/jsp/taginclude.jsp"%>

<html>
    <head>
        <title>Account Transfers</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
        <script src="/js/common.js"></script>
    </head>
    <body>  
    	<br>
    	<h2 style="color:blue" align="center">HALC Kafka Current Account Balances</h2>
    	<br>
    	<br>
    	
    	<form:form id = "refresh" name = "refresh" action = "refresh.form" >
    	
	        	
    		<table class="styled-table2">
		            <thead>
		                <tr>
		                    <th>Account Id</th>
		                    <th>Current Balance</th>
		                </tr>
		            </thead>
	            
		            <tbody>
		            	
		            	<c:forEach items="${acctbalances}" var="entry">
	    					<tr class="active-row">
		         					<td><p style="color:green;"><c:out value="${entry.accountId}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.amount}"/></p></td>
		     					</tr>
						</c:forEach>
	   						
	   						<tr>	                   		
		                 		<td colspan="1">
		                 			<input type="submit" class="submit" value="Refresh">
		                 		</td>
		                 		<td colspan="1">
	                 				<input type="Button" class="submit" value="Main" onClick="redirectHome()">
	                 			</td>		
		                   </tr>
		                   
		            </tbody>
	         </table>  	        
	        
        </form:form>
        
    </body>
</html>
