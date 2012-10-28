
package com.consumerkarma;

import java.util.List;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

import com.consumerkarma.datastructure.Item;
import com.consumerkarma.util.ParseUtil;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class MainActivity extends FragmentActivity implements OnItemClickListener {

    private final static String DEBUG_TAG = MainActivity.class.getName();

    private SearchView mSearchView;
    private ListView mListView;
    private TextView mTxtEmpty;

    private List<Item> mItems;
    private ItemsAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUtil.init(this);

        initViews();
    }

    private void initViews() {
        mListView = (ListView) findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        
        mTxtEmpty = (TextView) findViewById(R.id.empty_text);

        showEmptyText();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_speech_to_text:
                // KIM: add code here
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null && resultCode == RESULT_OK) {
            // TODO
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
