
package com.consumerkarma.datastructure;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Wrapper around ParseObject for class Item
 * 
 * @author Chris Arriola
 */
public class Item implements Parcelable {
    
    public static final String CLASS_NAME = "Item";
    
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_COMPANY = "company";
    
    public static final String COL_SCORE_ENV = "score_env_impact";
    public static final String COL_SCORE_ANIMAL_RIGHTS = "score_animal_rights";
    public static final String COL_SCORE_HUMAN_RIGHTS = "score_human_rights";
    public static final String COL_SCORE_OTHER = "score_other";
    public static final String COL_SCORE_EXECP_OPP = "score_execp_opp";
    
    private ParseObject mItem;
    
//    private String mTitle;
//    private String mDescription;
    private byte[] mImgBytes;
//    
//    private int mScoreEnvImpact;
//    private int mScoreAnimalRights;
//    private int mScoreHumanRights;
//    private int mScoreOther;
//    private int mScoreExecpOpp;
    
    
    public Item(ParseObject obj) {
        mItem = obj;
    }
    
    public String getItemId() {
        return mItem.getObjectId();
    }
    
    public String getTitle() {
        return mItem.getString(COL_TITLE);
    }
    
    public String getCompany() {
        return mItem.getString(COL_COMPANY);
    }
    
    public String getDescription() {
        return mItem.getString(COL_DESCRIPTION);
    }
    
    public void getImage(final GetDataCallback callback) {
        
        if (mImgBytes == null || mImgBytes.length == 0) {
            ParseFile file = (ParseFile) mItem.get(COL_IMAGE);
            file.getDataInBackground(new GetDataCallback() {
                
                @Override
                public void done(byte[] arg0, ParseException arg1) {
                    mImgBytes = arg0;
                    callback.done(arg0, arg1);
                }
            });
        } else {
            callback.done(mImgBytes, null);
        }
    }
    
    public static List<Item> toItemsList(List<ParseObject> objs) {
        
        List<Item> items = new ArrayList<Item>();
        for (ParseObject obj : objs) {
            items.add(new Item(obj));
        }
        
        return items;
    }

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        
    }

}
