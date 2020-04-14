var mainContainer=new Vue({
    el:'main.container',
    data:{
        // countdown:3
        orderSn:''
    },
    mounted:function () {
        // this.startCountdown();
        let orderSn = $util.getUrlParam("orderSn");
        if (orderSn!=null){
            this.orderSn=orderSn;
        }
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
        },
        goPay:function (orderSn) {
            window.location.href=Api.host.page+"/USER/pay-method.html?orderSn="+orderSn;
        }
    }
});