package assg1_q1_2;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * @author fawad jawaid
 *
 */
public class AudioPlayer implements LineListener {
	/**
	 * this flag indicates whether the playback completes or not.
	 */
	private boolean playCompleted;

	/**
	 * this flag indicates whether the playback is stopped or not.
	 */
	private boolean isStopped;

	private boolean isPaused;

	private Clip audioClip;

	/**
	 * Load audio file before playing back
	 * 
	 * @param audioFilePath
	 *            Path of the audio file.
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	public void load(String audioFilePath)
			throws UnsupportedAudioFileException, IOException,
			LineUnavailableException {
		File audioFile = new File(audioFilePath);

		AudioInputStream audioStream = AudioSystem
				.getAudioInputStream(audioFile);

		AudioFormat format = audioStream.getFormat();

		DataLine.Info info = new DataLine.Info(Clip.class, format);

		audioClip = (Clip) AudioSystem.getLine(info);

		audioClip.addLineListener(this);

		audioClip.open(audioStream);
	}
	
	/**
	 * Play a given audio file.
	 * 
	 * @throws IOException
	 * @throws UnsupportedAudioFileException
	 * @throws LineUnavailableException
	 */
	void play() throws IOException {

		audioClip.start();

		playCompleted = false;
		isStopped = false;

		while (!playCompleted) {
			// wait for the playback completes
			try {
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace(); 
				if (isStopped) {
					audioClip.stop();
					break;
				}
				if (isPaused) {
					audioClip.stop();
				} else {
					System.out.println("!!!!");
					audioClip.start();
				}
			}
		}

		audioClip.close();

	}

	/**
	 * Stop playing back.
	 */
	public void stop() {
		isStopped = true;
	}

	public void pause() {
		isPaused = true;
	}

	public void resume() {
		isPaused = false;
	}

        public void decreaseVolume() {
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
            audioClip.start();
        }
        
        public void increaseVolume() {
            FloatControl gainControl = (FloatControl) audioClip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(+5.0f); // Reduce volume by 10 decibels.
            audioClip.start();
        }

    
        public void slowForward() {
          int playBackSpeed = 1;
        
          int skip = playBackSpeed-1;
          System.out.println("Playback Rate: " + playBackSpeed);
        
          audioClip.loop(2*playBackSpeed);
          audioClip.start();
        }
   
        public void fastForward() {
        
          String[] args = new String[2];
          int playBackSpeed = 2;
        
           int skip = playBackSpeed-1;
           System.out.println("Playback Rate: " + playBackSpeed);
        
           audioClip.loop(2*playBackSpeed);
           audioClip.start();
        }          
          
         
          /**
	 * Listens to the audio line events to know when the playback completes.
	 */
	@Override
	public void update(LineEvent event) {
		LineEvent.Type type = event.getType();
		if (type == LineEvent.Type.STOP) {
			System.out.println("STOP EVENT");
			if (isStopped || !isPaused) {
				playCompleted = true;
			}
		}
	}
	
	public Clip getAudioClip() {
		return audioClip;
	}	
}