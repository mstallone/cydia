var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);

app.get('/', function(req, res) {
    res.send('hello world $.now()');
}); 

http.listen(3000, function(){
    console.log('listening on *:3000');
});

io.on('connection', function(socket){
    console.log("Connection from: " + socket.client.conn.remoteAddress + ", socket ID: " + socket.id);

    socket.on('new message', function(msg) {
        io.emit('new message', msg)
        console.log(msg);
    });

    socket.on('disconnect', function(){
        console.log("Disconnect from: " + socket.client.conn.remoteAddress + ", socket ID: " + socket.id);
    });
});