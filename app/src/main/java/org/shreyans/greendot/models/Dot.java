package org.shreyans.greendot.models;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.shreyans.greendot.DB;

@Table(database=DB.class)
public class Dot extends BaseModel {

    @PrimaryKey(autoincrement = true)
    int id;

    @ForeignKey(stubbedRelationship = true)
    public Goal goal;

    @Column
    public int week;

    @Column(defaultValue = "0")
    public int num;
}
