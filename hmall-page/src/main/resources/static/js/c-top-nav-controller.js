// import {$util} from './c-util.js';
// import {$api} from './c-api.js';

var hTopNav= new Vue({
    el: '#h-topNav',
    data: {
        user:null
        //     {
        //     id:null,
        //     username:"2017120478",
        //     password:null,
        //     roleLevel:1,
        //     nickname:"1",
        //     gender:null,
        //     avatarUrl:null,
        //     phone:null,
        //     email:null,
        //     status:null
        // }
    },
    mounted:function(){
        this.getSimpleUserInfo();
    },
    methods:{
        getSimpleUserInfo:function () {
            var $this=this;
            $api.getSimpleUserInfo().then(function (value) {
                console.log(value)
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    var user = data.data;
                    $this.user=user;
                    console.log(JSON.stringify(user))
                    console.log($this.user)
                }
            }).catch(function (reason) {
                console.log(reason);
                $this.user=null;
            })
        },
        logout:function () {
            var $this=this;
            $util.removeCookie('user-token');
            $this.user=null;
            window.location.href=window.location.href;
        },
        searchFormSubmit:function (e) {
            e.preventDefault();
            var keyword = $('#searchForm input[type="search"]').val();
            window.location.href=Api.host.page+'/search.html?keyword='+keyword;
        }
    }
});