package mcg;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class BackgroundMusicPlayer implements Runnable{

	private String musicFilePath;
	Clip clip;
	
	public BackgroundMusicPlayer(String mfp) {
		musicFilePath = mfp;
	}
	@Override
	public void run() {
		try {
			File musicFile = new File(musicFilePath);
			AudioInputStream musicAudio = AudioSystem.getAudioInputStream(musicFile);
			clip = AudioSystem.getClip();
			
			clip.open(musicAudio);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		if (clip != null && clip.isRunning()) {
			clip.stop();
			clip.close();
		}
	}

}
