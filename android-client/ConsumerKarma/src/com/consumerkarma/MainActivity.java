
package com.consumerkarma;

import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.consumerkarma.TTS.SimpleSpeechActivityDemo;
import com.consumerkarma.TTS.SpeechAuth;
import com.consumerkarma.TTS.SpeechConfig;
import com.consumerkarma.datastructure.Item;
import com.consumerkarma.util.ParseUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class MainActivity extends FragmentActivity implements OnItemClickListener {
    /**
     * The request code is required to use the startActivityForResult method. It
     * allows you to identify multiple requests from activities when they finish
     * their work. In this example, we only ever have a single child activity
     * active, which we identify by an arbitrary constant.
     **/
    private static final int SPEECH_REQUEST_CODE = 42;

    private final static String DEBUG_TAG = MainActivity.class.getName();

    private SearchView mSearchView;
    private ListView mListView;
    private TextView mTxtEmpty;

    private List<Item> mItems;
    private ItemsAdapter mAdapter;
    private String oauthToken = null;
    private MenuItem tts;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        validateOAuth();
        ParseUtil.init(this);

        initViews();
    }
    /**
     * Handle the result of an asynchronous OAuth check.
    **/
    private class OAuthResponseListener implements SpeechAuth.Client {
        public void 
        handleResponse(String token, Exception error)
        {
            if (token != null) {
                oauthToken = token;
                tts.setVisible(true);
                tts.setEnabled(true);
            }
            else {
                Log.v("SimpleSpeech", "OAuth error: "+error);
                // There was either a network error or authentication error.
                // Show alert for the latter.
                alert("Speech Unavailable", 
                    "This app was rejected by the speech service.  Contact the developer for an update.");
            }
        }
    }
    private void alert(String header, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
            .setTitle(header)
            .setCancelable(true)
            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }
    private void validateOAuth() {
        SpeechAuth auth = SpeechAuth.forService(SpeechConfig.oauthUrl(), 
                SpeechConfig.oauthKey(), SpeechConfig.oauthSecret());
         auth.fetchTo(new OAuthResponseListener());
    }
    

    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);

        mTxtEmpty = (TextView) findViewById(R.id.empty_text);
    }

    private final int TTS_TEXT_RESULT = 125;
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_speech_to_text:
                Intent intent = new Intent(this, SimpleSpeechActivityDemo.class);
                startActivityForResult(intent, TTS_TEXT_RESULT);
                return true;
            case R.id.menu_barcode:
                getBarcodeScan();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        // make non-clickable
        tts = menu.findItem(R.id.menu_speech_to_text);
        tts.setVisible(false);
        tts.setEnabled(false);
        
        // NOTE: only works on API 11
        final MenuItem menuSearch = menu.findItem(R.id.menu_search);

        mSearchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        mSearchView.setQueryHint("Search for a Product.");
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                search(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    private void search(String query) {
        ParseUtil.queryItem(MainActivity.this, query, new FindCallback() {

            @Override
            public void done(List<ParseObject> arg0, ParseException arg1) {
                hideDisplayProgress();
                if (arg0.size() != 0) {
                    refreshList(arg0);
                } else {
                    mTxtEmpty.setText("No Product/s Found.");
                    showEmptyText();
                }
            }
        });
        showDisplayProgress();
    }

    private void refreshList(List<ParseObject> list) {
        mItems = Item.toItemsList(list);
        mAdapter = new ItemsAdapter(this, mItems);
        mListView.setAdapter(mAdapter);
        showList();
    }

    private void showDisplayProgress() {
        InputMethodManager imm = (InputMethodManager) getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mSearchView.getApplicationWindowToken(), 0);

        final ProgressDialogFragment progressFragment = new ProgressDialogFragment();
        progressFragment.setMessage(getText(R.string.please_wait).toString());
        progressFragment.setCancelable(true);
        progressFragment.show(getFragmentManager(), "wait_dialog");
    }

    private void hideDisplayProgress() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment progressDialog = getFragmentManager().findFragmentByTag("wait_dialog");
        transaction.remove(progressDialog).commit();
    }

    private static final String OREO_UPC = "044000072742";

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && resultCode == RESULT_OK) {

            search("oreo");
            // if (OREO_UPC.equals(scanResult.getContents())) {
            // search("oreo");
            // } else if (false) {
            //
            // }

            Log.e("error", scanResult.getContents());
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Item item = (Item) parent.getItemAtPosition(position);
        Intent intent = new Intent(this, ItemDetailsActivity.class);
        intent.putExtra(ItemDetailsActivity.EXTRA_ITEM_ID, item.getItemId());
        startActivity(intent);
    }
}
