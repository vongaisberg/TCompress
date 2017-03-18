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
 * 
 * Uses a very simple DNA-repetition-searching algorithm to search for
 * repetitions in files By putting these repetitions in a dictionary and
 * replacing them with shorter placeholders you can make the resulting files
 * smaller It is extremely slow, because the original algorithm searches for all
 * repetitions and not just for most of them like all the other programs It is
 * also not very effective, because I am not even really trying to store
 * everything as small as possible Just see it as an experiment and a proof of
 * concept
 * 
 * 
 * @author <a href="http://grunzwanzling.me">Maximilian von Gaisberg
 *         (Grunzwanzling)</a>
 *
 */
public class Compress {

	/**
	 * 
	 * Compress files
	 * 
	 * @param file
	 *            The file to compress
	 * @param l
	 *            The minimum length of the repetitions
	 * @param n
	 *            The minimum number of repetition
	 * @param output
	 *            The directory to put the compressed files in
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
			String replacement = (char) 170 + prop.getProperty((String) ob) + (char) 170;
			System.out.println(replacement);
			text = text.replace((String) ob, replacement);
			System.out.println((String) ob);
		}
		Essentials.printStringToFile(text, new File(output + "text.txt"));
	}

	/**
	 * Decompress files
	 * 
	 * @param dictionary
	 *            The dictionary file
	 * @param text
	 *            The text file
	 * @param output
	 *            The uncompressed output file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void decompress(File dictionary, File text, File output)
			throws FileNotFoundException, IOException {

		String t = Essentials.readFile(text);
		Properties prop = new Properties();
		prop.load(new FileInputStream(dictionary));
		for (Object ob : prop.keySet())
			t = t.replace((char) 170 + prop.getProperty((String) ob) + (char) 170, (String) ob);
		Essentials.printStringToFile(t, output);
	}

	public static void main(String[] args) throws IOException {
		new Compress(new File("C:\\Users\\Maximilian\\Desktop\\log.txt"), 10,
				10, "C:\\Users\\Maximilian\\Desktop\\");
		String p = "C:\\Users\\Maximilian\\Desktop\\";
//		decompress(new File(p + "dictionary.properties"), new File(p
//				+ "text.txt"), new File(p + "output.png"));

	}
}
