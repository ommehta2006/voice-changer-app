package com.voicechanger;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int REQUEST_OVERLAY = 101;

    private RadioGroup voiceTypeGroup;
    private Button startButton;
    private Button stopButton;
    private TextView statusText;

    private String selectedVoice = "normal";
    private boolean isServiceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        voiceTypeGroup = findViewById(R.id.voiceTypeGroup);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        statusText = findViewById(R.id.statusText);

        // Check and request permissions
        checkPermissions();

        // Voice selection
        voiceTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioRobot) {
                selectedVoice = "robot";
            } else if (checkedId == R.id.radioFemale) {
                selectedVoice = "female";
            } else if (checkedId == R.id.radioMale) {
                selectedVoice = "male";
            } else {
                selectedVoice = "normal";
            }
            updateStatus("Selected: " + selectedVoice.toUpperCase());
        });

        // Start button
        startButton.setOnClickListener(v -> {
            if (checkAllPermissions()) {
                startVoiceChanger();
            } else {
                Toast.makeText(this, "Please grant all permissions", Toast.LENGTH_SHORT).show();
                checkPermissions();
            }
        });

        // Stop button
        stopButton.setOnClickListener(v -> stopVoiceChanger());

        updateUI();
    }

    private void checkPermissions() {
        String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        };

        boolean allGranted = true;
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                allGranted = false;
                break;
            }
        }

        if (!allGranted) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSIONS);
        }

        // Check overlay permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_OVERLAY);
        }
    }

    private boolean checkAllPermissions() {
        String[] permissions = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.MODIFY_AUDIO_SETTINGS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        };

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            return false;
        }

        return true;
    }

    private void startVoiceChanger() {
        Intent serviceIntent = new Intent(this, VoiceChangerService.class);
        serviceIntent.putExtra("voice_type", selectedVoice);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent);
        } else {
            startService(serviceIntent);
        }

        isServiceRunning = true;
        updateStatus("Voice Changer ACTIVE - " + selectedVoice.toUpperCase());
        updateUI();

        Toast.makeText(this, "Voice changer started! Make a phone call.", Toast.LENGTH_LONG).show();
    }

    private void stopVoiceChanger() {
        Intent serviceIntent = new Intent(this, VoiceChangerService.class);
        stopService(serviceIntent);

        isServiceRunning = false;
        updateStatus("Voice Changer STOPPED");
        updateUI();

        Toast.makeText(this, "Voice changer stopped", Toast.LENGTH_SHORT).show();
    }

    private void updateStatus(String message) {
        statusText.setText(message);
    }

    private void updateUI() {
        startButton.setEnabled(!isServiceRunning);
        stopButton.setEnabled(isServiceRunning);
        voiceTypeGroup.setEnabled(!isServiceRunning);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSIONS) {
            boolean allGranted = true;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false;
                    break;
                }
            }

            if (allGranted) {
                Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Some permissions denied. App may not work properly.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_OVERLAY) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Overlay permission granted", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
