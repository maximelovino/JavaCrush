package ch.hepia.it.JavaCrush.tests;

import ch.hepia.it.JavaCrush.gui.Assets;

public class AssetsTest {
	public static void main (String[] args) {
		Assets a = new Assets("assets", "Basic");
		System.out.println(a.size());
	}

}
