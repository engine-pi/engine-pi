# Die Trainingsaufgaben des Jugendwettbewerbs Informatik in Java

Ein Versuch die
[Trainingsaufgaben](https://jwinf.de/contest/?filter=open) auf
[jwinf.de](https://jwinf.de) (Jugendwettbewerb Informatik) in Java
nachzuprogrammieren. Als Gaming Engine kommt die [Engine
Alpha](https://engine-alpha.org) zum Einsatz.

Die Website jwinf.de läuft auf [medal](https://git.bwinf.de/bwinf/medal)
(eine kleine, in Rust geschriebene Plattform für Browser-Wettbewerbe).
Die einzelnen Aufgaben sind mit den
[bebras-modules](https://github.com/France-ioi/bebras-modules)
umgesetzt, die von der [Association
France-ioi](https://www.france-ioi.org) bereit gestellt wird.
Die meiste Spiellogik ist in dem Javascript-Module
[blocklyRobot_lib-1.1.js](https://github.com/France-ioi/bebras-modules/blob/master/pemFioi/blocklyRobot_lib-1.1.js)
implementiert.


# Task paths:

* `conditionals_excercises/find_the_destination`
* `conditionals_excercises/find_the_way_to_the_lake`
* `conditionals_excercises/gems_and_obstacles`
* `conditionals_excercises/heat_the_castle`
* `conditionals_excercises/light_all_candles`
* `conditionals_excercises/platforms`
* `loops_excercises/collecting_gems`
* `loops_excercises/securing_the_road`

# Build dependency

make dependency

https://github.com/France-ioi/bebras-modules/blob/master/pemFioi/quickAlgo/README.md

## Umsetzung einer Trainingsaufgabe auf jwinf.de:

Eine Trainingsaufgabe ist in einer Javascript- und einer HTML-Datei definiert z. B.

[Bedingte Anweisungen – Übungen / Zünde alle Kerzen an](https://jwinf.de/task/1156)

### Javascript

[20-DE-13-Kerzen-einfach/task_new.js](https://jwinf.de/tasks/jwinf/jwinf-aufgaben/2020/20-DE-13-Kerzen-einfach/task_new.js)

```js
function initTask(subTask) {
  var cellSide = 60;

  subTask.gridInfos = {
    hideSaveOrLoad: true,
    conceptViewer: false,
    contextType: "paint",
    //cellSide: cellSide,
    actionDelay: 200,
    languageStrings: {
      blocklyRobot_lib: {
         label: {
            "onPaint": "auf Kerze",
            "dropObject" : "zünde Kerze an"
         },
         code: {
          onPaint: "aufKerze",
          dropObject : "zuendeKerzeAn"
         },
         messages: {
            successContainersFilled: "Bravo, der Roboter hat alle Kerzen angezÃ¼ndet!",
            failureContainersFilled: "Der Roboter hat die Kerzen nicht korrekt angezÃ¼ndet.",
         }
      }
   },
    itemTypes: {
      robot: {img: imgPath+"blue_robot.png", side: 85, isRobot: true, offsetX: -10, offsetY: 10, zOrder: 1},
      initial_paint: {num: 3, img: "kerze.png", side: 60, isPaint: true, zOrder: 1},
      paint: {num: 2, img: "flamme.png", side: 60,  isWithdrawable: true, zOrder: 1},
      marker: {num: 4, img: "docht.png", side: 60, isContainer: true, containerFilter: function(item) {return item.type === "paint";}, zOrder: 0},
      number: { side: 60, zOrder: 2 },
      board_background: { num: 5, color: "#ffffff", side: 60, zOrder: 0 },
      board: { side: 60, isWritable: true, zOrder: 1 }
    },
    maxInstructions: {
      easy: 10,
      medium: 10,
      hard: 10,
    },
    includeBlocks: {
      groupByCategory: false,
      generatedBlocks: {
        robot: {
          shared: ["east", "west", "north", "south", "dropObject"],
          easy: [],
          medium: [],
          hard: ["onPaint"]
        }
      },
      standardBlocks: {
        includeAll: false,
        wholeCategories: {
          easy: [],
          medium: [],
          hard: []
        },
        singleBlocks: {
          shared: ["controls_repeat"],
          easy: [],
          medium: [],
          hard: ["controls_if"] // ["logic_negate", "controls_if_else"]
        }
      },
      pythonAdditionalFunctions: {
         shared: ["range"]
      },
    },

    blocklyColourTheme: "bwinf",
    ignoreInvalidMoves: false,
    checkEndEveryTurn: false,
    // checkEndCondition: function(context, lastTurn) {
    //   var solved = true;
    //   for (var iRow = 0; iRow < context.tiles.length; iRow++) {
    //     var row = subTask.data[subTask.level][subTask.iTestCase].tiles[iRow];
    //     for (var iCol = 0; iCol < row.length; iCol++) {
    //       var markers = context.getItems(iRow, iCol, {
    //         isMarker: true
    //       });
    //       var paint = context.getItems(iRow, iCol, {
    //         isPaint: true
    //       });
    //       if (paint.length != markers.length) {
    //         solved = false;
    //       }
    //     }
    //   }
    //   if (solved) {
    //     context.success = true;
    //     throw (window.taskStrings.success);
    //   }
    //   if (lastTurn) {
    //     context.success = false;
    //     throw (window.taskStrings.failure);
    //   }
    // },
    // computeGrade: function(context, message) {
    //   var rate = 0;
    //   if (context.success) {
    //     rate = 1;
    //   }
    //   return {
    //     successRate: rate,
    //     message: message
    //   };
    // }
  };

  subTask.data = {
    easy: [{
      tiles: [
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 1, 1, 4, 1, 1, 1, 1],
        [1, 1, 1, 1, 3, 1, 1, 1, 1],
        [1, 1, 1, 1, 3, 1, 1, 1, 1],
        [1, 1, 1, 1, 3, 1, 1, 1, 1]
      ],
      initItems: [{
        row: 5,
        col: 1,
        type: "robot"
      }, ]
    }],
    medium: [{
      tiles: [
        [1, 1, 1, 1, 1, 1, 1, 1, 1],
        [1, 1, 4, 4, 4, 4, 4, 1, 1],
        [1, 1, 3, 3, 3, 3, 3, 1, 1],
        [1, 1, 3, 3, 3, 3, 3, 1, 1],
        [1, 1, 3, 3, 3, 3, 3, 1, 1],
        [1, 1, 3, 3, 3, 3, 3, 1, 1]
      ],
      initItems: [{
        row: 5,
        col: 1,
        type: "robot"
      }, ]
    }],
    hard: [{
      tiles: [
      [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
      [1, 1, 1, 1, 1, 1, 1, 1, 1, 1],
      [1, 1, 4, 1, 4, 4, 1, 1, 4, 1],
      [1, 1, 3, 1, 3, 3, 1, 1, 3, 1],
      [1, 1, 3, 1, 3, 3, 1, 1, 3, 1],
      [1, 1, 3, 1, 3, 3, 1, 1, 3, 1]
      ],
      initItems: [{
        row: 5,
        col: 1,
        type: "robot"
      }, ]
    }]
  };

  initBlocklySubTask(subTask);
  displayHelper.thresholdEasy = 120;
  displayHelper.thresholdMedium = 240;
}

initWrapper(initTask, ["easy", "medium", "hard"], null, true);

```

### HTML

[20-DE-13-Kerzen-einfach/index_new.html?channelId=task](https://jwinf.de/tasks/jwinf/jwinf-aufgaben/2020/20-DE-13-Kerzen-einfach/index_new.html?channelId=task)

```html
<!doctype html>
<html>

<head>
  <meta charset="utf-8">
  <title>Kerzen anzünden</title>
  <script>
    window.stringsLanguage = 'de';
    window.taskStrings = {
      success: "Bravo! Der Roboter hat alle Kerzen angezündet.",
      failure: "Dein Roboter hat nicht alle Kerzen angezündet.",
    };
  </script>
  <script class="remove" type="text/javascript" src="../../../_common/modules/pemFioi/importModules-1.4-mobileFirst.js"
    id="import-modules"></script>
  <script class="remove" type="text/javascript">
    var modulesPath = '../../../_common/modules/'
    importModules([
      'jquery-1.7.1', 'JSON-js', 'raphael-2.2.1', 'beaver-task-2.0', 'jschannel', 'raphaelFactory-1.0', 'delayFactory-1.0', 'simulationFactory-1.0',
      'platform-pr', 'buttonsAndMessages', 'beav-1.0', 'installationAPI.01', 'miniPlatform', 'conceptDisplay-1.0', 'conceptViewer-1.0', 'conceptViewer_css-1.0',
      'taskStyles-mobileFirst', 'blockly-robot-1.1', 'jwinf_css'
    ]);
    // set Blockly as default language when none is specified through ?language=
    importLanguageModules('blockly');
  </script>
  <script class="remove" type="text/javascript">
    var json = {
      "id": "20-DE-13-Kerzen-einfach",
      "language": "de",
      "version": "de.01",
      "authors": "JwInf 2020",
      "translators": [],
      "license": "",
      "taskPathPrefix": "",
      "modulesPathPrefix": "",
      "browserSupport": [],
      "fullFeedback": true,
      "minWidth": "auto"
    };
  </script>
  <script type="text/javascript" src="task_new.js"></script>
</head>

<body onresize="task.displayedSubTask.updateScale()">
  <div id="task">
    <h1>Kerzen anzünden</h1>
    <img src="marker.png" style="display:none" />
    <img src="paint.png" style="display:none" />
    <img src="green_robot.png" style="display:none" />
    <img src="icon.png" style="display:none" />

    <div id="tabsContainer"></div>
    <div id="taskContent" style="text-align:left;position:relative">
      <div id="taskIntro">
        Programmiere den Roboter:<br>
        <p>
          Der Roboter soll alle Kerzen anzünden.
        </p>
        <div class="hard">
          <p data-lang="blockly">
            Der Baustein <img src='aufKerze.png'style="vertical-align: middle" /> gibt <code>True</code> zurück, wenn der Roboter auf einer Kerze <img src='kerze.png' width="50px"/> steht.
          </p>
          <p data-lang="python">
            Die Funktion <code>aufKerze()</code> gibt <code>True</code> zurück, wenn der Roboter auf einer Kerze <img src='kerze.png' width="50px"/> steht.
          </p>
        </div>
      </div>
      <div id="gridContainer"></div>
      <div id="blocklyLibContent"></div>
    </div>

  </div><!-- task -->
</body>

</html>
```
