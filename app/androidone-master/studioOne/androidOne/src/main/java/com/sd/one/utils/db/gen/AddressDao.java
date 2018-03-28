package com.sd.one.utils.db.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.sd.one.utils.db.entity.Address;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ADDRESS".
*/
public class AddressDao extends AbstractDao<Address, Long> {

    public static final String TABLENAME = "ADDRESS";

    /**
     * Properties of entity Address.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property AddressId = new Property(0, Long.class, "addressId", true, "_id");
        public final static Property CustomerId = new Property(1, long.class, "customerId", false, "CUSTOMER_ID");
        public final static Property Receiver = new Property(2, String.class, "receiver", false, "RECEIVER");
        public final static Property Mobile = new Property(3, String.class, "mobile", false, "MOBILE");
        public final static Property Province = new Property(4, String.class, "province", false, "PROVINCE");
        public final static Property City = new Property(5, String.class, "city", false, "CITY");
        public final static Property Area = new Property(6, String.class, "area", false, "AREA");
        public final static Property Address = new Property(7, String.class, "address", false, "ADDRESS");
        public final static Property Zipcode = new Property(8, String.class, "zipcode", false, "ZIPCODE");
        public final static Property CreteTime = new Property(9, String.class, "creteTime", false, "CRETE_TIME");
    };


    public AddressDao(DaoConfig config) {
        super(config);
    }
    
    public AddressDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ADDRESS\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: addressId
                "\"CUSTOMER_ID\" INTEGER NOT NULL ," + // 1: customerId
                "\"RECEIVER\" TEXT," + // 2: receiver
                "\"MOBILE\" TEXT," + // 3: mobile
                "\"PROVINCE\" TEXT," + // 4: province
                "\"CITY\" TEXT," + // 5: city
                "\"AREA\" TEXT," + // 6: area
                "\"ADDRESS\" TEXT," + // 7: address
                "\"ZIPCODE\" TEXT," + // 8: zipcode
                "\"CRETE_TIME\" TEXT);"); // 9: creteTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ADDRESS\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Address entity) {
        stmt.clearBindings();
 
        Long addressId = entity.getAddressId();
        if (addressId != null) {
            stmt.bindLong(1, addressId);
        }
        stmt.bindLong(2, entity.getCustomerId());
 
        String receiver = entity.getReceiver();
        if (receiver != null) {
            stmt.bindString(3, receiver);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(5, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(6, city);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(7, area);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String zipcode = entity.getZipcode();
        if (zipcode != null) {
            stmt.bindString(9, zipcode);
        }
 
        String creteTime = entity.getCreteTime();
        if (creteTime != null) {
            stmt.bindString(10, creteTime);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Address entity) {
        stmt.clearBindings();
 
        Long addressId = entity.getAddressId();
        if (addressId != null) {
            stmt.bindLong(1, addressId);
        }
        stmt.bindLong(2, entity.getCustomerId());
 
        String receiver = entity.getReceiver();
        if (receiver != null) {
            stmt.bindString(3, receiver);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String province = entity.getProvince();
        if (province != null) {
            stmt.bindString(5, province);
        }
 
        String city = entity.getCity();
        if (city != null) {
            stmt.bindString(6, city);
        }
 
        String area = entity.getArea();
        if (area != null) {
            stmt.bindString(7, area);
        }
 
        String address = entity.getAddress();
        if (address != null) {
            stmt.bindString(8, address);
        }
 
        String zipcode = entity.getZipcode();
        if (zipcode != null) {
            stmt.bindString(9, zipcode);
        }
 
        String creteTime = entity.getCreteTime();
        if (creteTime != null) {
            stmt.bindString(10, creteTime);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Address readEntity(Cursor cursor, int offset) {
        Address entity = new Address( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // addressId
            cursor.getLong(offset + 1), // customerId
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // receiver
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mobile
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // province
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // city
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // area
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // address
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // zipcode
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9) // creteTime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Address entity, int offset) {
        entity.setAddressId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setCustomerId(cursor.getLong(offset + 1));
        entity.setReceiver(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMobile(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setProvince(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setCity(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setArea(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAddress(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setZipcode(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCreteTime(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Address entity, long rowId) {
        entity.setAddressId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Address entity) {
        if(entity != null) {
            return entity.getAddressId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}