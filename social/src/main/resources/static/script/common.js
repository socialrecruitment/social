

function commonExport(baseUrl,$url,$param) {
    $.ajax({
        url:$url,
        type:'POST',
        data:$param,
        traditional:true,
        //async:false,
        success:function(result) {
            console.log('result:',result);
            var ret = result.ret;
            if (ret != 'undefined') {
                var fileName = ret.substring(ret.lastIndexOf('/') + 1);
                window.location.href = baseUrl + '/remote/query/downloadFile.html?fileName='+ fileName;
            }
        }
    });

}

function previewImg(obj) {
    var img = new Image();
    img.src = obj.src;

    var imgHtml = "<img src='" + obj.src + "' width='1200' height='675' />";

    //捕获页
    layui.layer.open({
        type: 1,
        shadeClose:true,
        title: false, //不显示标题
        area: [1200+'px', 675+'px'],
        content: imgHtml, //捕获的元素，注意：最好该指定的元素要存放在body最外层，否则可能被其它的相对元素所影响
        cancel: function () {

        }
    });
}
