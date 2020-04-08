var billBox=new Vue({
    el:'#billBox',
    data:{
        orderSn:'',
        cartItemIds:'',
        cartItems:[],
        total:0,
        userReceiveAddress:[],
        receiveAddressSelected:null
    },
    mounted:function () {
        let cartItemIds = $util.getUrlParam('cartItemIds');
        this.cartItemIds=cartItemIds;
        this.createOrderSn();
        this.listCartItemsInCartItemIds(cartItemIds);
        this.listUserReceiveAddress();
        $util.bindMessageHandler('save.user.receive.address.success',this.listUserReceiveAddress);
    },
    methods:{
        createOrderSn:function () {
            var $this=this;
            $api.createOrderSn().then(function (value) {
                let data = value.data;
                if (data.code == Api.code.SUCCESS) {
                    $this.orderSn=data.data;
                    console.log(data.data);
                }else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        listCartItemsInCartItemIds:function (cartItemIds) {
            var $this=this;
            $api.listCartItemsInCartItemIds(cartItemIds).then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    let cartItems = data.data;
                    $this.cartItems=cartItems;
                    for (let i=0;i<cartItems.length;i++){
                        let cartItem = cartItems[i];
                        $this.total+=(cartItem.price*cartItem.quantity);
                    }
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        listUserReceiveAddress:function () {
            var $this=this;
            $api.listUserReceiveAddress().then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    let userReceiveAddress = data.data;
                    $this.userReceiveAddress=userReceiveAddress;
                    for (var i=0;i<userReceiveAddress.length;i++){
                        let receiveAddress = userReceiveAddress[i];
                        if (receiveAddress.defaultStatus==1) {
                            $this.receiveAddressSelected=receiveAddress.id;
                            break;
                        }
                    }
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        generateOrderBtn:function () {
            var $this=this;
            //生成订单
            $api.saveOrder($this.orderSn,$this.cartItemIds,$this.receiveAddressSelected).then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    window.location.href=Api.host.page+'/USER/generate-order-success.html?orderSn='+$this.orderSn;
                } else if (data.code==Api.code.RETURN_FALSE){
                    $util.toast('订单生成失败');
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        }
    }
});