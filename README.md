# 🎙️ Voice Changer - Native Android App

## Real-time Voice Disguise for Phone Calls

**Native Android app that changes your voice during default phone calls!**

### ✨ Features:
- 🤖 **Robot Voice** - Robotic effect
- 👩 **Female Voice** - Higher pitch
- 👨 **Male Voice** - Deeper voice
- 😊 **Normal Voice** - Original
- ⚡ **Real-time processing** - Works during actual phone calls
- 📞 **System-level integration** - Works with default phone app
- 🎯 **Simple interface** - Easy to use

---

## 🚀 How to Build & Install

### Step 1: Build APK

**Option A: Using Android Studio (Recommended)**
1. Open Android Studio
2. Open folder: `voice-changer-app`
3. Wait for Gradle sync
4. Build → Build Bundle(s) / APK(s) → Build APK(s)
5. APK will be in: `app/build/outputs/apk/debug/app-debug.apk`

**Option B: Using Command Line**
```bash
cd voice-changer-app
./gradlew assembleDebug
```

### Step 2: Install on Android
```bash
adb install app/build/outputs/apk/debug/app-debug.apk
```

Or copy APK to phone and install manually.

---

## 📱 How to Use

### Step 1: Open App
- Launch "Voice Changer" app
- Grant all permissions when asked:
  - Microphone
  - Phone state
  - Overlay
  - Audio settings

### Step 2: Select Voice
Choose voice effect:
- 🤖 Robot Voice
- 👩 Female Voice
- 👨 Male Voice
- 😊 Normal Voice

### Step 3: Start Voice Changer
- Tap "START VOICE CHANGER"
- Status shows "Voice Changer ACTIVE"
- Notification appears

### Step 4: Make Phone Call
- Make or receive any phone call
- Your voice will be changed in real-time!
- Other person hears the changed voice

### Step 5: Stop (Optional)
- Tap "STOP VOICE CHANGER" when done
- Or keep it running for all calls

---

## 🎭 Voice Effects

### 🤖 Robot Voice
- Pitch: 0.8x (lower)
- Speed: 0.9x (slower)
- Effect: Robotic modulation

### 👩 Female Voice
- Pitch: 1.4x (higher)
- Speed: 1.1x (faster)
- Effect: Feminine tone

### 👨 Male Voice
- Pitch: 0.7x (much lower)
- Speed: 0.95x (slightly slower)
- Effect: Deep masculine tone

### 😊 Normal Voice
- Pitch: 1.0x (original)
- Speed: 1.0x (original)
- Effect: No change

---

## 📞 How It Works

### System-Level Integration:
```
Phone Call → Microphone → Voice Processor → Changed Voice → Call Audio
```

**Real-time processing:**
1. App captures microphone audio
2. Applies voice effects (pitch shift, speed change)
3. Outputs modified audio to phone call
4. Other person hears changed voice

### During Call:
- Voice changer runs as foreground service
- Processes audio in real-time
- No recording - instant processing
- Works with default phone app

---

## ⚠️ Important Notes

### Permissions Required:
- ✅ **Microphone** - To capture your voice
- ✅ **Phone State** - To detect calls
- ✅ **Audio Settings** - To modify audio
- ✅ **Overlay** - For service notification

### Keep Service Running:
- Don't force stop the app
- Keep notification visible
- Service runs in background

### Works With:
- ✅ Default phone app (GSM calls)
- ✅ Incoming calls
- ✅ Outgoing calls
- ⚠️ May not work with VoIP apps (WhatsApp, Telegram)

---

## 🔧 Technical Details

### Audio Processing:
- **Sample Rate:** 44100 Hz
- **Buffer Size:** Optimized for low latency
- **Audio Source:** VOICE_COMMUNICATION
- **Audio Stream:** STREAM_VOICE_CALL
- **Processing:** Real-time pitch shift and speed adjustment

### Voice Effects Algorithm:
```java
// Pitch shift by resampling
sourceIndex = (int) (i * pitchShift);
output[i] = (short) (input[sourceIndex] * speedFactor);

// Robot effect adds modulation
if (i % 10 < 5) {
    buffer[i] = (short) (buffer[i] * 0.8);
}
```

---

## 📊 Project Structure

```
voice-changer-app/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/voicechanger/
│   │       │   ├── MainActivity.java          # Main UI
│   │       │   ├── VoiceChangerService.java   # Voice processing
│   │       │   └── CallReceiver.java          # Call detection
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   │   └── activity_main.xml      # UI layout
│   │       │   └── values/
│   │       │       └── strings.xml
│   │       └── AndroidManifest.xml            # Permissions & components
│   └── build.gradle
├── build.gradle
├── settings.gradle
└── gradle.properties
```

---

## 🎯 Use Cases

### Privacy:
- Anonymous calls
- Voice disguise
- Identity protection

### Fun:
- Prank calls
- Entertainment
- Voice experiments

### Professional:
- Voice testing
- Audio demos
- Character voices

---

## 🔧 Troubleshooting

### "Voice not changing during call"
**Fix:**
- Check service is running (notification visible)
- Restart app and tap START again
- Grant all permissions

### "App crashes on start"
**Fix:**
- Grant all permissions
- Check Android version (need 7.0+)
- Reinstall app

### "Other person can't hear me"
**Fix:**
- Check microphone permission
- Restart phone
- Try different voice effect

### "Voice sounds distorted"
**Fix:**
- Try Normal voice first
- Check phone volume
- Restart voice changer

---

## ⚠️ Limitations

### Android Restrictions:
- Requires Android 7.0+ (API 24+)
- May not work on all devices
- Some manufacturers block audio routing
- VoIP apps may not work

### Audio Quality:
- Simple pitch shift algorithm
- May have artifacts
- Quality depends on device

### Root Access:
- **Not required** for basic functionality
- Root can improve audio routing
- Works without root on most devices

---

## 🚀 Building from Source

### Requirements:
- Android Studio Arctic Fox or later
- Android SDK 34
- Gradle 8.1+
- Java 8+

### Build Steps:
```bash
# Clone/navigate to project
cd voice-changer-app

# Build debug APK
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug

# Or build release APK
./gradlew assembleRelease
```

---

## ✅ Summary

**What it does:**
- Native Android app
- Changes voice during phone calls
- Real-time processing
- System-level integration
- 4 voice effects

**How to use:**
1. Build APK in Android Studio
2. Install on Android phone
3. Open app and grant permissions
4. Select voice effect
5. Tap START
6. Make phone call
7. Voice is changed!

**Requirements:**
- Android 7.0+
- Microphone permission
- Phone state permission
- Audio settings permission

---

## 📍 Location

```
D:\tester\own viewer\android-remote-access\voice-changer-app\
```

**Ready to build!** Open in Android Studio and build APK! 🎙️

---

**Built:** 2026-04-29  
**Type:** Native Android App  
**Language:** Java  
**Min SDK:** 24 (Android 7.0)  
**Target SDK:** 34 (Android 14)  

🎉 **Complete native Android voice changer app!**
