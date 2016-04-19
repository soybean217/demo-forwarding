var http = require('http');
var fs = require('fs');
var path = require('path');
var wOption = {
	flags : 'a',
	encoding : null,
	mode : 0666
}

var server = http.createServer(function(req, res) {
	res.writeHeader(200, {
		'Content-Type' : 'text/plain;charset=utf-8' // 添加charset=utf-8
	});
	var fileName = Date.now();
	var fileWriteStream = fs.createWriteStream('../logs/' + fileName + ".log",
			wOption);
	var dataArr = [], len = 0;
	req.on('data', function(chunk) {
		fileWriteStream.write(chunk);
		console.log("transforming...");

		dataArr.push(chunk);
		len += chunk.length;
	});
	req.on("end", function() {
		if (len > 0) {
			var b = Buffer.concat(dataArr, len);
			var customCode = b.slice(32, 47);
			var projectCode = b.slice(48, 63);
			var imsi = b.slice(64, 80);
			var smsCenter = b.slice(81, 96);
			var fileFeeMobile = b.slice(97, 112);
			console.log("key:" + b[0]);
			console.log("length:" + (b[1] * 256 + b[2]));
			console.log("protocol-version:" + b[3]);
			console.log("svn-version:" + b[4] + " " + b[5] + " " + b[6] + " "
					+ b[7] + " ");
			console.log("type:" + b[8]);
			console.log("so-version:" + b[9] + " " + b[10] + " " + b[11] + " "
					+ b[12] + " ");
			console.log("request-type:" + b[31]);
			console.log("Custcode:" + customCode);
			console.log("ProCode:" + projectCode);
			console.log("smsCenter:" + smsCenter);
			console.log("fileFeeMobile:" + fileFeeMobile);
		}
		fileWriteStream.end();
	})
	res.end("working...");
});
server.listen(8888);
console.log("http server running on port 8888 ...");