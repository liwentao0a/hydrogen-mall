var addAddressModal=new Vue({
    el:'#addAddressModal',
    data:{
        provinceSelected:'',
        citySelected:'',
        areaSelected:'',
        defaultStatusCheckbox:'',
        provinceCityArea:[],
        cityArea:[],
        area:[]
    },
    mounted:function () {
        this.getProvinceCityArea();
    },
    methods:{
        getProvinceCityArea:function () {
            var $this=this;
            axios.get(Api.host.page+'/asset/province-city-area.json').then(function (value) {
                $this.provinceCityArea=value.data;
                let provinceCityArea = $this.provinceCityArea;
                for (var i = 0; i < provinceCityArea.length;i++){
                    let pca = provinceCityArea[i];
                    let cas = pca.cities;
                    for (var j=0;j<cas.length;j++){
                        let ca = cas[j];
                        ca['provinceName']=pca.provinceName;
                        $this.cityArea.push(ca);
                        let as = ca.areas;
                        for (var k=0;k<as.length;k++){
                            let a = as[k];
                            a['cityName']=ca.cityName;
                            $this.area.push(a);
                        }
                    }
                }
                console.log('-------------------')
                console.log($this.area)
            }).catch(function (reason) {
                $util.toast(reason);
            });
        },
        provinceSelectChange:function () {
            this.citySelected='';
            this.areaSelected='';
        },
        citySelectChange:function () {
            this.areaSelected='';
        },
        addAddressFormSubmit:function (e) {
            e.preventDefault();

            //保存收货地址
            let userReceiveAddressJsonData = $(e.target).serializeJSON();
            console.log(userReceiveAddressJsonData)
            $api.saveUserReceiveAddress(userReceiveAddressJsonData).then(function (value) {
                let data = value.data;
                if (data.code==Api.code.SUCCESS){
                    $util.toast('保存成功');
                    $util.sendMessage('save.user.receive.address.success');
                } else if (data.code==Api.code.RETURN_FALSE){
                    $util.toast('保存失败');
                } else {
                    $util.toast(data.message);
                }
            }).catch(function (reason) {
                $util.toast(reason);
            });

            //关闭对话框
            $('#addAddressModal').modal('hide');

            //重置表单
            e.target.reset();
            this.provinceSelected='';
            this.citySelected='';
            this.areaSelected='';
        }
    }
});