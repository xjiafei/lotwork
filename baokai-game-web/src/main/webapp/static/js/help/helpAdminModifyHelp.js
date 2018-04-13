/**   
 * Simple Map   
 *    
 *    
 * var m = new Map();   
 * m.put('key','value');   
 * ...   
 * var s = "";   
 * m.each(function(key,value,index){   
 *      s += index+":"+ key+"="+value+"/n";   
 * });   
 * alert(s);   
 *    
 * @author dewitt   
 * @date 2008-05-24   
 */    
function Map() {     
    /** 存放键的数组(遍历用到) */    
    this.keys = new Array();     
    /** 存放数据 */    
    this.data = new Object();     
         
    /**   
     * 放入一个键值对   
     * @param {String} key   
     * @param {Object} value   
     */    
    this.put = function(key, value) {     
        if(this.data[key] == null){     
            this.keys.push(key);     
        }     
        this.data[key] = value;     
    };     
         
    /**   
     * 获取某键对应的值   
     * @param {String} key   
     * @return {Object} value   
     */    
    this.get = function(key) {     
        return this.data[key];     
    };     
         
    /**   
     * 删除一个键值对   
     * @param {String} key   
     */    
    this.remove = function(key) {     
        this.keys.remove(key);     
        this.data[key] = null;     
    };     
         
    /**   
     * 遍历Map,执行处理函数   
     *    
     * @param {Function} 回调函数 function(key,value,index){..}   
     */    
    this.each = function(fn){     
        if(typeof fn != 'function'){     
            return;     
        }     
        var len = this.keys.length;     
        for(var i=0;i<len;i++){     
            var k = this.keys[i];     
            fn(k,this.data[k],i);     
        }     
    };     
         
    /**   
     * 获取键值数组(类似Java的entrySet())   
     * @return 键值对象{key,value}的数组   
     */    
    this.entrys = function() {     
        var len = this.keys.length;     
        var entrys = new Array(len);     
        for (var i = 0; i < len; i++) {     
            entrys[i] = {     
                key : this.keys[i],     
                value : this.data[i]     
            };     
        }     
        return entrys;     
    };     
         
    /**   
     * 判断Map是否为空   
     */    
    this.isEmpty = function() {     
        return this.keys.length == 0;     
    };     
         
    /**   
     * 获取键值对数量   
     */    
    this.size = function(){     
        return this.keys.length;     
    };     
         
    /**   
     * 重写toString    
     */    
    this.toString = function(){     
        var s = "{";     
        for(var i=0;i<this.keys.length;i++,s+=','){     
            var k = this.keys[i];     
            s += k+"="+this.data[k];     
        }     
        s+="}";     
        return s;     
    };     
}

$(document).ready(function(){
	$('.menu-list li:eq(7)').attr("class","current");
	$('.col-side .nav dd:eq(0)').attr("class","current");
	$('.ui-check').hide();
	
/*	setTimeout(function(){
        var editor = $('#J-content').xheditor({upImgUrl:baseUrl+"/xheditorAdmin/uploadTemplateImg",upImgExt:"jpg,jpeg,gif,png",html5Upload:false});
    }, 0);*/
	
	var editor = $('#J-content').xheditor({upImgUrl:baseUrl+"/xheditorAdmin/uploadTemplateImg",upImgExt:"jpg,jpeg,gif,png",html5Upload:false});
	
	var form = $('#J-form'),button = $('#J-button-submit'),
		select1 = $('#J-select-type'),
		select2 = $('#J-select-type-2'),
		title = $('#J-title'),
		info = $('#J-info'),
		content = $('#J-content'),
		order = $('#J-order'),
		
		setMessage = function(dom, msg){
			dom.parent().find('.ui-check').html('<i></i>' + msg).show();
		},
		setMessage2 = function(dom, msg){
			dom.parent().parent().find('.ui-check').html('<i></i>' + msg).show();
		},
		hideMessage = function(dom){
			dom.parent().find('.ui-check').hide();
		},
		hideMessage2 = function(dom){
			dom.parent().parent().find('.ui-check').html('<i></i>');
			dom.parent().parent().find('.ui-check').hide();
		};
	button.click(function(e){
		e.preventDefault();
		if(select1.val() == -1 || select2.val() == -1 || select2.val() =="" ||select1.val()==""){
			select1.focus();
			select2.focus();
			setMessage(select1, '请选择类目');
			return;
		}
		hideMessage(select1);
		
		if($.trim(title.val()).length < 1 || $.trim(title.val()).length > 50){
			title.focus();
			setMessage(title, '标题长度应在1-50个字符');
			return;
		}
		hideMessage(title);
		
		if($.trim(info.val()).length < 1 || $.trim(info.val()).length > 100 ){
			info.focus();
			setMessage(info, '简介长度应在1-100个字符');
			return;
		}
		hideMessage2(info);
		
		if($.trim(content.val()).length < 1){
			content.focus();
			setMessage2(content, '内容不能为空');
			return;
		}
		hideMessage2(content);
		
		if(!(/^\d+$/g).test($.trim(order.val()))){
			setMessage(order, '排序只能为非负整数');
			return;
		}
		if(Number($.trim(order.val())) < 0){
			setMessage(order, '排序只能为非负整数');
			return;
		}		
		if(Number($.trim(order.val())) > 1000000){
			setMessage(order, '排序不能大于1000000');
			return;
		}
		hideMessage(order);
		form.submit();
	});
})