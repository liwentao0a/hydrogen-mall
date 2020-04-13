// 允许携带cookie
axios.defaults.withCredentials=true;

var Api={
    host:{
        page:'http://localhost:9000/page',
        // page:'http://localhost:63342/hydrogen-mall/hmall-page-web/static',
        // user:'http://localhost:8001',
        user:'http://localhost:9000/user',
        // passport:'http://localhost:8011',
        passport:'http://localhost:9000/passport',
        // product:'http://localhost:8021',
        product:'http://localhost:9000/product',
        // ware:'http://localhost:8031',
        ware:'http://localhost:9000/ware',
        // order:'http://localhost:8041',
        order:'http://localhost:9000/order',
    },
    code:{
        SUCCESS:1,
        RETURN_FALSE:2,
        RETURN_NULL:3,

        ACCOUNT_USERNAME_OR_PASSWORD_ERROR:1000,
        ACCOUNT_STATUS_ABNORMAL:1001,
        ACCOUNT_INSUFFICIENT_PERMISSIONS:1002,
        ACCOUNT_TOKEN_EXCEPTION:1003,
        ACCOUNT_USERNAME_ALREADY_EXISTS:1003,

        SYSTEM_ERROR:2001
    },
    createNew:function () {
        var api={

        }
        /*
        page
         */
        api.uploadImage=function(image){
            // return axios.post(Api.host.page+'/uploadImage',image,{
            let formData = new FormData();
            formData.append('image',image);
            return axios.post('http://localhost/uploadImage',formData,{
                contentType: 'application/json;charset=UTF-8',
            });
        }

        /*
        user
         */
        api.registeredUser=function (data) {
            return axios.post(Api.host.user+'/registeredUser',data,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.getSimpleUserInfo=function(){
            return axios.get(Api.host.user+'/USER/getSimpleUserInfo',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.getUserInfo=function(){
            return axios.get(Api.host.user+'/USER/getUserInfo',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listUserReceiveAddress=function(){
            return axios.get(Api.host.user+'/USER/listUserReceiveAddress',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.saveUserReceiveAddress=function(userReceiveAddressJsonData){
            return axios.post(Api.host.user+'/USER/saveUserReceiveAddress',userReceiveAddressJsonData,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.removeUserReceiveAddressById=function(receiveAddressId){
            return axios.delete(Api.host.user+'/USER/removeUserReceiveAddressById?receiveAddressId='+receiveAddressId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }



        /*
        passport
         */
        api.loginForUser=function (data) {
            return axios.post(Api.host.passport+'/loginForUser',data,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.loginForAdmin=function (data) {
            return axios.post(Api.host.passport+'/loginForAdmin',data,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.logout=function(token){
            return axios.get(Api.host.passport+'/USER/logout?token='+token,{
                contentType: 'application/json;charset=UTF-8',
            });
        }



        /*
        product
         */
        api.listBaseBanners=function () {
            return axios.get(Api.host.product+'/listBaseBanners',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listCatalogs=function () {
            return axios.get(Api.host.product+'/listCatalogs',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.pageSkuInfos=function (pageNum) {
            return axios.get(Api.host.product+'/pageSkuInfos?pageNum='+pageNum,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.pageSkuInfosByCatalog3=function (catalog3Id,pageNum) {
            return axios.get(Api.host.product+'/pageSkuInfosByCatalog3?catalog3Id='+catalog3Id+'&pageNum='+pageNum,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.pageSkuInfosByKeyword=function (keyword,pageNum) {
            return axios.get(Api.host.product+'/pageSkuInfosByKeyword?keyword='+keyword+'&pageNum='+pageNum,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listSkusByProductId=function (productId) {
            return axios.get(Api.host.product+'/listSkusByProductId?productId='+productId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.getSkuBySkuId=function(skuId){
            return axios.get(Api.host.product+'/getSkuBySkuId?skuId='+skuId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.getSkuInfoBySkuId=function(skuId){
            return axios.get(Api.host.product+'/getSkuInfoBySkuId?skuId='+skuId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }



        /*
        ware
         */
        api.getAvailableWareSkuStockBySkuId=function (skuId) {
            return axios.get(Api.host.ware+'/getAvailableWareSkuStockBySkuId?skuId='+skuId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }



        /*
        order
         */
        api.saveCartItem=function (skuId, quantity) {
            var formData = new FormData();
            formData.append('skuId',skuId);
            formData.append('quantity',quantity);
            return axios.post(Api.host.order+'/USER/saveCartItem',formData,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listCartItems=function () {
            return axios.get(Api.host.order+'/USER/listCartItems',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.removeCartItemByCartItemId=function (cartItemId) {
            return axios.delete(Api.host.order+'/USER/removeCartItemByCartItemId?cartItemId='+cartItemId,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listCartItemsInCartItemIds=function(cartItemIds){
            return axios.get(Api.host.order+'/listCartItemsInCartItemIds?cartItemIds='+cartItemIds,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.createOrderSn=function () {
            return axios.get(Api.host.order+'/createOrderSn',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.saveOrder=function (orderSn,cartItemIds,receiverId) {
            let formData = new FormData();
            formData.append('orderSn',orderSn);
            formData.append('cartItemIds',cartItemIds);
            formData.append('receiverId',receiverId);
            return axios.post(Api.host.order+'/USER/saveOrder',formData,{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        api.listOrders=function () {
            return axios.get(Api.host.order+'/USER/listOrders',{
                contentType: 'application/json;charset=UTF-8',
            });
        }
        return api;
    }
}

var $api=Api.createNew();
// export var $api=Api.createNew();