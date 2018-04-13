<!-- //////////////头部公共页面////////////// -->
{include file='/admin/header.tpl'}
<!-- /////////////头部公共页面/////////////// -->
	<div class="col-content">
<!-- //////////////头部公共页面////////////// -->
	{include file='/admin/funds/left.tpl'}
<!-- /////////////头部公共页面/////////////// -->
		<div class="col-main">
			<div class="col-crumbs"><div class="crumbs"><strong>当前位置：</strong><a href="#">资金</a> &gt; <a href="#">异常补款</a> &gt; 发起补款申请</div></div>
			<div class="col-content">
				<div class="col-main">
                	<form method="post" id="J-form">
                        <div class="main">
                            <h3 class="ui-title">发起补款申请</h3>
                            <ul class="ui-form">
                                <li>
                                    <label class="ui-label">确认收款卡号：</label>
                                    <span class="ui-text-info">KJHHH98080</span>
                                </li>
                                <li>
                                    <label class="ui-label">补款金额：</label>
                                    <span class="ui-text-info">2321.0000</span>
                                </li>
                                <li>
                                    <label class="ui-label">收款银行：</label>
                                    <select  class="ui-select w-3" name="beneficiaryBank" id="beneficiaryBank"><option>选择银行</option><option>22</option></select>                                  								
                                    <div class="ui-check"><i class="error"></i><span id="errortype">请选择收款银行</span></div>
                                
                                </li>
                                <li>
                                    <label class="ui-label">用户名：</label>								
                                    <input class="input w-4"  type="text" name="accountName" id="accountName" value="">                                    							
                                    <div class="ui-check"><i class="error"></i><span id="usernames">请输入用户名（4-16位）</span></div>
                                </li>
                                <li>
                                    <label class="ui-label">银行卡号：</label>
                                    <input class="input w-4"  type="text" name="bankcardnumber"  id="bankcardnumber" value="">                                    						
                                    <div class="ui-check"><i class="error"></i>请输入正确的银行卡号（16-19位）</div>
                                </li>
                                <li>
                                    <label class="ui-label">备注：</label>
                                    <div class="textarea w-4">
                                        <textarea id="txtinfo" name="txtinfo" maxlength="20"></textarea>
                                    </div>
                                    <div id="txtinfoPar1" class="ui-text-prompt" style="display: inline;">
                                    <span>
                                    (
                                    <span name="spancount">0</span>
                                    /20)
                                    </span>
                                    </div>
                                    <div id="txtinfoPar2" class="ui-check" style="display: none;">
                                    <i class="error"></i>
                                    <span>请输入此备注</span>
                                    </div>
                                </li>
                                <li class="ui-btn">
                                    <a class="btn btn-important" href="javascript:void(0);" id="J-Submit">确认补款<b class="btn-inner"></b></a>
                                    <a class="btn" href="javascript:void(0);">上一步<b class="btn-inner"></b></a>
                                </li>
                            </ul>
                        </div>
                      </form>
				</div>
			</div>
		</div>
	</div>
{include file='/admin/script-base.tpl'}
{literal}
<script>
(function() {
	 //一、二级菜单选中样式加载	
	selectMenu('Menufunds','MenuWithdrawalsConfig');
	//备注计数
	$('#txtinfo').keyup(function() {
		$('[name="spancount"]').html($('#txtinfo')[0].value.length);	
	}); 			
	//数字校验，自动矫正不符合数学规范的数学
	var inputs = $('#bankcardnumber'),checkFn;				
	checkFn = function(){
		var me = this,v = me.value,index;
		me.value = v = v.replace(/^\.$/g, '');
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
			if(v.substring(index,v.length).length>3){
				me.value = v=v.substring(0,v.indexOf(".")+3);
			}
		}
		me.value = v = v.replace(/[^\d|^\.]/g, '');
		me.value = v = v.replace(/^00/g, '0');
		if(v.split('.').length > 2){
			rguments.callee.call(me);
		}
	}; 
	inputs.keyup(checkFn);	inputs.blur(checkFn);
	var form = $('#J-form'),beneficiaryBank,beneficiaryBankPar,accountName,accountNamePar,bankcardnumber,bankcardnumberPar,txtinfo,txtinfoPar,istrue="0";	
	//表单检测错误数量
	errorTypes = ['beneficiaryBank','accountName','bankcardnumber','txtinfo'],
	errorHas = {},
	setErrorNum = function(name, num){
	if(typeof errorHas[name] != 'undefined'){
		errorHas[name] += num;
		errorHas[name] = errorHas[name] < 0 ? 0 : (errorHas[name] > 1 ? 1 : errorHas[name]);
		}
	};
	$.each(errorTypes, function(){
		errorHas[this] = 0;
	});	
	//收款银行验证
	beneficiaryBank = $('#beneficiaryBank');
	beneficiaryBankPar = beneficiaryBank.parent();	
	beneficiaryBank.blur(function(){
		var v = $.trim(this.value);		
		if(v =='选择银行' || v == null){	
			beneficiaryBankPar.find('.ui-check').css('display', 'inline');				
			setErrorNum('beneficiaryBank', 1);
		}
	}).focus(function(){		
		beneficiaryBankPar.find('.ui-check').css('display', 'none');		
	});
	//用户名验,判断是否已存在
	accountName = $('#accountName');
	accountNamePar = accountName.parent();
	accountName.blur(function(){
		var v = $.trim(this.value);			
		checkUsername(v);
	});	
	//银行卡验证
	bankcardnumber = $('#bankcardnumber');
	bankcardnumberPar = bankcardnumber.parent();	
	bankcardnumber.blur(function(){
		var v = $.trim(this.value);				
		if(v =='' || v == null){	
			bankcardnumberPar.find('.ui-check').css('display', 'inline');				
			setErrorNum('bankcardnumber', 1);
		}
		else{			
			if(luhmCheck(v)){
				bankcardnumberPar.find('.ui-check').css('display', 'none');	
				setErrorNum('bankcardnumber', -1);
			}	
			else{
				bankcardnumberPar.find('.ui-check').css('display', 'inline');				
				setErrorNum('bankcardnumber', 1);
			}			
		}
	}).focus(function(){		
		bankcardnumberPar.find('.ui-check').css('display', 'none');		
	});
	//备注验证
	txtinfo = $('#txtinfo');
	txtinfoPar1 =$('#txtinfoPar1');
	txtinfoPar2 =$('#txtinfoPar2');
	txtinfo.blur(function(){
		var v = $.trim(this.value);
		if(v == ''){
			txtinfoPar2.css('display', 'inline');
			txtinfoPar1.css('display', 'none');
			setErrorNum('txtinfo', 1);
		}else{
			txtinfoPar2.css('display', 'none');
			setErrorNum('txtinfo', -1);
		}
		}).focus(function(){
			txtinfoPar1.css('display', 'inline');
			txtinfoPar2.css('display', 'none');
	}); 
	//用户名验证
	function checkUsername(v){
		if(v!='' && v.length>=4 && v.length<=16){			
			$.ajax({
				type:'post',
				dataType:'json',
				cache:false,
				url:'/admin/proxy/topisset',			
				data:'username='+v,					
				success:function(data){								
					if(data['isSuccess']=="1"){					
						accountNamePar.find('.ui-check').css('display', 'none');	
						setErrorNum('accountName', -1);
						istrue="2";					
					}
					else{
						accountNamePar.find('.ui-check').css('display', 'inline');	
						$('#usernames').html('用户名不存在，请重新输入');
						setErrorNum('accountName', 1);	
						istrue="1";	
					}
				}
			});
		}
		else if(v==''||v.length<=4 || v.length>=16 ){	
			accountNamePar.find('.ui-check').css('display', 'inline');	
			setErrorNum('accountName', 1);	}
		else{	
			accountNamePar.find('.ui-check').css('display', 'none');	
			setErrorNum('accountName', -1);
		}			
	}	
	//表单总验证
	function checkFrom(){		
		if($('#beneficiaryBank')[0].value =='选择银行' || $('#beneficiaryBank')[0].value == null){	
			beneficiaryBankPar.find('.ui-check').css('display', 'inline');				
			setErrorNum('beneficiaryBank', 1);
		}
		if($.trim($('#accountName')[0].value) != ''){
			checkUsername($.trim($('#accountName')[0].value));
		}
		else{
			accountNamePar.find('.ui-check').css('display', 'inline');	
			setErrorNum('accountName', 1);	
		} 
		if($.trim($('#bankcardnumber')[0].value) =='' || $.trim($('#bankcardnumber')[0].value) == null){	
			bankcardnumberPar.find('.ui-check').css('display', 'inline');				
			setErrorNum('bankcardnumber', 1);
		}
		if($.trim($('#txtinfo')[0].value) == ''){
			txtinfoPar2.css('display', 'inline');
			txtinfoPar1.css('display', 'none');
			setErrorNum('txtinfo', 1);
		}		
	}	
	
	//表单提
	$('#J-Submit').click(function(){		
		form.submit();
	});		
	
	form.submit(function(e){			
		var err = 0;
		checkFrom();
		$.each(errorTypes, function(){
			if(typeof errorHas[this] != 'undefined'){
				err += errorHas[this];
			}
		});	
		if(err > 0 || istrue !="1"){
			e.preventDefault();
			return false;
		}
		return true;
	});
	
	
	
	
})();		
</script>
{/literal}
</body>
</html>