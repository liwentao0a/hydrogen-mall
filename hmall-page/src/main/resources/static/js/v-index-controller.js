// import {$util} from './c-util.js';
// import {$api} from './c-api.js';

var carouselExampleCaptions=new Vue({
    el:'#carouselExampleCaptions',
    data:{
        banners:[
            {
                id: 1,
                title: "畅快宅玩",
                body: "5G产品 限时秒杀",
                imgUrl: "https://img12.360buyimg.com/pop/s1180x940_jfs/t1/95881/32/11930/100327/5e40c0c2Ed6ddf498/4be06664f5bfdbbf.jpg.webp",
                linkUrl: "#"
            },
        ]
    },
    mounted:function(){
        this.listBaseBanners();
    },
    methods:{
        listBaseBanners:function() {
            var $this=this;
            $api.listBaseBanners().then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.banners=data.data;
                }else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            });
        },
        clickCarouselItem:function (linkUrl) {
            window.location.href=linkUrl;
        }
    }
});

var productCardsBox=new Vue({
    el:'#productCardsBox',
    data:{
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
        this.pageSkuInfos();
    },
    methods:{
        pageSkuInfos:function () {
            var $this=this;
            $api.pageSkuInfos($this.skuInfosPage.nextPage).then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.skuInfosPage=data.data;
                    $this.skuInfos.push.apply($this.skuInfos,data.data.list);
                }else {
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