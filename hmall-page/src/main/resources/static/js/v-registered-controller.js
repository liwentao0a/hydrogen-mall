$('#registeredForm').on('submit',function (e) {
});

var vv=new Vue({
    el:'#registeredForm',
    data:{
        iconSrc:'https://cn.vuejs.org/images/logo.png',
        iconTips:'点击上传头像',
        avatarUrl:'https://cn.vuejs.org/images/logo.png'
    },
    mounted:function () {

    },
    methods:{
        registeredFormSubmit:function (e) {
            e.preventDefault();
            //密码一致性校验
            var confirmPassword = $('#confirmPasswordInput').val();
            var password = $('#passwordInput').val();
            if (password!=confirmPassword){
                $util.toast('密码不一致');
                return false;
            }
            //注册
            console.log($('#registeredForm').serializeJSON());
            $api.registeredUser($('#registeredForm').serializeJSON())
                .then(function (value) {
                    console.log(value);
                    var data = value.data;
                    if (data.code==Api.code.SUCCESS){
                        $util.toast('注册成功');
                        window.location.href=Api.host.page+'/login.html';
                    }else {
                        $util.toast(data.message);
                    }
                }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        iconChange:function () {
            var $this=this;

            // var files = $('#iconInput').files ;
            let icon = $('#iconInput')[0].files[0];
            let iconUrl = window.URL.createObjectURL(icon);

            $this.iconSrc=iconUrl;
            $this.iconTips='上传中。。。';

            $api.uploadImage(icon).then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.avatarUrl=data.data;
                    $this.iconSrc=data.data;
                    $this.iconTips='上传成功';
                } else if (data.code==Api.code.RETURN_FALSE){
                    $this.iconTips='上传失败';
                } else {
                    $this.iconTips=data.message;
                }
            }).catch(function (reason) {
                $this.iconTips=reason;
            }).finally(function () {
                $('#iconInput').val('');
            });



        }
    }
});