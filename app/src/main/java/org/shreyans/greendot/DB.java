package org.shreyans.greendot;

import com.raizlabs.android.dbflow.annotation.Database;

// create database
@Database(name=DB.NAME, version=DB.VERSION)
public class DB {
    public static final String NAME = "DB";
    public static final int VERSION = 1;
}