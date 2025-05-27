package audio;
import javax.sound.sampled.*;
import java.util.Arrays;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Logger;
public class LowLevelAudioDriverSimulator {
	private static final Logger logger = Logger.getLogger(LowLevelAudioDriverSimulator.class.getName());
    private static final int BUFFER_SIZE = 1024; // size of audio buffer
    private static final ArrayBlockingQueue<AudioPacket> bufferQueue = new ArrayBlockingQueue<>(10);
    private static final AudioFormat format = new AudioFormat(44100, 16, 1, true, true);

    public static void main(String[] args) throws Exception {
        logger.info("Simulating low-level audio driver with latency tracking and spatial effect...");

        Thread captureThread = new Thread(LowLevelAudioDriverSimulator::captureAudio);
        Thread playbackThread = new Thread(LowLevelAudioDriverSimulator::playbackAudio);

        captureThread.start();
        playbackThread.start();

        captureThread.join();
        playbackThread.join();
    }

    // Captures audio and simulates writing to driver buffer
    private static void captureAudio() {
        try {
            TargetDataLine mic = AudioSystem.getTargetDataLine(format);
            mic.open(format);
            mic.start();

            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                int bytesRead = mic.read(buffer, 0, buffer.length);
                if (bytesRead > 0) {
                    long timestamp = System.nanoTime();
                    bufferQueue.put(new AudioPacket(Arrays.copyOf(buffer, bytesRead), timestamp));
                    logger.info("Captured audio chunk at " + timestamp);
                }
            }
        } catch (Exception e) {
            logger.severe("Error in capture: " + e.getMessage());
        }
    }

    // Simulates playback with latency tracking and a basic stereo pan effect
    private static void playbackAudio() {
        try {
            SourceDataLine speaker = AudioSystem.getSourceDataLine(format);
            speaker.open(format);
            speaker.start();

            while (true) {
                AudioPacket packet = bufferQueue.take();
                Thread.sleep(10); 
                long currentTime = System.nanoTime();
                long latency = (currentTime - packet.timestamp) / 1000000; // in ms
                logger.info("Latency: " + latency + " ms");

                byte[] spatialBuffer = applySpatialEffect(packet.data);
                speaker.write(spatialBuffer, 0, spatialBuffer.length);
            }
        } catch (Exception e) {
            logger.severe("Error in playback: " + e.getMessage());
        }
    }

    // Basic stereo panning effect by modifying sample amplitudes (simulate spatial audio)
    static byte[] applySpatialEffect(byte[] buffer) {
        byte[] spatialBuffer = buffer.clone();
        for (int i = 0; i < spatialBuffer.length - 1; i += 2) {
            int sample = (spatialBuffer[i] << 8) | (spatialBuffer[i + 1] & 0xFF);
            sample = (int) (sample * 0.7); // attenuate for left-only spatial feel
            spatialBuffer[i] = (byte) ((sample >> 8) & 0xFF);
            spatialBuffer[i + 1] = (byte) (sample & 0xFF);
        }
        return spatialBuffer;
    }

    // Helper class to hold audio data with timestamp
    private static class AudioPacket {
        byte[] data;
        long timestamp;

        public AudioPacket(byte[] data, long timestamp) {
            this.data = data;
            this.timestamp = timestamp;
        }
    }

}
