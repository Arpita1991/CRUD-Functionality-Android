package com.example.apatel9273.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    //defining Variables for textbox
    EditText firstName,lastName,marks,studentID;
    //defining AlertDialog
    AlertDialog alertDialog;
    //Declaring ArrayList for students
    ArrayList<String> students = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // calling the findViewById function that was defined in Activity will return instance of view for that particular text
        firstName = (EditText) findViewById(R.id.editText);
        lastName = (EditText) findViewById(R.id.editText2);
        marks = (EditText) findViewById(R.id.editText3);
        studentID = (EditText) findViewById(R.id.editText4);

// calling the findViewById function that was defined in Activity will return instance of view for that particular AddData button
       Button AddData = (Button) findViewById(R.id.AddData);
        assert AddData != null;
        //listener callback for AddData button so that when we click the button, it will respond
        AddData.setOnClickListener(new View.OnClickListener() {
            @Override
            //listen to the action on click
            public void onClick(View view) {
                // call DBHelper class and crete object db
                DBHelper db = new DBHelper(getApplicationContext());
                // calling addStudentData function where the parameters are passed
                // insert query will be executed where data will be inserted into table
                // it will return boolean value wheather it is inserted or not
                boolean dbquery = db.addStudentData(firstName.getText().toString(),lastName.getText().toString(),marks.getText().toString());
                // check if data is inserted
                if(dbquery) {
                    // open pop up with messaage that data is inserted.
                    Toast.makeText(getApplicationContext(),
                            "Data has been Inserted!!", Toast.LENGTH_LONG).show();
                    //create object of intent to diplay something
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                   // here its calling startActivity which will Replacing the MAinavtivity, that means it will refresh page.
                    startActivity(i);
                }
                else {
                    // if  condition false it will show pop up with error message
                    Toast.makeText(getApplicationContext(),
                            "Please Try Again", Toast.LENGTH_LONG).show();
                    //create object of intent to diplay something
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    // here its calling startActivity which will Replacing the MAinavtivity, that means it will refresh page.
                    startActivity(i);
                }


            }
        });


// calling the findViewById function that was defined in Activity will return instance of view for that particular viewData button
        Button viewData = (Button) findViewById(R.id.viewData);
        assert viewData != null;
        //listener callback for viewData button so that when we click the button, it will respond
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            //listen to the action on click
            public void onClick(View view) {
                // clear the arraylist
                students.clear();
                // call DBHelper class and crete object db
                DBHelper db = new DBHelper(getApplicationContext());
                // get the value of id and remove space from value by trim function
                String id = studentID.getText().toString().trim();
                //matches the id value with blank
                if(id.matches("")) {
                    // if condition true
                    // call the getAlldata fucntion to retrive all the students list and store in cursor
                Cursor cursor= db.getAllData();
                    // check if cursor is not null
                if (cursor != null) {
                    // cursor point to raw object
                    //while loop till cursor found the nextdata is blank
                    //movetonext function bumpto next raw object
                    while (cursor.moveToNext())
                    {
                        // getstring function to get the value from cursor fields
                        // concate thae values and store in student_detail variable
                        String Student_detail = "\nID:" + cursor.getString(0) + "\nFirst Name: " + cursor.getString(1) +
                                "\nLastName :" + cursor.getString(2) + "\nMarks :" + cursor.getString(3);
                       // add function to add the student detail in student ArrayList.
                        students.add(Student_detail);
                    }
                    // call showdialog function to show dialogbox with student data
                    showDialog();
                }
                }else
                {
                    //call getdata function to get data of particular student and store in cursor object.
                    Cursor cursor= db.getData(Integer.parseInt(id));
                    // check condition for blank cursor
                     if(cursor.getCount() == 0)
                      {
                          // create object for alert dialogbox
                          AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                          // setmessage for dialogbox
                          alertDialogBuilder.setMessage("No Data Found.");
                          // show function to show dialogbox.
                          alertDialogBuilder.show();

                      }else
                      {
                          cursor.moveToFirst();
                       // store data in student_details variable from cursor object by using getstring function
                          String Student_detail = "\nID:" + cursor.getString(0) + "\nFirst Name :" + cursor.getString(1) + "\nLastName :" + cursor.getString(2) + "\nMarks :" + cursor.getString(3);
                          // add function to store data in ArrayList
                          students.add(Student_detail);
                          // call showdialog function to show dialogbox with student data
                          showDialog();
                      }
                }
            }
        });

// calling the findViewById function that was defined in Activity will return instance of view for that particular daleteData button

        Button daleteData = (Button) findViewById(R.id.daleteData);
        assert daleteData != null;
        //listener callback for daleteData button so that when we click the button, it will respond
        daleteData.setOnClickListener(new View.OnClickListener() {
            //listen to the action on click

            @Override
            public void onClick(View view) {
                // call DBHelper class and crete object db
                DBHelper db = new DBHelper(getApplicationContext());
                // get the value of id and removing the space from that value and storing in variable.
                String id = studentID.getText().toString().trim();
                // call getdata function to retrive data which will store  in cursor object
                 Cursor cursor= db.getData(Integer.parseInt(id));
                // count the return length of cursor
                // check condition if the cursor is blank that means there is no data
                if(cursor.getCount() == 0)
                {
                    // create the object for dialogbox
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    // set the message for that dialogbox
                    alertDialogBuilder.setMessage("No Data Found.");
                    // show that dialogbox
                    alertDialogBuilder.show();
                }
                else
                {
                    //if there is data for that id else part is executed.
                    // call deletedata function with if
                    int dbquery = db.deleteData(Integer.parseInt(id));
                    // check if the data has been deleted
                    if (dbquery > 0) {
                        // show pop up message for successfully deletion.
                        Toast.makeText(getApplicationContext(),
                                "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {
                        //if it is not deleted show pop up message with Toast function with text error
                        Toast.makeText(getApplicationContext(),
                                "Please Try Again", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

// calling the findViewById function that was defined in Activity will return instance of view for that particular updateData button
        Button updateData = (Button) findViewById(R.id.updateData);
        assert updateData != null;
        //listener callback for updateData button so that when we click the button, it will respond
        updateData.setOnClickListener(new View.OnClickListener() {
            //listen to the action on click

            @Override
            public void onClick(View view) {
                // call DBHelper class and crete object db
                DBHelper db = new DBHelper(getApplicationContext());
                // get the value of id and remove space from value
                String id = studentID.getText().toString().trim();
                // define variables to store the new data
                String firstname,lastname,gmarks;
                // call getdata function to get detail of particulart student and store in cursor
                Cursor cursor= db.getData(Integer.parseInt(id));
                // check if cursor is not null
                if (cursor != null)
                    cursor.moveToFirst();
                // check if firstname value is blank than keep value of firstname as before
                if(firstName.getText().toString().matches(""))
                {
                    // get the firstname value from cursor and store in variable
                      firstname  = cursor.getString(1);
                }else {
                    // get the new value from firstname textbox and store in variable
                    firstname  = firstName.getText().toString() ;
                }

                // check if lastName value is blank than keep value of lastName as before
                if(lastName.getText().toString().matches(""))
                {
                    // get the lastName value from cursor and store in variable
                     lastname = cursor.getString(2);
                }
                else
                {
                    // get the new value from lastname textbox and store in variable
                     lastname  = lastName.getText().toString() ;
                }

                // check if marks value is blank than keep value of marks as before
                if(marks.getText().toString().matches(""))
                {
                    // get the marks value from cursor and store in variable
                     gmarks = cursor.getString(3);
                }else
                {
                    // get the new value from marks textbox and store in variable
                     gmarks = marks.getText().toString();
                }
                // call updatestudentdata function with parameter using db object
                // overthere it will execute update query and will update data for that particular student
                boolean dbquery = db.updateStudentData(Integer.parseInt(studentID.getText().toString()),
                        firstname,lastname,gmarks);
                // check if query is executed
                if(dbquery) {
                // show pop up for success message using TOAST
                    Toast.makeText(getApplicationContext(),
                            "Data has been Updated!!", Toast.LENGTH_SHORT).show();
                }
                else {

                    // show pop up for error message using TOAST
                    Toast.makeText(getApplicationContext(),
                            "Please Try Again", Toast.LENGTH_SHORT).show();
                }

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
// showdialog fucnntion to generate dialog box
    private void showDialog()
    {
        // create object for alertdialogbuilder
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        // get the size of list of students from list
        int totalStudnets = students.size();
        // create array for number of students to store data
        String[] name = new String[totalStudnets];
        // forloop for get each student data
        for(int i=0;i<totalStudnets;i++)
        {
            // get function to get the data fom student list and store  in name array
            name[i] = students.get(i);
        }

        // set negativebutton to close the dialogbox
        alertDialogBuilder.setNegativeButton("Go Back", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });
        //setitems function to set display data in dialogbox
        alertDialogBuilder.setItems(name,this);
        // settitle function to set title in dialogbox
        alertDialogBuilder.setTitle("Students");
        //show function to show the dialogbox
        alertDialogBuilder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(DialogInterface dialog, int pos)
    {
    // Toast.makeText(getApplicationContext(),students.get(pos),Toast.LENGTH_SHORT).show();
    }
}
