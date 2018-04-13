package com.winterframework.firefrog.game.lock.base;

public class Copy {

	static final  Integer[] bsNum={0,1,2,3,4,5,6,7,8,9};
	public static void main(String[] abs){
		String[] bbb="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19".split(",");
		String tt="0:";
		for(String b:bbb){
			tt="private static final String X2_zxHZ_"+b+"=\"";	
			for(Integer i:bsNum){
				for(Integer j:bsNum){
					if(j>i)continue;
					if(i+j==Integer.valueOf(b)){
						tt+=(""+i+j)+" ";
					}
				}
			}
			//System.out.println(tt.substring(0, tt.length()-1)+"\";");
			System.out.println("keyMap.put(\"X2_zxHZ_"+b+"\",X2_zxHZ_"+b+".split(\" \"));");
					
		}
	}
	private static void generat(){
		String[] bbb="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19".split(",");
		String tt="0:";
		for(String b:bbb){
			tt="private static final String X2_Hz_"+b+"=\"";	
			for(Integer i:bsNum){
				for(Integer j:bsNum){
					if(i+j==Integer.valueOf(b)){
						tt+=(""+i+j)+" ";
					}
				}
			}
			System.out.println(tt.substring(0, tt.length()-1)+"\";");
			System.out.println("keyMap.put(\"X2_Hz_"+b+"\",X2_Hz_"+b+".split(\" \"));");
					
		}
	}
	private static void generatKd(){
		String[] bbb="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19".split(",");
		String tt="0:";
		for(String b:bbb){
			tt="private static final String X2_Kd_"+b+"=\"";	
			for(Integer i:bsNum){
				for(Integer j:bsNum){
					if(i-j==Integer.valueOf(b) || j-i==Integer.valueOf(b)){
						tt+=(""+i+j)+" ";
					}
				}
			}
			//System.out.println(tt.substring(0, tt.length()-1)+"\";");
			System.out.println("keyMap.put(\"X2_Kd_"+b+"\",X2_Kd_"+b+".split(\" \"));");
					
		}
	}
	

}
