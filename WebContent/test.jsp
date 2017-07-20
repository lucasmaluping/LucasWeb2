<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<a href="addCustomer.do">add</a><br><br>
<a href="query.do">query</a><br><br>
<a href="delete.do">delete</a><br><br>
<a href="update.do">update</a>

<form action="ImageGetServlet"  method="post" enctype="multipart/form-data">

 <!-- 用户名：<input type="text" name="username"/><br/>  -->
  文件1：<input type="file" name="file1"/><br/>
  <input type="submit" value="提交"/>
</form>
</body>
</html>