<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameRoom/css/gameRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <div class="header">Game Room Player1     <form method="post" action="/quitGame" enctype="multipart/form-data">
        <button class="logout-btn">logout</button></form></div>
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
        createTablesForElement(document.getElementsByClassName('myBoardSection')[0] , true);
        createTablesForElement(document.getElementsByClassName('rivalBoardSection')[0] , false);


    });

    function getData() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove?row="+this.parentNode.parentNode.rowIndex + "&col="+this.parentNode.cellIndex+"&playerNumber=" + 0 ,
            //url:"/ExecuteMove",
            success : function(result) {
                updateUiData(result);
            }
        });

    }

    function updateUiData(data) {
        //updating boards
        var board = data[2];
        var table = $(".myBoard")[0];
        console.log("board length :" + board.length);
        for(var i = 0 ; i < board.length ; i++) {
            for(var j = 0 ; j < board.length ; j++) {
                var cell = table.rows[i].cells[j];
                console.log("row : " + i + "col : " + j + "content : " + cell);
            }
        }

    }

    function createTablesForElement(element , isMyBoard) {

        var myTableDiv = element;
        var boardSize = ${requestScope.get("boardSize")};
        console.log("boardsize = "+ boardSize);
        var table = document.createElement('TABLE');
        table.classList.add("myBoard");
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
                if(isMyBoard) {
                    cellBtn.addEventListener('click', getData, false);

                }
                td.appendChild(cellBtn);
                tr.appendChild(td);
            }
        }
        myTableDiv.appendChild(table);
        //$('myBoardSection').add
    }
</script>
</html>
