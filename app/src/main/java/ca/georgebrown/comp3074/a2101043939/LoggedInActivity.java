package ca.georgebrown.comp3074.a2101043939;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoggedInActivity extends Activity {

    private EditText pidEt;
    private EditText pfnameEt;
    private EditText plnameEt;
    private EditText pdepEt;
    private EditText pdocidEt;
    private EditText proomEt;
    private SQLiteHelper mySqlHelper;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        pidEt = (EditText) findViewById(R.id.pIdEditText);
        pfnameEt = (EditText) findViewById(R.id.pfnameEditText);
        plnameEt = (EditText) findViewById(R.id.plnameEditText);
        pdepEt = (EditText) findViewById(R.id.pdepEditText);
        pdocidEt = (EditText) findViewById(R.id.pDocIdEditText);
        proomEt = (EditText) findViewById(R.id.pRoomEditText);

        mySqlHelper = new SQLiteHelper(LoggedInActivity.this);
        myDatabase = mySqlHelper.getWritableDatabase();

    }

    //adding patients in the database
    public void addPatient(View view) {
        boolean isInserted =
                mySqlHelper.insertDataPatient( pidEt.getText().toString(),
                        pfnameEt.getText().toString(),
                        plnameEt.getText().toString(),
                        pdepEt.getText().toString(),
                        pdocidEt.getText().toString(),
                        proomEt.getText().toString());

        if (isInserted)
            Toast.makeText(LoggedInActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(LoggedInActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void displayPatients(View view) {
        Cursor patientRes = mySqlHelper.getAllPatientData();
        if(patientRes.getCount() == 0){
            //show error message
            showMessage("Error!", "Sorry, Nothing found in the database");
            return;
        }

        StringBuffer patientBuffer = new StringBuffer();
        while (patientRes.moveToNext())
        {
            patientBuffer.append("Patient Id:  " + patientRes.getString(0)+ "\n");
            patientBuffer.append("First Name:  " + patientRes.getString(1)+ "\n");
            patientBuffer.append("Last Name:   " + patientRes.getString(2)+ "\n");
            patientBuffer.append("Department:  " + patientRes.getString(3)+ "\n");
            patientBuffer.append("Doctor Id:   " + patientRes.getString(4)+ "\n");
            patientBuffer.append("Room:        " + patientRes.getString(5)+ "\n\n\n");
        }
       //show all the data from the database
        showMessage("Following Data was found: ", patientBuffer.toString());

    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInteface, int i) {
                        dialogInteface.dismiss();
                    }
                });
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void goToTestPage(View view) {
                Intent startAddTestActivity = new Intent("ca.georgebrown.comp3074.a2101043939.AddTestActivity");
                startActivity(startAddTestActivity);
    }

    public void clickLogout(View view) {
        Intent startMainActivity = new Intent(LoggedInActivity.this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
