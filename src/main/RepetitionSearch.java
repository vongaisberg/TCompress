package main;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import main.Methods;

/**
 * 
 */

/**
 * @author Maximilian
 *
 */
public class RepetitionSearch {
	static String Input;
	static int l;
	static int k;

	public ConcurrentHashMap<String, Integer>[] searchRepetitions(String path,
			int l, int k) throws IOException {
		Input = Methods.loadFile(path);
		int time = (int) System.currentTimeMillis();
		@SuppressWarnings("unchecked")
		ConcurrentHashMap<String, Integer>[] map2 = new ConcurrentHashMap[(Input
				.length() - (l - 1))];
		int length = (Input.length() - (l - 1));
		int count = 0;
		for (int i = 0; i < length; i++) {
			map2[i] = new ConcurrentHashMap<String, Integer>();
			System.out.println("Suche Repetitionen mit Länge " + (i + l)
					+ "...");
			String[] parts = Methods.split(Input, l + i);
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (int e = 0; e < parts.length; e++) {
				if (map.containsKey(parts[e])) {
					map.put(parts[e], map.get(parts[e]) + 1);
				} else {
					map.put(parts[e], 1);
				}
			}
			for (Entry<String, Integer> entry : map.entrySet()) {
				String key = entry.getKey();
				Integer value = entry.getValue();
				if (value >= k) {
					map2[i].put(key, value);
				}

				for (int e = 0; e < i; e++) {
					if (map2[i].size() == 0) {

						break;
					}
					Iterator<?> it = map2[e].entrySet().iterator();
					while (it.hasNext()) {
						@SuppressWarnings("rawtypes")
						Map.Entry pair = (Map.Entry) it.next();
						String key2 = (String) pair.getKey();
						Integer value2 = (Integer) pair.getValue();
						if ((key.contains(key2) && value == value2)
								&& !(key2 == key)) {
							map2[e].remove(key2);
							it.remove();
						}

					}
				}
			}
			count++;
			if (map2[i].size() == 0)
				break;
		}
		System.out
				.println("\n********************\nErgebnisse\n********************\n");

		for (int i = 0; i < count; i++) {
			for (Entry<String, Integer> entry : map2[i].entrySet()) {
				System.out.println(entry.getKey() + " " + entry.getValue());
			}

		}
		System.out.println("\nDie Berechnung dauerte"
				+ ((int) (System.currentTimeMillis() - time)) + "ms");
		return map2;
	}

	public RepetitionSearch() {
		// TODO Auto-generated constructor stub
	}

}
