<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Modified images</title>
  
    <link rel="stylesheet" href="css/results.css">   
    <link href="https://use.fontawesome.com/releases/v5.0.8/css/all.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Montserrat' rel='stylesheet' type='text/css'>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/modernizr/2.8.3/modernizr.min.js" type="text/javascript"></script> 
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/css/bootstrap.min.css" integrity="sha384-Zug+QiDoJOrZ5t4lssLdxGhVrurbmBWopoEl+M6BdEfwnCJZtKxi1KgxUyJq13dy" crossorigin="anonymous">
</head>
<body >
     <c:set var="myVar" value="${requestScope.filesPath}" />
     <c:set var="date" value="${requestScope.date}" />
     <c:set var="generated" value="${requestScope.generated}" />
    
   
    
  
   
    <div class="row" style="margin: 2rem">
        
       
	<%--<c:url value="/addPerson" var="addURL"></c:url>--%>
          
	<c:if test="${not empty requestScope.files}">
            <ul>					
            <c:forEach items="${requestScope.files}" var="file" varStatus="myIndex">
                <li><img src="<c:out value="${myVar}" />${file}" alt="${myIndex} not found" height="100px">                   
                   <a href="downloadServlet?generated=<c:out value="${generated}" />&date=<c:out value="${date}" />&fileName=${file}">${file}</a>
                   
                </li>
            </c:forEach>
            </ul> 
	</c:if>
    </div>  
        
        
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"  integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.3/js/bootstrap.min.js" integrity="sha384-a5N7Y/aK3qNeh15eJKGWxsqtnX/wWdSZSKp+81YjTmS15nvnvxKHuzaWwXHDli+4" crossorigin="anonymous"></script>
          
</body>
</html>