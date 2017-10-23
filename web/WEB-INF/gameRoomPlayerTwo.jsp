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

    var idPullingIsNotMyTurn =0;
    var gIsMyTurn = 0;
    $(function() {
        createTablesForElement(document.getElementsByClassName('myBoardSection')[0] , true);
        createTablesForElement(document.getElementsByClassName('rivalBoardSection')[0] , false);
        var board = $('.rivalBoardSection')[0];
        var statusLabel = document.createElement('label');
        statusLabel.setAttribute('class' , 'statusLabel');
        board.appendChild(statusLabel);
        statusLabel.style.display = 'inherit';
        statusLabel.style.paddingTop = '20px';
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

    function setBoardActive(isBoardActive) {
        var boardSize=5;
        var rivalUIBoard = $(".rivalBoard")[0];
        var logoutBtn = $(".logout-btn")[0];
        for(var i = 0 ; i < boardSize ; i++) {
            for(var j = 0 ; j < boardSize ; j++) {
                var rivalCell = (rivalUIBoard.rows[i].cells[j]).childNodes[0];
                var jRivalCell = $(rivalCell);
                jRivalCell.attr("disabled", isBoardActive);
            }
        }
        logoutBtn.disabled = isBoardActive;
    }

    function updateUiData(data) {
        //updating boards
        var boardSize = 5 ; // TODO : change board size to be dynamic
        var myBoard = data[2];
        var rivalBoard = data[3];
        var statistics = data[0];
        var myUIBoard = $(".myBoard")[0];
        var rivalUIBoard = $(".rivalBoard")[0];
        var isMyTurn = data[1];
        console.log('================================')
        gIsMyTurn = data[1];
        for(var i = 0 ; i < boardSize ; i++) {
            for(var j = 0 ; j < boardSize ; j++) {
                var myCell = (myUIBoard.rows[i].cells[j]).childNodes[0];
                var jCell = $(myCell);
                jCell.val(myBoard[i][j]);
                var rivalCell = (rivalUIBoard.rows[i].cells[j]).childNodes[0];
                var jRivalCell = $(rivalCell);
                console.log(rivalBoard[i][j]);
                if(rivalBoard[i][j] != 'X' &&
                    rivalBoard[i][j] != '-') {

                } else {
                    jRivalCell.val(rivalBoard[i][j]);
                }
            }
        }
        console.log("current score:" + statistics.score);
        //avargeTimeTurn

        $(".score").html(statistics.score);
        $(".time").html(statistics.avargeTimeTurn);
        //$(".turns").html(statistics.avargeTimeTurn);
        var rivalUIBoard = $(".rivalBoardSection");
        if(isMyTurn) {
            console.log("its your turn");
            setBoardActive(false);
            var statusLabelText = $('.statusLabel')[0];
            statusLabelText.textContent = "its Your turn";
        } else {
            console.log("its NOT your turn");
            setBoardActive(true);
            var statusLabelText = $('.statusLabel')[0];
            statusLabelText.textContent = "its NOT Your turn";

            pullingIsMyTurn();
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
        //$('myBoardSection').add
    }
    var countForDebug =0;
    function pullingIsMyTurn() {
        console.log("pullingIsMyTurn count : "+ countForDebug);
        countForDebug++;
        idPullingIsNotMyTurn = setInterval(function () {
            if (gIsMyTurn) {
                // its your turn
                setBoardActive(false);
                console.log("my turn ");
                var statusLabelText = $('.statusLabel')[0];
                statusLabelText.textContent = "its Your turn";
                clearInterval(idPullingIsNotMyTurn);
            } else {
                console.log("not my turn");
                setBoardActive(true);
                $.ajax({
                    type: "GET",
                    url: "/ExecuteMove",
                    success: function (result) {
                        gIsMyTurn = result[1]
                        console.log("gIsMyTurn= " + gIsMyTurn);
                    }

                });
            }
        },2000);
        console.log("call to pullingIsMyTurn 1");
        //pullingIsMyTurn();
    }
</script>
</html>
