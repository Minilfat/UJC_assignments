<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Trip Store: sign in</title>
    <style>
        body {
            margin: auto;
            background: url('https://images.pexels.com/photos/460672/pexels-photo-460672.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb') no-repeat;
            background-size: cover;
        }

        .wrapper {
            width: 320px;
            height: 400px;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: #0f1113;
            border-radius: 10px;
            opacity: 0.9;
        }

        .login {
            position: relative;
            height: 285px;
        }

        .register {
            position: relative;
            height: 85px;
        }

        .form-header {
            width: 100%;
            height: 15%;
            text-align: center;
            font-size: 1.5em;
            color: #cfd1d3;
            background-color: #3f3f3f;
            border-radius: 10px 10px 0px 0px;
            position: relative;
        }

        .input-row {
            width: 100%;
            height: 25%;
            position: relative;
        }
        .input-row input {
            border-radius: 3px;
            width: 84%;
            margin: auto;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%);
            background-color: #3f3f3f;
            font-size: 0.95em;
            border: 0px;
            padding: 3px 6px;
            color: #cfd1d3;
        }

        .submit-row {
            width: 100%;
            height: 25%;
            position: relative;
        }

        .submit-row input {
            border-radius: 3px;
            width: 30%;
            margin: auto;
            position: absolute;
            top: 50%;
            left: 8%;
            transform: translate(0, -50%);
            background-color: #207e03;
            font-size: 0.95em;
            border: 0px;
            padding: 8px 5px;
            color: #cfd1d3;
        }

        .register .submit-row {
            margin: auto;
            position: absolute;

            top: 50%;
            color: #cfd1d3;
        }

        hr {
            width: 92%;
            margin: auto;
        }

        span {
            margin: auto;
            position: absolute;

            left: 8%;
            color: #cfd1d3;
        }

        h6 {
            margin: auto;
            position: absolute;
            top: 8%;
            left: 8%;
            color: #cfd1d3;
            font-size: 1.0em;
        }

        h5 {
            margin: auto;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .error-mes {
            width: 100%;
            height: 10%;
            position: relative;
        }

        .error-mes span {
            color: red;
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
    </style>
</head>
<body>
<div class="navigator">
    <a class="item left" href="${pageContext.request.contextPath}/home">Home</a>
    <a class="item left" href="${pageContext.request.contextPath}/tours">Tours</a>
</div>
<div class="wrapper">
    <form class="login" method="post" action="${pageContext.request.contextPath}/login">
        <div class="form-header"><h5>Sign in</h5></div>

        <div class="input-row">
            <h6>Login</h6>
            <input type="text" name="login" placeholder="Username"/>
        </div>

        <div class="input-row">
            <h6>Password</h6>
            <input type="password" name="password" placeholder="Password"/>
        </div>

        <div class="error-mes">
            <span> ${errorMessage} </span>
        </div>

        <div class="submit-row">
            <input type="submit" value="Sign in"/>
        </div>
    </form>
    <hr>
    <br>
    <form class="register" method="GET" action="${pageContext.request.contextPath}/register">
        <span>Don't have an account? Register!</span>
        <div class="submit-row">
            <input type="submit" value="Register"/>
        </div>
    </form>
</div>
</body>
</html>