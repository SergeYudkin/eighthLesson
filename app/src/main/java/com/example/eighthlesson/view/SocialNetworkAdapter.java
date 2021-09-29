package com.example.eighthlesson.view;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    private CardSource dataSource;

    public SocialNetworkAdapter(CardSource dataSource, Fragment fragment) {

        this.dataSource = dataSource;
        this.fragment = fragment;
    }
    private MyOnClickListener listener;

    private Fragment fragment;
    private int menuContextClickPosition;

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

    public int getMenuContextClickPosition() {
        return menuContextClickPosition;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

         TextView title;
        TextView description;
         ImageView imageView;
         CheckBox like;
         CardView cardView;



        public MyViewHolder(View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);
            cardView = itemView.findViewById(R.id.car_view);



            fragment.registerForContextMenu(cardView); // контекстное меню привязяно к картинке, в этом случае.
            imageView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View v){
                    listener.onMyClick(v,getAdapterPosition());
                }
            });
            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    menuContextClickPosition = getAdapterPosition();
                    cardView.showContextMenu(100,100); // координаты выпадающего меню, если не задать координаты то выпадает по середине экрана.
                    return true;
                }
            });
        }
    }
}
