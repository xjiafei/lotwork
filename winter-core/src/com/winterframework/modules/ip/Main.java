package com.winterframework.modules.ip;

public class Main {
	public static void main(String[] args) {
		IPSeeker seeker = IPSeeker.getInstance();

		System.out.println(seeker.getCountry("127.0.0.1"));
		System.out.println(seeker.getAddress("203.208.27.251"));
		System.out.println(seeker.getArea("203.208.27.251"));
		

		//System.out.println(Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory());
		//Thread.currentThread().setName("ipipipip");

	}
}
