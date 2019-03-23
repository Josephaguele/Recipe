package com.example.joseph.recipe;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
    private Context mContext;
    private ArrayList<Recipe> mRecipeList;


    public RecipeAdapter(Context context, ArrayList<Recipe> recipeList) {
        mContext = context;
        mRecipeList = recipeList;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        holder.bindData(position);
    }


    @Override
    public int getItemCount() {
        return mRecipeList.size();
    }


    public void setRecipe(ArrayList<Recipe> data) {
        mRecipeList = data;
        notifyDataSetChanged();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;
        TextView mRecipeTextView;
        TextView mServingsTextView;

        public RecipeViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image_view);
            mRecipeTextView = itemView.findViewById(R.id.text_view_recipe);
            mServingsTextView = itemView.findViewById(R.id.text_view_servings);
        }


        public void bindData(int position) {
            Recipe recipe = mRecipeList.get(position);

            mServingsTextView.setText(String.valueOf(recipe.getServings()));
            mRecipeTextView.setText(String.valueOf(recipe.getName()));


            // If the image from the json parsed is not empty, load it
            if (!recipe.getImage().isEmpty()) {
                Picasso.with(mContext).load(recipe.getImage()).into(mImageView);
                //Picasso.with(mContext).load(recipe.getImage()).fit().centerInside().into(mImageView);

                // else if the recipe name is Nutella Pie, load the image for nutella pie
            } else if (recipe.getName().equals("Nutella Pie")) {
                mImageView.setImageResource(R.drawable.nutella_pie);
            } else if (recipe.getName().equals("Brownies")) {
                mImageView.setImageResource(R.drawable.brownies);
            } else if (recipe.getName().equals("Yellow Cake")) {
                mImageView.setImageResource(R.drawable.yellow_cake);
            } else {
                recipe.getName().equals("Cheesecake");
                mImageView.setImageResource(R.drawable.cheesecake);
            }
        }
    }
}
