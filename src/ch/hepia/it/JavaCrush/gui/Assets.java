package ch.hepia.it.JavaCrush.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Class to represent an icon pack
 */
public class Assets {
	private final ArrayList<Icon> icons;
	private final String folderPath;
	private static final ImageIcon empty = new ImageIcon();
	private final String name;

	/**
	 * Main constructor for the icon pack
	 * @param folderPath	The folder path where the icons are
	 * @param name			The name of the icon pack
	 */
	public Assets (String folderPath, String name) {
		this.name = name;
		this.icons = new ArrayList<>();
		this.folderPath = folderPath;
		fillFromFolder();
	}

	/**
	 * Method that fills the List of Icons with Icons generated from the files in the folder
	 */
	private void fillFromFolder () {
		File folder = new File(folderPath);
		for (File f : folder.listFiles()) {
			ImageIcon icn = new ImageIcon(new ImageIcon(f.getPath()).getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
			this.icons.add(icn);
		}
	}

	/**
	 * Method to get the icon for a specific value
	 * @param value	The value of the icon we want to retrieve
	 * @return		The Icon
	 */
	public Icon get (int value) {
		return value == -1 ? empty : this.icons.get(value);
	}

	/**
	 * @return	The number of Icons
	 */
	public int size () {
		return icons.size();
	}

	/**
	 * @return	The string representation of the icon pack (just its name)
	 */
	@Override
	public String toString () {
		return name;
	}
}
