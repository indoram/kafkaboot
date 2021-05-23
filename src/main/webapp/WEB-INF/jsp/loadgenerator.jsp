<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file = "/WEB-INF/jsp/taginclude.jsp"%>

<html>
    <head>
        <title>Account Transfers</title>
        <link href="<c:url value="/css/common.css"/>" rel="stylesheet" type="text/css">
    </head>
    <body>  
    	<br>
    	<h2 style="color:blue" align="left">HALC Kafka HALC Load Generator</h2>
    	<br>
    	<br>
    	
    	<form:form id = "loadGenerator" name = "loadGenerator" action = "generateload.form" modelAttribute = "loadGenerator">
    	
	        <table class="styled-table">
	            <thead>
	                <tr>
	                    <th>Max. Account Transfers</th>
	                    <th>Duration Secs</th>
	                </tr>
	            </thead>
	            
	            <tbody>
	            		
	                   <tr class="active-row">  		
	                        <td><form:input path="noOfTransactions"/></td>
	                        <td><form:input path="durationSecs"/></td>
	                   </tr>
	                   	                   
	                   <tr>	                   		
	                 		<td colspan="2">
	                 			<input type="submit" class="submit" value="Generate Load">
	                 		</td>	
	                   </tr>
	                   
	                   <c:if test="${not empty transferSuccessMsg}">
	                   		<tr>
	                   			<td colspan="4">	       
   									<p style="color:blue;"> <c:out value="${transferSuccessMsg}"/> </p>
   								</td>	
   							</tr>
						</c:if>
	                   
	                   <c:if test="${not empty durationKey}">
	                   		<tr>
	                   			<td colspan="4">	       
   									<p style="color:blue;"> <c:out value="${durationKey}"/> </p>
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
	            	   <c:forEach items="${fromAccountIds}" var="acct">
	     					<tr class="active-row">
	         					<td><p style="color:green;"><c:out value="${acct}"/></p></td>
	         					<td></td>
	     					</tr>
   						</c:forEach>
	            </tbody>
	         </table>  	        
	        
        </form:form>
        
    </body>
</html>
