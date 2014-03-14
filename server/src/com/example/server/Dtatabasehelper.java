package com.example.server;

import android.provider.BaseColumns;

public final class Dtatabasehelper {
	
    public Dtatabasehelper() {}

    /* Inner class that defines the table contents */
    public static abstract class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "userlist";
        public static final String TABLE1_NAME = "photolist";
        public static final String TABLE2_NAME = "image";
        public static final String COLUMN_USER_ID = "userid";
        public static final String COLUMN_USER_NAME = "username";
        public static final String COLUMN_PHOTO_ID = "photoid";
        public static final String COLUMN_PHOTO_NAME = "photoname";
        public static final String COLUMN_FILE_NAME = "filename";
       
}
}