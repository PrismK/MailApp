package com.mailapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.mailapp.R;
import com.mailapp.bean.NewsInfo;
import com.mailapp.view.MyImageView;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private List<NewsInfo> newsList;

    public NewsAdapter(Context context, List<NewsInfo> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
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
            convertView = View.inflate(context, R.layout.item_news, null);
            viewHolder.miv_image = convertView.findViewById(R.id.miv_image);
            viewHolder.tv_desc = convertView.findViewById(R.id.tv_content);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_news_title);
            viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_title.setText(newsList.get(position).getTitle());
        viewHolder.tv_type.setText(newsList.get(position).getType());
        viewHolder.tv_desc.setText(newsList.get(position).getDescription().substring(0, 47) + "...");
        viewHolder.miv_image.setImageUrl(newsList.get(position).getImage());
        return convertView;
    }

    private static class ViewHolder {
        private MyImageView miv_image;
        private TextView tv_title;
        private TextView tv_desc;
        private TextView tv_type;
    }
}
