package com.datascientists;

public class Runner {

	public Runner() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		ScreeningItem runner = new ScreeningItem();
		try {
			runner.importScreeningItems();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
