<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Lobby</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/lobby/css/lobbyStyle.css"/>
    </head>
    <body>
        <div class="container">
            <header class="head">
                <h1> Lobby </h1>
            </header>
            <div class="gamesList">
                <table>
                    <tr>
                        <th>Game Name</th>
                        <th>Created By</th>
                        <th>Board Size</th>
                        <th>Game Type</th>
                        <th>Active</th>
                    </tr>
                    <tr>
                        <td>game1</td>
                        <td>omer</td>
                        <td>15</td>
                        <td>Advanced</td>
                        <td>active</td>
                    </tr>
                </table>
            </div>
            <div class="activePlayers">
                <table>
                    <tr>
                        <th>Player Name</th>
                    </tr>
                </table>
            </div>
            <div class="uploadFile">
                <form method="post" action="/loadGame" enctype="multipart/form-data">
                    <label> Upload Game </label>
                    <input type="file" name="gameFile" accept="application/xml">
                    <input type="submit"/>
                </form>
            </div>
        </div>
    </body>
</html>