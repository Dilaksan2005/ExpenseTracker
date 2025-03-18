package com.example.expensetracker;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    static Context context;
    static ArrayList<String> uType, uAmount, uDescription;
    static LayoutInflater layoutInflater;

    public MyAdapter(Context c, ArrayList<String> t, ArrayList<String> a, ArrayList<String> d)
    {
        context = c;
        uType = t;
        uAmount = a;
        uDescription = d;
        layoutInflater = LayoutInflater.from(c);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.listme, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.type.setText(String.valueOf(uType.get(position)));
        holder.amount.setText(String.valueOf(uAmount.get(position)));
        holder.description.setText(String.valueOf(uDescription.get(position)));

    }

    @Override
    public int getItemCount() {
        return uType.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView type, amount, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            type = itemView.findViewById(R.id.textView8);
            amount = itemView.findViewById(R.id.textView18);
            description = itemView.findViewById(R.id.textView19);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, EditExpense.class);
                    intent.putExtra("type", uType.get(getAdapterPosition()));
                    intent.putExtra("amount", uAmount.get(getAdapterPosition()));
                    intent.putExtra("description", uDescription.get(getAdapterPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }

}
