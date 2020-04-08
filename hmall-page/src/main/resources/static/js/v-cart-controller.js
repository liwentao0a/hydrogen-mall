var cartBox=new Vue({
    el:'#cartBox',
    data:{
        cartItems:[
            // {
            //     "id": 18,
            //     "productId": 1,
            //     "productSkuId": 1,
            //     "userId": 2,
            //     "quantity": 1,
            //     "price": 3388,
            //     "productPic": "https://img14.360buyimg.com/n0/jfs/t1/85003/27/13140/97349/5e525503E9c41cfe0/60a396b4ac34f2c8.jpg",
            //     "productName": "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     "productSubTitle": "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     "userNickname": "1",
            //     "createDate": "2020-03-05T09:10:42.000+0000",
            //     "modifyDate": "2020-03-05T09:10:42.000+0000",
            //     "deleteStatus": 0,
            //     "productCategoryId": 61,
            //     "productSn": "93d8423d-7a78-4780-b69e-2097d15c127a-2020-03-05 17:10:42.14",
            //     "productAttr": "[{\"key\":\"颜色\",\"value\":\"天空之境\"},{\"key\":\"版本\",\"value\":\"128G\"},{\"key\":\"版本\",\"value\":\"标准版\"}]"
            // }
        ],
        total:0,
        isSelectAll:false,
        cartItemCheckboxValues:[]
    },
    mounted:function () {
        this.listCartItems();
    },
    methods:{
        listCartItems:function () {
            var $this=this;
            $api.listCartItems().then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.cartItems=data.data;
                }else if (data.code==Api.code.RETURN_NULL){
                    console.log('购物车为空');
                }else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        cartItemCheckboxChange:function () {
            var $this=this;
            $this.total=0;
            $('input[name="cartItemCheckbox"]:checked').each(function () {
                var subtotal = parseInt($(this).attr('subtotal'));
                $this.total+=subtotal;
            });
            if($('input[name="cartItemCheckbox"]:checked').length==$('input[name="cartItemCheckbox"]').length){
                $this.isSelectAll=true;
            }else {
                $this.isSelectAll=false;
            }
            console.log($this.cartItemCheckboxValues)
        },
        selectAllCheckBoxChange:function () {
            if (this.isSelectAll) {
                $('input[name="cartItemCheckbox"]:not(:checked)').click();
            }else {
                $('input[name="cartItemCheckbox"]:checked').click();
            }
        },
        cartItemDelBtnClick:function (cartItemId) {
            let $this = this;
            $util.alertDialog({message:"确认删除",positive:function () {
                    $api.removeCartItemByCartItemId(cartItemId).then(function (value) {
                        let data = value.data;
                        if (data.code==Api.code.SUCCESS){
                            $util.toast('删除成功');
                            let cartItems = $this.cartItems;
                            for (let i=0;i<cartItems.length;i++){
                                if (cartItems[i].id==parseInt(cartItemId)){
                                    cartItems.splice(i,1);
                                    console.log(cartItems);
                                    break;
                                }
                            }
                        } else if (data.code==Api.code.RETURN_FALSE){
                            $util.toast('删除失败');
                        } else {
                            $util.toast(data.message);
                        }
                    }).catch(function (reason) {
                        $util.toast(reason);
                    });
                }});
        },
        billBtnClick:function () {
            var $this=this;
            if (this.cartItemCheckboxValues.length==0){
                $util.toast('请选择结算的商品');
                return false;
            }
            console.log($this.cartItemCheckboxValues.toString());
            window.location.href=Api.host.page+'/USER/bill.html?cartItemIds='+$this.cartItemCheckboxValues.toString();
        }
    }
});