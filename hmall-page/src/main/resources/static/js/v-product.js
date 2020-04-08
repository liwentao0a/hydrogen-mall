/*
缩略图上一个和下一个
 */
function thumbMoveAnimate(distance) {
    $('.card .card-body>div').animate({
        scrollLeft: $('.card .card-body>div').scrollLeft() +distance
    }, 500);
}
$('.carousel-control-prev').on('click',function (e) {
    e.preventDefault();
    thumbMoveAnimate(-66);
});
$('.carousel-control-next').on('click',function (e) {
    e.preventDefault();
    thumbMoveAnimate(66);
});

/*
缩略图随鼠标移动滚动
 */
var startX;
var moveEndX;
var flag=false;
$(".card .card-body>div").mousedown(function (e) {
    startX=e.screenX;
    flag=true;
})
$(".card .card-body>div").mousemove(function (e) {
    if (flag){
        moveEndX=e.screenX;
        var movementX=moveEndX-startX;
        startX=moveEndX;
        // console.log(movementX)
        $('.card .card-body>div').scrollLeft($('.card .card-body>div').scrollLeft()-movementX);
    }
})
$(".card .card-body>div").mouseup(function (e) {
    flag=false;
})
$(".card .card-body>div").mouseleave(function (e) {
    flag=false;
});


/*
销售属性选择
 */
$('#saleAttrBoxs input[type="radio"]').on('change',function () {
    $('#saleAttrBoxs input[type="radio"]').next().removeClass('selectedSaleAttr');
    $('#saleAttrBoxs input[type="radio"]:checked').next().addClass('selectedSaleAttr');
})
$('.saleAttrValue').on('click',function (e) {
    e.preventDefault();
    $(this).prev().click();
})

