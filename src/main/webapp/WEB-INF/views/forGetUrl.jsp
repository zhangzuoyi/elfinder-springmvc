<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>elFinder 2.0</title>

		<!-- jQuery and jQuery UI (REQUIRED) -->
		<link rel="stylesheet" type="text/css" media="screen" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/themes/smoothness/jquery-ui.css">
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.18/jquery-ui.min.js"></script>

		<!-- elFinder CSS (REQUIRED) -->
		<link rel="stylesheet" type="text/css" media="screen" href="<s:url value='/static/elfinder/css/elfinder.min.css' />">
		<link rel="stylesheet" type="text/css" media="screen" href="<s:url value='/static/elfinder/css/theme.css' />">

		<!-- elFinder JS (REQUIRED) -->
		<script type="text/javascript" src="<s:url value='/static/elfinder/js/elfinder.js' />"></script>

		<!-- elFinder translation (OPTIONAL) -->
		<script type="text/javascript" src="<s:url value='/static/elfinder/js/i18n/elfinder.ru.js' />"></script>

		<!-- elFinder initialization (REQUIRED) -->
		<script type="text/javascript" charset="utf-8">
	
		    $().ready(function() {
		        var elf = $('#elfinder').elfinder({
		            url : '<s:url value="/connector" />',
		            getFileCallback : function(file) {
		                window.opener.setUrl(file);
		                window.close();
		            },
		            resizable: false
		        }).elfinder('instance');
		    });
		</script>
	</head>
	<body>

		<!-- Element where elFinder will be created (REQUIRED) -->
		<div id="elfinder"></div>

	</body>
</html>
