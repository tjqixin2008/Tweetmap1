
var http = require('http');
var fs = require('fs');
var elasticsearch = require('elasticsearch');



function send404Response(response) {
    response.writeHead(404, {"Content-Type": "text/plain"});
    response.write("Error 404: Page Not Found");
    response.end();
}

function onRequest(request, response) {
    if (request.method == 'GET' && request.url == '/') {
        response.writeHead(200, {"Content-Type": "text/html"});
        fs.createReadStream("./inde.html").pipe(response);
    }else {
        send404Response(response);
    }
}
function searchKey(keyword, socket,data) {
    //var result =[];
    var client = new elasticsearch.Client({
        host: 'http://search-searchtweet-kdp4noucxrgspwb7h5cu5ydc2m.us-east-1.es.amazonaws.com',
        log: 'trace'
    });

    client.search({
        index: keyword,
        body: {
            "from" : 0,
            "size" : 300
        }
    }).then(function (resp) {
        var hits = resp.hits.hits;
        //hits = _.map(hits, function(hit) {
        //    return hit._source;
        //});
        var result = [];
        for (var i = 0; i<hits.length; i++) {
            console.log(hits[i]);
            var longtitude = hits[i]._source.longtitude;
            var latitude = hits[i]._source.latitude;
            var item = {lat: longtitude, lng: latitude, usr:hits[i]._source.user, txt:hits[i]._source.text, ul:hits[i]._source.url };

            result.push(item);
        }
        console.log(result);
        socket.emit('marks',{message:result, id:data.id});
        // socket.disconnect();
    }, function (err) {
        socket.emit('error', err.message);
    });
}
var app = http.createServer(onRequest);
console.log("server is now running...");

app.listen(8080);
var io = require('socket.io').listen(app);
io.on('connect',function(socket){
    console.log('a user connected');
    socket.emit('welcome', { message: 'welcome!', id: socket.id });//Note that emit event name on the server matches the emit event name

    socket.on('keypass',function(data){    //of the client in this case.
        var key = data.message;
        searchKey(key,socket,data);
    });

});

