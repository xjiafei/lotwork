(function () {
    //只能输入数字跟小数点
    var allowNumberDot = function () {
        var me = this, v = me.value,maxLength=$(me).attr('maxsizelength');
        me.value = v = v.replace(/^\.$/g, '');
        me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
        me.value = v = v.replace(/[^\d|^\.]/g, '');
        me.value = v = v.replace(/^(0)+(\d)+/g, '$2');
        if(maxLength>0&&v.split('.')[0].length>maxLength){
            me.value = v.split('.')[0].substr(0,maxLength);
        }
        if(v.split('.').length > 1){
            me.value = v.split('.')[0].substr(0,maxLength)+'.'+v.split('.')[1].substr(0,2);
        }
        if (v.split('.').length > 2) {
            arguments.callee.call(me);
        }
    };
    //只能输入数字
    var allowNumber = function(){
        var me = this, v = me.value;
        me.value = v = v.replace(/[^\d]/g, '');
    }
    //只能输入数字/英文/@/小数点
    var allowEnNumDot = function () {
        var me = this, v = me.value;
        me.value = v = v.replace(/[^\a-zA-Z0-9.@]/g, '');
    };
    
    //只能输入漢字/数字/英文
    var allowCnEnNum = function () {
        var me = this, v = me.value;
        me.value = v = v.replace(/[^a-zA-Z0-9\u4e00-\u9fa5]/g, '');
    };
    
    //只能输入漢字/英文
    var allowCnEnName = function () {
        var me = this, v = me.value;
        var enRule = /[^a-zA-Z]/g;
        var cnRule = /[^\u4e00-\u9fa5\u00b7]/g;
        var regEn = new RegExp(enRule);
        var firstVal = v.substr(0,1);
		me.value = v = v.replace(/[\d]+/g, '');
        if(!regEn.test(firstVal)){
            me.value = v = v.replace(/[^a-zA-Z][^a-zA-Z]+/g, '');
        }else{
            me.value = v = v.replace(cnRule, '');
            me.value = v = v.replace(/[\u00b7]+/g, '\u00b7');
        }
    };
    
    //1111 1111 1111 1111
    var formatCardNum = function () {
        var me = this, v = me.value;
        me.value = v = v.replace(/[^\d ]/g, '');
        me.value = v = v.replace(/(\d{4})(?=\d)/g, '$1 ');
    };
    $('.numberDotOnly').keyup(allowNumberDot).blur(allowNumberDot);
    $('.numberOnly').keyup(allowNumber).blur(allowNumber);
    $('.enNumDotOnly').keyup(allowEnNumDot).blur(allowEnNumDot);
    $('.cnEnNumOnly').keyup(allowCnEnNum).blur(allowCnEnNum);
    $('.cnEnNameOnly').keyup(allowCnEnName).blur(allowCnEnName);
    $('.formatCardNum').keyup(formatCardNum).blur(formatCardNum);
})();