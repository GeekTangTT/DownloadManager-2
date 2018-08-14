// IDownloadService.aidl
package cn.appssec.downloadmanager;
import cn.appssec.downloadmanager.AidlRequest;
// Declare any non-default types here with import statements
import android.net.Uri;
import cn.appssec.downloadmanager.IQuery;
//import android.database.Cursor;
import cn.appssec.downloadmanager.ICursor;
import android.os.ParcelFileDescriptor;
import java.lang.String;
interface IDownloadService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
   long enqueue(in AidlRequest request);

   Uri getDownloadUri(long id);

   Uri getUriForDownloadedFile(long id);

   //ICursor query(in IQuery query);

   //传递的参数不能是数组
   //IQuery setFilterById(long[] ids);

   ParcelFileDescriptor openDownloadedFile(long id);

   String getMimeTypeForDownloadedFile(long id);


}
