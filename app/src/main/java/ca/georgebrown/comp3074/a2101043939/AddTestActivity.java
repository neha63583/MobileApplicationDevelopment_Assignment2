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

public class AddTestActivity extends Activity {

    private EditText tidEt;
    private EditText tPatientIdEt;
    private EditText tBplEt;
    private EditText tBphEt;
    private EditText tTempEt;

    private SQLiteHelper mySqlHelper;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test);
        tidEt = (EditText) findViewById(R.id.tIdEditText);
        tPatientIdEt = (EditText) findViewById(R.id.tPatientIdEditText);
        tBplEt = (EditText) findViewById(R.id.tBplEditText);
        tBphEt = (EditText) findViewById(R.id.tBphEditText);
        tTempEt = (EditText) findViewById(R.id.tTempEditText);


        mySqlHelper = new SQLiteHelper(AddTestActivity.this);
        myDatabase = mySqlHelper.getWritableDatabase();

    }

    public void goToPatientPage(View view) {
                Intent startLoggedInActivity = new Intent("ca.georgebrown.comp3074.a2101043939.LoggedInActivity");
                startActivity(startLoggedInActivity);
    }

    public void addTest(View view) {
        boolean isInserted =
                mySqlHelper.insertDataTest( tidEt.getText().toString(),
                        tPatientIdEt.getText().toString(),
                        tBplEt.getText().toString(),
                        tBphEt.getText().toString(),
                        tTempEt.getText().toString());

        if (isInserted)
            Toast.makeText(AddTestActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(AddTestActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void displayTests(View view) {
        Cursor testRes = mySqlHelper.getAllTestData();
        if(testRes.getCount() == 0){
            //show error message
            showMessage("Error!", "Sorry, Nothing found in the database");
            return;
        }

        StringBuffer testBuffer = new StringBuffer();
        while (testRes.moveToNext())
        {
            testBuffer.append("Test Id:     " + testRes.getString(0)+ "\n");
            testBuffer.append("Patient Id:  " + testRes.getString(1)+ "\n");
            testBuffer.append("BPL value:   " + testRes.getString(2)+ "\n");
            testBuffer.append("BPH value:   " + testRes.getString(3)+ "\n");
            testBuffer.append("Temperature: " + testRes.getString(4)+ "\n\n\n");
        }
        //show all the data from the database
        showMessage("Following Data was found: ", testBuffer.toString());

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

    public void clickLogout(View view) {
        Intent startMainActivity = new Intent(AddTestActivity.this, MainActivity.class);
        startActivity(startMainActivity);
    }
}
