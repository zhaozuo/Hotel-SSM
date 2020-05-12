<%--
  Created by IntelliJ IDEA.
  User: 19740
  Date: 2020/5/9
  Time: 下午 11:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="staff/testList">testList</a>
<br>
<form action="staff/testAdd" method="post">
    姓名：<input type="text" name="username"/><br>
    密码：<input type="password" name="password"/>
    <input type="submit" value="提交">
</form>
</body>
</html>
