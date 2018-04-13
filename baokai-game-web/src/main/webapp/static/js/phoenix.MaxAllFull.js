function CheckingMaxAllFull(obj,maxAllfull){

		var me = obj,v = me.value,index;
		if($.trim(v)=='') return false;
		me.value = v = v.replace(/^\.$/g, '');
		
		index = v.indexOf('.');
		if(index > 0){
			me.value = v = v.replace(/(.+\..*)(\.)/g, '$1');
		}
		 me.value = v = v.replace(/[^\d|^\.]/g, '');
		 me.value = v = v.replace(/^00/g, '0');
	 
	   me.value = v = v.replace( /([0-9]+.[0-9]{2})[0-9]*/ ,"$1"); 
		if(v.split('.').length > 2){
			arguments.callee.call(me);
		}
		if(me.value > (maxAllfull*1)){ me.value=(maxAllfull*1)}
			
	};