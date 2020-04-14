var payMethodBox=new Vue({
    el:'#payMethodBox',
    data:{
        orderSn:''
    },
    mounted:function () {
        let orderSn = $util.getUrlParam("orderSn");
        if (orderSn!=null){
            this.orderSn=orderSn;
        }
    },
    methods:{
        orderPay:function (type) {
            alert('订单sn：'+this.orderSn);
            alert('支付类型：'+type);
        }
    }
});