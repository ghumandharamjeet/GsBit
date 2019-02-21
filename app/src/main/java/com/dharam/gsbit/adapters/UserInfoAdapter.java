package com.dharam.gsbit.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dharam.gsbit.R;
import com.dharam.gsbit.model.UserInfo;

import java.util.List;

public class UserInfoAdapter extends RecyclerView.Adapter<UserInfoAdapter.UserInfoViewHolder> {

    List<UserInfo> userInfoList;

    public UserInfoAdapter(List<UserInfo> userInfoList)
    {
        this.userInfoList = userInfoList;
    }

    @NonNull
    @Override
    public UserInfoAdapter.UserInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View infoView = inflater.inflate(R.layout.layout_users_info, parent, false);

        UserInfoViewHolder userInfoViewHolder = new UserInfoViewHolder(infoView);

        return userInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserInfoViewHolder holder, int position) {

        UserInfo info = userInfoList.get(position);

        holder.phoneNumberTextView.setText(info.getPhoneNumber());
        holder.nameTextView.setText(info.getPersonName());
    }

    @Override
    public int getItemCount() {


        return userInfoList.size();
    }

    public static class UserInfoViewHolder extends RecyclerView.ViewHolder
    {
        TextView phoneNumberTextView;
        TextView nameTextView;

        public UserInfoViewHolder(View itemView)
        {
            super(itemView);
            phoneNumberTextView = (TextView) itemView.findViewById(R.id.phoneNumber);
            nameTextView = (TextView) itemView.findViewById(R.id.name);

        }
    }
}
