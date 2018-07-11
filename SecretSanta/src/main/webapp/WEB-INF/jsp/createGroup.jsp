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

            <form method="POST" action="/groups">
                <div>
                    <label for="groupName">Group</label>
                    <input type="text" placeholder="Group" id="groupName" name="name" required />
                </div>
                <div>
                    <button class="button-primary" id="submit" type="submit">Create group</button>
                </div>
            </form>
        </div>
    </body>

</html>