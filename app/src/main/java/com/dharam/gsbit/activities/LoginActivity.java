package com.dharam.gsbit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dharam.gsbit.R;
import com.dharam.gsbit.activities.UploadImageActivity;
import com.dharam.gsbit.model.UserInfo;
import com.dharam.gsbit.sql.UserInfoDAO;
import com.dharam.gsbit.validation_tools.InputValidations;

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumber;
    EditText password;
    Button logInButton;
    UserInfoDAO userInfoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logInButton = (Button) findViewById(R.id.logInButton);
        phoneNumber = (EditText) findViewById(R.id.phoneNumberEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        userInfoDAO = new UserInfoDAO(this);

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo info = new UserInfo();
                info.setPassword(password.getText().toString());
                info.setPhoneNumber(phoneNumber.getText().toString());

                if(!InputValidations.isPhoneNumberValid(info.getPhoneNumber()) || !InputValidations.isPasswordValid(info.getPassword()))
                {
                   showToastForAuthentication();
                    return;
                }

                //the same phone number should not be registered earlier
                if(!userInfoDAO.isUserExisting(info.getPhoneNumber()))
                {
                    Toast.makeText(getApplicationContext(), "User doesn't exist!", Toast.LENGTH_SHORT).show();
                }
                else if(userInfoDAO.authenticateUser(info)) //if the phoneNumber and password are correct
                {
                    Toast.makeText(getApplicationContext(), "Log In Successful!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), UploadImageActivity.class));
                    finish();
                }
                else
                {
                    showToastForAuthentication();
                    return;
                }
            }
        });
    }

    private void showToastForAuthentication()
    {
        Toast.makeText(getApplicationContext(), "Phone number or password incorrect", Toast.LENGTH_SHORT).show();
    }
}
