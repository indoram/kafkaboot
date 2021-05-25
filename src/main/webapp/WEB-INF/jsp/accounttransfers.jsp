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
    	<h2 style="color:blue" align="left">HALC Kafka Account Transfer</h2>
    	<br>
    	<br>
    	
    	<form:form id = "accountTransfer" name = "accountTransfer" action = "transfer.form" modelAttribute = "accountTransfer">
    	
    		<form:hidden path="fromAccount"/>
    		<form:hidden path="toAccount"/>
    		<form:hidden path="amount"/>
    		<form:hidden path="trn"/>
    		
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
	            		
	                   <tr class="active-row">	                   		
	                   		<td>${accountTransfer.fromAccount}</td>
	                        <td>${accountTransfer.toAccount}</td>
	                        <td>${accountTransfer.amount}</td>
	                        <td>${accountTransfer.trn}</td>
	                   </tr>
	                   	                   
	                   <tr>	                   		
	                 		<td colspan="3">
	                 			<input type="submit" class="submit" value="Transfer Funds">
	                 		</td>
	                 		
	                 		<td colspan="1">
	                 			<input type="Button" class="submit" value="Main" onClick="redirectHome()">
	                 		</td>	
	                 			                 			
	                   </tr>
	                   
	                   <c:if test="${not empty transferSuccessMsg}">
	                   		<tr>
	                   			<td colspan="4">	       
   									<p style="color:blue;"> <c:out value="${transferSuccessMsg}"/> </p>
   								</td>	
   							</tr>
						</c:if>
	                   
	                               
	            </tbody>
	        </table>	    
	        
	         	
    		<table class="styled-table1">
		            <thead>
		                <tr>
		                    <th>Account Id</th>
		                    <th>Current Balance</th>
		                </tr>
		            </thead>
	            
		            <tbody>
		            	
		            	<c:forEach items="${acctbalances}" var="entry">
	    					<tr class="active-row">
		         					<td><p style="color:green;"><c:out value="${entry.key}"/></p></td>
		         					<td><p style="color:green;"><c:out value="${entry.value}"/></p></td>
		     					</tr>
						</c:forEach>	   						

		            </tbody>
	         </table>  	            
	        
        </form:form>
        
    </body>
</html>