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


import com.Tourism.tourism_app.RoomDB.Category_Tbl;

import java.util.ArrayList;


public class Category_Adpt extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    OnItemClickListener listener;

    public interface OnItemClickListener
    {
        void OnItemClick(Category_Tbl model);

    }


    private int flag = 0;
    ArrayList<Category_Tbl> arrayList1;
    Context context;

    ArrayList<Category_Tbl> arrayList;

    public Category_Adpt(Context context, ArrayList<Category_Tbl> arrayList, OnItemClickListener listener) {
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
                    .inflate(R.layout.adpt_category, viewGroup, false);

            return new Viewholder(v1);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int pos) {


        viewHolder.setIsRecyclable(false);
        Viewholder vh = (Viewholder) viewHolder ;

        Category_Tbl model = arrayList.get(pos);

        vh.bind(model,listener);

        vh.name.setText(model.getTitle());
        vh.desc.setText(model.getDesc());

        if (model.getDesc().trim().equals(""))
        {
            vh.desc.setVisibility(View.GONE);
        }
        else
        {
            vh.desc.setVisibility(View.VISIBLE);
        }

        byte[] image = model.getImage();
        Bitmap bmp = BitmapFactory.decodeByteArray(image, 0, image.length);
        vh.image.setImageBitmap(Bitmap.createScaledBitmap(bmp, 200,
                200, false));



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
            desc = (TextView) v.findViewById(R.id.desc);
            image = (ImageView) v.findViewById(R.id.image);
        }


        public void bind(final Category_Tbl item , final OnItemClickListener listener)
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
