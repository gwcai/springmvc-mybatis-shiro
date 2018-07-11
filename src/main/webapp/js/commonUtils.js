/**
 * Created by agaowei on 2017/6/26.
 */

/**
 * 同步ajax请求
 * @param url
 * @param data
 * @param reqType
 * @param tips
 * @returns {*}
 */
function ajaxReq(url, data, reqType, tips = true, refresh = false) {
    var result = false;
    if (url == null || url == '') {
        lobibox_alert('error', '参数错误', '参数错误，请检联系管理员');
        // lobibox_notify('error','错误')
        Lobibox.notify(
            'warning',  // Available types 'warning', 'info', 'success', 'error'
            {
                size: 'mini',
                rounded: true,
                delay: false,
                position: 'center top', //or 'center bottom'
                msg: 'Lorem ipsum dolor sit amet against apennine any created, spend loveliest, building stripes.'
            }
        );
        return false;
    }
    //构建ajax 发起请求
    $.ajax({
        type: reqType,
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: 'json',
        async: false,//同步
        beforeSend: function () {
        },
        error: function (request) {
            lobibox_alert('error', '请求数据失败,"请求服务器数据失败，请检查网络配置或者稍后再试');
            result = false;
        },
        success: function (data) {
            result = handleReturnData(data, tips, refresh);
        }
    });
    return result;
}

/***
 * 请求html页面
 * @param url
 * @param data
 * @param reqType
 */
function ajaxReqHtml(url, data, reqType, complete) {
    $.ajax({
        type: reqType,
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: 'html',
        async: false,//同步
        beforeSend: function () {
        },
        error: function (request) {
            lobibox_alert('error', '请求数据失败,"请求服务器数据失败，请检查网络配置或者稍后再试');
        },
        success: function (data) {
            document.open("text/html", "replace");
            document.write(data);
            document.close();
        },
        complete: function (res) {
            complete && typeof complete == "function" && complete(res);
        }
    });
}

/**
 * 处理请求返回的数据
 * @param result
 * @param tips
 */
function handleReturnData(result, tips, refresh) {
    if (!result) {
        lobibox_alert('error', '提示', '系统错误');
        return false;
    }

    //操作成功
    var title = (result.title ? result.title : result.msg) ? (result.title ? result.title : result.msg) : '提示';
    if (result.code == 0) {
        if (tips) {
            lobibox_alert('success', title, result ? result.detail : '提交成功');
        }
        //页面跳转
        if (refresh) {
            setTimeout(function () {
                window.location = window.location;
                window.location.reload();
            }, 1000);
        } else if (result.jumpUrl) {
            setTimeout(function () {
                window.location = result.jumpUrl;
            }, 1000);
        } else if (result.html) {
            document.write(result.html);
        }
        return result.data ? result.data : true;
    } else if (result.code == 1 || result.code == 2) {
        lobibox_alert('error', title, result.detail ? result.detail : '系统错误');
    } else if (result.code == 3) {
        lobibox_alert('error', title, result.detail ? result.detail : '表单验证出错');
    } else {
        // 否则添加失败，提示用户
        lobibox_alert('error', title, result.detail ? result.detail : '未知错误');
    }
    return false;
}

/***
 * 异步ajax请求
 * @param url
 * @param data
 * @param reqType
 * @param callback(data)
 * @param opt 配置参数（可选）
 */
function ajaxReq_async(url, data, reqType, callback, opt, complete) {
    if (url == null || url == '') {
        lobibox_alert('error', '参数错误', '参数错误，请检联系管理员');
        return;
    }
    //构建ajax 发起请求
    $.ajax({
        type: reqType,
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        async: opt != null && opt.async != null ? opt.async : false,//同步
        beforeSend: function () {
        },
        error: function (request) {
            lobibox_alert('error', '请求数据失败', '请求服务器数据失败，请检查网络配置或者稍后再试');
        },
        success: function (data) {
            callback(data);
        },
        complete: function (res) {
            complete && typeof complete == "function" && complete(res);
        }
    });
}

function submitFormData(url, data, successFunction) {
    $.ajax({
        cache: true,
        type: "POST",
        url: url,
        data: data,
        processData: false,  // 告诉jQuery不要去处理发送的数据
        contentType: false,   // 告诉jQuery不要去设置Content-Type请求头
        success: successFunction
    })
}

/***
 * lobibox_alert提示框
 * @param type
 * @param title
 * @param msg
 */
function lobibox_alert(type, title, msg) {
    Lobibox.alert(type, {
        title: title, position: "top center", msg: msg, width: 500,
        buttons: {
            ok: {
                'class': 'lobibox-btn lobibox-btn-default',
                text: '确定',
                closeOnClick: true
            }
        }
    });
}

/***
 * lobibox_confirm确认框
 * @param title
 * @param msg
 * @return {boolean}
 */
function lobibox_confirm(title, msg, callback) {
    Lobibox.confirm({
        title: title, position: "top center", msg: msg, width: 500,
        buttons: {
            yes: {
                'class': 'lobibox-btn lobibox-btn-default',
                text: '是',
                closeOnClick: true
            },
            no: {
                'class': 'lobibox-btn lobibox-btn-default',
                text: '否',
                closeOnClick: true
            }
        },
        callback: function ($this, type) {
            callback(type);
        }
    });
}

function lobibox_notify(type, msg) {
    Lobibox.notify(
        type,
        {
            msg: msg,
            position: 'center top',
            sound: false
        })
}

/**
 * 找到当前单元格所在的行
 * @param fromEl
 * @return {*}
 */
function findNearRowObject(fromEl) {
    if (fromEl == null) return null;
    if ($(fromEl).is("tr")) return fromEl;
    return findNearRowObject($(fromEl).parent());
}

/***
 * 通过iframe下载文件
 * @param url
 */
function downLoadByIframe(url) {
    var IFrameRequest = document.createElement("iframe");
    IFrameRequest.id = "IFrameRequest";
    IFrameRequest.src = url;
    IFrameRequest.style.display = "none";
    document.body.appendChild(IFrameRequest);
}

/***
 * 通过form下载文件
 * @param url
 * @param method
 */
function downLoadByForm(url, method = 'get') {
    var form = $("<form>");   //定义一个form表单
    form.attr('style', 'display:none');   //下面为在form表单中添加查询参数
    form.attr('target', '');
    form.attr('method', method);
    form.attr('action', url);

    var input1 = $('<input>');
    input1.attr('type', 'hidden');
    input1.attr('name', 'condition');
    input1.attr('value', "data");

    $('body').append(form);  //将表单放置在web中
    form.append(input1);   //将查询参数控件提交到表单上
    form.submit();   //表单提交
}

function handleDate(source) {
    if (source == "")
        return "";
    var date = new Date(source);
    var y = 1900 + date.getYear();
    var m = "0" + (date.getMonth() + 1);
    var d = "0" + date.getDate();
    return y + "-" + m.substring(m.length - 2, m.length) + "-" + d.substring(d.length - 2, d.length);
}

function customFormatDate(date, fmt) {
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length));
    }
    let o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds()
    };
    for (let k in o) {
        if (new RegExp(`(${k})`).test(fmt)) {
            let str = o[k] + '';
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str));
        }
    }
    return fmt;
}
function padLeftZero(str) {
    return ('00' + str).substr(str.length);
}