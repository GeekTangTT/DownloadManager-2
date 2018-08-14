package cn.appssec.downloadmanager.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import cn.appssec.downloadmanager.R;

/**
 * Created by T on 18/8/1.
 */

public class MyProvider {

    private Context context;

    public MyProvider(Context context) {
        this.context = context;
    }

    public void startProvider() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TestContract.TestEntry.COLUMN_NAME, "数据过来了");
        contentValues.put(TestContract.TestEntry._ID, System.currentTimeMillis());
        context.getContentResolver().insert(TestContract.TestEntry.CONTENT_URI, contentValues);

        Cursor cursor = context.getContentResolver().query(TestContract.TestEntry.CONTENT_URI, null, null, null, null);

        try {
            Log.e("ContentProviderTest", "total data number = " + cursor.getCount());
            cursor.moveToFirst();
            Log.e("ContentProviderTest", "total data number = " + cursor.getString(1));
        } finally {
            cursor.close();
        }
    }

}
