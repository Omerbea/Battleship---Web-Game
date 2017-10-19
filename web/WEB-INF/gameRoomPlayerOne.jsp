<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameRoom/css/gameRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <div class="header">Game Room Player1 <button class="logout-btn">logout</button></div>
    <div class="myBoardSection"></div>
    <div class="rivalBoardSection"></div>
    <div class="statSection">
        <br><label>Score : </label><label class="score">0</label><br>
        <br><label>Avg Time : </label><label class="time">0</label><br>
        <br><label>Turn Played : </label><label class="turns">0</label><br>
    </div>
    <div class="toolSection">Tools section</div>
</div>
</body>

<script type="text/javascript">
    $(function() {
        createTablesForElement(document.getElementsByClassName('myBoardSection')[0]);
        createTablesForElement(document.getElementsByClassName('rivalBoardSection')[0]);


    });

    function getData() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove?row="+this.parentNode.parentNode.rowIndex + "&col="+this.parentNode.cellIndex+"&playerNumber=" + 0 ,
            //url:"/ExecuteMove",
            success : function(result) {
                console.log(result);
            }
        });

        //console.log("Row :" + this.parentNode.parentNode.rowIndex);
    }

    function createTablesForElement(element) {

        var myTableDiv = element;
        var boardSize = ${requestScope.get("boardSize")};
        console.log("boardsize = "+ boardSize);
        var table = document.createElement('TABLE');
        table.border='1';
        table.width = '100%';
        table.height = '100%';


        var tableBody = document.createElement('TBODY');
        table.appendChild(tableBody);

        for (var i=0; i<boardSize; i++){
            var tr = document.createElement('TR');
            tableBody.appendChild(tr);

            for (var j=0; j<boardSize; j++){
                var td = document.createElement('TD');
                td.width='75';
                td.height='60';
                var cellBtn = document.createElement('input');
                cellBtn.type = "button" ;
                cellBtn.style.height= '100%';
                cellBtn.style.width= '100%';
                cellBtn.addEventListener('click' , getData , false);
                td.appendChild(cellBtn);
                tr.appendChild(td);
            }
        }
        myTableDiv.appendChild(table);
        //$('myBoardSection').add
    }
</script>
</html>
