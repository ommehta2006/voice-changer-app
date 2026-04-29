# 📦 How to Build APK Using GitHub Actions (Free)

## What You Need
- GitHub account (free) → https://github.com
- Your project files (already ready!)

---

## ✅ STEP-BY-STEP GUIDE

---

### STEP 1: Create GitHub Account
1. Go to **https://github.com**
2. Click **Sign Up**
3. Enter email, password, username
4. Verify email
5. Done!

---

### STEP 2: Create New Repository
1. Go to **https://github.com/new**
2. Fill in:
   - **Repository name:** `voice-changer-app`
   - **Visibility:** ✅ Public (required for free Actions)
   - Leave everything else default
3. Click **Create repository**

---

### STEP 3: Upload Your Files
You have two options:

#### Option A: Upload via Website (Easiest)
1. On your new repository page, click **"uploading an existing file"**
2. Drag and drop the entire `voice-changer-app` folder contents
3. Click **Commit changes**

#### Option B: Upload via Git (Faster)
```bash
cd "D:\tester\own viewer\android-remote-access\voice-changer-app"

git init
git add .
git commit -m "Voice changer app"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/voice-changer-app.git
git push -u origin main
```
Replace `YOUR_USERNAME` with your GitHub username.

---

### STEP 4: GitHub Auto-Builds APK
After uploading:
1. GitHub detects the workflow file (`.github/workflows/build-apk.yml`)
2. Build starts **automatically** (takes 3-5 minutes)
3. You can watch progress in the **Actions** tab

---

### STEP 5: Download Your APK
1. Go to your repository on GitHub
2. Click the **"Actions"** tab (top menu)
3. Click the latest **"Build APK"** workflow run
4. Scroll down to **"Artifacts"** section
5. Click **"voice-changer-apk"** to download
6. Extract the ZIP → Get your **app-debug.apk**

---

### STEP 6: Install APK on Android
1. Copy `app-debug.apk` to your Android phone
2. Open file manager on phone
3. Tap the APK file
4. If asked, enable **"Install from Unknown Sources"**
5. Tap **Install**
6. Open **"Voice Changer"** app!

---

## 📁 What Files to Upload

Upload everything inside `voice-changer-app` folder:
```
voice-changer-app/
├── .github/
│   └── workflows/
│       └── build-apk.yml       ← GitHub Actions config
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/voicechanger/
│   │       │   ├── MainActivity.java
│   │       │   ├── VoiceChangerService.java
│   │       │   └── CallReceiver.java
│   │       ├── res/
│   │       │   ├── layout/activity_main.xml
│   │       │   └── values/strings.xml
│   │       └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── gradle/
│   └── wrapper/
│       └── gradle-wrapper.properties
├── build.gradle
├── settings.gradle
├── gradle.properties
├── gradlew
└── gradlew.bat
```

---

## ⏱️ Build Time
- GitHub Actions takes **3-5 minutes** to build
- You'll get a notification when done

---

## 🔄 How to Rebuild (After Code Changes)
1. Update files on GitHub
2. GitHub auto-builds new APK
3. Download from Actions tab

---

## ❓ Troubleshooting

### Build Failed?
1. Click on the failed workflow run
2. Click on the failed step
3. Read the error message
4. Most common fix: Check all files are uploaded correctly

### Can't Find APK Download?
1. Go to Actions tab
2. Click on latest green ✅ run
3. Scroll down to bottom of page
4. Look for "Artifacts" section
5. Download "voice-changer-apk"

### APK Won't Install?
1. Enable "Unknown Sources" in Android Settings
2. Settings → Security → Unknown Sources → ON
3. Try installing again

---

## 🎉 Done!

Once installed, open the app:
1. Grant permissions (Microphone, Phone)
2. Select voice (🤖 Robot / 👩 Female / 👨 Male)
3. Tap **START**
4. Make a phone call
5. Your voice is changed in real-time!
