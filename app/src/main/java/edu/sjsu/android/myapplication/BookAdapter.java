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

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private Context context;
    private ArrayList id, title, author, pages;
    private Activity activity;

    private Animation translate_ani;

    public BookAdapter(Activity activity, Context context, ArrayList id, ArrayList title, ArrayList author, ArrayList pages) {
        this.activity = activity;

        this.context = context;
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
    }

    @NonNull
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.book_row_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.display_id.setText(String.valueOf(id.get(position)));
        holder.display_title.setText(String.valueOf(title.get(position)));
        holder.display_author.setText(String.valueOf(author.get(position)));
        holder.display_pages.setText(String.valueOf(pages.get(position)));

        holder.linear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("title", String.valueOf(title.get(position)));
                intent.putExtra("author", String.valueOf(author.get(position)));
                intent.putExtra("pages", String.valueOf(pages.get(position)));

                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView display_id, display_title, display_author, display_pages;
        LinearLayout linear_layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            display_id = itemView.findViewById(R.id.display_id);
            display_title = itemView.findViewById(R.id.display_title);
            display_author = itemView.findViewById(R.id.display_author);
            display_pages = itemView.findViewById(R.id.display_pages);

            linear_layout = itemView.findViewById(R.id.book_linear_layout);
            translate_ani = AnimationUtils.loadAnimation(context, R.anim.translate_ani);
            linear_layout.setAnimation(translate_ani);
        }
    }
}
