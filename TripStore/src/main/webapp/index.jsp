<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Tours store</title>
    <style>
        html, body {height: 100%}
        body {
            margin: auto;
            background: url('https://images.pexels.com/photos/460672/pexels-photo-460672.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb') no-repeat;
            background-size: cover;
        }
        .navigator {
            width: 100%;
            min-width: 440px;
            min-height: 70px;
            background-color: #31302B;
            opacity: 0.95;
        }
        .navigator .item {
            text-decoration:none;
            color: white;
            padding: 23px 18px;
            font-size: 1.2em;
        }
        .left{float: left}
        .right {float: right}
        .item:hover {color: #9fcdff}
        .navigator .item:nth-child(1) {background-color: black;}
        .info {
            width: 470px;
            height: 250px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            opacity: 0.9;
            border-radius: 10px;
            padding: 15px;
            font-size: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
<div class="navigator">
    <a class="item left" href="${pageContext.request.contextPath}/home">Home</a>
    <a class="item left" href="${pageContext.request.contextPath}/tours">Tours</a>

    <c:if test="${sessionScope.logged != null}">
        <a class="item left" href="${pageContext.request.contextPath}/tours/my">My booked tours</a>
    </c:if>

    <c:if test="${sessionScope.logged == null}">
        <a class="item right" href="${pageContext.request.contextPath}/register">Register</a>
        <a class="item right" href="${pageContext.request.contextPath}/login">Login</a>
    </c:if>

    <c:if test="${sessionScope.logged != null}">
        <a class="item right" href="${pageContext.request.contextPath}/logout">Log out</a>
    </c:if>
</div>

<div class="info">
    Our online tour booking and reservation system gives you all the
    features you need to experience a great and cheap travelling!
    <br>
    <br>
    <br>
    <h3>Let's explore the World together!</h3>
</div>

</body>
</html>