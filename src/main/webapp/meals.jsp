<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>type</th>
        <th>time</th>
        <th>calories</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <%--<jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.MealWithExceed"/>--%>
        <tr>
            <th>${meal.description}</th>
            <th>${meal.date}</th>
            <c:choose>
                <c:when test="${meal.exceed}">
                    <th style="color: red">${meal.calories}</th>
                </c:when>
                <c:otherwise>
                    <th style="color: green">${meal.calories}</th>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>
</table>
</body>
</html>

