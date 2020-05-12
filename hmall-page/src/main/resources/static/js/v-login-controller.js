var username = $util.getCookie('username');
if (username != ''){
    $('#inputUsername').val(username);
}

$('#loginForm').on('submit',function (e) {
    e.preventDefault();
    console.log($(this).serializeJSON());
    $util.toast("正在登录，请稍等。。。")
    $api.loginForUser($(this).serialize())
        .then(function (value) {
        var data = value.data;
        if (data.code==Api.code.SUCCESS){
            var userToken = data.data;
            $util.setCookie('user-token',userToken,7);
            // var rememberMe = $('#inputRememberMe').val();
            if ($('#inputRememberMe').is(':checked')){
                var username = $('#inputUsername').val();
                $util.setCookie('username',username);
            }
            $util.toast('登录成功');
            window.location.href=Api.host.page+'/index.html';
        } else {
            $util.toast(data.message);
        }
    }).catch(function (reason) {
        $util.toast(reason);
    });
})