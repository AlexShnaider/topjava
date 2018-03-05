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
        <th>date and time</th>
        <th>calories</th>
    </tr>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style="color: ${meal.exceed ? "red" : "green"}">
            <th>${meal.description}</th>
            <th>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</th>
            <th>${meal.calories}</th>
        </tr>
    </c:forEach>
</table>
</body>
</html>

