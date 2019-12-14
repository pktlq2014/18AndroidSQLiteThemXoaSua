package com.example.a18androidsqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CongViecAdapter extends BaseAdapter
{
    // Bước 7
    // muốn gọi dc sửa bên main phải khai báo main ở day
    private MainActivity context;
    private int layout;
    private ArrayList<CongViec> arrayList;

    public CongViecAdapter(MainActivity context, int layout, ArrayList<CongViec> arrayList) {
        this.context = context;
        this.layout = layout;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class viewHolder
    {
        TextView textView1;
        ImageView imageView1, imageView2;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        viewHolder viewHolder;
        if(convertView == null)
        {
            viewHolder = new viewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout, null);
            viewHolder.textView1 = convertView.findViewById(R.id.textView1);
            viewHolder.imageView1 = convertView.findViewById(R.id.imageView1);
            viewHolder.imageView2 = convertView.findViewById(R.id.imageView2);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (CongViecAdapter.viewHolder) convertView.getTag();
        }
        final CongViec congViec = arrayList.get(position);
        viewHolder.textView1.setText(congViec.getTenCV());


        // gọi hàm sửa bên main ở day
        viewHolder.imageView1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context.dialogSuaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        // hàm xóa
        viewHolder.imageView2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                context.dialogXoaCongViec(congViec.getTenCV(), congViec.getIdCV());
            }
        });
        return convertView;
    }
}
