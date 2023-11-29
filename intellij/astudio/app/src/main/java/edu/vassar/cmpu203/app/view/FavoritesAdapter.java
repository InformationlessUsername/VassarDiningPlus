package edu.vassar.cmpu203.app.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.vassar.cmpu203.app.R;
import edu.vassar.cmpu203.app.model.Dish;
import edu.vassar.cmpu203.app.model.Menu;
import edu.vassar.cmpu203.app.model.User;

public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final IManageProfile.Listener listener;

    final List<Dish> dishes;
    final User user;

    public FavoritesAdapter(Menu menu, User user, IManageProfile.Listener listener) {
        this.listener = listener;

        // Create list of dishes
        dishes = new ArrayList<>(menu.getDishes().values());

        // Set user
        this.user = user;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.dish_view, parent, false);
        return new FavoritesViewHolder(view, this.listener);
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FavoritesViewHolder favoritesViewHolder = (FavoritesViewHolder) holder;
        Dish dish = dishes.get(position);
        favoritesViewHolder.bind(dish, this.user.isFavorite(dish));
    }



    @Override
    public int getItemCount() {
        return dishes.size();
    }


}