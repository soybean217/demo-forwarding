<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String currentUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() +  request.getRequestURI()
			+ (request.getQueryString() != null && request.getQueryString().length() > 0
					? "?" + request.getQueryString()
					: "");
	System.out.println(currentUrl);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://res.wx.qq.com/open/js/jweixin-1.1.0.js"></script>
</head>
<body>

</body>
<script type="text/javascript">
	wx.config({
		debug : true,
		appId : 'wxb011e7747898ad8c',
		timestamp : 1476409414,
		nonceStr : '2BbF4QLz3DidDlgE',
		signature : '24d7c17e118fe78977c850938b9684a053784022',
		jsApiList : [ 'checkJsApi', 'onMenuShareTimeline',
				'onMenuShareAppMessage', 'onMenuShareQQ', 'onMenuShareWeibo',
				'onMenuShareQZone', 'hideMenuItems', 'showMenuItems',
				'hideAllNonBaseMenuItem', 'showAllNonBaseMenuItem',
				'translateVoice', 'startRecord', 'stopRecord',
				'onVoiceRecordEnd', 'playVoice', 'onVoicePlayEnd',
				'pauseVoice', 'stopVoice', 'uploadVoice', 'downloadVoice',
				'chooseImage', 'previewImage', 'uploadImage', 'downloadImage',
				'getNetworkType', 'openLocation', 'getLocation',
				'hideOptionMenu', 'showOptionMenu', 'closeWindow',
				'scanQRCode', 'chooseWXPay', 'openProductSpecificView',
				'addCard', 'chooseCard', 'openCard' ]
	});
</script>
</html>