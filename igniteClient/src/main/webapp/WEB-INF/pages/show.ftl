<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Ignite缓存管理</title>
</head>

<body>

<h4>USER缓存管理</h4>
<form name="product_cache_form" method="post" action="/igniteClient/ignite/user/save">
    <div>User ID: <input type="text" name="id"></div>
    <div>Name: <input type="text" name="name"></div>
    <div>Id Card: <input type="text" name="idCard"></div>
    <div>School: <input type="text" name="school"></div>
    <div>Age: <input type="text" name="age"></div>
    <input type="submit" value="保存数据" name="load">
</form>

<form name="product_cache_form" method="post" action="/igniteClient/ignite/user/sql">
    <div>
        <input type="submit" value="查询缓存 " name="query">
        <input style="width:500px" type="text" name="sql">
    </div>
</form>

<form name="product_cache_form" method="post" action="/igniteClient/ignite/user">
    <div>User ID: <input type="text" name="id"></div>
    <div>
        <input type="submit" value="查询缓存 " name="query">
    </div>
</form>
<br/>

</body>
</html>