<%--
  Created by IntelliJ IDEA.
  User: agaowei
  Date: 2018/7/5
  Time: 21:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<%@include file="common/content.html"%>
<body class="no-skin">
<!--顶部状态区-->
<div id="navbar" class="navbar navbar-default ace-save-state">
    <%@include file="common/header.html"%>
</div>
<!--主页面区-->
<div class="main-container ace-save-state" id="main-container">
    <script type="text/javascript">
        try{ace.settings.loadState('main-container')}catch(e){}
    </script>

    <!--左侧菜单-->
    <div id="sidebar" class="sidebar responsive ace-save-state">
        <script type="text/javascript">
            try{ace.settings.loadState('sidebar')}catch(e){}
        </script>

        <ul class="nav nav-list">
            <%@include file="common/page-left.html"%>
        </ul><!-- /.nav-list -->

        <div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
            <i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" 
               data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
        </div>
    </div>
    <!--页面内容区-->
    <div class="main-content">
        <div class="page-content">
            <ul class="nav nav-tabs" role="tablist" id="tab-header"></ul>
            <div class="tab-content" id="tab-body"></div>
        </div>
    </div><!-- /.main-content -->

    <!--页尾-->
    <div class="footer">
        <%@include file="common/footer.html"%>
    </div>
</div><!-- /.main-container -->
</body>
</html>
