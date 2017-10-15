<html>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/gameRoom/css/gameRoomStyle.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <title>Game Room</title>
</head>
<body>
<div class="container">
    <div class="header">Game Room Player2</div>
    <div class="myBoardSection"></div>
    <div class="rivalBoardSection"></div>
    <div class="statSection">Stats</div>
    <div class="toolSection">Tools section</div>
</div>
</body>

<script type="text/javascript">
    $(function() {
        createTablesForElement(document.getElementsByClassName('myBoardSection')[0]);
        createTablesForElement(document.getElementsByClassName('rivalBoardSection')[0]);

    });

    function createTablesForElement(element) {

        var myTableDiv = element;

        var table = document.createElement('TABLE');
        table.border='1';

        var tableBody = document.createElement('TBODY');
        table.appendChild(tableBody);

        for (var i=0; i<'${boardSize}'; i++){
            var tr = document.createElement('TR');
            tableBody.appendChild(tr);

            for (var j=0; j<'${boardSize}'; j++){
                var td = document.createElement('TD');
                td.width='75';

                td.appendChild(document.createTextNode("Cell " + i + "," + j));
                tr.appendChild(td);
            }
        }
        myTableDiv.appendChild(table);
        $('myBoardSection').add
    }
</script>
</html>