var searchProductCardsBox=new Vue({
    el:'#searchProductCardsBox',
    data:{
        keyword:'',
        skuInfos:[],
        skuInfosPage:{
            total: 0,
            pageNum: 0,
            pageSize: 0,
            size: 0,
            startRow: 0,
            endRow: 0,
            pages: 0,
            prePage: 0,
            nextPage: 1,
            hasPreviousPage: false,
            hasNextPage: false,
            navigatePages: 0,
            navigatepageNums: [
                1
            ],
            navigateFirstPage: 0,
            navigateLastPage: 0,
            list: [],
            lastPage: false,
            firstPage: false
        }
    },
    mounted:function () {
        var keyword = $util.getUrlParam('keyword');
        if (keyword!=null){
            this.keyword=keyword;
        }
        this.pageSkuInfosByKeyword();
    },
    methods:{
        pageSkuInfosByKeyword:function () {
            var $this=this;
            $api.pageSkuInfosByKeyword($this.keyword,$this.skuInfosPage.nextPage).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.skuInfosPage=data.data;
                    $this.skuInfos.push.apply($this.skuInfos,data.data.list);
                } else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        productCardClick:function (productId,skuId) {
            window.location.href=Api.host.page+'/product.html?productId='+productId+'&skuId='+skuId;
        }
    }
});