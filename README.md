# 🎧 Low-Level Audio Driver Simulator (Java)

A real-time simulation of a low-level audio driver written in Java, designed to mimic how embedded audio systems (e.g., AR/VR headsets) capture, buffer, and play audio with latency tracking and spatial effects.

---

## 🚀 Features

- Real-time microphone audio capture and playback
- Simulated driver buffering using `ArrayBlockingQueue`
- Latency tracking using `System.nanoTime()` (±1 ms precision)
- Basic spatial audio effect (amplitude attenuation)
- Multi-threaded audio pipeline (capture and playback threads)
- JUnit 5 test cases validating:
  - Spatial amplitude reduction
  - Playback latency bounds (9–30 ms)

---

## 🛠️ Technologies Used

| Stack      | Tools/Libraries               |
|------------|-------------------------------|
| Language   | Java                          |
| Audio API  | `javax.sound.sampled`         |
| Concurrency| `ArrayBlockingQueue`, Threads |
| Build Tool | Maven                         |
| Testing    | JUnit 5                       |
| IDE        | VS Code / Eclipse             |

---

## 📂 Project Structure

src/
├── main/
│ └── java/
│ └── audio/
│ ├── LowLevelAudioDriverSimulator.java
├── test/
│ └── java/
│ └── audio/
│ └── LowLevelAudioDriverSimulatorTest.java
pom.xml

---

## 🧪 How to Run

1. **Run the main class** to start real-time audio capture and playback:
   ```bash
   LowLevelAudioDriverSimulator.java
---
Sucessfully running test cases:
![image](https://github.com/user-attachments/assets/83ec836d-d7a5-4e04-966e-75fa95a15bc4)

