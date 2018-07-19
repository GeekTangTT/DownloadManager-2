package cn.appssec.downloadmanager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static final int REQUEST_GRANT_PERMISSION = 0x01;

    public static String getDevType(Context context) {
        if ((context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "pad";
        } else {
            return "phone";
        }
    }

    public static boolean hasInternet(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    @SuppressLint("HardwareIds")
    public static String getDevID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean checkPermissions(Activity context, String... permissions) {
        List<String> needRequestPermissionList = findDeniedPermissions(context, permissions);
        if (null != needRequestPermissionList
                && needRequestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(
                    context,
                    needRequestPermissionList.toArray(new String[needRequestPermissionList.size()]),
                    REQUEST_GRANT_PERMISSION);
            return false;
        }

        return true;
    }
    private static List<String> findDeniedPermissions(Activity context, String[] permissions) {
        List<String> needRequestPermissionList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(context,
                    perm) != PackageManager.PERMISSION_GRANTED) {
                needRequestPermissionList.add(perm);
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        context, perm)) {
                    needRequestPermissionList.add(perm);
                }
            }
        }
        return needRequestPermissionList;
    }
}
