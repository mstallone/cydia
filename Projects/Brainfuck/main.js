$(document).ready(function() {
    var rawInput = "";

    $('#clearButton').hide();
    //$('#runningIndicator').hide();
    $('#brainfuckCode').bind('input propertychange', function() {
        $("#brainfuckInputLabel").hide();
        if (!$('#runButton').hasClass('disabled')) $('#runButton').addClass('disabled');

        if (this.value.length > 0) {
            $('#runButton').removeClass('disabled');
            if (this.value.indexOf(",") > -1) $("#brainfuckInputLabel").show();
        }
    });

    $('#runButton').click(function() {
        rawInput = $('#brainfuckInput').val() + String.fromCharCode(0);
        if (!$('#runButton').hasClass('disabled')) {
            //$('#runningIndicator').show();
            $('#brainfuckOutput').val(execute($('#brainfuckCode').val().replace(/\s+/g, ''))[2]).trigger('propertychange');
            //$('#runningIndicator').hide();
        }
    });

    $('#debugmodeButton').click(function() {
        sweetAlert("Oops...", "Debug mode has not been implemented yet.", "error");
    });

    $('#brainfuckOutput').bind('input propertychange', function() {
        $('#clearButton').hide();
        if (this.value.length > 0) {
            $('#clearButton').show();
        }
    });

    $('#clearButton').click(function() {
        $('#brainfuckOutput').val("").trigger('propertychange');
    });

    var loopedCode = function(rawCode) {
        var leftCount = 0,
            rightCount = 0;
        for (var i = 0; i < rawCode.length; i++) {
            if (rawCode.charCodeAt(i) === 91) leftCount++;
            else if (rawCode.charCodeAt(i) === 93) rightCount++;
            if (leftCount === rightCount && leftCount !== 0) return rawCode.substring(1, i);
        }
    }

    var execute = function(rawCode, index, data, output) {
        //--//INIT\\--\\
        index = index || 0;
        if (!data) {
            data = [];
            for (var i = 0; i < 10000; i++) data.push(0);
        }
        output = output || "";
        console.log(rawCode + "\n" + index + "\n" + data);

        //--//MAIN\\--\\
        while (rawCode.length > 0) {
            var currentCharacter = rawCode.charCodeAt(0);
            switch (currentCharacter) {
                case 43: //+
                    data[index]++;
                    if (data[index] === 256) data[index] = 0;
                    rawCode = rawCode.slice(1);
                    break;
                case 45: //-
                    data[index]--;
                    if (data[index] === -1) data[index] = 255;
                    rawCode = rawCode.slice(1);
                    break;
                case 44: //,
                    data[index] = rawInput.charCodeAt(0);
                    rawInput = rawInput.slice(1);
                    rawCode = rawCode.slice(1);
                    break;
                case 46: //.
                    output += String.fromCharCode(data[index]);
                    rawCode = rawCode.slice(1);
                    break;
                case 60: //<
                    index--;
                    if (index === -1) index = data.length - 1;
                    rawCode = rawCode.slice(1);
                    break;
                case 62: //>
                    index++;
                    if (index === data.length) index = 0;
                    rawCode = rawCode.slice(1);
                    break;
                case 91: //[
                    var loopCode = loopedCode(rawCode);
                    while (data[index] !== 0) {
                        var newData = execute(loopCode, index, data, output);
                        index = newData[0];
                        data = newData[1];
                        output = newData[2]
                    }
                    rawCode = rawCode.substring(loopCode.length + 2);
                    break;
                default: //*
                    rawCode = rawCode.slice(1);
                    break;
            }
        }
        return [index, data, output];
    }

    if ($.url('?code')) {
        var inputCode = $.url('?code').replace(new RegExp('%3E', 'g'), '>').replace(new RegExp('%3C', 'g'), '<').replace(/%20/g, '');
        var inputInput = $.url('?input').replace(/%20/g, ' ') || "";
        $('#brainfuckCode').val(inputCode).trigger('propertychange');
        $('#brainfuckInput').val(inputInput).trigger('propertychange');
        $('#runButton').click();
    }
});
