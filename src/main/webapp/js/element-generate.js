/**
 * Created by agaowei on 2017/6/22.
 */
(function(elementui){
    elementui.generateComponent = function(options,selector){
        var Ctor = Vue.extend(options);
        return new Ctor().$mount(selector);
    }

    //消息框
    elementui.confirm = function(title,content,selector,callback){
        var Main = {
            methods: {
                open() {
                    this.$confirm(content, title, {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'info',
                        callback:function (action,instance) {
                            callback(action);
                        }
                    });
                }
            }
        }
        this.generateComponent(Main,selector);
    }

    elementui.alert = function(title,content,selector){
        var Main = {
            methods: {
                open() {
                    this.$alert(content, title, {
                        confirmButtonText: '确定',
                        type: 'warning'
                    });
                }
            }
        }
        this.generateComponent(Main,selector);
    }
})(window.elementui={});
