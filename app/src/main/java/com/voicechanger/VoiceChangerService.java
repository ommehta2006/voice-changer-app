package com.voicechanger;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class VoiceChangerService extends Service {

    private static final String CHANNEL_ID = "VoiceChangerChannel";
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFFER_SIZE = AudioRecord.getMinBufferSize(
            SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
    );

    private AudioRecord audioRecord;
    private AudioTrack audioTrack;
    private Thread processingThread;
    private boolean isProcessing = false;
    private String voiceType = "normal";

    // Voice effect parameters
    private float pitchShift = 1.0f;
    private float speedFactor = 1.0f;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            voiceType = intent.getStringExtra("voice_type");
            if (voiceType == null) voiceType = "normal";

            // Set voice parameters
            setVoiceParameters(voiceType);

            // Start foreground service
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("Voice Changer Active")
                    .setContentText("Voice: " + voiceType.toUpperCase())
                    .setSmallIcon(android.R.drawable.ic_btn_speak_now)
                    .build();

            startForeground(1, notification);

            // Start voice processing
            startVoiceProcessing();
        }

        return START_STICKY;
    }

    private void setVoiceParameters(String voice) {
        switch (voice) {
            case "robot":
                pitchShift = 0.8f;
                speedFactor = 0.9f;
                break;
            case "female":
                pitchShift = 1.4f;
                speedFactor = 1.1f;
                break;
            case "male":
                pitchShift = 0.7f;
                speedFactor = 0.95f;
                break;
            default:
                pitchShift = 1.0f;
                speedFactor = 1.0f;
                break;
        }
    }

    private void startVoiceProcessing() {
        try {
            // Initialize AudioRecord (microphone input)
            audioRecord = new AudioRecord(
                    MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    BUFFER_SIZE
            );

            // Initialize AudioTrack (speaker output)
            audioTrack = new AudioTrack(
                    AudioManager.STREAM_VOICE_CALL,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    BUFFER_SIZE,
                    AudioTrack.MODE_STREAM
            );

            audioRecord.startRecording();
            audioTrack.play();

            isProcessing = true;

            // Start processing thread
            processingThread = new Thread(() -> {
                short[] buffer = new short[BUFFER_SIZE];
                short[] processedBuffer = new short[BUFFER_SIZE];

                while (isProcessing) {
                    // Read from microphone
                    int readSize = audioRecord.read(buffer, 0, BUFFER_SIZE);

                    if (readSize > 0) {
                        // Apply voice effects
                        applyVoiceEffect(buffer, processedBuffer, readSize);

                        // Write to speaker/call
                        audioTrack.write(processedBuffer, 0, readSize);
                    }
                }
            });

            processingThread.start();

        } catch (Exception e) {
            e.printStackTrace();
            stopSelf();
        }
    }

    private void applyVoiceEffect(short[] input, short[] output, int size) {
        // Apply pitch shift and speed change
        for (int i = 0; i < size; i++) {
            // Simple pitch shift by resampling
            int sourceIndex = (int) (i * pitchShift);

            if (sourceIndex < size) {
                // Apply speed factor and amplitude
                output[i] = (short) (input[sourceIndex] * speedFactor);
            } else {
                output[i] = 0;
            }
        }

        // Apply additional effects based on voice type
        if ("robot".equals(voiceType)) {
            applyRobotEffect(output, size);
        }
    }

    private void applyRobotEffect(short[] buffer, int size) {
        // Add robotic modulation
        for (int i = 0; i < size; i++) {
            if (i % 10 < 5) {
                buffer[i] = (short) (buffer[i] * 0.8);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopVoiceProcessing();
    }

    private void stopVoiceProcessing() {
        isProcessing = false;

        if (processingThread != null) {
            try {
                processingThread.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
            audioRecord = null;
        }

        if (audioTrack != null) {
            audioTrack.stop();
            audioTrack.release();
            audioTrack = null;
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Voice Changer Service",
                    NotificationManager.IMPORTANCE_LOW
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
