
package com.consumerkarma.datastructure;

import java.util.ArrayList;
import java.util.List;

import com.parse.ParseObject;

/**
 * Wrapper around ParseObject for class Item
 * 
 * @author Chris Arriola
 */
public class Item {
    
    public static final String CLASS_NAME = "Item";
    
    public static final String COL_TITLE = "title";
    public static final String COL_DESCRIPTIOn = "description";
    public static final String COL_IMAGE = "image";
    public static final String COL_SCORE_ENV = "score_env_impact";
    public static final String COL_SCORE_ANIMAL_RIGHTS = "score_animal_rights";
    public static final String COL_SCORE_HUMAN_RIGHTS = "score_human_rights";
    public static final String COL_SCORE_OTHER = "score_other";
    public static final String COL_SCORE_EXECP_OPP = "score_execp_opp";
    
    private ParseObject mItem;
    
    public Item(ParseObject obj) {
        mItem = obj;
    }
    
    public static List<Item> toItemsList(List<ParseObject> objs) {
        
        List<Item> items = new ArrayList<Item>();
        for (ParseObject obj : objs) {
            items.add(new Item(obj));
        }
        
        return items;
    }

}
