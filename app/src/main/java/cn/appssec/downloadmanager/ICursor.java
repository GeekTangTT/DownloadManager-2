package cn.appssec.downloadmanager;

import android.content.ContentResolver;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by T on 18/7/20.
 */

public interface ICursor extends Closeable,Parcelable {
    static final int FIELD_TYPE_NULL = 0;
    static final int FIELD_TYPE_INTEGER = 1;

    static final int FIELD_TYPE_FLOAT = 2;

    static final int FIELD_TYPE_STRING = 3;

    static final int FIELD_TYPE_BLOB = 4;

    int getCount();

    int getPosition();

    boolean move(int offset);

    boolean moveToPosition(int position);

    boolean moveToFirst();

    boolean moveToLast();

    boolean moveToNext();

    boolean moveToPrevious();

    boolean isFirst();

    boolean isLast();

    boolean isBeforeFirst();

    boolean isAfterLast();

    int getColumnIndex(String columnName);

    int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException;

    String getColumnName(int columnIndex);

    String[] getColumnNames();

    int getColumnCount();

    byte[] getBlob(int columnIndex);

    String getString(int columnIndex);

    void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer);

    short getShort(int columnIndex);

    int getInt(int columnIndex);

    long getLong(int columnIndex);

    float getFloat(int columnIndex);

    double getDouble(int columnIndex);

    int getType(int columnIndex);

    boolean isNull(int columnIndex);

    void deactivate();

    @Deprecated
    boolean requery();

    void close();

    boolean isClosed();

    void registerContentObserver(ContentObserver observer);

    void unregisterContentObserver(ContentObserver observer);

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);

    void setNotificationUri(ContentResolver cr, Uri uri);

    Uri getNotificationUri();

    boolean getWantsAllOnMoveCalls();

    void setExtras(Bundle extras);

    Bundle getExtras();

    Bundle respond(Bundle extras);



}




/*
    static final int FIELD_TYPE_NULL = 0;
    static final int FIELD_TYPE_INTEGER = 1;

    static final int FIELD_TYPE_FLOAT = 2;

    static final int FIELD_TYPE_STRING = 3;

    static final int FIELD_TYPE_BLOB = 4;

    int getCount();

    int getPosition();

    boolean move(int offset);

    boolean moveToPosition(int position);

    boolean moveToFirst();

    boolean moveToLast();

    boolean moveToNext();

    boolean moveToPrevious();

    boolean isFirst();

    boolean isLast();

    boolean isBeforeFirst();

    boolean isAfterLast();

    int getColumnIndex(String columnName);

    int getColumnIndexOrThrow(String columnName) throws IllegalArgumentException;

    String getColumnName(int columnIndex);

    String[] getColumnNames();

    int getColumnCount();

    byte[] getBlob(int columnIndex);

    String getString(int columnIndex);

    void copyStringToBuffer(int columnIndex, CharArrayBuffer buffer);

    short getShort(int columnIndex);

    int getInt(int columnIndex);

    long getLong(int columnIndex);

    float getFloat(int columnIndex);

    double getDouble(int columnIndex);

    int getType(int columnIndex);

    boolean isNull(int columnIndex);

    void deactivate();

    @Deprecated
    boolean requery();

    void close();

    boolean isClosed();

    void registerContentObserver(ContentObserver observer);

    void unregisterContentObserver(ContentObserver observer);

    void registerDataSetObserver(DataSetObserver observer);

    void unregisterDataSetObserver(DataSetObserver observer);

    void setNotificationUri(ContentResolver cr, Uri uri);

    Uri getNotificationUri();

    boolean getWantsAllOnMoveCalls();

    void setExtras(Bundle extras);

    Bundle getExtras();

    Bundle respond(Bundle extras);*/
