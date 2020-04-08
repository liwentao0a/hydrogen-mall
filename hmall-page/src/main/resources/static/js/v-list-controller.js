var catalogProductCardsBox=new Vue({
    el:'#catalogProductCardsBox',
    data:{
        catalog3Id:'',
        catalog3Name:'所有分类',
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
        var catalog3Id = $util.getUrlParam('catalog3Id');
        var catalog3Name = $util.getUrlParam('catalog3Name');
        if (catalog3Id!=null){
            this.catalog3Id=catalog3Id;
        }
        if (catalog3Name!=null){
            this.catalog3Name=catalog3Name;
        }
        this.pageSkuInfosByCatalog3();
    },
    methods:{
        pageSkuInfosByCatalog3:function () {
            var $this=this;
            $api.pageSkuInfosByCatalog3($this.catalog3Id,$this.skuInfosPage.nextPage).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.skuInfosPage=data.data;
                    $this.skuInfos.push.apply($this.skuInfos,data.data.list);
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