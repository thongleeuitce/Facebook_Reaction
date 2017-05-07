package com.example.thongle.facebook_reaction;

import android.content.res.Resources;

/**
 * Created by thongle on 05/05/2017.
 */

public class ConvertDp2Px {
    public static int dp_to_px(int dp){
        return (int)(dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
