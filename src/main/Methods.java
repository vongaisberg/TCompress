package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Methods {
	public static String loadFile(String file) {
		System.out.println("Lade...");
		String Input = "";
		FileReader fr;
		try {
			fr = new FileReader(file);

			BufferedReader br = new BufferedReader(fr);
			for (String line = br.readLine(); line != null;)
				Input += line;
			br.close();

			System.out.println("Loading completed");

		} catch (FileNotFoundException e) {
			System.out.println("Eingabedatei wurde nicht gefunden!");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Input;
	}

	public static String[] split(String Input, int l) {
		int length = Input.length() - l + 1;
		String[] part = new String[length];
		for (int i = 0; i < length; ++i)
			part[i] = Input.substring(i, i + l);
		return (part);
	}
}
