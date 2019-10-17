package com.Tourism.tourism_app;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Tourism.tourism_app.RoomDB.Pack_Item_Tbl;

import java.util.ArrayList;


public class Package_Item_Adpt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void OnItemClick(Pack_Item_Tbl model);

    }


    private int flag = 0;
    ArrayList<Pack_Item_Tbl> arrayList1;
    Context context;

    ArrayList<Pack_Item_Tbl> arrayList;

    public Package_Item_Adpt(Context context, ArrayList<Pack_Item_Tbl> arrayList, OnItemClickListener listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        this.listener = listener;


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewtype) {


            View v1 = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.adpt_package_item, viewGroup, false);

            return new Viewholder(v1);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {


        viewHolder.setIsRecyclable(false);
        Viewholder vh = (Viewholder) viewHolder ;

        Pack_Item_Tbl model = arrayList.get(pos);

        vh.bind(model,listener);

        vh.name.setText(model.getTitle());
        vh.desc.setText(model.getDesc());


        byte[] image = model.getImage();

        if (image != null)
        {
            vh.image.setVisibility(View.VISIBLE);

            Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
            vh.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                    200, false));
        }
        else
        {
            vh.image.setVisibility(View.GONE);
        }

    }



    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        TextView name,desc;
        ImageView image;

        public Viewholder(@NonNull View v )
        {
            super(v);

            name = (TextView) v.findViewById(R.id.title);
            image = (ImageView) v.findViewById(R.id.image);
            desc = (TextView) v.findViewById(R.id.desc);

        }


        public void bind(final Pack_Item_Tbl item , final OnItemClickListener listener)
        {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    listener.OnItemClick(item);
                }
            });

        }

    }



}
