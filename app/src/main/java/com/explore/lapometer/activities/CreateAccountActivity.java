package com.explore.lapometer.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.explore.lapometer.stubs.LoginStub;
import android.widget.EditText;
import com.explore.lapometer.util.AppConstants;
import com.explore.lapometer.util.Authentication;

import com.explore.lapometer.R;

public class CreateAccountActivity extends Activity {
    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextConfirmPassword;
    Button buttonCreateUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        setComponents();
    }

    private void setComponents() {
        editTextUsername = (EditText) findViewById(R.id.editTextCreateUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextCreatePassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        buttonCreateUser = (Button) findViewById(R.id.buttonCreateAccount);

        editTextConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if( !b ) {
                    if( !editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()) ) {
                        ((EditText) view).setError("Passwords don't match.");
                    }
                }
            }
        });

        buttonCreateUser.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if( username.isEmpty() ) {
                    editTextUsername.setError("Enter a valid username.");
                } else if( password.isEmpty()) {
                    editTextPassword.setError("Enter a valid password.");
                } else if( !editTextPassword.getText().toString().equals(editTextConfirmPassword.getText().toString()) ) {
                    editTextConfirmPassword.setError("Passwords don't match.");
                } else {
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(AppConstants.CREDENTIALS, new Authentication().encryptCredentials(username, password));
                    editor.apply();
                    Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.create_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
