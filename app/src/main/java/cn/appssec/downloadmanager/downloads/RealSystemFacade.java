/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.appssec.downloadmanager.downloads;

//import android.app.DownloadManager;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.security.NetworkSecurityPolicy;
import android.support.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.appssec.downloadmanager.DownloadManager;

//import android.security.net.config.ApplicationConfig;

//import cn.appssec.downloadmanager.ArrayUtils;


class RealSystemFacade implements SystemFacade {
    private Context mContext;

    public RealSystemFacade(Context context) {
        mContext = context;
    }

    @Override
    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Network getActiveNetwork(int uid, boolean ignoreBlocked) {
//        return mContext.getSystemService(ConnectivityManager.class)
//                .getActiveNetworkForUid(uid, ignoreBlocked);
        Network network = null;
//        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
//            Class classConnect = Class.forName(connectivityManager.getClass().getName());
//            Method methodGetActiveNetworkForUid = classConnect.getDeclaredMethod("getActiveNetworkForUid", new Class[]{int.class, boolean.class});
//            methodGetActiveNetworkForUid.setAccessible(true);
//            network = (Network) methodGetActiveNetworkForUid.invoke(connectivityManager, uid, ignoreBlocked);
            network = connectivityManager.getActiveNetwork();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        return network;
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public NetworkInfo getNetworkInfo(Network network, int uid, boolean ignoreBlocked) {
        NetworkInfo networkInfo = null;
//        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network1 = connectivityManager.getActiveNetwork();
            networkInfo = connectivityManager.getNetworkInfo(network1);
//            Class classConnect = Class.forName(connectivityManager.getClass().getName());
//            Method methodGetNetworkInfoForUid = classConnect.getDeclaredMethod("getNetworkInfoForUid", new Class[]{Network.class, int.class, boolean.class});
//            methodGetNetworkInfoForUid.setAccessible(true);
//            networkInfo = (NetworkInfo) methodGetNetworkInfoForUid.invoke(connectivityManager, network, uid, ignoreBlocked);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
        return networkInfo;
//        return mContext.getSystemService(ConnectivityManager.class)
//                .getNetworkInfoForUid(network, uid, ignoreBlocked);
    }

    @Override
    public long getMaxBytesOverMobile() {
        final Long value = DownloadManager.getMaxBytesOverMobile(mContext);
        return (value == null) ? Long.MAX_VALUE : value;
    }

    @Override
    public long getRecommendedMaxBytesOverMobile() {
        final Long value = DownloadManager.getRecommendedMaxBytesOverMobile(mContext);
        return (value == null) ? Long.MAX_VALUE : value;
    }

    @Override
    public void sendBroadcast(Intent intent) {
        mContext.sendBroadcast(intent);
    }

    @Override
    public boolean userOwnsPackage(int uid, String packageName) throws NameNotFoundException {
        return mContext.getPackageManager().getApplicationInfo(packageName, 0).uid == uid;
    }

    @Override
    public boolean isCleartextTrafficPermitted(int uid) {
        PackageManager packageManager = mContext.getPackageManager();
        String[] packageNames = packageManager.getPackagesForUid(uid);
        if (packageNames == null || packageNames.length == 0) {
            // Unknown UID -- fail safe: cleartext traffic not permitted
            return false;
        }

        // Cleartext traffic is permitted from the UID if it's permitted for any of the packages
        // belonging to that UID.
        for (String packageName : packageNames) {
            if (isCleartextTrafficPermitted(packageName)) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public SSLContext getSSLContextForPackage(Context context, String packageName)
            throws GeneralSecurityException {
//        ApplicationConfig appConfig;
//        try {
//            appConfig = NetworkSecurityPolicy.getApplicationConfigForPackage(context, packageName);
//        } catch (NameNotFoundException e) {
//            // Unknown package -- fallback to the default SSLContext
//            return SSLContext.getDefault();
//        }
//        SSLContext ctx = SSLContext.getInstance("TLS");
//        ctx.init(null, new TrustManager[] {appConfig.getTrustManager()}, null);
//        return ctx;

        SSLContext ctx = SSLContext.getInstance("TLS");
        NetworkSecurityPolicy networkSecurityPolicy =  NetworkSecurityPolicy.getInstance();

        try {
            Class classNetWorkSecurityPolicy = Class.forName(networkSecurityPolicy.getClass().getName());
            Method methodGetApplicationConfigForPackage = classNetWorkSecurityPolicy.getDeclaredMethod("getApplicationConfigForPackage", new Class[]{Context.class, String.class});
            methodGetApplicationConfigForPackage.setAccessible(true);
            Object objAppconfig = methodGetApplicationConfigForPackage.invoke(networkSecurityPolicy, context, packageName);
            Class classApplicationConfig = Class.forName(objAppconfig.getClass().getName());
            Method methodGetTrustManager = classApplicationConfig.getDeclaredMethod("getTrustManager");
            X509TrustManager x509TrustManager = (X509TrustManager) methodGetTrustManager.invoke(objAppconfig);
            ctx.init(null, new TrustManager[]{x509TrustManager}, null);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return ctx;
    }

    /**
     * Returns whether cleartext network traffic (HTTP) is permitted for the provided package.
     */
    private boolean isCleartextTrafficPermitted(String packageName) {
        PackageManager packageManager = mContext.getPackageManager();
        PackageInfo packageInfo;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            // Unknown package -- fail safe: cleartext traffic not permitted
            return false;
        }
        ApplicationInfo applicationInfo = packageInfo.applicationInfo;
        if (applicationInfo == null) {
            // No app info -- fail safe: cleartext traffic not permitted
            return false;
        }
        return (applicationInfo.flags & ApplicationInfo.FLAG_USES_CLEARTEXT_TRAFFIC) != 0;
    }
}
