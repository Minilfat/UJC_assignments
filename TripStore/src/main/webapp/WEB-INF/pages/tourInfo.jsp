<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Information about tour</title>
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

        .tour-info {
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
        }

        .textbox {
            width: 100%;
            height: 10%;
            margin: auto;
            color: black;

            position: relative;
            font-size: 1.1em;

            background-color: white;
        }
        .left {
            margin: auto;
            float: left;
        }
        .right {
            margin: auto;
            float: right;
        }
        hr {
            border-top: 3px dotted #000;
        }


    </style>
</head>
<body>

<div class="navigator">
    <a class="item left" href="${pageContext.request.contextPath}/home">Home</a>
    <a class="item left" href="${pageContext.request.contextPath}/tours">Tours</a>
    <a class="item left" href="${pageContext.request.contextPath}/tours/my">My booked tours</a>
    <a class="item right" href="${pageContext.request.contextPath}/logout">Log out</a>
</div>


<div class="tour-info">
    <div class="textbox">
        <p class="left">City</p>
        <p class="right">${tour.city}</p>
    </div>
    <hr>
    <div class="textbox">
        <p class="left">Starts</p>
        <p class="right">${tour.from}</p>
    </div>
    <hr>
    <div class="textbox">
        <p class="left">Ends</p>
        <p class="right">${tour.to}</p>
    </div>
    <hr>
    <div class="textbox">
        <p class="left">Price</p>
        <p class="right">$${tour.price}</p>
    </div>
    <hr>
    <br>
    <br>
    <div style="text-align:center">
        You successfully booked the tour
    </div>
</div>

</body>
</html>
