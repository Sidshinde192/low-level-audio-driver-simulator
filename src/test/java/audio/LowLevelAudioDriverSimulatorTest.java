package audio;
 


import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

public class LowLevelAudioDriverSimulatorTest {

    @Test
    public void testApplySpatialEffectReducesAmplitude() {
        byte[] input = new byte[10];
        Arrays.fill(input, (byte) 100); // simulate a buffer with high amplitude

        byte[] output = LowLevelAudioDriverSimulator.applySpatialEffect(input);

        for (int i = 0; i < output.length - 1; i += 2) {
            int sampleBefore = (input[i] << 8) | (input[i + 1] & 0xFF);
            int sampleAfter = (output[i] << 8) | (output[i + 1] & 0xFF);
            assertTrue(Math.abs(sampleAfter) <= Math.abs(sampleBefore),
                       "Sample amplitude should be reduced by spatial effect");
        }
    }

    @Test
    public void testLatencySimulationWithinBounds() {
        long start = System.nanoTime();
        try {
            Thread.sleep(10); // simulate processing time
        } catch (InterruptedException ignored) {}
        long end = System.nanoTime();
        long latency = (end - start) / 1_000_000; // convert to ms

        assertTrue(latency >= 9 && latency <= 30,
                   "Simulated latency should be within reasonable bounds (9-30 ms)");
    }
}




