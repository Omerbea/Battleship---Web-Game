<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameRoom/css/gameRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <div class="header">
        <label class="player-name"></label>
        <form method="post" action="/quitGame" enctype="multipart/form-data">
        <button class="logout-btn">logout</button></form></div>
    <div class="myBoardSection"></div>
    <div class="rivalBoardSection"></div>
    <div class="statSection">
        <br><label>Score : </label><label class="score">0</label><br>
        <br><label>Avg Time : </label><label class="time">0</label><br>
        <br><label>Turn Played : </label><label class="turns">0</label><br>
    </div>
    <div class="toolSection">Tools section
        <br><br>
    <img class="mine-img" src="/resources/bomb_PNG29.png" draggable="true" width="50" height="50"
    ondragstart="drag(event)"></img>
        <br>
        <label class="tot-mine"></label>
    </div>
</div>
</body>
<script type="text/javascript">
    var gNumOfMines = 0;
    var gListOfMineCoordinates = 0;
    function allowDrop(ev) {


        console.log(gListOfMineCoordinates);
        row = $(ev.target)[0].parentNode.parentNode.rowIndex;
        col = $(ev.target)[0].parentNode.cellIndex;
        /*console.log("row : " + row );
        console.log("col : " + col);*/
        if(gListOfMineCoordinates.filter(function(coordinate){
                return coordinate.row == row && coordinate.column == col;
            }).length > 0){
            ev.preventDefault();
            console.log("can be placed");
            var btn = $(event.target);
            btn.css("background-color" , "green");
        } else {
            var btn = $(event.target);
            btn.css("background-color" , "");

        }
    }

    function leavingDrag(event) {
        console.log("drag leave");

        var btn = $(event.target);
        btn.css("background-color" , "");

    }

    function drop(ev) {
        console.log("mine has dropped");
        ev.preventDefault();
        var data = ev.dataTransfer.getData("text");
        console.log("===========")
        row = $(ev.target)[0].parentNode.parentNode.rowIndex;
        col = $(ev.target)[0].parentNode.cellIndex;
        console.log("column dropped " + col);
        console.log("row dropped " + row);

        $.ajax({
            type: "GET" ,
            url : "/ExecuteMine?row="+row + "&col="+col,
            success : function(result) {
                console.log(result);
                if(result === "good") {
                    $.ajax({
                        type: "GET" ,
                        url : "/ExecuteMove",
                        success : function(result) {
                            updateUiData(result);
                        }
                    });
                    console.log("Mine was added.");
                } else if(result === "bad") {
                    console.log("Problem adding Mine.");
                }
            }
        });
    }

    function drag(ev) {
        ev.dataTransfer.setData("text", ev.target.id);
        console.log("mine has picked up");
    }




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
        pullingIsMyTurn();
    });


    /*$(function() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove",
            success : function(result) {
                updateUiData(result);
            }
        });
    })*/

    function getDataNoCoordinates() {
        $.ajax({
            type: "GET" ,
            url : "/ExecuteMove",
            success : function(result) {
                updateUiData(result);
            }
        });
    }

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
                console.log(jRivalCell.val());
                if(jRivalCell.val() === "") {
                    jRivalCell.css("background-color" , "");
                    jRivalCell.attr("disabled", isBoardActive);
                }else {
                    if (jRivalCell.isDisabled){
                        console.log("yes im disabled");
                    }
                    jRivalCell.attr("disabled" , true);
                    console.log("hitMe: my"+ jRivalCell.val());
                }

            }
        }
        logoutBtn.disabled = isBoardActive;
    }

    function updateUiData(data) {
        //updating boards
        var url_string = window.location.href;
        var url = new URL(url_string);
        var boardSize = url.searchParams.get("boardSize");
        var myBoard = data[2];
        var rivalBoard = data[3];
        var statistics = data[0];
        var myUIBoard = $(".myBoard")[0];

        gListOfMineCoordinates = data[6];

        var pNameStatus = $('.player-name')[0];
        pNameStatus.textContent = data[5];var rivalUIBoard = $(".rivalBoard")[0];

        var isMyTurn = data[1];
        console.log("data1 : " + data[1]);
        gIsMyTurn = data[1];
        gNumOfMines = data[0].numofMines;



        if(gNumOfMines == 0) {
            $('.mine-img').hide();
            $('.tot-mine').hide();
        } else {
            $('.tot-mine')[0].textContent = gNumOfMines;
        }


        for(var i = 0 ; i < boardSize ; i++) {
            for(var j = 0 ; j < boardSize ; j++) {
                var myCell = (myUIBoard.rows[i].cells[j]).childNodes[0];
                var jCell = $(myCell);
                jCell.val(myBoard[i][j]);
                jCell.attr('ondrop' , "drop(event)");
                jCell.attr('ondragover' , "allowDrop(event)");
                jCell.attr('ondragleave' , "leavingDrag(event)");
                var rivalCell = (rivalUIBoard.rows[i].cells[j]).childNodes[0];
                var jRivalCell = $(rivalCell);

                console.log(rivalBoard[i][j]);
                if(rivalBoard[i][j] != 'X' &&
                    rivalBoard[i][j] != '-') {

                    rivalCell.addEventListener('mouseover' , function (event) {
                        console.log("on mouse over");
                        var btn = $(event.target);
                        btn.css("background-color" , "green");});


                    rivalCell.addEventListener('mouseleave' , function (event) {
                        console.log("on mouse leave");
                        var btn1 = $(event.target);
                        btn1.css("background-color", "");
                    })


                } else
                {

                    jRivalCell.val(rivalBoard[i][j]);

                    rivalCell.addEventListener('mouseover', function (event) {
                        console.log("on mouse over");
                        var btn = $(event.target);
                        btn.css("background-color", "red");

                    });


                    rivalCell.addEventListener('mouseleave', function (event) {
                        console.log("on mouse leave");
                        var btn1 = $(event.target);
                        btn1.css("background-color", "");
                    })
                }
            }
        }
        console.log("current score:" + statistics.score);
        //avargeTimeTurn
        console.log(data);
        var statusLabelText = $('.statusLabel')[0];
        if(data[4] === "non") {
            showStatus(statusLabelText ,  "You Miss! Try again next turn.");
        } else if (data[4] === "hit") {
            showStatus(statusLabelText ,  "You Hit! You have another turn.");
        } else if(data[4] === "Win") {
            showStatus(statusLabelText ,  "You Win! Good job!");
            window.location.href = "/jsp/finishGameStatistics.jsp";
        } else if(data[4] === "rivalWin") {
            showStatus(statusLabelText ,  "You Lose! See you next game.");
            setTimeout(function() {
                statusLabelText = " ";
            } , 2000);
            window.location.href = "/jsp/finishGameStatistics.jsp";
        } else if(data[4] === "rivalQuit") {
            showStatus(statusLabelText ,  "Rival Quit! You Win! Good job!");
            window.location.href = "/jsp/finishGameStatistics.jsp";

        }

        $(".score").html(statistics.score);
        $(".time").html(statistics.avargeTimeTurn);
        //$(".turns").html(statistics.avargeTimeTurn);
        var rivalUIBoard = $(".rivalBoardSection");
        if(isMyTurn) {
            console.log("its your turn");
            setBoardActive(false);

        } else {
            console.log("its NOT your turn");
            setBoardActive(true);

            //pullingIsMyTurn();
        }


    }

    function showStatus(item , msg) {
        item.textContent = msg;
        setTimeout(function() {
            item.textContent = " ";
        } , 3000);
    }

    function createTablesForElement(element , isMyBoard) {

        var myTableDiv = element;
        var url_string = window.location.href;
        var url = new URL(url_string);
        var boardSize = url.searchParams.get("boardSize");
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
                    cellBtn.addEventListener('click' , function(event){
                        $(event.target).disabled = true;
                    });

                }
                td.appendChild(cellBtn);
                tr.appendChild(td);
            }
        }
        myTableDiv.appendChild(table);
        //$('myBoardSection').add
    }
    var countForDebug =0;

    function doOnClick (){
        console.log("doonclick")
        console.log("after GetData");
        console.log("after disabled");
    }


    function pullingIsMyTurn() {
        console.log("pullingIsMyTurn count : "+ countForDebug);
        countForDebug++;
        idPullingIsNotMyTurn = setInterval(function () {
            if (gIsMyTurn) {
                // its your turn
                setBoardActive(false);
                console.log("my turn ");
                getDataNoCoordinates();
                //clearInterval(idPullingIsNotMyTurn);
            } else {
                console.log("not my turn");
                getDataNoCoordinates();
                setBoardActive(true);
                $.ajax({
                    type: "GET",
                    url: "/ExecuteMove",
                    success: function (result) {
                        gIsMyTurn = result[1]
                        if(result[4] == "rivalQuit") {
                            console.log("rival quit");
                           // window.location.href = window.location.pathname + '/lobby';
                            getDataNoCoordinates();
                            console.log("after redirect quit");
                        }
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
