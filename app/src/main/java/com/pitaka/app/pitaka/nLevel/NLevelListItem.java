package com.pitaka.app.pitaka.nLevel;

/**
 * Created by sadra on 7/29/17.
 */


import android.view.View;

public interface NLevelListItem {

    public boolean isExpanded();
    public void toggle();
    public NLevelListItem getParent();
    public View getView();
}