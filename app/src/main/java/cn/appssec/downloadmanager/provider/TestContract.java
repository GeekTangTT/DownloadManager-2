package cn.appssec.downloadmanager.provider;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

//URI组装代码
public class TestContract {
    //区别不同的contentProvider
    protected static final String CONTENT_AUTHORITY = "cn.appssec.downloadmanager";

    protected static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //区分不同的数据表
    protected static final String PATH_TEST = "downloads";//downloads//test
    public static final class TestEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TEST).build();
        //区别表中不同的数据
        //开了一个静态方法，用以在有新数据产生时根据id生成新的uri
        protected static Uri buildUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        protected static final String TABLE_NAME = "test";

        public static final String COLUMN_NAME = "name";
    }
}