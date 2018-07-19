<%--
  Created by IntelliJ IDEA.
  User: agaowei
  Date: 2018/7/17
  Time: 21:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Basic CRUD Application - jQuery EasyUI CRUD Demo</title>
    <%@include file="../common/content.html"%>
    <link rel="stylesheet" type="text/css" href="https://www.jeasyui.com/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="https://www.jeasyui.com/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="https://www.jeasyui.com/easyui/themes/color.css">
    <script type="text/javascript" src="https://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>

    <style>
        .texbox{
            width:100px;border:1px solid #ccc;margin-right: 20px;
        }
    </style>
</head>
<body>

<table id="userTable" toolbar="#toolbar" style="width:100%;min-height: 600px; overflow-y: auto" title="用户列表" iconcls="icon-edit"></table>

<div id="toolbar">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加用户</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">编辑用户</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除用户</a>

    <div id="tb" style="padding:3px">
        <form name="queryForm" id="queryForm">
            <input class="easyui-textbox" name="name" data-options="label:'姓名:',required:true">
            <input class="easyui-textbox" name="sex" data-options="label:'性别:',required:true">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="doQuery()">Search</a>
        </form>
    </div>
</div>

<div id="dlg" class="easyui-dialog" style="width:400px" data-options="closed:true,modal:true,border:'thin',buttons:'#dlg-buttons'">
    <form id="userForm" method="post" novalidate style="margin:0;padding:20px 50px">
        <input name="id" type="hidden"/>
        <h3>用户信息</h3>
        <div style="margin-bottom:10px">
            <input name="name" class="easyui-textbox" required="true" label="姓名:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input name="loginName" class="easyui-textbox" required="true" label="登录名:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input type="password" name="password" class="easyui-textbox" required="true" label="登录密码:" style="width:100%">
        </div>
        <div style="margin-bottom:10px">
            <input name="sex" class="easyui-textbox" required="true" label="性别:" style="width:100%">
        </div
        ><div style="margin-bottom:10px">
            <input name="address" class="easyui-textbox" required="true" label="地址:" style="width:100%">
        </div><div style="margin-bottom:10px">
            <input name="birthday" class="easyui-textbox" required="true" label="出生日期:" style="width:100%">
        </div>
    </form>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveUser()" style="width:90px">Save</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
</div>
<script type="text/javascript">
    var url;

    $(function () {
        init();
        doQuery();
    });

    function doQuery() {
        $('#userTable').datagrid({ queryParams: $("#queryForm").serializeJson() });   //点击搜索
        var data = ajaxReq("/user/list",$("#queryForm").serializeJson(),"post",false);
        if(data) {
            $('#userTable').datagrid('loadData', data.rows);
        }
        setPagination();
    }

    function init(){
        $('#userTable').datagrid({   //#tt是展示数据的表格ID
            //url: '/user/list',   //MVC路由，可以不是MVC格式
            title: '用户列表',
            fitColumns: true, //列自适应
            nowrap: false,
            idField: 'id',//主键列的列名
            loadMsg: '正在加载用户的信息...',
            pagination: true,//是否有分页
            singleSelect: false,//是否单行选择
            pageSize: 20,//页大小，一页多少条数据
            pageNumber: 1,//当前页，默认的
            pageList: [10,15,20],   //可以设置的页大小的列表
            striped:true,
            columns: [[
                { field: 'ck', checkbox: true, align: 'left', width: 50 },  //额外添加的checkBox的列
                { field: 'id', title: '编号', width: 80 },    //field属性必须和UserInfo的属性名对应。
                { field: 'name', title: '姓名', width: 120 },
                { field: 'loginName', title: '登录名', width: 120 },
                { field: 'sex', title: '性别', width: 120 },
                { field: 'address', title: '住址', width: 120 },
                {
                    field: 'birthday', title: '生日', width: 100, align: 'right',
                    formatter: function (value, row, index) {
                        if(value) {
                            return ChangeDateFormat(value);
                            //return (eval(value.replace(/\/Date\((\d+)\)\//gi, "new Date($1)"))).pattern("yyyy-M-d");   //利用datapattern.js插件，格式化日期输出。
                        }else{
                            return "";
                        }
                    }
                }
            ]]
        });
    }

    function setPagination(){
        $("#userTable").datagrid('getPager').pagination({
            beforePageText:'第',
            afterPageText:'页，共{pages}页',
            layout:['list','sep','first','prev','sep','manual','sep','next','last','sep','refresh','info'],
            displayMsg:'第 {from} 到 {to} 条， 共 {total} 条数据'
        });
    }

    function ChangeDateFormat(cellval) {
        var date = new Date(parseInt(cellval.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
        return date.getFullYear() + "-" + month + "-" + currentDate;
    }

    function newUser(){
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','新建用户');
        $('#userForm').form('clear');
    }
    function editUser(){
        var row = $('#userTable').datagrid('getSelected');
        if (row){
            $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑用户');
            $('#userForm').form('load',row);
        }
    }
    function saveUser(){
        var data = $('#userForm').serializeJson();
        ajaxReq("/user/save",data,"post",false);
    }

    function destroyUser(){
        var row = $('#userTable').datagrid('getSelected');
        if (row){
            $.messager.confirm('确认','您确定要删除吗?',function(r){
                if (r){
                    var result = ajaxReq('/user/delete',row.id,"post",false);
                    if(!result){
                        $.messager.show({    // show error message
                            title: '删除失败',
                            msg: result.detail
                        });
                    }
                }
            });
        }
    }
</script>
</body>
</html>
