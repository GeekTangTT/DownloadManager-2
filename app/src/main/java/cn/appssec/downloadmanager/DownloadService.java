package cn.appssec.downloadmanager;

import android.app.Service;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.CookieManager;

import java.io.FileNotFoundException;

import cn.appssec.downloadmanager.provider.MyProvider;

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
        public long enqueue(Request request) throws RemoteException {
            //DownloadManager downloadManager=new DownloadManager(DownloadService.this);
            String mPackageName = DownloadService.this.getPackageName();
            ContentResolver mResolver = DownloadService.this.getContentResolver();
            ContentValues values=request.toContentValues(mPackageName);
            Uri downloadUri=mResolver.insert(Downloads.Impl.CONTENT_URI,values);
            long id=Long.parseLong(downloadUri.getLastPathSegment());
            return id;
        }

//        @Override
//        public long enqueue(AidlRequest request) throws RemoteException {
//            String uri = request.getUri();
//            String destination = request.getDestinationUri();
//
//            String mimeType = request.getMimeType();
//            Log.d(TAG, " uri : " + uri + "      destination : " + destination + "       mimetype : " + mimeType);
//            DownloadManager downloadManager = new DownloadManager(DownloadService.this);
//            DownloadManager.Request requestTrue = new DownloadManager.Request(uri);
//
//            if (!TextUtils.isEmpty(mimeType)) {
//                requestTrue.setMimeType(mimeType);
//            }
//            if (!TextUtils.isEmpty(destination)) {
//                Uri destinationUri = Uri.parse(destination);
//                requestTrue.setDestinationUri(destinationUri);
//                Log.d(TAG, "enqueue:uri--> "+destinationUri);
//            }
//            requestTrue.setVisibleInDownloadsUi(true);
//            requestTrue.allowScanningByMediaScanner();
////            request.setDescription(webAddress.getHost());
//            String cookies = CookieManager.getInstance().getCookie(uri);
//            requestTrue.addRequestHeader("Cookie", cookies);
//            requestTrue.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//            return downloadManager.enqueue(requestTrue);
//            //ContentValues values=request
//        }


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

        @Override
        public ParcelFileDescriptor openDownloadedFile(long id) throws RemoteException {
            ContentResolver mResolver;
            mResolver=DownloadService.this.getContentResolver();
//            ParcelFileDescriptor parcelFileDescriptor=new ParcelFileDescriptor();
            try {
                return mResolver.openFileDescriptor(getDownloadUri(id),"r");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public String getMimeTypeForDownloadedFile(long id) throws RemoteException {
            DownloadManager.Query query = new DownloadManager.Query().setFilterById(id);
            DownloadManager downloadManager=new DownloadManager(DownloadService.this);
            Cursor cursor = null;
            try {
                //cursor = query(query);
                //public Cursor query(Query query)
                cursor=downloadManager.query(query);
                if (cursor == null) {
                    return null;
                }
                while (cursor.moveToFirst()) {
                    //context.getContentResolver().insert(uri,contentValues);
                    return cursor.getString(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_MEDIA_TYPE));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            // downloaded file not found or its status is not 'successfully completed'
            return null;
        }


    };
}
