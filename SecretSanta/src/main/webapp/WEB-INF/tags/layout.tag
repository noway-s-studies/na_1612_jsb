<%@tag description="Common layout" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html lang="en">
    <head>
        <title>SecretSanta</title>
        <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/skeleton/2.0.4/skeleton.min.css" />

        <c:url value="/css/alerts.css" var="jstlCss" />
        <link href="${jstlCss}" rel="stylesheet" />
    </head>
    <body>
        <div>
            <h1>SecretSanta</h1>
        </div>
        <div id="container">
            <c:if test="${fn:length(errorMessage) gt 0}">
                <div class="row">
                    <div class="six columns">
                        <p class="alert alert-error"><strong>Error!</strong> ${errorMessage}</p>
                    </div>
                </div>
            </c:if>
            <c:if test="${fn:length(infoMessage) gt 0}">
                <div class="row">
                    <div class="six columns">
                        <p class="alert alert-info"><strong>Info:</strong> ${infoMessage}</p>
                    </div>
                </div>
            </c:if>
            
            <jsp:doBody/>
            
        </div>
    </body>

</html>