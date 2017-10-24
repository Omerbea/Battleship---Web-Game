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
        <label >final statistics for : </label>

    </div>

    <div class="loser-stat">
        <label >final statistics for : </label>
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
        console.log(data);
    }

</script>
</html>
