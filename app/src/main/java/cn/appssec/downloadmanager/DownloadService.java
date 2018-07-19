package cn.appssec.downloadmanager;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

public class DownloadService extends Service {
    public static final String TAG = "DownloadService";
    public static final String ACTION = "cn.appssec.downloadmanager.DOWNLOAD";

    public DownloadService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, " DownloadService onCreate()");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, " DownloadService onStartCommand");
        String action = intent.getAction();
//        if (ACTION.equals(action)) {
//            Log.d(TAG, " 准备下载");
//            String uri = intent.getStringExtra("uri");
//            String destination = intent.getStringExtra("destinationUri");
//            String mimeType = intent.getStringExtra("mimeType");
//
//            Log.d(TAG, " uri : " + uri + "      destination : " + destination + "       mimetype : " + mimeType);
//
//            DownloadManager downloadManager = new DownloadManager(this);
//            DownloadManager.Request request = new DownloadManager.Request(uri);
//            request.setMimeType(mimeType);
//            Uri destinationUri = Uri.parse(destination);
//            request.setDestinationUri(destinationUri);
//            request.setVisibleInDownloadsUi(true);
//            request.allowScanningByMediaScanner();
////            request.setDescription(webAddress.getHost());
//            String cookies = CookieManager.getInstance().getCookie(uri);
//            request.addRequestHeader("Cookie", cookies);
//            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            downloadManager.enqueue(request);
//        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
        return mStub;
    }

    private final IDownloadService.Stub mStub = new IDownloadService.Stub() {
        @Override
        public long enqueue(AidlRequest request) throws RemoteException {
            String uri = request.getUri();
            String destination = request.getDestinationUri();
            String mimeType = request.getMimeType();
            Log.d(TAG, " uri : " + uri + "      destination : " + destination + "       mimetype : " + mimeType);
            DownloadManager downloadManager = new DownloadManager(DownloadService.this);
            DownloadManager.Request requestTrue = new DownloadManager.Request(uri);

            if (!TextUtils.isEmpty(mimeType)) {
                requestTrue.setMimeType(mimeType);
            }
            if (!TextUtils.isEmpty(destination)) {
                Uri destinationUri = Uri.parse(destination);
                requestTrue.setDestinationUri(destinationUri);
            }
            requestTrue.setVisibleInDownloadsUi(true);
            requestTrue.allowScanningByMediaScanner();
//            request.setDescription(webAddress.getHost());
            String cookies = CookieManager.getInstance().getCookie(uri);
            requestTrue.addRequestHeader("Cookie", cookies);
            requestTrue.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            return downloadManager.enqueue(requestTrue);
        }

        @Override
        public Uri getDownloadUri(long id) throws RemoteException {
            Log.d(TAG, " get download uri");
            DownloadManager downloadManager = new DownloadManager(DownloadService.this);
            return downloadManager.getDownloadUri(id);
        }

        @Override
        public Uri getUriForDownloadedFile(long id) throws RemoteException {
            DownloadManager downloadManager = new DownloadManager(DownloadService.this);
            return downloadManager.getUriForDownloadedFile(id);
        }
    };
}
