var productInfoBox=new Vue({
    el:'#productInfoBox',
    data:{
        refresh:1,
        productId:null,//url参数
        skuId:null,//url参数
        quantity:1,//购买数量输入框动态绑定
        skus:[
            // {
            //     id: 1,
            //     productId: 1,
            //     price: 3388,
            //     name: "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     description: "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            //     catalog3Id: 61,
            //     defaultImg: "https://img14.360buyimg.com/n0/jfs/t1/85003/27/13140/97349/5e525503E9c41cfe0/60a396b4ac34f2c8.jpg",
            //     skuImages: [
            //         {
            //             id: 2,
            //             skuId: 1,
            //             imgName: "12",
            //             imgUrl: "https://img14.360buyimg.com/n0/jfs/t1/51065/15/4139/146358/5d1c06b9E55c8ed6c/6be09dcbedb04d56.jpg",
            //             productImgId: 2,
            //             isDefault: 0
            //         }
            //     ],
            //     skuSaleAttrValues: [
            //         {
            //             id: 3,
            //             skuId: 1,
            //             saleAttrId: 3,
            //             productSaleAttrId: 1,
            //             saleAttrValueId: 7,
            //             saleAttrName: "版本",
            //             saleAttrValueName: "标准版"
            //         }
            //     ],
            //     skuAttrValues: [
            //         {
            //             id: 2,
            //             attrId: 13,
            //             valueId: 46,
            //             skuId: 1
            //         }
            //     ]
            // }
        ],//请求的数据
        currentSKU:{
            id: 1,
            productId: 1,
            price: 3388,
            name: "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            description: "华为 HUAWEI P30 超感光徕卡三摄麒麟980AI智能芯片全面屏屏内指纹版手机6GB+128GB天空之境全网通双4G手机",
            catalog3Id: 61,
            defaultImg: "https://img14.360buyimg.com/n0/jfs/t1/85003/27/13140/97349/5e525503E9c41cfe0/60a396b4ac34f2c8.jpg",
            skuImages: [
                {
                    id: 2,
                    skuId: 1,
                    imgName: "12",
                    imgUrl: "https://img14.360buyimg.com/n0/jfs/t1/51065/15/4139/146358/5d1c06b9E55c8ed6c/6be09dcbedb04d56.jpg",
                    productImgId: 2,
                    isDefault: 0
                }
            ],
            skuSaleAttrValues: [
                {
                    id: 3,
                    skuId: 1,
                    saleAttrId: 3,
                    productSaleAttrId: 1,
                    saleAttrValueId: 7,
                    saleAttrName: "版本",
                    saleAttrValueName: "标准版"
                }
            ],
            skuAttrValues: [
                {
                    id: 2,
                    attrId: 13,
                    valueId: 46,
                    skuId: 1
                }
            ]
        },//当前显示的sku
        productSaleAttrs:[],//商品销售属性集合
        productSaleAttrValueRadioValues:[],//商品销售属性值单选框值集合
        skuSaleAttrValuesToSKUMap:new Map(),//sku销售属性值-sku
        saleAttrValueAssociationMap:new Map(),//销售属性值-与key组合能获取对应sku的销售属性值集合
        skuIdToSkuStockMap:new Map(),//skuId-sku库存总数
    },
    mounted:function () {
        var productId = $util.getUrlParam('productId');
        if (productId!=null&&productId!=''){
            this.productId=productId;
        }
        var skuId = $util.getUrlParam('skuId');
        if (skuId!=null&&skuId!=''){
            this.skuId=skuId;
        }
        this.listSkusByProductId();
    },
    methods:{
        createOrderToken:function () {
            var $this=this;
            $api.createOrderToken().then(function (value) {
                let data = value.data;
                if (data.code == Api.code.SUCCESS) {
                    $this.orderToken=data.data;
                    console.log(data.data);
                }else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        listSkusByProductId:function () {
            var $this=this;
            $api.listSkusByProductId($this.productId).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.skus=data.data;

                    var productSaleAttrsMap = new Map([]);
                    var skuSaleAttrValuesToSKUMap=$this.skuSaleAttrValuesToSKUMap;

                    for (var i=0;i<$this.skus.length;i++){
                        var sku = $this.skus[i];
                        var skuSaleAttrValues = sku.skuSaleAttrValues;
                        if (skuSaleAttrValues!=null){
                            var skuSaleAttrValuesArray=new Array();
                            for (var j=0;j<skuSaleAttrValues.length;j++){
                                var skuSaleAttrValue = skuSaleAttrValues[j];
                                skuSaleAttrValuesArray.push(skuSaleAttrValue.saleAttrValueId);
                                var productSaleAttr = productSaleAttrsMap.get(skuSaleAttrValue.productSaleAttrId);
                                if (productSaleAttr==null&&productSaleAttr==undefined){
                                    productSaleAttr={
                                        productSaleAttrId:skuSaleAttrValue.productSaleAttrId,
                                        saleAttrName:'',
                                        saleAttrValues:[]
                                    }
                                    productSaleAttrsMap.set(skuSaleAttrValue.productSaleAttrId,productSaleAttr);
                                }
                                productSaleAttr.saleAttrName=skuSaleAttrValue.saleAttrName;
                                productSaleAttr.saleAttrValues.push({
                                    saleAttrValueId:skuSaleAttrValue.saleAttrValueId,
                                    saleAttrValueName:skuSaleAttrValue.saleAttrValueName
                                });
                                console.log(skuSaleAttrValue);
                            }

                            skuSaleAttrValuesArray.sort();
                            skuSaleAttrValuesToSKUMap.set(skuSaleAttrValuesArray.toString(),sku);
                            //销售属性关联map
                            for (var j=0;j<skuSaleAttrValuesArray.length;j++) {
                                var availableSaleAttr = $this.saleAttrValueAssociationMap.get(skuSaleAttrValuesArray[j]);
                                if (availableSaleAttr==null&&availableSaleAttr==undefined){
                                    availableSaleAttr=[];
                                }
                                $this.saleAttrValueAssociationMap.set(skuSaleAttrValuesArray[j],skuSaleAttrValuesArray.concat(availableSaleAttr));
                            }
                        }
                    }
                    //商品销售属性集合去重
                    productSaleAttrsMap.forEach(function (val,key) {
                        var saleAttrValues = val.saleAttrValues;
                        var saleAttrValuesMap=new Map();
                        for (var i=0;i<saleAttrValues.length;i++){
                            saleAttrValuesMap.set(saleAttrValues[i].saleAttrValueId,saleAttrValues[i]);
                        }
                        val.saleAttrValues=Array.from(saleAttrValuesMap.values());
                    });
                    console.log(JSON.parse(JSON.stringify(Array.from(productSaleAttrsMap.values()))));
                    $this.productSaleAttrs=JSON.parse(JSON.stringify(Array.from(productSaleAttrsMap.values())));

                    $this.getAvailableWareSkuStockBySkuId();
                    $this.initCurrentSKU();
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        getAvailableWareSkuStockBySkuId:function () {
            var $this=this;
            function getWareSkuTotalStockBySkuIdToMap(skuId) {
                var thisSkuId=skuId;
                $api.getAvailableWareSkuStockBySkuId(thisSkuId).then(function (value) {
                    var data = value.data;
                    if (data.code==Api.code.SUCCESS){
                        $this.skuIdToSkuStockMap.set(thisSkuId,data.data);
                        console.log($this.skuIdToSkuStockMap)
                        $this.$forceUpdate();
                    } else {
                        $util.toast(data.message);
                    }
                }).catch(function (reason) {
                    $util.toast(reason);
                });
            }
            for (var i=0;i<$this.skus.length;i++){
                var skuId = $this.skus[i].id;
                getWareSkuTotalStockBySkuIdToMap(skuId);
            }
        },
        initCurrentSKU:function () {
            var $this=this;
            if ($this.skuId==null){
                $this.currentSKU=$this.skus[0];
            }else {
                for (var i=0;i<$this.skus.length;i++){
                    if ($this.skus[i].id==$this.skuId){
                        $this.currentSKU=$this.skus[i];
                    }
                }
            }
            this.$nextTick(function () {
                for (var i=0;i<$this.currentSKU.skuSaleAttrValues.length;i++){
                    $('#saleAttrBoxs input[type="radio"][value="'+$this.currentSKU.skuSaleAttrValues[i].saleAttrValueId+'"]').click();
                    console.log('----'+$this.currentSKU.skuSaleAttrValues[i].saleAttrValueId);
                }
            });
        },
        saleAttrValueClick:function (e) {
            e.preventDefault();
            $(e.currentTarget).prev().click();
        },
        saleAttrValueRadioChange:function (e) {
            var $this=this;
            $('#saleAttrBoxs input[type="radio"]').next().removeClass('blueBorder');
            $('#saleAttrBoxs input[type="radio"]:checked').next().addClass('blueBorder');
            var skuMapKey = this.productSaleAttrValueRadioValues.concat().sort().toString();
            console.log(skuMapKey);
            console.log(this.skuSaleAttrValuesToSKUMap);
            var sku = this.skuSaleAttrValuesToSKUMap.get(skuMapKey);
            if (sku!=null&&sku!=undefined){
                this.currentSKU=sku;
            }
            var saleAttrValue = $(e.currentTarget).val();
            var availableSaleAttr = this.saleAttrValueAssociationMap.get(parseInt(saleAttrValue));
            console.log(typeof saleAttrValue)
            console.log(saleAttrValue)
            console.log(this.saleAttrValueAssociationMap)
            console.log(availableSaleAttr)
            $('#saleAttrBoxs input[type="radio"]').next().addClass('dashedBorder');
            for (var i=0;i<availableSaleAttr.length;i++){
                $('#saleAttrBoxs input[type="radio"][value="'+availableSaleAttr[i]+'"]').next().removeClass('dashedBorder');
            }
            $('#saleAttrBoxs .saleAttrValue.blueBorder.dashedBorder').each(function () {
                var vModelIndex = $(this).prev().attr('vModelIndex');
                $this.productSaleAttrValueRadioValues[parseInt(vModelIndex)]='';
            });
            $('#saleAttrBoxs .saleAttrValue.blueBorder.dashedBorder').removeClass('blueBorder');
            console.log($this.productSaleAttrValueRadioValues)
        },
        thumbnailClick:function (imgUrl) {
            var $cardImgTop = $('#productInfoBox .card-img-top');
            $cardImgTop.fadeOut(0);
            this.currentSKU.defaultImg=imgUrl;
            $cardImgTop.fadeIn(600);
        },
        quantityInputChange:function () {
            var quantity = this.quantity;
            if (quantity<1){
                quantity=1;
            } else if (quantity>200){
                var totalStock = this.skuIdToSkuStockMap.get(this.currentSKU.id);
                if (totalStock!=null&&totalStock!=undefined&&quantity>totalStock) {
                    $util.toast('库存不足');
                    quantity=totalStock;
                }else {
                    quantity=200;
                }
            }
            this.quantity=quantity;
        },
        addToCartBtnClick:function () {
            var $this=this;
            $api.saveCartItem($this.currentSKU.id,$this.quantity).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    window.location.href=Api.host.page+'/USER/add-to-cart-success.html?skuId='+$this.currentSKU.id+'&quantity='+$this.quantity+'&originUrl='+window.location.href;
                } else if (data.code==Api.code.RETURN_FALSE){
                    $util.toast('添加失败');
                } else if (data.code==Api.code.ACCOUNT_TOKEN_EXCEPTION){
                    window.location.href=Api.host.page+'/login.html';
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });
        }
    }
});