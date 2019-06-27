package com.mailapp.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mailapp.R;
import com.mailapp.activity.GetMailActivity;
import com.mailapp.activity.NotSendMailActivity;
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
        Button btn_notsend = inflate.findViewById(R.id.btn_notsend);

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
        btn_notsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NotSendMailActivity.class));
            }
        });
    }

}