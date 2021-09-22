package com.example.eighthlesson;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private CardSource dataSource;

    public SocialNetworkAdapter(CardSource dataSource) {

        this.dataSource = dataSource;
    }

    private MyOnClickListener listener;
    public void setOnMyOnClickListener(MyOnClickListener listener){
        this.listener = listener;
    }

    public  void  setData (CardSource dataSource){ this.dataSource = dataSource; }


    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.title.setText(dataSource.getCardData(position).getTitle());
        holder.description.setText(dataSource.getCardData(position).getDescription());
        holder.imageView.setImageResource(dataSource.getCardData(position).getPicture());
        holder.like.setChecked(dataSource.getCardData(position).isLike());

    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView title;
        TextView description;
         ImageView imageView;
         CheckBox like;


    //    public TextView getTextView() {return textView;}

       // public void setTextView(TextView textView) {this.textView = textView;}

        public MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);

            imageView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v){
                    listener.onMyClick(v,getAdapterPosition());
                }
            });
        }
    }
}
