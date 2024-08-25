package com.example.finalproject_videogames.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject_videogames.R;
import com.example.finalproject_videogames.fragments.game_details;
import com.example.finalproject_videogames.models.Game;

import java.util.ArrayList;

public class gameListAdapter extends RecyclerView.Adapter<gameListAdapter.MyViewHolder> {

    private ArrayList<Game> gameList;
    private  ArrayList<Game> gameListFull;
    public gameListAdapter(ArrayList<Game> dataSet) {
        this.gameList = dataSet;
        this.gameListFull = new ArrayList<>(gameList);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView textViewName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRes);
            textViewName = itemView.findViewById(R.id.textViewNameCardView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        Game selectedGame = gameList.get(position);
                        Fragment detailFragment = game_details.newInstance(selectedGame);
                        FragmentTransaction transaction = ((AppCompatActivity) view.getContext()).getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragmentContainerView, detailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    public gameListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull gameListAdapter.MyViewHolder holder, int position) {
        TextView textViewName = holder.textViewName;

        textViewName.setText(gameList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public void filter(String text){
        gameList.clear();

        if(text.isEmpty()){
            gameList.addAll(gameListFull);
        }
        else{
            text = text.toLowerCase();
            for (Game game : gameListFull) {
                if (game.getName().toLowerCase().contains(text)){
                    gameList.add(game);
                }

            }
        }
        notifyDataSetChanged();
    }

}


