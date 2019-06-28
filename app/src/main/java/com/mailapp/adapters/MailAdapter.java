package com.mailapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mailapp.R;
import com.mailapp.bean.MailInfo;

import java.util.List;

public class MailAdapter extends BaseAdapter {
    private Context context;
    private List<MailInfo> mailInfoList;

    public MailAdapter(Context context, List<MailInfo> mailInfoList) {
        this.context = context;
        this.mailInfoList = mailInfoList;
    }

    @Override
    public int getCount() {
        return mailInfoList.size();
    }

    @Override
    public Object getItem(int position) {
        return mailInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_mail, null);
            viewHolder.tv_mailSendTime_day = convertView.findViewById(R.id.tv_mailSendTime);
            viewHolder.tv_mailTitle = convertView.findViewById(R.id.tv_mailTitle);
            viewHolder.tv_mailContext = convertView.findViewById(R.id.tv_mailContext);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_mailSendTime_day.setText(mailInfoList.get(position).getMailSendTime());
        viewHolder.tv_mailTitle.setText(mailInfoList.get(position).getMailTitle());
        viewHolder.tv_mailContext.setText(mailInfoList.get(position).getMailContext());
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_mailSendTime_day;
        private TextView tv_mailTitle;
        private TextView tv_mailContext;
    }
}
