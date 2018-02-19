package ca.georgebrown.comp3074.a2101043939;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static EditText userIdEt;
    private static EditText passwordEt;
    private SQLiteHelper mySqlHelper;
    private SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userIdEt = (EditText)findViewById(R.id.userIdEditText);
        passwordEt = (EditText)findViewById(R.id.passwordEditText);
    }
    
    public void clickLogin(View view) {
        mySqlHelper = new SQLiteHelper(MainActivity.this);
        String userid = userIdEt.getText().toString();
        String pass = passwordEt.getText().toString();
        String StoredPassword = mySqlHelper.checkUserId(userid);
        if(userid == null || userid.trim().equals("") || pass == null || pass.trim().equals(""))
        {
            Toast.makeText(getApplicationContext(), "Please enter userid and password!", Toast.LENGTH_LONG).show();
        }
        else if(pass.equals(StoredPassword)) {
            Toast.makeText(getApplicationContext(), "Welcome, " +  userid + ". Login Successful:)", Toast.LENGTH_LONG).show();
            Intent startLoggedInActivity = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(startLoggedInActivity);
        }
        else {
            Toast.makeText(getApplicationContext(), "Sorry!, Invalid UserId or Password :(", Toast.LENGTH_LONG).show();
            userIdEt.setText("");
            passwordEt.setText("");
        }
    }

    public void clickRegister(View view) {
        Intent startRegisterActivity = new Intent("ca.georgebrown.comp3074.a2101043939.RegisterActivity");
        startActivity(startRegisterActivity);
    }

    public void cancelEntry(View view) {
        userIdEt.setText("");
        passwordEt.setText("");
    }
}
