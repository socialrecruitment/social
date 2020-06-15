$.extend($.fn.validatebox.defaults.rules, {
    selectValueRequired: {
        validator: function(value,param){
            return $(param[0]).find("option:contains('"+value+"')").val() != ''&& $(param[0]).find("option:contains('"+value+"')").val() != undefined;
        },
        message: '该输入项为必输项'
    },
    minLength: {  
        validator: function(value, param){  
            return value.length >= param[0];  
        },  
        message: 'Please enter at least {0} characters.'  
    },
    maxLength: {  
        validator: function(value, param){  
            return value.length <= param[0];  
        },  
        message: 'Please enter at most {0} characters.'  
    },
	chs: {
        validator: function (value) {
            return /^[\u0391-\uFFE5]+$/.test(value);
        },
        message: 'Please enter Chinese.'
    },
    letterNum: {
        validator: function (value) {
            return /^[0-9A-Za-z_]+$/.test(value);
        },
        message: 'Please enter letters or numbers.'
    },
    positiveNum:{
    	 validator: function (value){
    		 return value>=0;
    	 },
    	 message: 'Please enter positive numbers.'
    },
    decimalNum:{
        validator: function (value){
            return value > 0 && value < 1;
        },
        message: '请输入0~1之间的数字.'
    },
    doubleNum:{
        validator:function(value){
            return /^([1-9]\d*\.\d*|0\.\d*[1-9]\d*)|([0-9]*[1-9][0-9]*)$/.test(value);
        },
        message:'请输入数字.'
    },
    integerNum:{
        validator: function (value){
            return /^[0-9]*[1-9][0-9]*$/.test(value);
        },
        message: 'Please enter integer numbers.'
    },
    postCode: {
        validator: function (value) {
            return /^[0-9]\d{5}$/.test(value);
        },
        message: 'Please enter a valid postCode number.'
    },
    qq: {
        validator: function (value) {
            return /^[1-9]\d{4,10}$/.test(value);
        },
        message: 'Please enter a valid QQ number.'
    },
    phone: {
        validator: function (value) {//
            /*return /(^(13[0-9]{9}|15[012356789][0-9]{8}|18[0256789][0-9]{8}|147[0-9]{8})$)|(^(([0\+]\d{2,3}-)?(0\d{2,3})-)?(\d{7,8})(-(\d{3,}))?$)/.test(value);*/
            return /(^(1(([3,8][0-9])|(4[5,7])|(5[^4])|(7[0,6,7,8])))\d{8}$)/.test(value);
        },
        message: 'Please enter a valid phone number or fixed phone.'
    },    
    idCard: {
        validator: function (value) {
			if (value.length == 18 && 18 != value.length) return false;
			var number = value.toLowerCase();
			var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
			var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
			if (re == null || a.indexOf(re[1]) < 0) return false;
			if (re[2].length == 9) {
				number = number.substr(0, 6) + '19' + number.substr(6);
				d = ['19' + re[4], re[5], re[6]].join('-');
			} else d = [re[9], re[10], re[11]].join('-');
			if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
			for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
			return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
        },
        message:'Please enter a valid identity card number.'
    }
}); 

var isDateTime = function (format, reObj) {
    format = format || 'yyyy-MM-dd';
    var input = this, o = {}, d = new Date();
    var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
    var len = f1.length, len1 = f3.length;
    if (len != f2.length || len1 != f4.length) return false;
    for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
    for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
    o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
    o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
    o.dd = s(o.dd, o.d, d.getDate(), 31);
    o.hh = s(o.hh, o.h, d.getHours(), 24);
    o.mm = s(o.mm, o.m, d.getMinutes());
    o.ss = s(o.ss, o.s, d.getSeconds());
    o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
    if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
    if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
    d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
    var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
    return reVal && reObj ? d : reVal;
    function s(s1, s2, s3, s4, s5) {
        s4 = s4 || 60, s5 = s5 || 2;
        var reVal = s3;
        if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
        if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
        return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
    }
};