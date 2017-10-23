<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/waitingRoom/css/waitingRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <h1><div class="wait">Waiting for another player to enter game. please wait</div></h1>
</div>
</body>

<script type="text/javascript">

    function getGameDetails() {

    }
    $(function() {


        var canStartGame = false;

        var id = setInterval(function () {
            $.ajax({
                type: "GET",
                url: "/activeGamesData",
                success: function (result) {
                    console.log(result);

                    console.log(result);
                    $.each(result,function(index , element) {
                        if(element.name == "${gameName}") {
                            if(element.playersEnteredGame >= 2) {
                                window.location.href = window.location.pathname + "?gameName="+element.name + "&boardSize=" + element.boardSize;
                                clearInterval(id);
                                return;
                            }
                            else {
                                console.log("Stiil waiting for player .");
                            }
                        }})

/*                    if (result.playersEnteredGame >= 2) {
                        console.log(result.playersEnteredGame);
                        canStartGame = true;
                        window.location( "/waitingRoom");
                        clearInterval(id);
                        return;

                    }*/
                }
            });
            console.log("after functrion");
        }, 2000);
    });

</script>
</html>