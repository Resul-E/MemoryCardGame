package mcg;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class SoundPlayer implements Runnable{
	
	private String soundPath;
	
	public SoundPlayer(String sp) {
		soundPath = sp;
	}

	@Override
	public void run() {
		try {
			File soundFile = new File(soundPath);
			AudioInputStream soundAudio = AudioSystem.getAudioInputStream(soundFile);
			Clip clip = AudioSystem.getClip();
			
			clip.open(soundAudio);
			clip.start();
			clip.addLineListener(new LineListener() {
				
				@Override
				public void update(LineEvent event) {
					if(event.getType() == LineEvent.Type.STOP) {
						clip.close();
					}
				}
			});
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
