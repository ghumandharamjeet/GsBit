package com.dharam.gsbit.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dharam.gsbit.R;
import com.dharam.gsbit.model.UserInfo;
import com.dharam.gsbit.sql.UserInfoDAO;
import com.dharam.gsbit.validation_tools.InputValidations;

public class SignUpActivity extends AppCompatActivity {

    EditText personName;
    EditText password;
    EditText confirmPassword;
    EditText phoneNumber;
    Button signupButton;
    UserInfoDAO userInfoDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        personName = (EditText) findViewById(R.id.nameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);
        confirmPassword = (EditText) findViewById(R.id.confirmPasswordEditText);
        phoneNumber = (EditText) findViewById(R.id.phoneEditText);

        signupButton = (Button) findViewById(R.id.signUpButton);

        userInfoDAO = new UserInfoDAO(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UserInfo info = new UserInfo(personName.getText().toString(), password.getText().toString(), phoneNumber.getText().toString());

                if(validateInputs(info))
                {
                    boolean success = saveInfo(info);

                    if(success)
                    {
                        Toast.makeText(getApplicationContext(), "SuccessFully Registered! You can Log In.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    /**
     * This method will validate all the inputs (password, name and phone number)
     * @param info
     * @return
     */
    private boolean validateInputs(UserInfo info)
    {
        String confirmPasswordEntered = confirmPassword.getText().toString();

        if( !InputValidations.isValidString(info.getPassword()) || !InputValidations.isValidString(info.getPersonName()) || !InputValidations.isValidString(info.getPhoneNumber()))
        {
            Toast.makeText(this, "All the fields are mandatory", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!InputValidations.isPhoneNumberValid(info.getPhoneNumber()))
        {
            Toast.makeText(this, "Phone Number is not correct", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!InputValidations.isPasswordValid(info.getPassword()))
        {
            Toast.makeText(this, "Password must be more than 6 characters", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!InputValidations.isPasswordValid(confirmPasswordEntered) || !info.getPassword().equals(confirmPasswordEntered))
        {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    /**
     * This method will save the user's info to the database
     * @param info
     * @return
     */
    private boolean saveInfo(UserInfo info)
    {
        if(userInfoDAO.isUserExisting(info.getPhoneNumber()))
        {
            Toast.makeText(this, "User Already Exists!!", Toast.LENGTH_SHORT).show();
            return false;
        }

        userInfoDAO.saveUser(info);

        return true;
    }

}
