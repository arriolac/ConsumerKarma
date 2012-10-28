package com.consumerkarma;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;
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
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
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
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
        
        // Set ConsumerKarma Scores
        TextView hrCount = (TextView) findViewById(R.id.hr_count);
        TextView polCount = (TextView) findViewById(R.id.pol_count);
        TextView execCount = (TextView) findViewById(R.id.exec_count);
        TextView animalCount = (TextView) findViewById(R.id.animal_count);
        TextView envCount = (TextView) findViewById(R.id.env_count);
        TextView otherCount = (TextView) findViewById(R.id.other_count);
        
        int ihrCount = item.getHrCount();
        int ipolCount = item.getHrCount();
        int iexecCount = item.getHrCount();
        int ianimalCount = item.getHrCount();
        int ienvCount = item.getHrCount();
        int iotherCount = item.getHrCount();
        
        hrCount.setText("" + item.getHrCount());
        polCount.setText("" + item.getPolCount());
        execCount.setText("" + item.getExecCount());
        animalCount.setText("" + item.getAnimalCount());
        envCount.setText("" + item.getEnvCount());
        otherCount.setText("" + item.getOtherCount());
        
        // Set BG
        ImageView hrBg = (ImageView) findViewById(R.id.hr_bg);
        ImageView polBg = (ImageView) findViewById(R.id.pol_bg);
        ImageView execBg = (ImageView) findViewById(R.id.exec_bg);
        ImageView animalBg = (ImageView) findViewById(R.id.animal_bg);
        ImageView envBg = (ImageView) findViewById(R.id.env_bg);
        ImageView otherBg = (ImageView) findViewById(R.id.other_bg);
        
        setDrawableBg(hrBg, ihrCount);
        setDrawableBg(polBg, ipolCount);
        setDrawableBg(execBg, iexecCount);
        setDrawableBg(animalBg, ianimalCount);
        setDrawableBg(envBg, ienvCount);
        setDrawableBg(otherBg, iotherCount);
            
    }
    
    private void setDrawableBg(ImageView view, int count) {
        if (count >= 7) {
            view.setImageDrawable(getResources().getDrawable(R.drawable.green_circle));
        } else if (count < 7 && count >= 4) {
            view.setImageDrawable(getResources().getDrawable(R.drawable.yellow_circle));
        } else {
            view.setImageDrawable(getResources().getDrawable(R.drawable.red_circle));
        }
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
