/**
 * 
 */
package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import essentials.Essentials;

/**
 * @author Maximilian
 *
 */
public class Compress {

	/**
	 * @throws IOException
	 * 
	 */
	public Compress(File file, int l, int n, String output) throws IOException {
		Properties prop = RepetitionSearch.searchRepetitions(
				Essentials.readFile(file), l, n);
		prop.store(new FileOutputStream(new File(output
				+ "dictionary.properties")), "Dictionary");
		String text = Essentials.readFile(file);
		for (Object ob : prop.keySet()) {
			String replacement = ((char) 170) + prop.getProperty((String) ob)
					+ (char) 170;
			System.out.println(replacement);
			text = text.replace((String) ob, replacement);
			System.out.println((String) ob);
		}
		Essentials.printStringToFile(text, new File(output + "text.txt"));
	}

	public static void decompress(File dictionary, File text, File output)
			throws FileNotFoundException, IOException {

		String t = Essentials.readFile(text);
		Properties prop = new Properties();
		prop.load(new FileInputStream(dictionary));
		for (Object ob : prop.keySet()) {
			String replacement = ((char) 170) + prop.getProperty((String) ob)
					+ (char) 170;
			t = t.replace(replacement, (String) ob);
		}
		Essentials.printStringToFile(t, output);
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		new Compress(new File("C:\\Users\\Maximilian\\Desktop\\zzyzx.png"), 20,
				20, "C:\\Users\\Maximilian\\Desktop\\");
		String p = "C:\\Users\\Maximilian\\Desktop\\";
		decompress(new File(p + "dictionary.properties"), new File(p
				+ "text.txt"), new File(p + "output.png"));

	}
}
