var accountInfoBox=new Vue({
    el:'#accountInfoBox',
    data:{
        userInfo: {
            id: 2,
            username: "null",
            password: "null",
            roleLevel: 1,
            nickname: "null",
            gender: 1,
            avatarUrl: "https://cn.vuejs.org/images/logo.png",
            phone: "null",
            email: "null",
            status: 1
        },
        userReceiveAddress:[
            // {
            //     "id": 1,
            //     "userId": 2,
            //     "name": "aa",
            //     "phone": "13098765476",
            //     "defaultStatus": 1,
            //     "postCode": null,
            //     "province": "广东",
            //     "city": "潮州",
            //     "region": "潮安",
            //     "detailAddress": "xxxx"
            // }
        ]
    },
    mounted:function () {
        this.getUserInfo();
        $util.bindMessageHandler('save.user.receive.address.success',this.listUserReceiveAddress);
    },
    methods:{
        getUserInfo:function () {
            var $this=this;
            $api.getUserInfo().then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.userInfo=data.data;
                } else {
                    $util.toast(data.message);
                    if (data.code==Api.code.ACCOUNT_TOKEN_EXCEPTION){
                        window.location.href=Api.host.page+'/login.html';
                    }
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        listUserReceiveAddress:function () {
            var $this=this;
            $api.listUserReceiveAddress().then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.userReceiveAddress=data.data;
                } else if (data.code==Api.code.RETURN_NULL){
                    $util.toast('暂无数据');
                } else if (data.code==Api.code.ACCOUNT_TOKEN_EXCEPTION){
                    window.location.href=Api.host.page+'/login.html';
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        receiveAddressDelBtnClick:function (receiveAddressId) {
            let $this = this;
            $util.alertDialog({
                title:"删除",
                message:"确定删除收货地址",
                positive:function () {
                    $api.removeUserReceiveAddressById(receiveAddressId).then(function (value) {
                        let data = value.data;
                        if (data.code==Api.code.SUCCESS){
                            let userReceiveAddress = $this.userReceiveAddress;
                            for (var i=0;i<userReceiveAddress.length;i++){
                                let receiveAddress = userReceiveAddress[i];
                                if (receiveAddress.id==receiveAddressId){
                                    userReceiveAddress.splice(i,1);
                                    break;
                                }
                            }
                        }else if (data.code==Api.code.RETURN_FALSE){
                            $util.toast('删除失败');
                        } else {
                            $util.toast(data.message);
                        }
                    }).catch(function (reason) {
                        $util.toast(reason);
                    });
                }
            });
        }
    }
});