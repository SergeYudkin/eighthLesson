package com.example.eighthlesson.view;

import android.os.Build;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eighthlesson.R;
import com.example.eighthlesson.model.CardData;
import com.example.eighthlesson.model.CardSource;

public class SocialNetworkAdapter extends RecyclerView.Adapter<SocialNetworkAdapter.MyViewHolder> {

    public void setDataSource(CardSource dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    private CardSource dataSource;



    private TextView data;

    public SocialNetworkAdapter(Fragment fragment) {

        this.fragment = fragment;
    }
    private MyOnClickListener listener;

    private final Fragment fragment;
    private int menuContextClickPosition;

    public void setOnMyOnClickListener(MyOnClickListener listener){
        this.listener = listener;
    }

    public  void  setData (CardSource dataSource){
        this.dataSource = dataSource; }

    public TextView getData() {
        return data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, @NonNull int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view,parent,false);

        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,int position) {


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
         TextView date;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            description = itemView.findViewById(R.id.description);
            title = itemView.findViewById(R.id.title);
            imageView = itemView.findViewById(R.id.imageView);
            like = itemView.findViewById(R.id.like);
            cardView = itemView.findViewById(R.id.car_view);
            data = itemView.findViewById(R.id.date);



            fragment.registerForContextMenu(cardView); // контекстное меню привязяно к картинке, в этом случае.
            imageView.setOnClickListener(v -> listener.onMyClick(v,getAdapterPosition()));
            cardView.setOnLongClickListener(new View.OnLongClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View view) {
                    menuContextClickPosition = getAdapterPosition();
                    cardView.showContextMenu(100,100); // координаты выпадающего меню, если не задать координаты то выпадает по середине экрана.
                    return true;
                }
            });
        }

        public void setData (CardData cardData){
            title.setText(cardData.getTitle());
            data.setText(cardData.getData().toString());
            description.setText(cardData.getDescription());
            like.setChecked(cardData.isLike());
            imageView.setImageResource(cardData.getPicture());
        }


    }
}
