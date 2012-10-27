
package com.consumerkarma;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends SherlockActivity {
    
    private final static String DEBUG_TAG = MainActivity.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            Toast.makeText(this, "Contents: " + scanResult.getContents() +
                    "FormatName: " + scanResult.getFormatName(), Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        Button btnScan = (Button) findViewById(R.id.btn_scan);
        btnScan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getBarcodeScan();
            }
        });
    }

    private void getBarcodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }
}
