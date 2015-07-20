<%@ page contentType="text/html;charset=utf-8"%>
<!DOCTYPE html>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="org.demo.dao.ConnectionService"%>
<%
  Calendar calendar = Calendar.getInstance();
			String today = calendar.get(Calendar.YEAR) + "-"
					+ (calendar.get(Calendar.MONTH) + 1) + "-"
					+ calendar.get(Calendar.DAY_OF_MONTH);
			String dateFrom = request.getParameter("dateFrom") == null
					? today
					: request.getParameter("dateFrom");
			String dateTo = request.getParameter("dateTo") == null
					? today
					: request.getParameter("dateTo");
%>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="FuMing">

<title>Login</title>

<!-- Bootstrap core CSS -->
<link rel="stylesheet"
	href="http://cdn.bootcss.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- code.jquery.com -->
<script src="http://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="../js-css/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<link href="../js-css/bootstrap-datetimepicker.min.css" rel="stylesheet"
	media="screen">
<script type="text/javascript"
	src="../js-css/bootstrap-datetimepicker.js" charset="UTF-8"></script>

<!-- DataTables -->
<link rel="stylesheet" type="text/css"
	href="http://cdn.datatables.net/1.10.4/css/jquery.dataTables.css">
<script type="text/javascript" charset="utf8"
	src="http://cdn.datatables.net/1.10.4/js/jquery.dataTables.js"></script>

</head>

<body>
	<%@ include file="menu.jsp"%>
	<div class="container">

		<form class="form-signin" role="form">
			from:<input size="10" name="dateFrom" type="text"
				value="<%=dateFrom%>" class="form_datetime"> 00:00:00 to:<input
				size="10" name="dateTo" type="text" value="<%=dateTo%>"
				class="form_datetime"> 23:59:59
			<script type="text/javascript">
				$(".form_datetime").datetimepicker({
					format : 'yyyy-mm-dd',
					minView : 2,
					autoclose : 1
				});
			</script>

			<button type="submit" name="submit" value="1">view</button>
		</form>

	</div>
	<%
	  if (request.getParameter("submit") != null
						&& request.getParameter("submit").equals("1")) {
	%>
	<table id="table_id" class="display">
		<thead>
			<tr>
				<th>日期</th>
				<th>MO内容</th>
				<th>条数</th>
			</tr>
		</thead>
		<tbody>
			<%
			  PreparedStatement ps = null;
							Connection con = null;
							ResultSet rs = null;
							try {
								con = ConnectionService.getInstance()
										.getConnectionForLocal();
								String sql = "SELECT FROM_UNIXTIME(id/1000/100000, '%Y-%m-%d') AS dt,msg,COUNT(DISTINCT link_id) AS ct FROM wj.receives WHERE id>=UNIX_TIMESTAMP(?)*1000*100000 AND id<=UNIX_TIMESTAMP(?)*1000*100000 AND send_status = 0 AND msg_type='mr' AND status_report=0  GROUP BY FROM_UNIXTIME(id/1000/100000, '%Y-%m-%d'),		 service_id,msg,msg_type,send_status,status_report HAVING ct >5 ORDER BY msg DESC , dt DESC";
								//String sql = "SELECT FROM_UNIXTIME(id/1000/100000, '%Y-%m-%d') AS dt,msg,COUNT(DISTINCT link_id) AS ct FROM wj.receives WHERE id>=UNIX_TIMESTAMP('"+dateFrom + " 0:0:0"+"')*1000*100000 AND id<=UNIX_TIMESTAMP('"+dateTo + " 23:59:59"+"')*1000*100000 AND send_status = 0 AND msg_type='mr' AND status_report=0  GROUP BY FROM_UNIXTIME(id/1000/100000, '%Y-%m-%d'),		 service_id,msg,msg_type,send_status,status_report HAVING ct >5 ORDER BY msg DESC , dt DESC";
								ps = con.prepareStatement(sql);
								int m = 1;
								ps.setString(m++, dateFrom + " 0:0:0");
								ps.setString(m++, dateTo + " 23:59:59");
								rs = ps.executeQuery();
								while (rs.next()) {
			%>
			<tr>
				<td><%=rs.getString("dt")%></td>
				<td><%=rs.getString("msg")%></td>
				<td><%=rs.getString("ct")%></td>
			</tr>
			<%
			  }
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								if (con != null) {
									try {
										con.close();
									} catch (Exception e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
			%>
		</tbody>
	</table>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#table_id').DataTable({paging: false});
		});
	</script>
	<%
	  }
	%>

	<!-- /container -->


	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="../js-css/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
