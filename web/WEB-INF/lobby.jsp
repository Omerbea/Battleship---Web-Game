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
                        <th>How Many Players Waiting</th>
                        <th>Active</th>
                        <th>Delete Game</th>
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
                <form method="post" action="${pageContext.request.contextPath}/loadGame" enctype="multipart/form-data">
                    <label> Upload Game </label>
                    <input type="file" name="gameFile" accept="application/xml" fi>
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
                url: "${pageContext.request.contextPath}/logout",
                success: function () {
                    console.log("redirected to login.");
                    window.location.replace("${pageContext.request.contextPath}/logIn");
                }
            });
        }

        $.ajax({
            type: "GET" ,
            url : "${pageContext.request.contextPath}/activeGamesData" ,
            success : function(result) {
                console.log(result);
                $('.gamesList tbody td').remove();
                $.each(result,function(index , element) {
                    if(element.playersEnteredGame < 2) {
                        $('.gamesList tbody').append("<tr><td><a href=\"waitingRoom?gameName=" + element.name + "\">" + element.name + "</a></td>" +
                            "<td>" + element.playerNameThatLoadedCurrentGame + "</td>" +
                            "<td>" + element.boardSize + "</td>" +
                            "<td>" + element.typeGame + "</td>" +
                            "<td>" + element.playersEnteredGame + "</td>" +
                            "<td>" + element.isActiveGame + "</td>" +
                            "<td><button onclick='removeGame()'>Delete</button></td>");
                    } else {
                        $('.gamesList tbody').append("<tr><td><a>" + element.name + "</a></td>" +
                            "<td>" + element.playerNameThatLoadedCurrentGame + "</td>" +
                            "<td>" + element.boardSize + "</td>" +
                            "<td>" + element.typeGame + "</td>" +
                            "<td>" + element.playersEnteredGame + "</td>" +
                            "<td>" + element.isActiveGame + "</td>" +
                            "<td><button onclick='removeGame()'>Delete</button></td>");
                    }
                });

            }
        });

        $.ajax({
            type: "GET" ,
            url : "${pageContext.request.contextPath}/activePlayersData" ,
            success : function(result) {
                console.log(result);
                $('.activePlayers tbody td').remove();
                $.each(result,function(index , element) {
                    $('.activePlayers tbody').append("<tr><td>"+element+"</td></tr>");
                });
            }
        });
         $.ajax({
            type: "GET" ,
            url : "${pageContext.request.contextPath}/errorCodeLoadGame" ,
            success : function(result) {
                console.log ("errorCoseLoadGame():")
                console.log(result);
                if (result ==''){
                    console.log("no error")

                }
                else {
                    console.log ("error load..")
                    showStatus($('.errorBoxLoadFile')[0] , result);
                    //$('.errorBoxLoadFile').append("<label>" + result + "</label>");

                }
            }
        });

        var intervalActivatePlayers = setInterval(function () {
            $.ajax({
                type: "GET" ,
                url : "${pageContext.request.contextPath}/activePlayersData" ,
                success : function(result) {
                    console.log(result);
                    $('.activePlayers tbody td').remove();
                    $.each(result,function(index , element) {
                        $('.activePlayers tbody').append("<tr><td>"+element+"</td></tr>");
                    });
                }
            });
        }, 2000);

        var intervalActivateGame = setInterval(function () {
            $.ajax({
                type: "GET" ,
                url : "${pageContext.request.contextPath}/activeGamesData" ,
                success : function(result) {
                    console.log(result);
                    $('.gamesList tbody td').remove();
                    $.each(result,function(index , element) {
                        if(element.playersEnteredGame < 2) {
                            $('.gamesList tbody').append("<tr><td><a href=\"waitingRoom?gameName=" + element.name + "\">" + element.name + "</a></td>" +
                                "<td>" + element.playerNameThatLoadedCurrentGame + "</td>" +
                                "<td>" + element.boardSize + "</td>" +
                                "<td>" + element.typeGame + "</td>" +
                                "<td>" + element.playersEnteredGame + "</td>" +
                                "<td>" + element.isActiveGame + "</td>" +
                                "<td><button onclick='removeGame()'>Delete</button></td>");
                        } else {
                            $('.gamesList tbody').append("<tr><td><a>" + element.name + "</a></td>" +
                                "<td>" + element.playerNameThatLoadedCurrentGame + "</td>" +
                                "<td>" + element.boardSize + "</td>" +
                                "<td>" + element.typeGame + "</td>" +
                                "<td>" + element.playersEnteredGame + "</td>" +
                                "<td>" + element.isActiveGame + "</td>" +
                                "<td><button onclick='removeGame()'>Delete</button></td>");
                        }
                    });
                }
            });
        }, 2000);

        function removeGame() {

            $.ajax({
                type: "GET" ,
                url : "${pageContext.request.contextPath}/removeGameByPlayer?gameName="+event.target.parentNode.parentNode.children[0].textContent ,
                success : function(result) {
                    console.log(result);
                }
            });
        }

        function showStatus(item , msg) {
            item.textContent = msg;
            setTimeout(function() {
                item.textContent = " ";
            } , 3000);
        }
    </script>
</html>