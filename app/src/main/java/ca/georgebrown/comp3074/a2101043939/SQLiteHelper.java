package ca.georgebrown.comp3074.a2101043939;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {
    private String password;
    public static final String DATABASE_NAME = "MyOxfordHospital.db";
    public static final String TABLE_PATIENT = "patient";
    public static final String TABLE_TEST = "test";
    public static final String TABLE_NURSE = "nurse";
    public static final String TABLE_DOCTOR = "doctor";

    //the name and column index of each column in patient table:



    public SQLiteHelper(Context context)  {

        super(context, DATABASE_NAME, null, 1);
    }

    @Override   //called when no database exists in disk and helper class needs to create a new one
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " +
                TABLE_PATIENT + " ( patient_id   interger   primary key, " +
                                  " patient_fname    text   not null, " +
                                  " patient_lname    text, " +
                                  " patient_department   text   not null, " +
                                  " patient_doctor_id   integer, " +
                                  " patient_room   integer );");

        db.execSQL("create table " +
                TABLE_TEST + " ( test_id   interger   primary key, " +
                " patient_id    integer   not null, " +
                " bpl    text, " +
                " bph   text   not null, " +
                " temperature   integer);");

        db.execSQL("create table " +
                TABLE_NURSE + " ( nurse_id   interger   primary key, " +
                " nurse_fname   text   not null, " +
                " nurse_lname   text   not null, " +
                " nurse_department   text   not null, " +
                " nurse_email   text);");

        db.execSQL("create table " +
                TABLE_DOCTOR + " ( doctor_id   interger   primary key, " +
                " doctor_fname   text   not null, " +
                " doctor_lname   text   not null, " +
                " doctor_department   text   not null, " +
                " doctor_email   text);");


    }

    @Override  //called when the database in the disk needs to be upgraded
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("TaskDBAdapter", "Upgrading from version " +
                                 oldVersion + " to " + newVersion +
                                 ", which will destroy all the data. ");

        //drop the table and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NURSE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DOCTOR);
        onCreate(db); //create new
    }

    public boolean insertDataPatient(String patient_id, String patient_fname,String patient_lname,String patient_department,String patient_doctor_id, String patient_room) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues patientcv = new ContentValues();
        patientcv.put("patient_id", patient_id);
        patientcv.put("patient_fname",patient_fname);
        patientcv.put("patient_lname",patient_lname);
        patientcv.put("patient_department",patient_department);
        patientcv.put("patient_doctor_id",patient_doctor_id);
        patientcv.put("patient_room",patient_room);
        long result = db.insert(TABLE_PATIENT, null, patientcv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataTest(String test_id, String patient_id, String bpl,String bph,String temperature) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues testcv = new ContentValues();
        testcv.put("test_id", test_id);
        testcv.put("patient_id",patient_id);
        testcv.put("bpl",bpl);
        testcv.put("bph",bph);
        testcv.put("temperature",temperature);
        long result = db.insert(TABLE_TEST, null, testcv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataNurse(String nurse_id, String nurse_fname, String nurse_lname,String nurse_department,String nurse_email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues nursecv = new ContentValues();
        nursecv.put("nurse_id", nurse_id);
        nursecv.put("nurse_fname",nurse_fname);
        nursecv.put("nurse_lname",nurse_lname);
        nursecv.put("nurse_department",nurse_department);
        nursecv.put("nurse_email",nurse_email);
        long result = db.insert(TABLE_NURSE, null, nursecv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public boolean insertDataDoctor(String doctor_id, String doctor_fname, String doctor_lname,String doctor_department,String doctor_email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues doctorcv = new ContentValues();
        doctorcv.put("doctor_id", doctor_id);
        doctorcv.put("doctor_fname",doctor_fname);
        doctorcv.put("doctor_lname",doctor_lname);
        doctorcv.put("doctor_department",doctor_department);
        doctorcv.put("doctor_email",doctor_email);
        long result = db.insert(TABLE_DOCTOR, null, doctorcv);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllPatientData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor patientRes = db.rawQuery("select * from " + TABLE_PATIENT, null);
        return patientRes;
    }

    public Cursor getAllTestData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor testRes = db.rawQuery("select * from " + TABLE_TEST, null);
        return testRes;
    }

    String checkUserId(String Username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_DOCTOR,null,  "doctor_id=?",new String[]{Username},null, null, null, null);
        if(cursor.getCount()<1){
            cursor.close();
            return "Not Exist";
        }
        else if(cursor.getCount()>=1 && cursor.moveToFirst()){
            password = cursor.getString(cursor.getColumnIndex("doctor_email"));
            cursor.close();
        }
        return password;
    }
}


