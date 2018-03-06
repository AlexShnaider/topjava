<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <jsp:useBean id="meals" scope="request" type="java.util.List"/>
    <title>Meals</title>
</head>
<body>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>type</th>
        <th>date and time</th>
        <th>calories</th>
        <th colspan="2">actions</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr style="color: ${meal.exceed ? "red" : "green"}">
            <th>${meal.description}</th>
            <th>${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}</th>
            <th>${meal.calories}</th>
            <td><a href="meals?id=${meal.id}&action=delete"><img src="img/basket.png"></a></td>
            <td><a href="meals?id=${meal.id}&action=edit"><img src="img/pencil.png"></a></td>
        </tr>
    </c:forEach>
</table>
<br />
<form action="meals">
    <input type="hidden" name="action" value="add"/>
    <input type="submit" value="Add new meal"/>
</form>
</body>
</html>

