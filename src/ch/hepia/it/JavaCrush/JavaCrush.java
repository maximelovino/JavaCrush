package ch.hepia.it.JavaCrush;

import ch.hepia.it.JavaCrush.gui.CrushView;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class JavaCrush {
	public static void main (String[] args) {
		final Random rnd = new Random();
		JFrame frame = new JFrame();
		CrushView view = new CrushView(rnd);
		frame.getContentPane().add(view);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(800,800));
		frame.setVisible(true);
	}
}
