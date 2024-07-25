package com.example.finalproject_videogames.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalproject_videogames.R;
import com.example.finalproject_videogames.models.Game;

import java.io.Serializable;

public class game_details extends Fragment {

    private static final String ARG_GAME = "game";
    private Game game;

    public game_details() {
        // Required empty public constructor
    }

    public static game_details newInstance(Game game) {
        game_details fragment = new game_details();
        Bundle args = new Bundle();
        args.putSerializable(ARG_GAME, (Serializable) game);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            game = (Game) getArguments().getSerializable(ARG_GAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragView = inflater.inflate(R.layout.fragment_game_details, container, false);

        TextView nameTextView = fragView.findViewById(R.id.gameName);
        TextView deckTextView = fragView.findViewById(R.id.gameDeck);
        TextView releaseDateTextView = fragView.findViewById(R.id.gameReleaseDate);
        TextView platformsTextView = fragView.findViewById(R.id.gamePlatforms);
        ImageView imageView = fragView.findViewById(R.id.gameImage);

        if (game != null) {
            nameTextView.setText(game.getName());
            deckTextView.setText(game.getDeck());
            releaseDateTextView.setText(game.getOriginalReleaseDate());

            StringBuilder platformsBuilder = new StringBuilder();
            for (Game.Platform platform : game.getPlatforms()) {
                platformsBuilder.append(platform.getName()).append("\n");
            }
            platformsTextView.setText(platformsBuilder.toString());

            Glide.with(this)
                    .load(game.getImage().getOriginalUrl())
                    .into(imageView);
        }


        return fragView;
    }
}