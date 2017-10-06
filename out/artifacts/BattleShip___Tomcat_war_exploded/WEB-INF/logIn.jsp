<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Log In</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/login/css/loginStyle.css"/>
    </head>
    <body>
    <div class="container">
        <div class="header">
		    <h1> Log In Page </h1>
        </div>
        <div class="loginform">
            <form method="post" action="/logIn">
                <label> user name: </label>
                <input  name="userName" type=text />
                <label> password: </label>
                <input  name="password" type=password />
                <input type="submit" />
            </form>
        </div>
    </div>
    </body>
</html>