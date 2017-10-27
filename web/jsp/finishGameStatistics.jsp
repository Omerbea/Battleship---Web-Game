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
        </label>
    </div>
    <div class="winner-stat">
        <label class="winner-name" >name :</label><br></br>
        <label class="winner-avg-time" >Average Time For Turn :</label><br></br>
        <label class="winner-hits" >Number Of Hits :</label><br></br>
        <label class="winner-miss" >Number Of Misses :</label><br></br>
        <label class="total-turns" >Total Number Of Turns: </label><br></br>
        <label class="winner-score" >Score: </label>

    </div>
    <div class="loser-stat">
        <label >final statistics for : </label><br></br>
        <label class="loser-name" >name :</label><br></br>
        <label class="loser-avg-time" >Average Time For Turn :</label><br></br>
        <label class="loser-hits" >Number Of Hits :</label><br></br>
        <label class="loser-miss" >Number Of Misses :</label><br></br>
        <label class="total-turns" >Total Number Of Turns: </label><br></br>
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

        var avgTime = $('.winner-avg-time')[0];
        avgTime.textContent += player1data.avargeTimeTurn;
        avgTime = $('.loser-avg-time')[0];
        avgTime.textContent += player2data.avargeTimeTurn;

        var hits = $('.winner-hits')[0];
        hits.textContent += player1data.hits;
        hits = $('.loser-hits')[0];
        hits.textContent += player2data.hits;

        var miss = $('.winner-avg-time')[0];
        miss.textContent += player1data.missNum;
        miss = $('.loser-avg-time')[0];
        miss.textContent += player2data.missNum;

        var turns = $('.total-turns')[0];
        turns.textContent += player1data.numOfTurns;
        turns = $('.total-turns')[0];
        turns.textContent += player2data.numOfTurns;

        var score = $('.winner-score')[0];
        score.textContent += player1data.score;
        score = $('.loser-score')[0];
        score.textContent += player2data.score;

        console.log(data);

        setTimeout(function(){
            //window.location.href = "/lobby";
        },5000);
    }

</script>
</html>
