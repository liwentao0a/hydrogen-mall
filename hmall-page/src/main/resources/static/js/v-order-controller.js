var orderBox=new Vue({
    el:'#orderBox',
    data:{
        orders:[]
//     [
//     {
//         "id": 32,
//         "userId": 2,
//         "couponId": null,
//         "orderSn": "3ac5c284-1e79-4a07-80f1-453cef503cc5-1584523200112",
//         "createTime": "2020-03-18T09:20:04.000+0000",
//         "username": "2017120478",
//         "totalAmount": 0,
//         "payAmount": 0,
//         "freightAmount": 0,
//         "payType": 0,
//         "sourceType": 0,
//         "status": 0,
//         "orderType": 0,
//         "deliveryCompany": null,
//         "deliverySn": null,
//         "autoConfirmDay": 7,
//         "billType": 0,
//         "billHeader": null,
//         "billContent": null,
//         "billReceiverPhone": null,
//         "billReceiverEmail": null,
//         "receiverName": "aa",
//         "receiverPhone": "13098765476",
//         "receiverPostCode": null,
//         "receiverProvince": "广东",
//         "receiverCity": "潮州",
//         "receiverRegion": "潮安",
//         "receiverDetailAddress": "xxxx",
//         "note": "无备注",
//         "confirmStatus": 0,
//         "deleteStatus": 0,
//         "paymentTime": null,
//         "deliveryTime": null,
//         "receiveTime": null,
//         "modifyTime": "2020-03-18T09:20:04.000+0000",
//         "orderItems": [
//             {
//                 "id": 54,
//                 "orderId": 32,
//                 "orderSn": "3ac5c284-1e79-4a07-80f1-453cef503cc5-1584523200112",
//                 "productId": 1,
//                 "productPic": "https://img14.360buyimg.com/n0/jfs/t1/85003/27/13140/97349/5e525503E9c41cfe0/60a396b4ac34f2c8.jpg",
//                 "productName": "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
//                 "productPrice": 3388,
//                 "productQuantity": 100,
//                 "productSkuId": 1,
//                 "productCategoryId": 61,
//                 "productAttr": "[{\"key\":\"颜色\",\"value\":\"天空之境\"},{\"key\":\"版本\",\"value\":\"128G\"},{\"key\":\"版本\",\"value\":\"标准版\"}]"
//             }
//         ]
//     }
// ]
    },
    mounted:function () {
        this.listOrders();
    },
    methods:{
        listOrders:function () {
            var $this=this;
            $api.listOrders().then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.orders=data.data;
                    let orders = $this.orders;
                    for (var i=0;i<orders.length;i++){
                        let order = orders[i];
                        let orderItems = order.orderItems;
                        let orderTotal = 0;
                        for (var j=0;j<orderItems.length;j++){
                            let orderItem = orderItems[j];
                            orderTotal+=orderItem.productPrice*orderItem.productQuantity;
                        }
                        order['orderTotal']=orderTotal;
                    }
                    console.log(data.data);
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        goPay:function (orderSn) {
            window.location.href=Api.host.page+"/USER/pay-method.html?orderSn="+orderSn;
        }
    }
});