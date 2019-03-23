package com.example.joseph.recipe;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.joseph.recipe.NetworkUtilities.TAG;

public class JsonUtilities {

    // Fields of the RECIPES
    private static final String RECIPE_ID = "id";
    private static final String RECIPE_NAME = "name";
    private static final String SERVINGS = "servings";
    private static final String RECIPE_IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";
    private static final String STEPS = "steps";

    // Fields of INGREDIENTS OF EACH RECIPE
    private static final String INGREDIENT_QUANTITY = "quantity";
    private static final String INGREDIENT_NAME = "ingredient";
    private static final String INGREDIENT_MEASURE = "measure";

    // Fields of STEPS OF EACH RECIPE
    private static final String STEPS_ID = "id";
    private static final String STEPS_SHORT_DESC = "shortDescription";
    private static final String STEPS_LONG_DESC = "description";
    private static final String STEPS_VIDEO_URL = "videoURL";
    private static final String STEPS_THUMBNAIL_URL = "thumbnailURL";

    /// empty constructor
    public JsonUtilities() {
    }

    public static ArrayList<Recipe> extractFeatureFromJson(String bakingRecipeJSON) {

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(bakingRecipeJSON)) {
            return null;
        }

        ArrayList<Recipe> recipes = new ArrayList<>();
        try {
            // Create a JSONObject from the JSON response string
            JSONArray recipeJson = new JSONArray(bakingRecipeJSON);
            // Try to parse the JSON response string. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            for (int i = 0; i < recipeJson.length(); i++) {
                // Get a single recipe description at position i within the list of recipes provided in the array
                JSONObject currentRecipe = recipeJson.optJSONObject(i);

                // Extract values for the following keys in the json provided
                // These are the first top level of the json parsing
                int recipeId = currentRecipe.optInt(RECIPE_ID);
                String recipeName = currentRecipe.optString(RECIPE_NAME);
                int servings = currentRecipe.optInt(SERVINGS);
                String recipeImage = currentRecipe.optString(RECIPE_IMAGE);


                ArrayList<Ingredient> ingredients = new ArrayList<>();
                JSONArray ingredientJson = currentRecipe.optJSONArray(INGREDIENTS);

                for (int j = 0; j < ingredientJson.length(); j++) {
                    // This gets a single ingredient at position j within the list of recipes provided
                    JSONObject thisIngredient = ingredientJson.getJSONObject(j);

                    // Then extract the values for the following keys
                    String ingredientName = thisIngredient.optString(INGREDIENT_NAME);
                    String ingredientMeasure = thisIngredient.optString(INGREDIENT_MEASURE);
                    int ingredientQuantity = thisIngredient.optInt(INGREDIENT_QUANTITY);

                    Ingredient ing = new Ingredient(ingredientQuantity, ingredientMeasure, ingredientName);
                    ingredients.add(ing);
                }

                ArrayList<Step> stepsList = new ArrayList<>();
                JSONArray stepsJson = currentRecipe.optJSONArray(STEPS);

                for (int s = 0; s < stepsJson.length(); s++) {
                    // This gets a single step at position c within the list of recipes provided
                    JSONObject thisStep = stepsJson.optJSONObject(s);

                    // Then extract the values for the following keys
                    int stepId = thisStep.optInt(STEPS_ID);
                    String stepLongDesc = thisStep.optString(STEPS_LONG_DESC);
                    String stepShortDesc = thisStep.optString(STEPS_SHORT_DESC);
                    String videoUrl = thisStep.optString(STEPS_VIDEO_URL);
                    String thumbnailURL = thisStep.optString(STEPS_THUMBNAIL_URL);

                    Step step = new Step(stepId, stepShortDesc, stepLongDesc, videoUrl, thumbnailURL);
                    stepsList.add(step);
                }

                Recipe recipe = new Recipe(
                        recipeId, recipeName, ingredients,
                        stepsList, servings, recipeImage
                );
                recipes.add(recipe);
                Log.i(TAG, recipes.toString());
            }


        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the JSON result", e);
        }
        return recipes;
    }


}
