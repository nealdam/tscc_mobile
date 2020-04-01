package capstone.spring20.tscc_mobile.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


import capstone.spring20.tscc_mobile.R;

public class CustomGridAdapter extends BaseAdapter {
    private List<Bitmap> listData;
    private LayoutInflater layoutInflater;
    private Context context;

    public CustomGridAdapter(Context context, List<Bitmap> list) {
        this.listData = list;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_gridview_layout, null);
            holder = new ViewHolder();
            holder.trashView = convertView.findViewById(R.id.imageView_trash);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Bitmap img = listData.get(position);

        holder.trashView.setImageBitmap(img);

        return convertView;
    }

    static class ViewHolder {
        ImageView trashView;
    }
}
