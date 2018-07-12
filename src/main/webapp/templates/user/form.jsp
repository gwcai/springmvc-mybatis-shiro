<%--
Created by IntelliJ IDEA.
User: agaowei
Date: 2018/7/7
Time: 12:26
To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<form class="form-horizontal" role="form" action="/user/save" method="post">
    <input nme="id" type="hidden" value="">
    <div class="form-group">
        <label for="name" class="col-sm-2 control-label">名字</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="name" name="name" value="" placeholder="请输入名字">
        </div>
    </div>
    <div class="form-group">
        <label for="loginName" class="col-sm-2 control-label">登录名</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="loginName" name="loginName" value="" placeholder="请输入姓">
        </div>
    </div>
    <div class="form-group">
        <label for="password" class="col-sm-2 control-label">登录密码</label>
        <div class="col-sm-10">
            <input type="password" name="password" value="" id="password">
        </div>

    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-default">保存</button>
        </div>
    </div>
</form>
</body>
</html>