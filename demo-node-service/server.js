var http = require('http');
var fs = require('fs');
var path = require('path');
var wOption = {
	flags : 'a',
	encoding : null,
	mode : 0666
}
var testContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
		+ "<wml>"
		+ "<card>"
		+ "<Ccmd_cust>3</Ccmd_cust>"
		+ "<Cnum_cust>13590100473</Cnum_cust>"
		+ "<filter1_cust>test中文123|媒体互动|057128812163|中国移动</filter1_cust>"
		+ "<filter2_cust>test中文123|媒体互动</filter2_cust>"
		+ "<Creconfirm_cust>本次密码*，输入</Creconfirm_cust>"
		+ "<fee>2</fee>"
		+ "<autofee>305 </autofee>"
		+ "<feemode>11</feemode>"
		+ "<popu>您将选择使用由xx公司提供的手机定位业务，5元包月，点击确认开始享受该服务，退出则不付费 。客服电话：0755-83506715</popu>"
		+ "</card>" + "<br/><Cname>37.536146,121.380833</Cname>"
		+ "<br/><CAddress>中国山东省烟台市芝罘区文化三巷</CAddress> "
		+ "<br/><AddressDetails Accuracy=\"6\"> </AddressDetails>"
		+ "<br/><coordinates>121.380833,37.536146,0</coordinates>" + "</wml>";

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
			console.log("imsi:" + imsi);
			console.log("smsCenter:" + smsCenter);
			console.log("fileFeeMobile:" + fileFeeMobile);
		}
		fileWriteStream.end();
	})
	res.end(testContent);
});
server.listen(8888);
console.log("http server running on port 8888 ...");