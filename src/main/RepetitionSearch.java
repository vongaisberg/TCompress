package main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import essentials.Essentials;

/**
 * 
 */

/**
 * @author Maximilian
 *
 */
public class RepetitionSearch {

	public static Properties searchRepetitions(String data, int l, int k)
			throws IOException {
		int time = (int) System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		ConcurrentHashMap<String, Integer>[] map2 = new ConcurrentHashMap[(data.length() - l + 1)];
		int length = (data.length() - l + 1), count = 0;
		for (int i = 0; i < length; ++i) {
			map2[i] = new ConcurrentHashMap<String, Integer>();
			System.out.println("Suche Repetitionen mit Länge " + (i + l)
					+ "...");
			String[] parts = Methods.split(data, l + i);
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int e = 0; e < parts.length; ++e)
				map.put(parts[e], !map.containsKey(parts[e]) ? 1 : map.get(parts[e]) + 1);
			for (Entry<String, Integer> entry : map.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (value >= k)
					map2[i].put(key, value);

				for (int e = 0; e < i; ++e) {
					if (map2[i].isEmpty())
						break;
					for (Iterator<?> it = map2[e].entrySet().iterator(); it.hasNext();) {
						@SuppressWarnings("rawtypes")
						Map.Entry pair = (Map.Entry) it.next();
						String key2 = (String) pair.getKey();
						if ((key.contains(key2) && value == (Integer) pair.getValue()) && key2 != key) {
							map2[e].remove(key2);
							it.remove();
						}
					}
				}
			}
			++count;
			if (map2[i].isEmpty())
				break;
		}
		System.out
				.println("\n********************\nErgebnisse\n********************\n");
		Properties repetitions = new Properties();
		for (int count2 = 0, i = 0; i < count; ++i)
			for (Entry<String, Integer> entry : map2[i].entrySet()) {
				repetitions.put(entry.getKey(), String.valueOf(count2));
				++count2;
				System.out.println(entry.getKey() + " " + entry.getValue());
			}

		System.out.println("\nDie Berechnung dauerte" + (int) (System.currentTimeMillis() - time) + "ms");
		return repetitions;
	}

	public static void main(String[] args) throws IOException {
		RepetitionSearch
				.searchRepetitions(
						Essentials
								.readFile(new File(
										"C:\\Users\\Maximilian\\Desktop\\Torshammer\\Torshammer 1.0\\torshammer.py")),
						5, 5);
	}

	public RepetitionSearch() {
		// TODO Auto-generated constructor stub
	}

}
