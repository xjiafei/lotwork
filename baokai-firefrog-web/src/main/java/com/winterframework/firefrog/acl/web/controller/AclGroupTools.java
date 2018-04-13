package com.winterframework.firefrog.acl.web.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winterframework.firefrog.acl.web.dto.AclGroupStruc;

public class AclGroupTools {

	protected static Map<Long, AclGroupStruc> toOneGroup(List<AclGroupStruc> str) {
		Map<Long, AclGroupStruc> acl = new HashMap<Long, AclGroupStruc>();
		Collections.sort(str, new Comparator<AclGroupStruc>() {
			@Override
			public int compare(AclGroupStruc fruite1, AclGroupStruc fruite2) {
				return fruite1.getLvl().intValue() - fruite2.getLvl().intValue();
			}
		});

		for (AclGroupStruc s : str) {
			acl.put(s.getId(), s);
			AclGroupStruc strc = acl.get(s.getPid());
			if (strc != null) {
				if (strc.getSubAclGroups() == null) {
					strc.setSubAclGroups(new ArrayList());
				}
				strc.getSubAclGroups().add(s);
			}

		}

		return acl;
	}

	public static void getSun(AclGroupStruc str,List<AclGroupStruc> list){
		if(str.getSubAclGroups()!=null)
		for(AclGroupStruc st:str.getSubAclGroups()){
			list.add(st);
			getSun(st,list);
		}
	}
	public static Integer getTotal(AclGroupStruc str){
		int i=0;
		List<AclGroupStruc> list=str.getSubAclGroups();
		for(AclGroupStruc s:list){
			i+=getTotal(s); 
		};
		return i;
	}
	
	/*public static void main(String[] args) {
		String str="";
		str+=" [ {                                ";
		str+="      \"id\" : 66,                                  ";
		str+="      \"pid\" : -1,                                 ";
		str+="      \"name\" : \"顶级组\",                        ";
		str+="      \"lvl\" : 1,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1382435194585,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1382435194585,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 111,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"HRD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1403702120898,               ";
		str+="      \"modifierer\" : \"cancus\",                  ";
		str+="      \"gmtModified\" : 1406274030036,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 132,                                 ";
		str+="      \"pid\" : 111,                                ";
		str+="      \"name\" : \"LRD\",                           ";
		str+="      \"lvl\" : 3,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1403749972432,               ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268443425,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 142,                                 ";
		str+="      \"pid\" : 111,                                ";
		str+="      \"name\" : \"123123\",                        ";
		str+="      \"lvl\" : 3,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1406533863833,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1406533863833,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 127,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"PMD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1403748966218,               ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268468647,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 135,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"OPD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1404293945197,               ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268491281,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 136,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"PSD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1404374349220,               ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268514228,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 137,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"CEO\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1404876829567,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1404876829567,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 138,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"测试用\",                        ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1405060843182,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1405060843182,              ";
		str+="      \"inUser\" : 0,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 139,                                 ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"ADM\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1405588628142,               ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268598573,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 37000113,                            ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"ITD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1399446950696,               ";
		str+="      \"modifierer\" : \"cancus\",                  ";
		str+="      \"gmtModified\" : 1406513294263,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 140,                                 ";
		str+="      \"pid\" : 37000113,                           ";
		str+="      \"name\" : \"ARD\",                           ";
		str+="      \"lvl\" : 3,                                  ";
		str+="      \"creatorer\" : \"cleo2\",                    ";
		str+="      \"gmtCreated\" : 1406268986013,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1406268986013,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 141,                                 ";
		str+="      \"pid\" : 140,                                ";
		str+="      \"name\" : \"新平台测试组\",                  ";
		str+="      \"lvl\" : 4,                                  ";
		str+="      \"creatorer\" : \"cleo2\",                    ";
		str+="      \"gmtModified\" : 1406269045648,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 143,                                 ";
		str+="      \"pid\" : 37000113,                           ";
		str+="      \"name\" : \"ARD2\",                          ";
		str+="      \"lvl\" : 3,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1406533965699,               ";
		str+="      \"modifierer\" : null,                        ";
		str+="      \"gmtModified\" : 1406533965699,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 40000113,                            ";
		str+="      \"pid\" : 66,                                 ";
		str+="      \"name\" : \"FSD\",                           ";
		str+="      \"lvl\" : 2,                                  ";
		str+="      \"modifierer\" : \"cleo2\",                   ";
		str+="      \"gmtModified\" : 1406268574753,              ";
		str+="      \"inUser\" : 1,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    }, {                                            ";
		str+="      \"id\" : 117,                                 ";
		str+="      \"pid\" : 40000113,                           ";
		str+="      \"name\" : \"阿斯蒂芬\",                      ";
		str+="      \"lvl\" : 3,                                  ";
		str+="      \"creatorer\" : \"cancus\",                   ";
		str+="      \"gmtCreated\" : 1403703633362,               ";
		str+="      \"modifierer\" : \"cancus\",                  ";
		str+="      \"gmtModified\" : 1404267180614,              ";
		str+="      \"inUser\" : 0,                               ";
		str+="      \"subAclGroups\" : null                       ";
		str+="    } ]";
		List<AclGroupStruc> strc=JsonMapper.nonAlwaysMapper().fromJson(str,JsonMapper.nonAlwaysMapper().createCollectionType(List.class,AclGroupStruc.class));
		AclGroupStruc A=AclGroupTools.toOneGroup(strc).get(66L);
		System.out.println(A.getBox());
	}*/
}
