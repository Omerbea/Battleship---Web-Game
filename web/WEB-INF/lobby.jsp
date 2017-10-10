<%@ page import="java.util.HashSet" %>
<%@ page import="java.util.HashMap" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Lobby</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/lobby/css/lobbyStyle.css"/>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    </head>
    <body>
        <div class="container">
            <header class="head">
                <button onclick="logout()">logout</button>
                <h1> Lobby </h1>
            </header>
            <div class="gamesList">
                <table>
                    <tbody>
                    <tr>
                        <th>Game Name</th>
                        <th>Created By</th>
                        <th>Board Size</th>
                        <th>Game Type</th>
                        <th>Active</th>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="activePlayers">
                <table>
                    <tbody>
                        <tr>
                            <th>Active Players</th>
                        </tr>
                    </tbody>
                </table>
            </div>
            <div class="uploadFile">
                <form method="post" action="/loadGame" enctype="multipart/form-data">
                    <label> Upload Game </label>
                    <input type="file" name="gameFile" accept="application/xml">
                    <P>
                        <label> Name Game</label>
                        <input name="nameGame" type="text" />
                    </P>
                    <input type="submit"/>
                    <p>
                        <div class="errorBoxLoadFile">

                        </div>
                    </p>
                </form>
            </div>
        </div>
    </body>
    <script type="text/javascript">
        function logout() {
            $.ajax({
                type: "GET",
                url: "/logout",
                success: function () {
                    console.log("redirected to login.");
                    window.location.replace("../logIn");
                }
            });
        }
        $.ajax({
            type: "GET" ,
            url : "/activePlayersData" ,
            success : function(result) {
                console.log(result);
                $.each(result,function(index , element) {
                    $('.activePlayers tbody').append("<tr><td>"+element+"</td></tr>");
                });
            }
        });
        $.ajax({
            type: "GET" ,
            url : "/activeGamesData" ,
            success : function(result) {
                console.log(result);
                $.each(result,function(index , element) {
                    $('.gamesList tbody').append("<tr><td>"+element.name+"</td>" +
                    "<td>"+element.playerNameThatLoadedCurrentGame+"</td>" +
                    "<td>"+element.boardSize+"</td>" +
                    "<td>"+element.typeGame+"</td>" +
                    "<td>"+element.isHaveRegisterPlayer+"</td>");
                });
            }
        });


        $.ajax({
            type: "GET" ,
            url : "/errorCodeLoadGame" ,
            success : function(result) {
                console.log ("errorCoseLoadGame():")
                console.log(result);
                if (result ==''){
                    console.log("no error")

                }
                else {
                    console.log ("error load..")
                    $('.errorBoxLoadFile').append("<label>" + result + "</label>");
                }
            }
        });


        /*
            var myRequest ;
            if (window.XMLHttpRequest){
                console.log("yes");
                myRequest = new XMLHttpRequest();
            }
            else{
                console.log("no");
                myRequest = window.ActiveXObject (microsoft.XMLHttpRequest);
            }
            myRequest.onreadystatechange= function () {
                console.log("getErrorCode()");
                console.log(this.readyState);
                console.log(this.status);
                if (this.readyState ==4 && this.status == 2){
                    console.log("duplicate name");
                }
            }*/
    </script>
</html>