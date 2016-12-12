package ch.hepia.it.JavaCrush.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class Assets {
	private final ArrayList<Icon> icons;
	private final String folderPath;
	private final ImageIcon empty = new ImageIcon();
	private final String name;

	public Assets (String folderPath, String name) {
		this.name = name;
		this.icons = new ArrayList<>();
		this.folderPath = folderPath;
		fillFromFolder();
	}

	private void fillFromFolder () {
		File folder = new File(folderPath);
		for (File f : folder.listFiles()) {
			ImageIcon icn = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			this.icons.add(icn);
		}
	}


	public Icon get (int idx) {
		return idx == -1 ? this.empty : this.icons.get(idx);
	}

	public int size () {
		return icons.size();
	}

	@Override
	public String toString () {
		return name;
	}
}
