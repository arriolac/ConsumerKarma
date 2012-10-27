
package com.consumerkarma;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.consumerkarma.util.ParseUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class MainActivity extends FragmentActivity {

    private final static String DEBUG_TAG = MainActivity.class.getName();

    private SearchView mSearchView;
    private Button mBtnScan;
    private ListView mListView;
    private TextView mTxtEmpty;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUtil.init(this);

        initViews();
    }

    private void initViews() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                getBarcodeScan();
            }
        });

        mListView = (ListView) findViewById(R.id.list_view);
        mTxtEmpty = (TextView) findViewById(R.id.empty_text);

        showEmptyText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);

        // NOTE: only works on API 11
        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setQueryHint("Search for a Product.");
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                ParseUtil.queryItem(MainActivity.this, query, new FindCallback() {

                    @Override
                    public void done(List<ParseObject> arg0, ParseException arg1) {
                        hideDisplayProgress();
                        if (arg0.size() != 0) {
                            Toast.makeText(MainActivity.this, arg0.get(0).getString("title"), 
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Not found",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });
                
                mSearchView.setIconified(true);
                showDisplayProgress();
                
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    
    private void showDisplayProgress() {
        // TODO
    }

    private void hideDisplayProgress() {
        // TODO
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && resultCode == RESULT_OK) {
            Toast.makeText(this, "Contents: " + scanResult.getContents() +
                    "FormatName: " + scanResult.getFormatName(), Toast.LENGTH_LONG).show();
        }
    }

    private void getBarcodeScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.initiateScan();
    }

    private void showEmptyText() {
        mTxtEmpty.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
    }

    private void setEmptyText(String txt) {
        mTxtEmpty.setText(txt);
    }

    private void showList() {
        mTxtEmpty.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);
    }
}
