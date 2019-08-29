package assg1_q1_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author fawad
 */
public class AudioCapture {

 //to be shared between multiple classes (outer and inner)
  static AudioFormat audioFormat;
  static TargetDataLine targetDataLine;
  
  public AudioCapture() throws Exception
  {
      
  }
  
   public static void main( String args[]) throws Exception
   {
      //new AudioCapture();
      //start the program by creating the GUI thread 
      GUI frame = new GUI();
      frame.setVisible(true);
      
   }//end main

   
    //This method captures audio input from a
  // microphone and saves it in an audio file.
  public void captureAudio(){
	System.out.println("CaptureAudio() called");
	
    try{
      //Get things set up for capture
      audioFormat = getAudioFormat(); //default format (defined in the function below "getAudioFormat")
	  
	  //Verify if the information is loaded successfully
		System.out.println("Encoding="+audioFormat.getEncoding());
		System.out.println("Sampling_Rate="+audioFormat.getSampleRate());
		System.out.println("No of Channels="+audioFormat.getChannels());
		System.out.println("Sample Size(bits)="+audioFormat.getSampleSizeInBits());
		
      DataLine.Info dataLineInfo =
                          new DataLine.Info(
                            TargetDataLine.class,
                            audioFormat);
      targetDataLine = (TargetDataLine)
               AudioSystem.getLine(dataLineInfo);

      //Create a thread to capture the microphone  data into an audio file and start the thread running.  It will run until the
      // Stop option is selected.  This method will return after starting the thread.
     new CaptureThread().start();
		  
    }catch (Exception e) {
      e.printStackTrace();
      System.exit(0);
    }//end catch
  }//end captureAudio method

  
  public void stopCaptureAudio(){
      targetDataLine.stop();
      targetDataLine.close();
  }
  
  //This method creates and returns an  AudioFormat object for a given set of format parameters.  
  //If these parameters don't work  well for you, try some of the other allowable parameter values, which are shown
  // in comments following the declarations.
  private AudioFormat getAudioFormat(){
    float sampleRate = 44100.0F;
    //8000,11025,16000,22050,44100
    int sampleSizeInBits = 16;
    //8,16
    int channels = 1;
    //1,2
    boolean signed = true;
    //true,false
    boolean bigEndian = false;
    //true,false
    return new AudioFormat(sampleRate,
                           sampleSizeInBits,
                           channels,
                           signed,
                           bigEndian);
  }//end getAudioFormat
//=============================================//
   
   
  
  //Inner class to capture data from microphone and write it to an output audio file.
class CaptureThread extends Thread{
  public void run(){
    
	System.out.println("\nCapturing thread STARTS..."); 
  
    AudioFileFormat.Type fileType = null;
    File audioFile = null;
	
	fileType = AudioFileFormat.Type.WAVE;
    audioFile = new File("MyVoice.wav");

    try{
      targetDataLine.open(audioFormat);
      targetDataLine.start();
      AudioSystem.write(
            new AudioInputStream(targetDataLine),
            fileType,
            audioFile);
	  System.out.println("\nCapturing thread ENDS...");
	  
    }catch (Exception e){
      e.printStackTrace();
    }//end catch

  }//end run
}//end inner class CaptureThread
  
}
