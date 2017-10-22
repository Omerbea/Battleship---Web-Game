<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameRoom/css/gameRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <div class="header">Game Room Player2 <button class="logout-btn">logout</button></div>
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

    $(function() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove",
            //url:"/ExecuteMove",
            success : function(result) {
                updateUiData(result);
            }
        });
    })

    function getData() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove?row="+this.parentNode.parentNode.rowIndex + "&col="+this.parentNode.cellIndex+"&playerNumber=" + 0 ,
            //url:"/ExecuteMove",
            success : function(result) {
                updateUiData(result);
                console.log(result);
            }
        });

    }

    function updateUiData(data) {
        //updating boards
        var boardSize = 5 ; // TODO : change board size to be dynamic
        var myBoard = data[2];
        var rivalBoard = data[3];
        var myUIBoard = $(".myBoard")[0];
        var rivalUIBoard = $(".rivalBoard")[0];
        var isMyTurn = data[1];
        for(var i = 0 ; i < boardSize ; i++) {
            for(var j = 0 ; j < boardSize ; j++) {
                var myCell = (myUIBoard.rows[i].cells[j]).childNodes[0];
                var jCell = $(myCell);
                jCell.val(myBoard[i][j]);
                var rivalCell = (rivalUIBoard.rows[i].cells[j]).childNodes[0];
                var jRivalCell = $(rivalCell);
                jRivalCell.val(rivalBoard[i][j]);
            }
        }


        if(isMyTurn) {
            console.log("its your turn");
        } else {
            console.log("its NOT your turn");
        }


    }

    function createTablesForElement(element , isMyBoard) {

        var myTableDiv = element;
        var boardSize = ${requestScope.get("boardSize")};
        console.log("boardsize = "+ boardSize);
        var table = document.createElement('TABLE');
        if(isMyBoard) {
            table.classList.add("myBoard");
        } else {
            table.classList.add("rivalBoard");
        }
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
                cellBtn.textAlign = "center" ;
                cellBtn.style.height= '100%';
                cellBtn.style.width= '100%';

                if(!isMyBoard) {
                    cellBtn.addEventListener('click', getData, false);

                }
                td.appendChild(cellBtn);
                tr.appendChild(td);
            }
        }
        myTableDiv.appendChild(table);
/*
        var txtLabel = documnet.createElement('label');
        txtLabel.style.display = "inline-flex";
        txtLabel.style.padding = '40px';
        myTableDiv.appendChild(txtLabel);*/

        //$('myBoardSection').add
    }
</script>
</html>
