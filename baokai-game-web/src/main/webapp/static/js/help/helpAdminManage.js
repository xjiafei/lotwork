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
	var table = $('#J-data-table'),checkboxs = table.find('.checked-row'),del = $('#J-checked-delete'),dels = table.find('.row-del'),
	//提示框
		minWindow = new phoenix.MiniWindow({cls:'ui-alert-custom'}),
		mask = phoenix.Mask.getInstance();
	minWindow.dom.css({
		width:300
	});
	minWindow.addEvent('beforeShow', function(){
		mask.show();
	});
	minWindow.addEvent('afterHide', function(){
		mask.hide();
	});
	
	//表格全选
	$('#J-checked-row-all').click(function(){
		var me = this;
		checkboxs.each(function(){
			this.checked = me.checked;
		});
	});
	del.click(function(e){
		var i = 0;
		e.preventDefault();
		var id='';
		checkboxs.each(function(){
			if(this.checked)
				{
                    id = id + this.value;
                    id = id + ',';
                    i++;
				}
		});
		if(i > 0){
			var strArr = [];
            e.preventDefault();
            strArr.push('<div class="text">您确定要删除所属帮助条目吗？删除后将不能恢复</div>');
            strArr.push('<div class="control"><a style="width:36%" id="sButton" data-buttontype="1" href="javascript:void(0);" class="btn">确定<b class="btn-inner"></b></a> <a data-buttontype="0" href="javascript:void(0);" style="width:36%" class="btn">取消<b class="btn-inner"></b></a></div>');
            minWindow.setContent(strArr.join(''));
            minWindow.show();
            $('#sButton').data("dir", id);
		}
		else 
		{
			var strArr = [];
            e.preventDefault();
            strArr.push('<div class="text">请选择您要删除的帮助条目</div>');
            strArr.push('<div class="control"><a data-buttontype="0" href="javascript:void(0);" class="btn">确定<b class="btn-inner"></b></a></div>');
            minWindow.setContent(strArr.join(''));
            minWindow.show();
		}
	});
	
	//删除行
	dels.click(function(e){
		var strArr = [];
		e.preventDefault();
		strArr.push('<div class="text">确定要删除这条帮助信息？</div>');
		strArr.push('<div class="control"><a style="width:36%" id="sButton" data-buttontype="1" href="javascript:void(0);" class="btn">确定<b class="btn-inner"></b></a> <a data-buttontype="0" href="javascript:void(0);" style="width:36%" class="btn">取消<b class="btn-inner"></b></a></div>');
		minWindow.setContent(strArr.join(''));
		minWindow.show();
		$('#sButton').data("dir", $(this).attr("dir"));
	});
	$(document).on('click', '.btn', function(e){
		if(e.target.getAttribute('data-buttontype') == '1'){
			var id = $(this).data("dir");
			$.post(baseUrl+"/helpAdmin/delHelp",{id:id},function(result){
				alert("删除成功");
				$('#queryForm').submit();
			});
			minWindow.hide();
		}else{
			minWindow.hide();
		}
	});
})