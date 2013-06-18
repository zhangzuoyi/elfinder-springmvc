<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
	<title>Replace Textarea by Code &mdash; CKEditor Sample</title>
	<meta charset="utf-8">
	<script src="<s:url value='/static/ckeditor/ckeditor.js' />"></script>
	<!-- <link href="sample.css" rel="stylesheet"> -->
</head>
<body>
	<h1 class="samples">
		<a href="index.html">CKEditor Samples</a> &raquo; Replace Textarea Elements Using JavaScript Code
	</h1>
	<form method="post">
		<div class="description">
			<p>
				This editor is using an <code>&lt;iframe&gt;</code> element-based editing area, provided by the <strong>Wysiwygarea</strong> plugin.
			</p>
<pre class="samples">
CKEDITOR.replace( '<em>textarea_id</em>' )
</pre>
		</div>
		<textarea cols="80" id="editor1" name="editor1" rows="10">
		</textarea>
		<script>

			// This call can be placed at any point after the
			// <textarea>, or inside a <head><script> in a
			// window.onload event handler.

			// Replace the <textarea id="editor"> with an CKEditor
			// instance, using default configurations.

			CKEDITOR.replace( 'editor1' ,{
			    filebrowserBrowseUrl : '<s:url value="/forckeditor" />'
			    //uiColor : '#9AB8F3'
			});

		</script>
		<p>
			<input type="submit" value="Submit">
		</p>
	</form>
	<div id="footer">
		<hr>
		<p>
			CKEditor - The text editor for the Internet - <a class="samples" href="http://ckeditor.com/">http://ckeditor.com</a>
		</p>
		<p id="copy">
			Copyright &copy; 2003-2013, <a class="samples" href="http://cksource.com/">CKSource</a> - Frederico
			Knabben. All rights reserved.
		</p>
	</div>
</body>
</html>