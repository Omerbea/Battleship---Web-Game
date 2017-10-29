<%--
  Created by IntelliJ IDEA.
  User: Omer
  Date: 24/10/2017
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameFinishStat/gameFinishStat.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Statistics</title>

</head>
<body>
<div class="container">
    <div class="head-title">
        <label>
            Finish Game Statistics
        </label><br></br>
        <label class="announce-winner">And The Winner Is :</label>
    </div>
    <div class="winner-stat">
        <label class="winner-name" >name :</label><br></br>
        <label class="winner-avg-time" >Average Time For Turn :</label><br></br>
        <label class="winner-hits" >Number Of Hits :</label><br></br>
        <label class="winner-miss" >Number Of Misses :</label><br></br>
        <label class="total-turns-winner" >Total Number Of Turns: </label><br></br>
        <label class="winner-total-mines" >Number of Mines not used: </label><br></br>
        <label class="winner-score" >Score: </label>

    </div>
    <div class="loser-stat">
        <label class="loser-name" >name :</label><br></br>
        <label class="loser-avg-time" >Average Time For Turn :</label><br></br>
        <label class="loser-hits" >Number Of Hits :</label><br></br>
        <label class="loser-miss" >Number Of Misses :</label><br></br>
        <label class="total-turns-loser" >Total Number Of Turns: </label><br></br>
        <label class="loser-total-mines" >Number of Mines not used: </label><br></br>
        <label class="loser-score" >Score: </label>
    </div>
</div>
</body>
<script type="text/javascript">

    $(function(){
        console.log("callTofinalStatistics");
        $.ajax({
            type: "GET" ,
            url : "/finalStatistics",
            //url:"/ExecuteMove",
            success : function(result) {
                updateUiData(result);
            }
        });
    })

    function updateUiData(data) {
        var player1data = data[0];
        var player2data = data[1];
        console.log(player1data);
        console.log(player2data);

        var winnerTitle = $('.announce-winner')[0];
        if(player1data.playerNameQuit != null || player1data.playerNameQuit != "" ) {
            if(player1data.playerName == player1data.playerNameQuit) {
                winnerTitle.textContent += player2data.playerName;
            } else {
                winnerTitle.textContent += player1data.playerName;
            }

        } else {
            if (player1data.score > player2data.score) {
                winnerTitle.textContent += player1data.playerName;
            } else {
                winnerTitle.textContent += player2data.playerName;
            }
        }

        var name = $('.winner-name')[0];
        name.textContent += player1data.playerName;
        name = $('.loser-name')[0];
        name.textContent += player2data.playerName;

        var avgTime = $('.winner-avg-time')[0];
        avgTime.textContent += player1data.avargeTimeTurn;
        avgTime = $('.loser-avg-time')[0];
        avgTime.textContent += player2data.avargeTimeTurn;

        var hits = $('.winner-hits')[0];
        hits.textContent += player1data.hits;
        hits = $('.loser-hits')[0];
        hits.textContent += player2data.hits;

        var miss = $('.winner-miss')[0];
        miss.textContent += player1data.missNum;
        miss = $('.loser-miss')[0];
        miss.textContent += player2data.missNum;

        var miss = $('.winner-total-mines')[0];
        miss.textContent += player1data.numofMines;
        miss = $('.loser-total-mines')[0];
        miss.textContent += player2data.numofMines;

        var turns = $('.total-turns-winner')[0];
        turns.textContent += player1data.numOfTurns;
        turns = $('.total-turns-loser')[0];
        turns.textContent += player2data.numOfTurns;

        var score = $('.winner-score')[0];
        score.textContent += player1data.score;
        score = $('.loser-score')[0];
        score.textContent += player2data.score;

        console.log(data);

        setTimeout(function(){
            window.location.href = "/lobby";
        },15000);
    }

</script>
</html>
