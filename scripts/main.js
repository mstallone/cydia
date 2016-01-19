$(document).ready(function() {
    $('.container').hide();

    $(document).on('keyup', function(event) {
        if (event.keyCode === 27) goHome();
    });

    addProjects();
});

function goHome(){
    $(".mdl-layout").toggleClass("blur");
    document.title = "Matthew Stallone";

    if($(".mdl-layout").hasClass("blur")){
        $(".mdl-layout").one('webkitTransitionEnd', function() {
            $('.container').show();
            $('.close').addClass('animated rollIn');
            //$('.container').addClass('animated fadeInUp');
        });
    } else {
        $('.container').hide();
        $('.close').removeClass('animated rotateIn');
        document.title = "Matthew Stallone's Portfolio";
    }
}

function addProjects() {
    $("#portfolio").empty();

    $("#portfolio").append(newProject("BlindAssist", "My prototype for a navigational aide for the visually impaired (aka NAVI). NAVI \
    helps the visually impaired navigate their world by utilizing infrared obstacle detection algorithms I wrote. My algorithms allow the \
    prototype to digitally map the 3-D space around the user, including identifying the size of and distance from obstacles. NAVI gives \
    feedback through two different modes. One is tonal feedback based on distance; the second utilizes text to speech to describe the distance \
    and height of the objects in the user’s pathway. The Android communicates through Bluetooth with an Arduino.", "BlindAssist", ["MainActivity.java", "main.ino"], "08/2015"));

    $('#portfolio').append(newProject("ThoughtCloud", "An instant mass-messaging service, which sends messages as \"thoughts\" \
    to all users with the app open. The \"thought\" only lasts on the screen for a few seconds, and then disapears forever.\
    The app utilizes the Android SDK and Node.js.", "ThoughtCloud", ["Server.js", "MainActivity.java", "Settings.java"], "07/2015"));

    $('#portfolio').append(newProject("BatteryBar", "A Mac OS X app that overrides the UI to display the battery percent on the menu bar.",
    "BatteryBar", ["Demo.png", "AppDelegate.m", "LoginItemAppDelegate.m", "PreferencesTextField.m", "PreferencesWindowController.m", "PreferencesViewController.h",
    "PreferencesViewController.m"], "06/2015"));

    $('#portfolio').append(newProject("Postfix Calculator (Stack)", "An implementation of a custom stack in a postfix calculator. I also created a custom GUI to use with this\
    calculator.","PostFixCalcCustomStack", ["MJSStack.java","PostfixCalculater.java","CalculatorGUI.java"], "09/2013"));

    $('#portfolio').append(newProject("Lines (Queue)", "An implementation of a custom queue. Compares waiting times of a single line checkout store with a multiple-line checkout store.",
    "Lines", ["Customer.java","MJSQueue.java","MultiLineStore.java","SingleLineStore.java"], "10/2013"));

    $('#portfolio').append(newProject("BarScanner (Hash Table)", "An implementation of a barcode scanner, using a hash table \
    for all the products (each product has a unique universal product code (UPC)).","BarScanner", ["BarscannerOne.java","BarscannerTwo.java","ItemPrice.java", "ListNode.java"], "04/2014"));

    $('#portfolio').append(newProject("20 Questions (TreeNode)", "An implementation of the 20 questions game, using a treenode structure for the questions and answers. \
    The code builds a text file database of nodes (questions and answers).", "TwentyQuestions", ["TreeNode.java","QuestionGameV2.java"], "11/2013"));

    $('#portfolio').append(newProject("Miscellaneous Data Structures", "These are different data structures I wrote, but never made it into a project.",
    "DataStructures", ["MJSDeque.java", "MJSDoubleNode.java", "MJSLinkedList.java"], "11/2013"));

    $('#portfolio').append(newProject("MovieGoers (Maps and Sets)", "An implementation of Java's maps and sets. Tells you which movies you and your friends can see.",
    "MapsSets", ["MovieGoersAdvance.java", "MovieGoersBasic.java"], "04/2014"));

    $('#portfolio').append(newProject("Drawing", "A Java drawing app. Based on AP Computer Science Grid World's grid.", "DrawingApp",
    ["ColorDisplay.java", "ColorGrid.java", "DrawingGrid.java", "GridDriver.java", "TextGrid.java"], "05/2014"));

    $('#portfolio').append(newProject("CodeForces Python", "Python code for solutions to some of the posted problems at <a href='http://codeforces.com'>http://codeforces.com</a>.", "CodeForces_Python",
    ["112A_PetyaAndStrings.py","282A_Bit++.py","116A_Tram.py","339A_HelpfulMaths.py","131A_cAPSlOCK.py","588B_DuffInLove.py","133A_HQ9+.py","588B_DuffInLove_2.py",
    "158B_Taxi.py","607A_ChainReaction.py","160A_Twins.py","608A_SaitamaDestroysHotel.py","219A_K-String.py","608B_HammingDistanceSum.py","231A_Team.py","609A_USBFlashDrives.py",
    "236A_BoyOrGirl.py","609B_TheBestGift.py","25A_IQTest.py","609C_LoadBalancing.py","266A_StonesOnTheTable.py","281A_WordCapitalization.py"], "12/2015"));

    $('#portfolio').append(newProject("Musical Instrument", "An Arduino project which plays notes when different buttons are pressed. \
    The project can recored the button presses and play the song back at variable speeds.", "MusicalInstrument", ["MusicalInstrument.ino"], "07/2015"));

    $('#portfolio').append(newProject("Infinity-Scroll", "A project for iOS, which allows for an infinite number of pages (implemented for \
        NSDate), using UIPageViewController.", "Infinity-Scroll",
    ["Demo.gif", "DetailedViewController.h", "DetailedViewController.m", "ViewController.h", "ViewController.m"], "11/2015"));

    $('#portfolio').append(newProject("Brainf**k Interpreter", "A simple, full-featured interpreter in Javascript. \
    \"Brainf**k is an esoteric programming language noted for its extreme minimalism. The language consists of only eight simple commands\
     and an instruction pointer. Nevertheless, it was shown to be Turing-complete. It is designed to challenge and amuse programmers, and\
      was not made to be suitable for practical use. It was created in 1993 by Urban Müller. The language's name is a reference to the vulgar term \
      \"brain f**k\", which refers to things so complicated or unusual that they exceed the limits of one's understanding\"\
      (<a href=\"https://en.wikipedia.org/wiki/Brainfuck\">Wikipedia</a>). The interpreter is hosted <a href='http://www.matthewstallone.com/Brainfuck/'>Here</a>.",
       "Brainfuck", ["main.js"], "05/2015"));

    $('#portfolio').append(newProject("ACSL Solutions", "My solutions to some of the ACSL (American Computer Science League) problem sets.","ACSL",
    ["ACSL_Fanorona.java", "ACSL_Fanorona.pdf", "ACSL_x.java", "ACSL_y.java", "ACSL_z.java"], "2014-15"));

    $('#portfolio').append(newProject("Tube Game", "A simple iOS game I made. Objective: Survive the longest. \
    How: Use the accelerometer to avoid spinning circles with obstacles. (See Demo [Demo is on a simulator, and thus the accelerometer does not work.]).", "TubeGame",
    ["Demo.gif", "ViewController.m"], "09/2015"));

    $('#portfolio').append(newProject("Don't Fall! (Game)", "A simple iOS game I made. Objective: Survive the longest. \
    How: Use the accelerometer (app uses trigonometry to calculate tilt of the device) to avoid falling platforms. (See Demo).",
    "DontFall", ["Demo.png", "GameScene.m", "GameViewController.m"], "01/2015"));
}

function newProject(name, description, link, paths, date){
    var mainFiles = "";

    for (i = 0; i < paths.length; i++) {
        mainFiles += '<div class="section__text mdl-cell mdl-cell--10-col-desktop mdl-cell--6-col-tablet mdl-cell--3-col-phone">\
                        <form method="get" target="_blank" action="Projects/' + link + '/' + paths[i] + '">\
                            <button class="mdl-button mdl-js-button mdl-button--primary">\
                                <span style="text-transform: capitalize;">view ' + paths[i] +'</span>\
                            </button>\
                        </form>\
                    </div>';
    }

    return '<section class="section--center mdl-grid mdl-grid--no-spacing mdl-shadow--2dp">\
        <div class="mdl-card mdl-cell mdl-cell--12-col">\
            <div class="mdl-card__supporting-text mdl-grid mdl-grid--no-spacing">\
                <h4 class="mdl-cell mdl-cell--12-col">' + name + '</h4>\
                <p class="mdl-cell mdl-cell--12-col">' + description + '<br><br><strong>Main files:</strong></p>\
                ' + mainFiles + '
            </div>\
            <div class="mdl-card__actions">\
                <a href="Projects/' + link + '/' + link + '.zip' + '" class="mdl-button" download>download full project</a>\
            </div>\
        </div>\
        <button class="mdl-button mdl-js-button mdl-js-ripple-effect mdl-button--icon" id="' + link + '">\
            <i class="material-icons">more_vert</i>\
        </button>\
        <ul class="mdl-menu mdl-js-menu mdl-menu--bottom-right" for="' + link + '">\
            <li class="mdl-menu__item" onclick="alert(\'' + date + ': (mm/yyyy)\');">' + date + '</li>\
        </ul>\
    </section>\
    <section class="section--center mdl-grid mdl-grid--no-spacing"></section>';
}
