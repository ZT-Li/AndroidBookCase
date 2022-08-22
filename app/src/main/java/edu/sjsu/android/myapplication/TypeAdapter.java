package edu.sjsu.android.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder>{

    private Context context;
    private ArrayList type;
    private Activity activity;

    private Animation translate_ani;

    public TypeAdapter (Activity activity, Context context, ArrayList type) {
        this.activity = activity;
        this.context = context;
        this.type = type;
    }

    @NonNull
    @Override
    public TypeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.type_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.display_type.setText(String.valueOf(type.get(position)));

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DisplayBook.class);
                intent.putExtra("type", String.valueOf(type.get(position)));

                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return type.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView display_type;
        LinearLayout linear_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            display_type = itemView.findViewById(R.id.display_type);

            linear_layout = itemView.findViewById(R.id.type_linear_layout);
            translate_ani = AnimationUtils.loadAnimation(context, R.anim.translate_ani);
            linear_layout.setAnimation(translate_ani);
        }
    }
}
