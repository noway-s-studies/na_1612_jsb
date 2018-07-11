<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
<head>
    <title>SecretSanta</title>
    <jsp:include page="../cssLoader.jsp"></jsp:include>
</head>
    <body>
        <div>
            <h1>SecretSanta</h1>
        </div>
        <div id="container">
            <div class="ten columns">
                <c:if test="${fn:length(errorMessage) gt 0}">
                    <div class="row">
                        <div class="six columns">
                            <p class="alert alert-error"><strong>Error!</strong> ${errorMessage}</p>
                        </div>
                    </div>
                </c:if>
                <div>Id: ${group.id}; Name: ${group.name} </div>
            </div>
        </div>
    </body>
</html>