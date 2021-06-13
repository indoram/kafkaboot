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
    	<h2 style="color:blue" align="center"><a href = "/">HALC Kafka PostgreSQL last 500</a></h2>
    	<br>
    	<br>
    	
    	<form:form id = "refresh" name = "refresh" action = "refresh.form" >
    	        		
    		<table class="styled-table4">
		            <thead>
		                <tr>
		                	<th>Id</th>
		                    <th>Debit Account Id</th>		                    
		                    <th>Credit Account Id</th>
		                    <th>Amount</th>
		                    <th>trn</th>
		                    <th>publish time</th>
		                    <th>settled time</th>
		                </tr>
		            </thead>
	            
		            <tbody>
		            	
		            	<c:forEach items="${settled}" var="entry">
	    					<tr class="active-row">
		         					<td><p style="color:green;"><c:out value="${entry.tranId}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.fromAccount}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.toAccount}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.amount}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.trn}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.publishDateTime}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.settleDateTime}"/></p></td>
		     					</tr>
						</c:forEach>
	   						
	   				
		                   
		            </tbody>
	         </table>  	        
	        
        </form:form>
        
    </body>
</html>
