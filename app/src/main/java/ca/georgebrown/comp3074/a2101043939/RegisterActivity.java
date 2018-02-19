package ca.georgebrown.comp3074.a2101043939;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

    private EditText docnurseIdEt;
    private EditText dnFnameEt;
    private EditText dnLnameEt;
    private EditText dnDepEt;
    private EditText dnEmailEt;

    private SQLiteHelper mySqlHelper;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        docnurseIdEt = (EditText) findViewById(R.id.docnurseIdEditText);
        dnFnameEt = (EditText) findViewById(R.id.dnFnameEditText);
        dnLnameEt = (EditText) findViewById(R.id.dnLnameEditText);
        dnDepEt = (EditText) findViewById(R.id.dnDepEditText);
        dnEmailEt = (EditText) findViewById(R.id.dnEmailEditText);

        mySqlHelper = new SQLiteHelper(RegisterActivity.this);
        myDatabase = mySqlHelper.getWritableDatabase();
    }

    public void registerDoctor(View view) {
        boolean isInserted =
                mySqlHelper.insertDataDoctor( docnurseIdEt.getText().toString(),
                        dnFnameEt.getText().toString(),
                        dnLnameEt.getText().toString(),
                        dnDepEt.getText().toString(),
                        dnEmailEt.getText().toString());

        if (isInserted)
            Toast.makeText(RegisterActivity.this, "Data Inserted Successfully, Try Login :)", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(RegisterActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

    public void registerNurse(View view) {
        boolean isInserted =
                mySqlHelper.insertDataNurse( docnurseIdEt.getText().toString(),
                        dnFnameEt.getText().toString(),
                        dnLnameEt.getText().toString(),
                        dnDepEt.getText().toString(),
                        dnEmailEt.getText().toString());

        if (isInserted)
            Toast.makeText(RegisterActivity.this, "Data Inserted Successfully, Try Login :)", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(RegisterActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
    }

}
