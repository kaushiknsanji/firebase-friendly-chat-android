package com.google.firebase.codelab.friendlychat;

import android.content.Intent;

import androidx.activity.ComponentActivity;

/**
 * Utility class for {@link Intent}s used in the app to launch implicit intents.
 *
 * @author Kaushik N Sanji
 */
public class IntentUtility {

    public static void launchGallery(ComponentActivity fromActivity, int requestCode) {
        // Create Intent to open any document
        Intent launchGalleryIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        // Filter results that can be streamed like files (excludes timezones and contacts)
        launchGalleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
        // Filter for Images only
        launchGalleryIntent.setType("image/*");
        // Start the Activity with the request code for results, when there is a
        // Gallery based activity to handle
        if (launchGalleryIntent.resolveActivity(fromActivity.getPackageManager()) != null) {
            fromActivity.startActivityForResult(launchGalleryIntent, requestCode);
        }
    }

}