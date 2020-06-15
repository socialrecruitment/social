/**
 * Created by shenyf on 2017/4/26.
 */
function flushCaptcha(obj,basePath){
    // 刷新验证码图片
    $(obj).click(function () {
        $(obj).attr("src", basePath + "/captcha.jpeg?timestamp" + (new Date()).valueOf());
    });
}
