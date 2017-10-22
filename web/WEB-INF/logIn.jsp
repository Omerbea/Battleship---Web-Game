<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Log In</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/login/css/loginStyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

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
                <% if((String)request.getAttribute("errMsg") != null) { %>
                    <label><%=(String)request.getAttribute("errMsg")%></label>
                <%}%>
            </form>
        </div>
    </div>
    </body>
</html>