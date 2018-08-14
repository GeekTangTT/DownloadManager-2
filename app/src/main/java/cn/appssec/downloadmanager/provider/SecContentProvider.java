package cn.appssec.downloadmanager.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;

import cn.appssec.downloadmanager.DownloadManager;
import cn.appssec.downloadmanager.DownloadService;

/**
 * Author: liuqiang
 * Date: 2018-08-13
 * Time: 15:44
 * Description:
 */
public class SecContentProvider extends ContentProvider {


    //match
    private static final UriMatcher URI_MATCHER = new UriMatcher(
            UriMatcher.NO_MATCH);
    private static final int DOWNLOAD_INFO = 1;

    static {
        //添加两个URI筛选
        URI_MATCHER.addURI("cn.appssec.downloadmanager.SecContentProvider",
                "download_info", DOWNLOAD_INFO);
    }

    @Override
    public boolean onCreate() {
        Log.d("Q_M", "SecContentProvider---->>onCreate");
        return false;
    }

    public long[] strArrsToLong(String[] arrs) {
        long[] ints = new long[arrs.length];
        for (int i = 0; i < arrs.length; i++) {
            ints[i] = Long.parseLong(arrs[i]);
        }
        return ints;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        Log.d("Q_M", "SecContentProvider---->>查询成功，query");

        long[] ids = strArrsToLong(selectionArgs);

        Log.d("Q_M", "SecContentProvider---->>查询成功，query" + Arrays.toString(ids));
        Log.d("Q_M", "SecContentProvider---->>查询成功，uri = " + uri);


        Cursor cursor = null;
        int flag = URI_MATCHER.match(uri);

        Log.d("Q_M", "SecContentProvider---->>查询成功，flag=" + flag);

        switch (flag) {
            case DOWNLOAD_INFO:
                DownloadManager downloadManager = new DownloadManager(getContext());
                DownloadManager.Query query = new DownloadManager.Query().setFilterById(ids);
                cursor = downloadManager.query(query);
                break;
            default:
                break;
        }
        Log.i("Q_M", "SecContentProvider---->>查询成功，Count=" + cursor.getCount());
//
//
//        DownloadManager downloadManager = new DownloadManager(getContext());
//
//        DownloadManager.Query query = new DownloadManager.Query().setFilterById(ids);
//        cursor = downloadManager.query(query);


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
