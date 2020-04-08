var mainContainer=new Vue({
    el:'main.container',
    data:{
        // countdown:3
    },
    mounted:function () {
        // this.startCountdown();
    },
    methods:{
        // startCountdown:function () {
        //     var $this=this;
        //     setInterval(function () {
        //         $this.countdown--;
        //         if ($this.countdown<=0){
        //             window.location.href=Api.host.page+'/USER/order.html';
        //         }
        //     },1000);
        // },
        redirectTo:function (url) {
            if (url!=null&&url.indexOf('://')==-1){
                url=Api.host.page+url;
            }
            window.location.href=url;
        }
    }
});