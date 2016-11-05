package com.example.hannessiren.confidexsmartcooler;

import android.nfc.Tag;
import android.nfc.tech.NfcV;
import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;

// <"execute", "publishProgress", "onPostExecute">
public class ReadTag extends AsyncTask<Tag, Void, Void> {

    private TextView temperature;

    public ReadTag(TextView temperature) {
        // Konstruktori, ota tänne vastaan kaikki UI komponentit.
        // Expand as needed
        this.temperature = temperature;
    }

    @Override
    protected Void doInBackground(Tag... tags) {
        // OmaObjekti[]
        for (int i = 0; i < tags.length; i++) {
            NfcV nfcvTag = NfcV.get(tags[i]);
            try {
                nfcvTag.connect();
                // NFC tagille menevä komento
                byte[] cmd = new byte[]{
                        (byte) 0x00, // Flags
                        (byte) 0x2B // Command: Get system information
                };
                // Lähettää viestin ja odottaa vastausta
                byte[] data = nfcvTag.transceive(cmd);
                // Pilkkoo ekan tavun pois
                // data = Arrays.copyOfRange(data, 1, 15);
                // TODO handlaa dataa

            } catch (IOException e) {
                return null;
            } finally {
                try {
                    nfcvTag.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
        // return OmaObjekti;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // Mahdollista manipuloida UI threadia suoraan
        temperature.setText("uliuli");
    }
}
