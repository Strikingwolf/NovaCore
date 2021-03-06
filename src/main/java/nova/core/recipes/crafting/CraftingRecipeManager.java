package nova.core.recipes.crafting;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import nova.core.item.ItemStack;
import nova.core.recipes.RecipeAddedEvent;
import nova.core.recipes.RecipeManager;
import nova.core.recipes.RecipeRemovedEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Manages crafting recipes and has functions to efficiently lookup a crafting recipe.
 *
 * @author Stan Hebben
 */
public class CraftingRecipeManager {
    private final RecipeManager recipeManager;
    private final List<CraftingRecipe> dynamicRecipes;
    private final Multimap<String, CraftingRecipe> staticRecipes;

    public CraftingRecipeManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
        this.dynamicRecipes = new ArrayList<>();
        this.staticRecipes = ArrayListMultimap.create();

        recipeManager.addRecipeAddedListener(CraftingRecipe.class, this::onCraftingRecipeAdded);
        recipeManager.addRecipeRemovedListener(CraftingRecipe.class, this::onCraftingRecipeRemoved);
    }

    /**
     * Adds a recipe. Adds it to the global recipe list as CraftingRecipe.
     *
     * @param recipe
     */
    public void addRecipe(CraftingRecipe recipe) {
        recipeManager.addRecipe(recipe);
    }

    /**
     * Removes a recipes. Removes if from the global recipe list.
     *
     * @param recipe
     */
    public void removeRecipe(CraftingRecipe recipe) {
        recipeManager.removeRecipe(recipe);
    }

    /**
     * Gets the recipe that matches the given crafting grid.
     *
     * @param grid crafting grid
     * @return matching crafting recipe, if any
     */
    public Optional<CraftingRecipe> getRecipe(CraftingGrid grid) {
        for (CraftingRecipe dynamicRecipe : dynamicRecipes) {
            if (dynamicRecipe.matches(grid))
                return Optional.of(dynamicRecipe);
        }

        Optional<ItemStack> firstItem = grid.getFirstNonEmptyItem();
        if (!firstItem.isPresent())
            return Optional.empty();

        String firstItemId = firstItem.get().getItem().getID();
        if (!staticRecipes.containsKey(firstItemId))
            return Optional.empty();

        for (CraftingRecipe staticRecipe : staticRecipes.get(firstItemId)) {
            if (staticRecipe.matches(grid))
                return Optional.of(staticRecipe);
        }

        return Optional.empty();
    }

    // #######################
    // ### Private Methods ###
    // #######################

    private void onCraftingRecipeAdded(RecipeAddedEvent<CraftingRecipe> e) {
        Optional<Collection<String>> possibleFirstItemIds = e.getRecipe().getPossibleItemsInFirstSlot();
        if (possibleFirstItemIds.isPresent()) {
            for (String itemId : possibleFirstItemIds.get()) {
                staticRecipes.put(itemId, e.getRecipe());
            }
        } else {
            dynamicRecipes.add(e.getRecipe());
        }
    }

    private void onCraftingRecipeRemoved(RecipeRemovedEvent<CraftingRecipe> e) {
        Optional<Collection<String>> possibleFirstItemIds = e.getRecipe().getPossibleItemsInFirstSlot();
        if (possibleFirstItemIds.isPresent()) {
            for (String itemId : possibleFirstItemIds.get()) {
                staticRecipes.remove(itemId, e.getRecipe());
            }
        } else {
            dynamicRecipes.remove(e.getRecipe());
        }
    }
}
