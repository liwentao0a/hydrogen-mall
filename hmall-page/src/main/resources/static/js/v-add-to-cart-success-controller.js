var infoBox=new Vue({
    el:'#infoBox',
    data:{
        skuId:-1,
        originUrl:'',
        quantity:0,
        sku:null
            // {
            //     "id": 1,
            //     "productId": 1,
            //     "price": 3388,
            //     "name": "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     "description": "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     "catalog3Id": 61,
            //     "defaultImg": "https://img14.360buyimg.com/n0/jfs/t1/85003/27/13140/97349/5e525503E9c41cfe0/60a396b4ac34f2c8.jpg",
            //     "skuImages": null,
            //     "skuSaleAttrValues": null,
            //     "skuAttrValues": null
            // }
    },
    mounted:function () {
        var skuId = $util.getUrlParam('skuId');
        var originUrl = $util.getUrlParam('originUrl');
        var quantity = $util.getUrlParam('quantity');
        if (skuId!=null){
            this.skuId=skuId;
            this.getSkuInfoBySkuId();
        }
        if (originUrl!=null){
            this.originUrl=originUrl;
        }
        if (quantity!=null){
            this.quantity=quantity;
        }
    },
    methods:{
        redirectTo:function (url) {
            if (url!=null&&url.indexOf('://')==-1){
                url=Api.host.page+url;
            }
            window.location.href=url;
        },
        getSkuInfoBySkuId:function () {
            var $this=this;
            $api.getSkuInfoBySkuId($this.skuId).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.sku=data.data;
                } else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        }
    }
});