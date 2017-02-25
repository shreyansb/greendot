package org.shreyans.greendot.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.shreyans.greendot.DB;

@Table(database=DB.class)
public class Goal extends BaseModel {
    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;

    @Column
    public int freq;

    @Column
    public boolean active;
}