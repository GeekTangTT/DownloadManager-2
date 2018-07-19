package cn.appssec.downloadmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String TAG = "MainActivity";
    private Button mButton;
    private Button mButton2;
    public static final String FILE = "file://";
    private String[] mNeedPermissions = {
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_NETWORK_STATE
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.bt_down);
        mButton.setOnClickListener(this);
        mButton2 = (Button) findViewById(R.id.bt_down2);
        mButton2.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new CheckPermissionThread().start();
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("cn.secspace.test");
        TestBroadReceiver testBroadReceiver = new TestBroadReceiver();
        registerReceiver(testBroadReceiver, intentFilter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.bt_down) {
            Log.d("download manager", " 下载完成");
//            String uristr = "http://118.190.70.77:8085/testdocs/15M.doc";
//            Uri uri = Uri.parse(uristr);
//            String minetype = "application/msword";
//            String path = "/storage/emulated/0/Download/";
//            String fileName = "15M.doc";
////            WebAddress webAddress;
//
//            DownloadManager downloadManager = new DownloadManager(this);
//            DownloadManager.Request request = new DownloadManager.Request(uri);
//            request.setMimeType(minetype);
//            Uri destinationUri = Uri.parse("file:///storage/emulated/0/Download/15M.doc");
//            request.setDestinationUri(destinationUri);
//            request.setVisibleInDownloadsUi(true);
//            request.allowScanningByMediaScanner();
////            request.setDescription(webAddress.getHost());
//            String cookies = CookieManager.getInstance().getCookie(uristr);
//            request.addRequestHeader("Cookie", cookies);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            downloadManager.enqueue(request);
            Intent intent = new Intent();
            intent.setAction(android.app.DownloadManager.ACTION_DOWNLOAD_COMPLETE);
//            intent.setPackage(mPackage);
            intent.putExtra(android.app.DownloadManager.EXTRA_DOWNLOAD_ID, Long.valueOf(233));
            sendBroadcast(intent);


        }
        if (id== R.id.bt_down2) {
            Log.d(TAG, "onClick: 发送广播");
            Intent intent = new Intent();
            intent.setAction("cn.secspace.test");
//            intent.setPackage(mPackage);
            intent.putExtra(android.app.DownloadManager.EXTRA_DOWNLOAD_ID, Long.valueOf(233));
            sendBroadcast(intent);
        }
    }

    private class CheckPermissionThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (true) {
                if (Utils.checkPermissions(MainActivity.this, mNeedPermissions)) {
//                    mHandler.sendEmptyMessage(0);
                    break;
                }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class TestBroadReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onreceive 收到广播");
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            Log.d(TAG, " download id : " + id);
        }
    }
}
