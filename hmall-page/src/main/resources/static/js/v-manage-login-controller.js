var loginBox=new Vue({
    el:'#loginBox',
    data:{
        usernameInput:'',
        rememberMeCheckbox:false
    },
    mounted:function () {
        let username = $util.getCookie('username');
        if (username!=null){
            this.usernameInput=username;
        }
    },
    methods:{
        adminLoginFormSubmit:function (e) {
            e.preventDefault();

            var $this=this;
            $api.loginForAdmin($(e.target).serialize()).then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $util.setCookie('user-token',data.data,7);
                    if ($this.rememberMeCheckbox){
                        $util.setCookie('username',$this.usernameInput);
                    }
                    $util.toast('登录成功');
                    window.location.href=Api.host.page+'/admin/manage.html';
                }else if (data.code==Api.code.RETURN_FALSE){
                    $util.toast('用户名密码错误');
                } else{
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        }
    }
});