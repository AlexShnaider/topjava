<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="style.css">
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <title>Edit meal</title>
</head>
<body>
<form method="post" action="meals" enctype="application/x-www-form-urlencoded">
    <input type="hidden" name="id" value="${meal.id}">
    <dl>
        <dt>Meal name:</dt>
        <dd>
            <select name="description">
                <option value="${meal.description}" hidden>${meal.description}</option>
                <option value="Breakfast">Breakfast</option>
                <option value="Lunch">Lunch</option>
                <option value="Dinner">Dinner</option>
            </select>
        </dd>
        <br/> <br/>
        <dt>Date and time:</dt>
        <dd>
            <input type="text" name="dateTime" value="<%=meal.getDateTime() == null ? "" :
            meal.getDateTime().toLocalDate().toString() + " " + meal.getDateTime().toLocalTime().toString()%>"
                   placeholder="yyyy-MM-dd HH-mm">
        </dd>
        <br/> <br/>
        <dt>Calories:</dt>
        <dd>
            <input type="number" name="calories" value="${meal.calories}">
        </dd>
    </dl>
    <button type="submit">Save</button>
    <button type="button" onclick="window.history.back()">Cancel</button>
</form>
</body>
</html>
