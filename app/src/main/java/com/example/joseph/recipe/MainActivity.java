package com.example.joseph.recipe;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.example.joseph.recipe.NetworkUtilities.BASEURL;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Recipe>> {

    private RecyclerView mRecyclerView;
    private RecipeAdapter mRecipeAdapter;
    private ArrayList<Recipe> mRecipeList;
    private LinearLayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Recipe> recipeList = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);

        // View holder objects are managed by an adapter. The adapter creates view holders as needed.

        mRecipeAdapter = new RecipeAdapter(this, recipeList);
        mRecyclerView.setAdapter(mRecipeAdapter);

        LoaderManager loaderManager = getLoaderManager();
        // for calling the LoaderManager in the AsyncTaskLoader class.
        loaderManager.initLoader(1, null, this).forceLoad();
    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        Log.i("MainActivity", "trying to parse JSON");

        return new RecipeLoader(this, BASEURL);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> data) {
        if (data != null && !data.isEmpty()) {

            //i tested that the json data is generated properly and comes up by making sure that the generated data appears has a toast message
            String d = data.toString();
            //Toast.makeText(this,"Data not empty",Toast.LENGTH_SHORT).show();
            //  Toast.makeText(this, d,Toast.LENGTH_LONG).show();
            mRecipeAdapter.setRecipe((ArrayList<Recipe>) data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {

    }
}