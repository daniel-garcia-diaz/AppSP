package com.example.mysplash;

import android.content.Context;
import android.media.Image;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

public class MyAdapter extends BaseAdapter implements Serializable {

    private List<Info2> list;
    private Context context;
    private LayoutInflater layoutInflater;

    public MyAdapter(List<Info2> list, Context context)
    {
        this.list = list;
        this.context = context;
        if( context != null)
        {
            layoutInflater = LayoutInflater.from(context);
        }
    }
    public boolean isEmptyOrNull( )
    {
        return list == null || list.size() == 0;
    }

    @Override
    public int getCount() {
        if (isEmptyOrNull()) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        if(isEmptyOrNull())
        {
            return null;
        }
        return list.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView = null;
        TextView textView1 = null;
        ImageView imageView =  null;
        view = layoutInflater.inflate(R.layout.activity_lista, null);
        textView = view.findViewById(R.id.textView19);
        textView1 = view.findViewById(R.id.textView20);
        textView1.setText(String.valueOf(list.get(i).getContraContra()));
        textView.setText(String.valueOf(list.get(i).getUsuarioContra()));
        return view;
    }
}
