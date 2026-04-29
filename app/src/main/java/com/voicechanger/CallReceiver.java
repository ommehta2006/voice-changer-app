package com.voicechanger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null) {
            if (action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)) {
                String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

                if (state != null) {
                    if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                        // Incoming call ringing
                        Toast.makeText(context, "Incoming call - Voice changer ready", Toast.LENGTH_SHORT).show();
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                        // Call answered (incoming or outgoing)
                        Toast.makeText(context, "Call active - Voice changing...", Toast.LENGTH_SHORT).show();
                    } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                        // Call ended
                        Toast.makeText(context, "Call ended", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (action.equals(Intent.ACTION_NEW_OUTGOING_CALL)) {
                // Outgoing call
                Toast.makeText(context, "Outgoing call - Voice changer ready", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
