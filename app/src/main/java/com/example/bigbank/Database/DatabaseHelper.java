package com.example.bigbank.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bigbank.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE_NAME = "USER_TABLE";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_FULL_NAME = "full_name";
    public static final String COLUMN_IDENTITY_CARD = "identity_card";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_NATION = "nation";
    public static final String COLUMN_OCCUPATION = "occupation";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_POST_CODE = "post_code";
    public static final String COLUMN_STATE = "state";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_IDENTITY_CARD_PICTURE_FRONT = "identity_card_picture_front";
    public static final String COLUMN_IDENTITY_CARD_PICTURE_BACK = "identity_card_picture_back";
    public static final String COLUMN_FACE_PICTURE = "face_picture";
    public static final String COLUMN_CVC = "cvc";
    public static final String COLUMN_CARD_NUMBER = "card_number";
    public static final String COLUMN_IS_USING_FIRST_TIME = "using_first_time";
    public static final String COLUMN_HAS_RECEIVED_CARD = "received_card";

    private static final String DATABASE_NAME = "bigBank.db";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_TAG = "SQL_ERROR";

    //String to create a customer table
    private static final String CREATE_PROPERTY_TABLE =
            "CREATE TABLE " +
                    USER_TABLE_NAME
                    + USER_TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_EMAIL + " TEXT, "
                    + COLUMN_PASSWORD + " TEXT, "
                    + COLUMN_FULL_NAME + " TEXT, "
                    + COLUMN_IDENTITY_CARD + " TEXT, "
                    + COLUMN_PHONE_NUMBER + " TEXT, "
                    + COLUMN_GENDER + " TEXT, "
                    + COLUMN_NATION + " TEXT, "
                    + COLUMN_OCCUPATION + " TEXT, "
                    + COLUMN_ADDRESS + " TEXT, "
                    + COLUMN_POST_CODE + " TEXT, "
                    + COLUMN_STATE + " TEXT, "
                    + COLUMN_CITY + " TEXT, "
                    + COLUMN_IDENTITY_CARD_PICTURE_FRONT + " TEXT, "
                    + COLUMN_IDENTITY_CARD_PICTURE_BACK + " TEXT, "
                    + COLUMN_FACE_PICTURE + " TEXT, "
                    + COLUMN_CVC + " TEXT, "
                    + COLUMN_CARD_NUMBER + " TEXT, "
                    + COLUMN_IS_USING_FIRST_TIME + " INTEGER, "
                    + COLUMN_HAS_RECEIVED_CARD + " INTEGER " + ")";


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        try {
            sqLiteDatabase.execSQL(CREATE_PROPERTY_TABLE);
        } catch (Exception e) {
            Log.d(SQL_TAG, e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public long insertUser(Context context, User user) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, user.getEmail());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_FULL_NAME, user.getFullName());
        contentValues.put(COLUMN_IDENTITY_CARD, user.getIdentityCard());
        contentValues.put(COLUMN_PHONE_NUMBER, user.getPhoneNumber());
        contentValues.put(COLUMN_GENDER, user.getGender());
        contentValues.put(COLUMN_NATION, user.getNation());
        contentValues.put(COLUMN_OCCUPATION, user.getOccupation());
        contentValues.put(COLUMN_ADDRESS, user.getAddress());
        contentValues.put(COLUMN_POST_CODE, user.getPostCode());
        contentValues.put(COLUMN_STATE, user.getState());
        contentValues.put(COLUMN_CITY, user.getCity());
        contentValues.put(COLUMN_IDENTITY_CARD_PICTURE_FRONT, user.getIdentityCardPictureFront());
        contentValues.put(COLUMN_IDENTITY_CARD_PICTURE_BACK, user.getIdentityCardPictureBack());
        contentValues.put(COLUMN_FACE_PICTURE, user.getFacePicture());
        contentValues.put(COLUMN_HAS_RECEIVED_CARD, user.getReceivedCard());
        contentValues.put(COLUMN_IS_USING_FIRST_TIME, user.getUsingFirstTime());


        long result =  sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

        return result;

    }
}
