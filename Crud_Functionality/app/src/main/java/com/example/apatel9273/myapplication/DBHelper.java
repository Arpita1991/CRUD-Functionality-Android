package com.example.apatel9273.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public SQLiteDatabase db;
    // DB_NAME ContentValues to store database name
    static final String DB_NAME = "CCollege.DB";

    // defining variable to store database version
    static final int DB_VERSION = 1;
    // TABLE_NAME variable to store table name
    public static final String TABLE_NAME = "student";
    // defining variable to store the field name of table.
    public static final String _ID = "id";
    public static final String student_firstname = "firstname";
    public static final String student_lastname = "lastname";
    public static final String student_marks = "marks";

   // DBhelper constructor
    public DBHelper(Context context) {super(context, DB_NAME, null, DB_VERSION);
      //  db.execSQL(CREATE_TABLE);
    }
    // Creating Table on oncreate method. This method is called by the framework.
    @Override

    public void onCreate(SQLiteDatabase db) {
        // call execSQL method which allows to execute an sql statement -so it execute query to create table
        db.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                student_firstname + " TEXT, " +
                student_lastname + " TEXT, " +
                student_marks + " TEXT)"
        );
    }
//onUpgrade is called if the database version is increased in your application code.
// This method allows you to update an existing database schema or to drop the existing database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // call execSQL method which allows to execute an sql statement -so it execute query to drop table and create new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
// addstudentdata function to add new student details in table.
    public boolean addStudentData(String firstname, String lastname, String marks) {
        // The SQLiteOpenHelper class provides the getReadableDatabase() and getWriteableDatabase()
        // methods to get access to an SQLiteDatabase object; either in read or write mode.
        SQLiteDatabase db = getWritableDatabase();
        //object contentValues allows to define key or value to store record in table.
        ContentValues contentValues = new ContentValues();
        // put function hold the key and value in contentValues object
        contentValues.put(student_firstname, firstname);
        contentValues.put(student_lastname, lastname);
        contentValues.put(student_marks, marks);
        // SQLiteDatabase provides the insert function to insert data in table. here it store the return value in variable x.
        long x=db.insert(TABLE_NAME, null, contentValues);
        // check weather variable value is -1 , that means data is inserted or not
        if(x==-1) return false;
        // if condition is false it will return ture.
        else return true;
    }

    // updateStudentData function to update student details in table.
    public boolean updateStudentData(int id, String name, String lastname, String marks) {
        // getWritableDatabase to write the data using db object
        SQLiteDatabase db = this.getWritableDatabase();
        //object contentValues store record in table.
        ContentValues contentValues = new ContentValues();
        // put function hold the key and value in contentValues object
        contentValues.put(student_firstname, name);
        contentValues.put(student_lastname, lastname);
        contentValues.put(student_marks, marks);
        // SQLiteDatabase provides the update function to update data in table. here it update the existing data by student id.
        db.update(TABLE_NAME, contentValues, _ID + " = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    // getAllData function to view data.
    public Cursor getAllData()
    {
        // getReadableDatabase to read the data using db object
        SQLiteDatabase db = this.getReadableDatabase();
        //Passing "null" parameters will return all columns. from table
       String[] columns = {_ID,student_firstname,student_lastname,student_marks};
        //query() methods is called to get the data from particular table.
        return db.query(TABLE_NAME,columns,null,null,null,null,null);
    }
    public int deleteData(int id) {
// getWritableDatabase to write the data using db object
        SQLiteDatabase db = this.getWritableDatabase();
        //call delete function delete row from table using id, here table name , id is passed to which data want to delete
        int result = db.delete(TABLE_NAME, _ID + " = ?",
                    new String[] { String.valueOf(id) });
        // close the SQLite db
            db.close();
        //return result
        return  result;
        }

    public Cursor getData(int id){
// getReadableDatabase to Read the data using db object
        SQLiteDatabase db = this.getReadableDatabase();
        //rawQuery() directly accepts an SQL select statement as input.
        //rawquery() method to retrive data for particular student by id
        // A Cursor represents the result of a query ,it points to one row of the query result.
        Cursor res =  db.rawQuery( "select * from "+TABLE_NAME+" where "+ _ID + " = " +id+"", null );
       //return results
        return res;
    }

}
