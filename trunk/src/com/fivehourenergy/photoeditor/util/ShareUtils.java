package com.fivehourenergy.photoeditor.util;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class ShareUtils {

	public static void shareImage(Activity activity,String absolutePath) {
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("image/jpeg");
        localIntent.addFlags(524288);
        localIntent.putExtra("android.intent.extra.SUBJECT", "instaDownloader");
        localIntent.putExtra("android.intent.extra.TEXT", "");
            localIntent.putExtra("android.intent.extra.STREAM",
                    Uri.fromFile(new File(absolutePath)));
            activity.startActivity(Intent.createChooser(localIntent, "Options Share"));
    }
}
