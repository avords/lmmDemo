<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <title>Spring Security Example </title>
</head>
<body>
<#if error??>
用户名或密码错
</#if>
<#if logout??>
您已注销成功
</#if>
<form action="/login" method="post">
    <!--第一种实现方式，csrf攻击，也可以加入请求头-->
    <input type="hidden" name="_csrf" value="${_csrf.token}">
    <input type="hidden" name="_csrf_header" value="${_csrf.headerName}">
    <div><label> 用户名 : <input type="text" name="username"/> </label></div>
    <div><label> 密  码 : <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="登录"/></div>
</form>
</body>
</html>