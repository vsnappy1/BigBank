package com.example.bigbank.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bigbank.InfoSubmissionActivity;
import com.example.bigbank.Model.User;

import java.util.ArrayList;

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
    private static final int DATABASE_VERSION = 2;
    private static final String SQL_TAG = "SQL_ERROR";

    //String to create a customer table
    private static final String CREATE_USER_TABLE_QUERY =
            "CREATE TABLE "
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
            sqLiteDatabase.execSQL(CREATE_USER_TABLE_QUERY);
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
        contentValues.put(COLUMN_CARD_NUMBER, user.getCardNumber());
        contentValues.put(COLUMN_CVC, user.getCvc());
        contentValues.put(COLUMN_IDENTITY_CARD_PICTURE_FRONT, user.getIdentityCardPictureFront());
        contentValues.put(COLUMN_IDENTITY_CARD_PICTURE_BACK, user.getIdentityCardPictureBack());
        contentValues.put(COLUMN_FACE_PICTURE, user.getFacePicture());
        contentValues.put(COLUMN_HAS_RECEIVED_CARD, user.getReceivedCard());
        contentValues.put(COLUMN_IS_USING_FIRST_TIME, user.getUsingFirstTime());

        long result = sqLiteDatabase.insert(USER_TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

        Log.i("TAKA", "recorded inserted successfully");

        return result;
    }


    public ArrayList<User> getAllUsers() {

        ArrayList<User> list = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + USER_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Log.i("TAKA", "read start ");


        if (cursor.moveToFirst()) {
            do {
                Log.i("TAKA", "read ~1");

                int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                String email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
                String fullName = cursor.getString(cursor.getColumnIndex(COLUMN_FULL_NAME));
                String identityCard = cursor.getString(cursor.getColumnIndex(COLUMN_IDENTITY_CARD));
                String phoneNumber = cursor.getString(cursor.getColumnIndex(COLUMN_PHONE_NUMBER));
                String gender = cursor.getString(cursor.getColumnIndex(COLUMN_GENDER));
                String nation = cursor.getString(cursor.getColumnIndex(COLUMN_NATION));
                String occupation = cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION));
                String address = cursor.getString(cursor.getColumnIndex(COLUMN_ADDRESS));
                String postCode = cursor.getString(cursor.getColumnIndex(COLUMN_POST_CODE));
                String state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));
                String city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                String identityCardPictureFront = cursor.getString(cursor.getColumnIndex(COLUMN_IDENTITY_CARD_PICTURE_FRONT));
                String identityCardPictureBack = cursor.getString(cursor.getColumnIndex(COLUMN_IDENTITY_CARD_PICTURE_BACK));
                String facePicture = cursor.getString(cursor.getColumnIndex(COLUMN_FACE_PICTURE));
                String cvc = cursor.getString(cursor.getColumnIndex(COLUMN_CVC));
                String cardNumber = cursor.getString(cursor.getColumnIndex(COLUMN_CARD_NUMBER));
                int usingFirstTime = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_USING_FIRST_TIME));      // 1 mean true and 0 means false
                int receivedCard = cursor.getInt(cursor.getColumnIndex(COLUMN_HAS_RECEIVED_CARD));      // 1 mean true and 0 means false

                Log.i("TAKA", "read ~2");

                User user = new User();
                user.setId(id);
                user.setEmail(email);
                user.setPassword(password);
                user.setFullName(fullName);
                user.setIdentityCard(identityCard);
                user.setPhoneNumber(phoneNumber);
                user.setOccupation(occupation);
                user.setAddress(address);
                user.setPostCode(postCode);
                user.setCity(city);
                user.setGender(gender);
                user.setNation(nation);
                user.setState(state);
                user.setUsingFirstTime(usingFirstTime);
                user.setReceivedCard(receivedCard);
                user.setFacePicture(facePicture);
                user.setIdentityCardPictureFront(identityCardPictureFront);
                user.setIdentityCardPictureBack(identityCardPictureBack);
                user.setCardNumber(cardNumber);
                user.setCvc(cvc);

                list.add(user);
                Log.i("TAKA", "user read "+list.size());
            } while (cursor.moveToNext());
        }

        Log.i("TAKA", "recorded present "+cursor.getCount());

        cursor.close();
        db.close();
        return list;
    }

    public long addCardNumberAndCvc(int id, String card, String cvc){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_CARD_NUMBER, card);
        contentValues.put(COLUMN_CVC, cvc);

        long result = db.update(USER_TABLE_NAME,contentValues,COLUMN_ID+" = "+id, null);
        db.close();

        Log.i("TAKA", "user updated");
        return result;
    }


    public long cardReceived(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_IS_USING_FIRST_TIME, 0);
        contentValues.put(COLUMN_HAS_RECEIVED_CARD, 1);

        long result = db.update(USER_TABLE_NAME,contentValues,COLUMN_ID+" = "+id, null);
        db.close();

        Log.i("TAKA", "card received");
        return result;

    }
}
