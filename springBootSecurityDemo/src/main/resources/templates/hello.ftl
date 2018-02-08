<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Hello World!</title>
</head>
<body>
<h1>Hello world!</h1>
<form action="/logout" method="post">
    <!--第一种实现方式，csrf攻击，也可以加入请求头-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="hidden" name="_csrf_header" value="${_csrf.headerName}">
    <div><input type="submit" value="退出"/></div>
</form>
</body>
</html>