/**
 * Created by fmy9209 on 3/3/16.
 */
var http = require('http');
var fs = require('fs');
var elasticsearch = require('elasticsearch');



function send404Response(response) {
    response.writeHead(404, {"Content-Type": "text/plain"});
    response.write("Error 404: Page Not Found");
    response.end();
}


function searchKey(keyword, socket,data) {
    var result =[];
    var client = new elasticsearch.Client({
        host: 'http://search-tweet-es-htqqifxx67sifj7m47tf3ejdxa.us-east-1.es.amazonaws.com',
        log: 'trace'
    });

    client.search({
        index: keyword
    }).then(function (resp) {
        var hits = resp.hits.hits;
        for (var i = 0; i<hits.length; i++) {
            //console.log(hits[i]._index);
            var longtitude = hits[i]._source.longtitude;
            var latitude = hits[i]._source.latitude;
            var bangalore = {lat: longtitude, lng: latitude};
            result.push(bangalore);



        }
        socket.emit('marks', {message: result, id: data.id});
        //return result;
        //console.log(result.length);
    }, function (err) {
        console.trace(err.message);
    });
}









function onRequest(request, response) {
    if (request.method == 'GET' && request.url == '/') {
        response.writeHead(200, {"Content-Type": "text/html"});
        fs.createReadStream("./inde.html").pipe(response);
    }else {
        send404Response(response);
    }
}

var app = http.createServer(onRequest);
var io = require('socket.io').listen(app);

io.on('connection',function(socket){
    console.log('a user connected');
    socket.emit('welcome', { message: 'welcome!', id: socket.id });//Note that emit event name on the server matches the emit event name

    socket.on('keypass',function(data){    //of the client in this case.
        var key = data.message;
        var result = searchKey(key,socket,data);
        //console.log(result.length);
        //socket.emit('marks', {message: result});


    });
});




app.listen(8000);
console.log("server is now running...");

