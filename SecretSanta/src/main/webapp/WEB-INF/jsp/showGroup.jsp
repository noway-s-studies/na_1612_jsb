<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<t:layout>
    <c:if test="${group.state == 'CREATED'}">
        <div class="row">
            <div class="six columns">
                <h4>Add members to group: ${group.name}</h4>
            </div>
        </div>
        <div class="row">
            <div class="ten columns">
                <form action="/groups/${group.id}/members" method="POST">
                    <label for="name">Name</label>
                    <input type="text" id="name" name="name" placeholder="Name" required />
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Email" required />
                    <button class="button-primary" type="submit">+</button>
                </form>
            </div>
        </div>
        <form action="/groups/${group.id}/finish" method="POST">
            <div>
                <button class="button-primary">Finish</button>
            </div>
        </form>
    </c:if>
    <c:if test="${fn:length(group.members) gt 0}">
        <div class="row">
            <table>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                </tr>
                <c:forEach items="${group.members}" var="item">
                    <tr><td>${item.name}</td><td>${item.email}</td></tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</t:layout>