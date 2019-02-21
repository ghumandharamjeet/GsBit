package com.dharam.gsbit.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dharam.gsbit.R;
import com.dharam.gsbit.adapters.UserInfoAdapter;
import com.dharam.gsbit.model.UserInfo;
import com.dharam.gsbit.sql.UserInfoDAO;

import java.util.List;

public class ShowAllRecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_records);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.userInfoRecyclerView);
        TextView textviewUsers = (TextView) findViewById(R.id.textViewUsers);

        UserInfoDAO userInfoDAO = new UserInfoDAO(this);

        List<UserInfo> userInfoList = userInfoDAO.getAllUsers();

        //if there is no record existing
        if(userInfoList.isEmpty())
        {
            textviewUsers.setText("No Users Registered Yet.");
            recyclerView.setVisibility(View.GONE);
        }
        else
        {
            UserInfoAdapter adapter = new UserInfoAdapter(userInfoList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(adapter);
        }

    }
}
