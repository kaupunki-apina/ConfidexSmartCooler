package com.example.hannessiren.confidexsmartcooler;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView Temperature;
    TextView InfoText;
    //TextView textViewInfo;
    private NfcAdapter nfcAdapter;


    // Check if NFC is disabled or not supported
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this,
                    "NFC is not supported on this device!",
                    Toast.LENGTH_LONG).show();
            finish();
        } else if (!nfcAdapter.isEnabled()) {
            Toast.makeText(this,
                    "Please enable NFC",
                    Toast.LENGTH_LONG).show();
            finish();
        }
    }


    // Discovering tag
    // Creates a Tag type tag when NFC tag is discovered
    // saves the tag ID to String variable and displays it in a TextView
    @Override
    protected void onResume() {
        super.onResume();

        Temperature = (TextView) findViewById(R.id.temperature);
        InfoText = (TextView) findViewById(R.id.InfoText);
        /**
        new Thread(new Runnable() {
            @Override
            public void run() {
                // EI KOSKETA UI THREADIIN TÄÄLTÄ
            }
        }).run();
        **/

        Intent intent = getIntent();
        String action = intent.getAction();

        if (NfcAdapter.ACTION_TAG_DISCOVERED == action) {
            Toast.makeText(this, "NFC tag discovered", Toast.LENGTH_SHORT).show();
            InfoText.setText("Tag ID:");

            Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            if (tag == null) {
                InfoText.setText("Bad tag");
            } else {
                new ReadTag(Temperature).execute(tag);
                /**
                 String tagInfo = " ";
                 byte[] tagId = tag.getId();
                 //
                 int k;
                 for (k = 0; k < tagId.length; k++) {

                 if(tagId[k] == 0){
                 tagInfo += "00:";
                 }
                 else if(tagId[k]<10 && tagId[k] > 0){
                 tagInfo += "0" + Integer.toHexString(tagId[k] & 0xFF) + ":";
                 }
                 else {
                 tagInfo += Integer.toHexString(tagId[k] & 0xFF) + ":";
                 }
                 }

                 tagInfo = tagInfo.substring(0, (3*tagId.length)) + "\n";
                 Temperature.setText(tagInfo.toUpperCase());
                 }
                 }

                 else {
                 Toast.makeText(this, "Scan an NFC tag", Toast.LENGTH_SHORT).show();
                 }
                 **/
            }
        }
    }
}