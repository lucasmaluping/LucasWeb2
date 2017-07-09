<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<%
		//在javaWEB规范中使用Cookie类代表cookie
		
		//1.创建cookie类的对象
		Cookie cookie = new Cookie("name","lucas");	
		//2.调用response的一个方法把cookie传给客户端
		response.addCookie(cookie);
	%>
</body>
</html>