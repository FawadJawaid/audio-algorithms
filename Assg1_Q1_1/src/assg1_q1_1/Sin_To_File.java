package assg1_q1_1;

import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Sin_To_File implements LineListener
{

   /* this flag indicates whether the playback completes or not. */
   static boolean playCompleted = false; //will be set to true (by 1)Clip event OR by  2)UI thread
  
   //When Clip "Starts", "Stops", "Starts", "Open", "Close", an event is sent to following function
   @Override
   public void update(LineEvent event) 
   {
        LineEvent.Type type = event.getType();
		 System.out.println("Clip Event="+type);  //just for info/debugging
        if (type == LineEvent.Type.START) {
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;  //set the flag, so that the program can terminate while loop
         }
 
    }
    
    //Function to generate a sine wave (number of samples will depend on duration and Sampling rate
    public static byte[] createSinWaveBuffer(int samp_rate, double freq, double volume, int ms /* duration in milliseconds */) 
    {
	   /* Time(seconds) * sampling rate(samples/second) = number of samples */
	   int samples = (int)((ms * samp_rate) / 1000);  
           byte[] output = new byte[samples];   //Generate output will be written in this array
          
           double period = (double)samp_rate / freq;
           for (int i = 0; i < output.length; i++) 
	   {
           double angle = 2.0 * Math.PI * i / period;
           output[i] = (byte)(Math.sin(angle) * volume);  
	    }

       return output;
    }

		
	public static void main(String[] args) throws LineUnavailableException 
	{
            
            //start the program by creating the GUI thread 
            GUI frame = new GUI();
            frame.setVisible(true);
            
                /*AudioFileFormat.Type fileType = null;
		File audioFile = null;
		fileType = AudioFileFormat.Type.WAVE;
                audioFile = new File("sinetone.wav");
		
		
		System.out.println("\nProgram Starts");
                System.out.println("\n          --- Sine Wave Generator ---");
		int SAMPLE_RATE = 44100;
		double FREQ = 0;
		double VOL = 0;  //volume
		int DURATION_MS = 0; //duration in milli-seconds
                int CHANNEL = 0;
	        
                //Taking Input from the user at runtime
                Scanner in = new Scanner(System.in);
 
                System.out.println("Enter Frequency ");
                FREQ = in.nextDouble();
                
                System.out.println("Enter Duration ");
                DURATION_MS = in.nextInt();
                
                System.out.println("Enter Volume ");
                VOL = in.nextDouble();
                
                System.out.println("Enter Channel (1 for Mono, 2 for Stereo) ");
                CHANNEL = in.nextInt();
                
	 	byte [] toneBuffer = createSinWaveBuffer(SAMPLE_RATE,FREQ,VOL,DURATION_MS);
		
		//two possible constructors. We use the one without coding...default coding scheme is PCM
		//final AudioFormat af = new AudioFormat(AudioFormat.Encoding encoding, SAMPLE_RATE, 8, 1, true, true);
		final AudioFormat af = new AudioFormat(SAMPLE_RATE, 8/*size of sample in byte*//*, CHANNEL/*channels*///, true, true);
		
		//Verify if the information is loaded successfully
                /*System.out.println();
		System.out.println("Encoding = "+af.getEncoding());
		System.out.println("Sampling_Rate = "+af.getSampleRate());
		System.out.println("No of Channels = "+af.getChannels());
		System.out.println("Sample Size(bits) = "+af.getSampleSizeInBits());
		int sampleSizeInBytes = af.getSampleSizeInBits()/8;
		int totalSamples = (toneBuffer.length/sampleSizeInBytes); 
		System.out.println("Total Samples = "+totalSamples);
		
		//convert byte[] (with sine tone) to input stream and then to AudioStream
		AudioInputStream audInStr = new AudioInputStream ( new ByteArrayInputStream(toneBuffer) ,af,totalSamples);
                
                
                try{
		AudioSystem.write(
                audInStr,
                fileType,
                audioFile);
		}
		catch(Exception e)
		{
		   System.out.println("Exception = "+e);
		}
                
                //LineUnavailableException is thrown if line not available
		SourceDataLine line = AudioSystem.getSourceDataLine(af); 
                // line.open(af, SAMPLE_RATE);		//lines acquires necessary resources and become operational.
	        line.open(af);	//lines acquires necessary resources and become operational.
                line.start();	//allows a line to engage in I/O
	
		int count = line.write(toneBuffer, 0, toneBuffer.length); //Writes audio data to the mixer via this source data line.
		line.drain();	//Drains queued data from the line by continuing data I/O until the data line's internal buffer has been emptied.
		//Note: line.flush() is used to discard
                line.close();
	
			
		System.out.println("Program Ends"); */
	}
}