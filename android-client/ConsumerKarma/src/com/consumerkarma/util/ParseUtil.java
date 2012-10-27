package com.consumerkarma.util;

import android.content.Context;

import com.consumerkarma.datastructure.Item;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseQuery;

public class ParseUtil {

    private static final String APPLICATION_ID = "xXnJr64tDsIdqugTpfJxg9nKaUE6oEH6tg3M7j74";
    private static final String CLIENT_KEY = "XkXKXMOnhnh1QEmSg11XWJUGpnZBjEMY3f7oJFxx";
    
    
    public static void init(Context context) {
        Parse.initialize(context, APPLICATION_ID, CLIENT_KEY);
    }
   
    public static void queryItem(Context ctx, String queryString, FindCallback callback) {
        ParseQuery query = new ParseQuery(Item.CLASS_NAME);
        query.whereMatches(Item.COL_TITLE, queryString, "i");
        query.findInBackground(callback);
    }
    
    public static void queryItemById(Context ctx, String id, GetCallback callback) {
        ParseQuery query = new ParseQuery(Item.CLASS_NAME);
        query.getInBackground(id, callback);
    }
    
}
