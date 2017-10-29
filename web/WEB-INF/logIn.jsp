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
            <form method="post" action="${pageContext.request.contextPath}/logIn">
                <label> user name: </label>
                <input  name="userName" type=text />
                <br><br>
                <label> password: </label>
                <input  name="password" type=password />
                <br><br>
                <input class="classSub" type="submit" />
                <% if((String)request.getAttribute("errMsg") != null) { %>
                    <label><%=(String)request.getAttribute("errMsg")%></label>
                <%}%>
            </form>
        </div>
    </div>
    </body>
    <script  type="text/javascript">
         function getErrMsg() {
             console.log("in getErrMsg");
             //var errMsg = <%= (String)request.getAttribute("errMsg") %>;
             //console.log("here");
             //console.log(errMsg);
             var errMsg = ${requestScope.get("errMsg")};
             request
             console.log(errMsg);

         }
         //getErrMsg();
    </script>
</html>