var catalogBox=new Vue({
    el:'#catalogBox',
    data:{
        catalogs:[
            {
                id: 1,
                name: "图书、音像、电子书刊",
                catalog2s: [
                    {
                        id: 1,
                        name: "电子书刊",
                        catalog1Id: 1,
                        catalog3s: [
                            {
                                id: 1,
                                name: "电子书",
                                catalog2Id: 1
                            }
                        ]
                    },
                ]
            }
        ]
    },
    mounted:function(){
        this.listCatalogs();
    },
    methods:{
        listCatalogs:function () {
            var $this=this;
            $api.listCatalogs().then(function (value) {
                var data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $this.catalogs=data.data;
                } else {
                    console.log(data.message);
                }
            }).catch(function (reason) {
                console.log(reason);
            })
        },
        catalog3Click:function (catalog3Id,catalog3Name) {
            window.location.href=Api.host.page+'/list.html?catalog3Id='+catalog3Id+'&catalog3Name='+catalog3Name;
        }
    }
})