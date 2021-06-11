package com.example.covidhelpline;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class IncomingCallReceiver extends BroadcastReceiver {
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);

        try{
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();
                //show a dialog for feedback
                //once he submits the feedback upload it to firebase with location and category
            }

            if(state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                Toast.makeText(context, "Call Received", Toast.LENGTH_LONG).show();
                startMainActivity(context, number);
                // showDialog(context);
            }

            if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e){e.printStackTrace();}

    }

    private void startMainActivity(Context context, String phone_number) {
        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.putExtra("phone_number",phone_number);
        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
    }

    private void showDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Was this call useful");
        builder.create().show();
    }
}
