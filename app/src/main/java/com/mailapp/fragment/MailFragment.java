package com.mailapp.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.mailapp.R;
import com.mailapp.activity.GetMailActivity;
import com.mailapp.activity.LoginActivity;
import com.mailapp.activity.WriteMailActivity;
import com.mailapp.activity.SendMailActivity;

public class MailFragment extends Fragment {

    private View inflate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        inflate = View.inflate(getActivity(), R.layout.fragment_mail, null);
        initView();
        return inflate;
    }

    private void initView() {
        Button btn_getmail = inflate.findViewById(R.id.btn_getmail);
        Button btn_sendmail = inflate.findViewById(R.id.btn_sendmail);
        Button btn_writemail = inflate.findViewById(R.id.btn_writemail);
        TextView tv_logout = inflate.findViewById(R.id.tv_logout);

        btn_getmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GetMailActivity.class));
            }
        });
        btn_sendmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SendMailActivity.class));
            }
        });
        btn_writemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WriteMailActivity.class));
            }
        });
        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("警告！").setMessage("确定退出登陆吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences login_sp = getActivity().getSharedPreferences("userInfo", 0);
                                SharedPreferences.Editor edit = login_sp.edit();
                                edit.clear();
                                edit.commit();
                                startActivity(new Intent(getActivity(), LoginActivity.class));
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
    }

}