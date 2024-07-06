package mcg;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class HighScoreManager {
	private static final String HIGH_SCORES_FILE = "src/scores.txt";

	public void saveHighScore(String username, int score) {
		HashMap<String, Integer> highScores = readHighScores();
		highScores.put(username, Math.max(highScores.getOrDefault(username, 0), score));

		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(HIGH_SCORES_FILE))) {
			highScores.entrySet().stream()
					.sorted(HashMap.Entry.<String, Integer>comparingByValue().reversed())
					.limit(10)
					.forEach(entry -> {
						try {
							writer.write(entry.getKey() + ":" + entry.getValue());
							writer.newLine();
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public HashMap<String, Integer> readHighScores() {
		HashMap<String, Integer> highScores = new HashMap<>();
		try {
			List<String> lines = Files.readAllLines(Paths.get(HIGH_SCORES_FILE));
			for (String line : lines) {
				String[] parts = line.split(":");
				if (parts.length == 2) {
					highScores.put(parts[0], Integer.parseInt(parts[1]));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return highScores;
	}
}
