var Util={
    createNew:function() {
        var util={
            toastContainerId:null,
            handlerQueues:{}
        }
        util.sendMessage=function(queue,args=null){
            let handlerQueue = util.handlerQueues[queue];
            if (handlerQueue==undefined){
                return false;
            }
            for (var i=0;i<handlerQueue.length;i++){
                let handler = handlerQueue[i];
                try {
                    if (handler.length==1){
                        handler(args);
                    } else {
                        handler();
                    }
                }catch (e) {
                    console.log('util.sendMessage-'+e);
                }
            }
        }
        util.bindMessageHandler=function(queue,func){
            if(typeof(func)!='function'){
                return false;
            }
            let  handlerQueue= util.handlerQueues[queue];
            if (handlerQueue==undefined){
                handlerQueue=[];
                util.handlerQueues[queue]=handlerQueue;
            }
            handlerQueue.push(func);
        }

        /**
         * 页面toast设置
         * @param message
         */
        util.toast=function (message) {
            if (util.toastContainerId==null){
                util.toastContainerId='toastContainer-'+Date.now();
                var toastContainerHtml='<div aria-live="polite" aria-atomic="true" style="position: fixed;top: 0;left: 0;right: 0; min-height: 200px;pointer-events: none;">\n' +
                    '  <div id="'+util.toastContainerId+'" style="position: absolute; top: 0; right: 0;padding: 1.5rem;pointer-events: none;">\n' +
                    '  </div>\n' +
                    '</div>';
                $('body').append(toastContainerHtml);
            }
            var toastId='toast-'+Date.now();
            var toastHtml='<div id="'+toastId+'" class="toast" role="alert" aria-live="assertive" aria-atomic="true" data-delay="10000" style="min-width: 200px;pointer-events: auto;">\n' +
                '      <div class="toast-header">\n' +
                '            <svg color="#007bff" class="rounded mr-2" xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-bell"><path d="M18 8A6 6 0 0 0 6 8c0 7-3 9-3 9h18s-3-2-3-9"></path><path d="M13.73 21a2 2 0 0 1-3.46 0"></path></svg>'+
                // '        <img src="..." class="rounded mr-2" alt="...">\n' +
                '        <strong class="mr-auto">消息</strong>\n' +
                // '        <small class="text-muted">just now</small>\n' +
                '        <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">\n' +
                '          <span aria-hidden="true">&times;</span>\n' +
                '        </button>\n' +
                '      </div>\n' +
                '      <div class="toast-body">\n' +message+
                '      </div>\n' +
                '    </div>\n';
            $('#'+util.toastContainerId).append(toastHtml);
            $('#'+toastId).toast('show');
        }
        /**
         * 对话框
         */
        util.alertDialog=function({title='提示',message='这是一个提示',
            positiveName='确定',positive=function () {},
            negativeName='取消',negative=function () {}}={}){
            let dateNow = Date.now();
            var modalId='alertDialogModal-'+dateNow;
            var positiveBtnId='positiveBtnId-'+dateNow;
            var negativeBtnId='negativeBtnId-'+dateNow;
            var modalHtml='<div class="modal fade" id="'+modalId+'" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">\n' +
                '  <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable" role="document">\n' +
                '    <div class="modal-content">\n' +
                '      <div class="modal-header">\n' +
                '        <h5 class="modal-title" id="exampleModalCenterTitle">'+title+'</h5>\n' +
                '        <button type="button" class="close" data-dismiss="modal" aria-label="Close">\n' +
                '          <span aria-hidden="true">&times;</span>\n' +
                '        </button>\n' +
                '      </div>\n' +
                '      <div class="modal-body">\n' + message +
                '      </div>\n' +
                '      <div class="modal-footer">\n' +
                '        <button type="button" id="'+negativeBtnId+'" class="btn btn-secondary" data-dismiss="modal">'+negativeName+'</button>\n' +
                '        <button type="button" id="'+positiveBtnId+'" class="btn btn-primary" data-dismiss="modal">'+positiveName+'</button>\n' +
                '      </div>\n' +
                '    </div>\n' +
                '  </div>\n' +
                '</div>';
            $('body').append(modalHtml);
            $('#'+negativeBtnId).on('click',negative);
            $('#'+positiveBtnId).on('click',positive);
            $('#'+modalId).modal('show');
        }
        /**
         * cookie设置
         * @param cname
         * @param cvalue
         * @param exdays
         */
        util.setCookie=function (cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays*24*60*60*1000));
            var expires = "expires="+ d.toUTCString();
            document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
        }
        /**
         * cookie获取
         * @param cname
         * @returns {string}
         */
        util.getCookie=function (cname) {
            var name = cname + "=";
            var decodedCookie = decodeURIComponent(document.cookie);
            var ca = decodedCookie.split(';');
            for(var i = 0; i <ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') {
                    c = c.substring(1);
                }
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        }
        /**
         * cookie删除
         * @param cname
         */
        util.removeCookie=function (cname) {
            document.cookie = cname+"=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
        }
        /**
         * 获取地址栏url参数
         * @param name
         * @returns {string|null}
         */
        util.getUrlParam=function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); // 构造一个含有目标参数的正则表达式对象
            var r = window.location.search.substr(1).match(reg); // 匹配目标参数
            if (r != null) return decodeURI(r[2]);
            return null; // 返回参数值
        }
        return util;
    }
}

var $util=Util.createNew();
// export var $util=Util.createNew();

