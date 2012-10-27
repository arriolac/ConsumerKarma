package com.consumerkarma;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.consumerkarma.datastructure.Item;
import com.consumerkarma.util.ParseUtil;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

public class ItemDetailsActivity extends FragmentActivity {
    
    public static String EXTRA_ITEM_ID = "item_id";
    
    private String mItemId;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.items_details_activity);
        
        mItemId = getIntent().getExtras().getString(EXTRA_ITEM_ID);
        showDisplayProgress();
        ParseUtil.queryItemById(this, mItemId, new GetCallback() {
            
            @Override
            public void done(ParseObject arg0, ParseException arg1) {
                hideDisplayProgress();
                initProductScreen(new Item(arg0));
            }
        });
    }
    
    private void initProductScreen(Item item) {
        
        // Set header
        final ImageView img = (ImageView) findViewById(R.id.item_img);
        TextView txtTitle = (TextView) findViewById(R.id.item_title);
        TextView txtCompany = (TextView) findViewById(R.id.item_company);
        TextView txtDescription = (TextView) findViewById(R.id.item_description);
        
        item.getImage(new GetDataCallback() {
            
            @Override
            public void done(byte[] arg0, ParseException arg1) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(arg0, 0, arg0.length);
                img.setImageBitmap(bitmap);
            }
        });
        txtTitle.setText(item.getTitle());
        txtCompany.setText(item.getCompany());
        txtDescription.setText(item.getDescription());
    }

    private void showDisplayProgress() {
        findViewById(R.id.root).setVisibility(View.INVISIBLE);
        final ProgressDialogFragment progressFragment = new ProgressDialogFragment();
        progressFragment.setMessage(getText(R.string.please_wait).toString());
        progressFragment.setCancelable(true);
        progressFragment.show(getFragmentManager(), "wait_dialog");
    }

    private void hideDisplayProgress() {
        findViewById(R.id.root).setVisibility(View.VISIBLE);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Fragment progressDialog = getFragmentManager().findFragmentByTag("wait_dialog");
        transaction.remove(progressDialog).commit();
    }
}
