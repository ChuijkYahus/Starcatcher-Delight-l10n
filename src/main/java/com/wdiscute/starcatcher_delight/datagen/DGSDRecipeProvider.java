package com.wdiscute.starcatcher_delight.datagen;

import com.github.ysbbbbbb.kaleidoscopecookery.KaleidoscopeCookery;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.ChoppingBoardBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.datagen.builder.PotRecipeBuilder;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModItems;
import com.github.ysbbbbbb.kaleidoscopecookery.init.tag.TagCommon;
import com.wdiscute.starcatcher.SCTags;
import com.wdiscute.starcatcher.datagen.DGSCRecipeProvider;
import com.wdiscute.starcatcher.registry.SCItems;
import com.wdiscute.starcatcher_delight.StarcatcherDelight;
import com.wdiscute.starcatcher_delight.registry.SDItems;
import com.wdiscute.utils.Utils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.satisfy.farm_and_charm.core.recipe.CookingPotRecipe;
import net.satisfy.farm_and_charm.core.recipe.MincerRecipe;
import net.satisfy.farm_and_charm.core.registry.ObjectRegistry;
import vectorwing.farmersdelight.client.recipebook.CookingPotRecipeBookTab;
import vectorwing.farmersdelight.common.tag.CommonTags;
import vectorwing.farmersdelight.data.builder.CookingPotRecipeBuilder;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static vectorwing.farmersdelight.common.registry.ModItems.*;

public class DGSDRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    public DGSDRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries)
    {
        super(output, registries);
    }

    public static final int FAST_COOKING = 100;      // 5 seconds
    public static final int NORMAL_COOKING = 200;    // 10 seconds
    public static final int SLOW_COOKING = 400;      // 20 seconds

    public static final float SMALL_EXP = 0.35F;
    public static final float MEDIUM_EXP = 1.0F;
    public static final float LARGE_EXP = 2.0F;


    @Override
    protected void buildRecipes(RecipeOutput output)
    {
        //farmers delight recipes
        RecipeOutput famerDelightOutput = output.withConditions(modLoaded("farmersdelight"));
        FarmersDelightRecipes.cookMeals(famerDelightOutput);

        FarmersDelightRecipes.cutRecipe(SCTags.COMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getCommon(), famerDelightOutput);
        FarmersDelightRecipes.cutRecipe(SCTags.UNCOMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getUncommon(), famerDelightOutput);
        FarmersDelightRecipes.cutRecipe(SCTags.RARE_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getRare(), famerDelightOutput);
        FarmersDelightRecipes.cutRecipe(SCTags.EPIC_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getEpic(), famerDelightOutput);
        FarmersDelightRecipes.cutRecipe(SCTags.LEGENDARY_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getLegendary(), famerDelightOutput);

        //kaleidoscope recipes
        RecipeOutput kaleidoscopeOutput = output.withConditions(modLoaded("kaleidoscope_cookery"));
        KaleidoscopeRecipes.potMeals(kaleidoscopeOutput);

        KaleidoscopeRecipes.cutRecipe(SCTags.COMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getCommon(), kaleidoscopeOutput);
        KaleidoscopeRecipes.cutRecipe(SCTags.UNCOMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getUncommon(), kaleidoscopeOutput);
        KaleidoscopeRecipes.cutRecipe(SCTags.RARE_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getRare(), kaleidoscopeOutput);
        KaleidoscopeRecipes.cutRecipe(SCTags.EPIC_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getEpic(), kaleidoscopeOutput);
        KaleidoscopeRecipes.cutRecipe(SCTags.LEGENDARY_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getLegendary(), kaleidoscopeOutput);

        //let's do recipes
        RecipeOutput letsdoOutput = output.withConditions(modLoaded("farm_and_charm"));
        LetsDoRecipes.cookMeals(letsdoOutput);

        LetsDoRecipes.cutRecipe(SCTags.COMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getCommon(), letsdoOutput);
        LetsDoRecipes.cutRecipe(SCTags.UNCOMMON_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getUncommon(), letsdoOutput);
        LetsDoRecipes.cutRecipe(SCTags.RARE_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getRare(), letsdoOutput);
        LetsDoRecipes.cutRecipe(SCTags.EPIC_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getEpic(), letsdoOutput);
        LetsDoRecipes.cutRecipe(SCTags.LEGENDARY_STARCAUGHT_FISHES, SDItems.STARCAUGHT_FILLET.getLegendary(), letsdoOutput);
    }

    private static class LetsDoRecipes
    {
        public static final TagKey<Item> DOUGH = DGSDRecipeProvider.commonTag("dough");
        private static void cutRecipe(TagKey<Item> input, ItemLike output, RecipeOutput o)
        {
            cutRecipe(Ingredient.of(input), output, o);
        }

        private static void cutRecipe(Ingredient input, ItemLike output, RecipeOutput o)
        {
            ResourceLocation id = StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(output.asItem()).getPath()).withPrefix("lets_do/");
            Advancement.Builder advancementBuilder = o.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                    .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);

            MincerRecipe recipe = new MincerRecipe("MEAT", input, new ItemStack(output));
            o.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/")));
        }

        public static class Builder
        {
            public Builder(Item output)
            {
                this.output = new ItemStack(output);
            }

            final ItemStack output;

            List<Ingredient> ingredients = new ArrayList<>();

            public Builder addIngredient(TagKey<Item> tagIn)
            {
                ingredients.add(Ingredient.of(tagIn));
                return this;
            }

            public Builder addIngredient(DeferredItem<Item> item)
            {
                ingredients.add(Ingredient.of(item));
                return this;
            }

            public Builder addIngredient(Item item)
            {
                ingredients.add(Ingredient.of(item));
                return this;
            }

            public void save(RecipeOutput o, ResourceLocation id)
            {
                NonNullList<Ingredient> list = NonNullList.create();

                list.addAll(ingredients);

                Advancement.Builder advancementBuilder = o.advancement().addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                        .rewards(net.minecraft.advancements.AdvancementRewards.Builder.recipe(id)).requirements(AdvancementRequirements.Strategy.OR);

                CookingPotRecipe recipe = new CookingPotRecipe(list, false, Items.BOWL.getDefaultInstance(), output, false);
                o.accept(id, recipe, advancementBuilder.build(id.withPrefix("recipes/")));
            }
        }

        private static void cookMeals(RecipeOutput output)
        {
            //special
            new Builder(SDItems.CACTIFISH_STEW.get())
                    .addIngredient(SCItems.CACTIFISH)
                    .addIngredient(Items.CACTUS)
                    .addIngredient(CommonTags.Items.CROPS_TOMATO)
                    .addIngredient(CommonTags.Items.CROPS_CABBAGE)
                    .addIngredient(CommonTags.Items.CROPS_ONION)
                    .save(output, SDItems.CACTIFISH_STEW.getId().withPrefix("lets_do/"));

            new Builder(SDItems.MAGMA_FISH_BALLS.get())
                    .addIngredient(SCTags.WORMS)
                    .addIngredient(SCItems.MAGMA_FISH)
                    .addIngredient(CommonTags.Items.CROPS_TOMATO)
                    .addIngredient(DOUGH)
                    .addIngredient(Items.EGG)
                    .save(output, SDItems.MAGMA_FISH_BALLS.getId().withPrefix("lets_do/"));

            new Builder(SDItems.SLUDGE_STEW.get())
                    .addIngredient(SCItems.SLUDGE_CATFISH)
                    .addIngredient(ObjectRegistry.ROTTEN_TOMATO.get())
                    .addIngredient(Items.DIRT)
                    .addIngredient(SCItems.WORM)
                    .addIngredient(ObjectRegistry.ONION.get())
                    .addIngredient(ObjectRegistry.LETTUCE.value())
                    .save(output, SDItems.SLUDGE_STEW.getId().withPrefix("lets_do/"));

            new Builder(SDItems.BLOSSOM_TOAST.get())
                    .addIngredient(SCItems.BLOSSOMFISH)
                    .addIngredient(ObjectRegistry.LETTUCE.value())
                    .addIngredient(Items.BREAD)
                    .addIngredient(Items.PINK_PETALS)
                    .save(output, SDItems.BLOSSOM_TOAST.getId().withPrefix("lets_do/"));

            new Builder(SDItems.STEAMED_REDSCALED_TUNA.get())
                    .addIngredient(SCItems.REDSCALED_TUNA)
                    .addIngredient(ObjectRegistry.LETTUCE.value())
                    .addIngredient(ObjectRegistry.LETTUCE.value())
                    .addIngredient(ObjectRegistry.ONION.value())
                    .save(output, SDItems.STEAMED_REDSCALED_TUNA.getId().withPrefix("lets_do/"));

            new Builder(SDItems.GRILLED_SHROOMFISH.get())
                    .addIngredient(SCItems.SHROOMFISH)
                    .addIngredient(ObjectRegistry.LETTUCE.value())
                    .addIngredient(Items.POTATO)
                    .save(output, SDItems.GRILLED_SHROOMFISH.getId().withPrefix("lets_do/"));


            new Builder(SDItems.SPORE_NIGIRI.get())
                    .addIngredient(SCItems.SPOREFISH)
                    .addIngredient(ObjectRegistry.BARLEY.get())
                    .addIngredient(ObjectRegistry.BARLEY.get())
                    .save(output, SDItems.SPORE_NIGIRI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.SWEET_BERRY_TAIYAKI.get())
                    .addIngredient(Items.SWEET_BERRIES)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.SWEET_BERRY_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.GLOW_BERRY_TAIYAKI.get())
                    .addIngredient(Items.GLOW_BERRIES)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.GLOW_BERRY_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.CHOCOLATE_TAIYAKI.get())
                    .addIngredient(Items.COCOA_BEANS)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.CHOCOLATE_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.HONEY_TAIYAKI.get())
                    .addIngredient(Items.HONEY_BOTTLE)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.HONEY_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.PUMPKIN_TAIYAKI.get())
                    .addIngredient(Items.PUMPKIN)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.PUMPKIN_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.WATERMELON_TAIYAKI.get())
                    .addIngredient(Items.MELON_SLICE)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.WATERMELON_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.SUSPICIOUS_TAIYAKI.get())
                    .addIngredient(SCItems.WILLISH)
                    .addIngredient(DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.SUSPICIOUS_TAIYAKI.getId().withPrefix("lets_do/"));

            new Builder(SDItems.WEATHER_SOUP.get())
                    .addIngredient(SCItems.LIGHTNING_BASS)
                    .addIngredient(SCItems.THUNDER_BASS)
                    .addIngredient(Items.BROWN_MUSHROOM)
                    .addIngredient(Items.POTATO)
                    .save(output, SDItems.WEATHER_SOUP.getId().withPrefix("lets_do/"));

            //generic quality foods
            for (int i = 0; i < 5; i++)
            {
                //TEMAKI
                new Builder(SDItems.TEMAKI.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(Items.KELP)
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.TEMAKI.get(i).get()).getPath()).withPrefix("lets_do/"));

                //hosomaki
                new Builder(SDItems.HOSOMAKI.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HOSOMAKI.get(i).get()).getPath()).withPrefix("lets_do/"));

                //uramaki
                new Builder(SDItems.URAMAKI.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.URAMAKI.get(i).get()).getPath()).withPrefix("lets_do/"));

                //nigiri
                new Builder(SDItems.NIGIRI.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .addIngredient(ObjectRegistry.BARLEY.get())
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.NIGIRI.get(i).get()).getPath()).withPrefix("lets_do/"));

                //healthy fish omelette
                new Builder(SDItems.HEALTHY_FISH_OMELETTE.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(ObjectRegistry.LETTUCE.value())
                        .addIngredient(CommonTags.Items.CROPS_TOMATO)
                        .addIngredient(Items.EGG)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HEALTHY_FISH_OMELETTE.get(i).get()).getPath()).withPrefix("lets_do/"));

                //fish salad
                new Builder(SDItems.FISH_SALAD.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(ObjectRegistry.LETTUCE.value())
                        .addIngredient(CommonTags.Items.CROPS_TOMATO)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_SALAD.get(i).get()).getPath()).withPrefix("lets_do/"));

                //fish and chips
                new Builder(SDItems.FISH_AND_CHIPS.get(i).asItem())
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.POTATO)
                        .addIngredient(Items.POTATO)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_AND_CHIPS.get(i).get()).getPath()).withPrefix("lets_do/"));

            }
        }
    }

    private static class KaleidoscopeRecipes
    {
        private static void cutRecipe(TagKey<Item> input, ItemLike output, RecipeOutput o)
        {
            ChoppingBoardBuilder.builder()
                    .setIngredient(input)
                    .setResult(output, 1)
                    .setModelId(Utils.rl(KaleidoscopeCookery.MOD_ID, "salmon"))
                    .setCutCount(4)
                    .save(o, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(output.asItem()).getPath()).withPrefix("kaleidoscope/"));
        }

        private static void potMeals(RecipeOutput output)
        {
            //special
            PotRecipeBuilder.builder()
                    .setResult(SDItems.CACTIFISH_STEW.get())
                    .addInput(SCItems.CACTIFISH)
                    .addInput(Items.CACTUS)
                    .addInput(TagCommon.CROPS_TOMATO)
                    .addInput(TagCommon.CROPS_LETTUCE)
                    .addInput(ModItems.CATERPILLAR)
                    .setBowlCarrier()
                    .save(output, SDItems.CACTIFISH_STEW.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.MAGMA_FISH_BALLS.get())
                    .addInput(SCTags.WORMS)
                    .addInput(SCItems.MAGMA_FISH)
                    .addInput(TagCommon.CROPS_TOMATO)
                    .addInput(TagCommon.DOUGH)
                    .addInput(Items.EGG)
                    .save(output, SDItems.MAGMA_FISH_BALLS.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.SLUDGE_STEW.get())
                    .addInput(SCItems.SLUDGE_CATFISH)
                    .addInput(ModItems.TOMATO.get())
                    .addInput(Items.DIRT)
                    .addInput(SCItems.WORM)
                    .addInput(ModItems.RED_CHILI.get())
                    .addInput(Items.BONE)
                    .save(output, SDItems.SLUDGE_STEW.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.BLOSSOM_TOAST.get())
                    .addInput(SCItems.BLOSSOMFISH)
                    .addInput(TagCommon.CROPS_LETTUCE)
                    .addInput(Items.BREAD)
                    .addInput(Items.PINK_PETALS)
                    .save(output, SDItems.BLOSSOM_TOAST.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.STEAMED_REDSCALED_TUNA.get())
                    .addInput(SCItems.REDSCALED_TUNA)
                    .addInput(TagCommon.CROPS_LETTUCE)
                    .addInput(TagCommon.CROPS_LETTUCE)
                    .addInput(TagCommon.CROPS_CHILI_PEPPER)
                    .save(output, SDItems.STEAMED_REDSCALED_TUNA.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.GRILLED_SHROOMFISH.get())
                    .addInput(SCItems.SHROOMFISH)
                    .addInput(TagCommon.CROPS_LETTUCE)
                    .addInput(Items.POTATO)
                    .save(output, SDItems.GRILLED_SHROOMFISH.getId().withPrefix("kaleidoscope/"));


            PotRecipeBuilder.builder()
                    .setResult(SDItems.SPORE_NIGIRI.get())
                    .addInput(SCItems.SPOREFISH)
                    .addInput(TagCommon.CROPS_RICE)
                    .addInput(TagCommon.CROPS_RICE)
                    .save(output, SDItems.SPORE_NIGIRI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.SWEET_BERRY_TAIYAKI.get())
                    .addInput(Items.SWEET_BERRIES)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.SWEET_BERRY_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.GLOW_BERRY_TAIYAKI.get())
                    .addInput(Items.GLOW_BERRIES)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.GLOW_BERRY_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.CHOCOLATE_TAIYAKI.get())
                    .addInput(Items.COCOA_BEANS)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.CHOCOLATE_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.HONEY_TAIYAKI.get())
                    .addInput(Items.HONEY_BOTTLE)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.HONEY_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.PUMPKIN_TAIYAKI.get())
                    .addInput(Items.PUMPKIN)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.PUMPKIN_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.WATERMELON_TAIYAKI.get())
                    .addInput(Items.MELON_SLICE)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.WATERMELON_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.SUSPICIOUS_TAIYAKI.get())
                    .addInput(SCItems.WILLISH)
                    .addInput(TagCommon.DOUGH)
                    .addInput(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .save(output, SDItems.SUSPICIOUS_TAIYAKI.getId().withPrefix("kaleidoscope/"));

            PotRecipeBuilder.builder()
                    .setResult(SDItems.WEATHER_SOUP.get())
                    .addInput(SCItems.LIGHTNING_BASS)
                    .addInput(SCItems.THUNDER_BASS)
                    .addInput(Items.BROWN_MUSHROOM)
                    .addInput(Items.POTATO)
                    .save(output, SDItems.WEATHER_SOUP.getId().withPrefix("kaleidoscope/"));

            //generic quality foods
            for (int i = 0; i < 5; i++)
            {
                //TEMAKI
                PotRecipeBuilder.builder()
                        .setResult(SDItems.TEMAKI.get(i).asItem(), 4)
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(Items.KELP)
                        .addInput(Items.KELP)
                        .addInput(TagCommon.CROPS_RICE)
                        .addInput(TagCommon.CROPS_RICE)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.TEMAKI.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //hosomaki
                PotRecipeBuilder.builder()
                        .setResult(SDItems.HOSOMAKI.get(i).asItem(), 4)
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(Items.KELP)
                        .addInput(TagCommon.CROPS_RICE)
                        .addInput(TagCommon.CROPS_RICE)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HOSOMAKI.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //uramaki
                PotRecipeBuilder.builder()
                        .setResult(SDItems.URAMAKI.get(i).asItem(), 4)
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(Items.KELP)
                        .addInput(TagCommon.CROPS_RICE)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.URAMAKI.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //nigiri
                PotRecipeBuilder.builder()
                        .setResult(SDItems.NIGIRI.get(i).asItem(), 4)
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(TagCommon.CROPS_RICE)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.NIGIRI.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //healthy fish omelette
                PotRecipeBuilder.builder()
                        .setResult(SDItems.HEALTHY_FISH_OMELETTE.get(i).asItem())
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(TagCommon.CROPS_LETTUCE)
                        .addInput(TagCommon.CROPS_TOMATO)
                        .addInput(Items.EGG)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HEALTHY_FISH_OMELETTE.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //fish salad
                PotRecipeBuilder.builder()
                        .setResult(SDItems.FISH_SALAD.get(i).asItem())
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(TagCommon.CROPS_LETTUCE)
                        .addInput(TagCommon.CROPS_TOMATO)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_SALAD.get(i).get()).getPath()).withPrefix("kaleidoscope/"));

                //fish and chips
                PotRecipeBuilder.builder()
                        .setResult(SDItems.FISH_AND_CHIPS.get(i).asItem())
                        .addInput(SDItems.STARCAUGHT_FILLET.get(i))
                        .addInput(Items.POTATO)
                        .addInput(Items.POTATO)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_AND_CHIPS.get(i).get()).getPath()).withPrefix("kaleidoscope/"));
            }

        }
    }

    private static class FarmersDelightRecipes
    {
        private static void cutRecipe(TagKey<Item> input, ItemLike output, RecipeOutput o)
        {
            cutRecipe(Ingredient.of(input), output, o);
        }

        private static void cutRecipe(Ingredient input, ItemLike output, RecipeOutput o)
        {
            CuttingBoardRecipeBuilder.cuttingRecipe(input, Ingredient.of(CommonTags.Items.TOOLS_KNIFE), output, 1)
                    .addResult(SCItems.FISH_BONES)
                    .build(o, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(output.asItem()).getPath()).withPrefix("farmers_delight/"));
        }

        private static void cookMeals(RecipeOutput output)
        {
            //special
            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.CACTIFISH_STEW.get(), 1, NORMAL_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.CACTIFISH)
                    .addIngredient(Items.CACTUS)
                    .addIngredient(CommonTags.Items.CROPS_TOMATO)
                    .addIngredient(CommonTags.Items.CROPS_CABBAGE)
                    .addIngredient(CommonTags.Items.CROPS_ONION)
                    .unlockedByAnyIngredient(SCItems.CACTIFISH)
                    .unlockedByAnyIngredient(Items.CACTUS)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.CACTIFISH_STEW.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.MAGMA_FISH_BALLS.get(), 4, SLOW_COOKING, MEDIUM_EXP)
                    .unlockedByAnyIngredient(SCItems.MAGMA_FISH)
                    .addIngredient(SCTags.WORMS)
                    .addIngredient(SCItems.MAGMA_FISH)
                    .addIngredient(CommonTags.Items.CROPS_TOMATO)
                    .addIngredient(Tags.Items.FOODS_DOUGH)
                    .addIngredient(Items.EGG)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.MAGMA_FISH_BALLS.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.SLUDGE_STEW.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.SLUDGE_CATFISH)
                    .addIngredient(ROTTEN_TOMATO.get())
                    .addIngredient(Items.DIRT)
                    .addIngredient(SCItems.WORM)
                    .addIngredient(ONION.get())
                    .addIngredient(Items.BONE)
                    .unlockedByAnyIngredient(SCItems.SLUDGE_CATFISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.SLUDGE_STEW.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.BLOSSOM_TOAST.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.BLOSSOMFISH)
                    .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                    .addIngredient(Items.BREAD)
                    .addIngredient(Items.PINK_PETALS)
                    .unlockedByAnyIngredient(SCItems.BLOSSOMFISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.BLOSSOM_TOAST.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.STEAMED_REDSCALED_TUNA.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.REDSCALED_TUNA)
                    .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                    .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                    .addIngredient(CommonTags.Items.FOODS_ONION)
                    .unlockedByAnyIngredient(SCItems.BLOSSOMFISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.STEAMED_REDSCALED_TUNA.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.GRILLED_SHROOMFISH.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.SHROOMFISH)
                    .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                    .addIngredient(Items.POTATO)
                    .unlockedByAnyIngredient(SCItems.SHROOMFISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.GRILLED_SHROOMFISH.getId().withPrefix("farmers_delight/"));


            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.SPORE_NIGIRI.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.SPOREFISH)
                    .addIngredient(RICE.get())
                    .addIngredient(RICE.get())
                    .unlockedByAnyIngredient(SCItems.SPOREFISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.SPORE_NIGIRI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.SWEET_BERRY_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.SWEET_BERRIES)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(Items.SWEET_BERRIES)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.SWEET_BERRY_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.GLOW_BERRY_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.GLOW_BERRIES)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(Items.GLOW_BERRIES)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.GLOW_BERRY_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.CHOCOLATE_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.COCOA_BEANS)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(Items.COCOA_BEANS)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.CHOCOLATE_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.HONEY_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.HONEY_BOTTLE)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(Items.HONEY_BOTTLE)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.HONEY_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.PUMPKIN_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(PUMPKIN_SLICE.get())
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(PUMPKIN_SLICE.get())
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.PUMPKIN_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.WATERMELON_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(Items.MELON_SLICE)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(Items.MELON_SLICE)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.WATERMELON_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.SUSPICIOUS_TAIYAKI.get(), 2, FAST_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.WILLISH)
                    .addIngredient(CommonTags.Items.FOODS_DOUGH)
                    .addIngredient(SCTags.STARCAUGHT_FISHABLE_FISH)
                    .unlockedByAnyIngredient(SCItems.WILLISH)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.SUSPICIOUS_TAIYAKI.getId().withPrefix("farmers_delight/"));

            CookingPotRecipeBuilder.cookingPotRecipe(SDItems.WEATHER_SOUP.get(), 1, SLOW_COOKING, MEDIUM_EXP)
                    .addIngredient(SCItems.LIGHTNING_BASS)
                    .addIngredient(SCItems.THUNDER_BASS)
                    .addIngredient(Items.BROWN_MUSHROOM)
                    .addIngredient(Items.POTATO)
                    .unlockedByAnyIngredient(SCItems.LIGHTNING_BASS, SCItems.THUNDER_BASS)
                    .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                    .save(output, SDItems.WEATHER_SOUP.getId().withPrefix("farmers_delight/"));

            //generic quality foods
            for (int i = 0; i < 5; i++)
            {
                //TEMAKI
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.TEMAKI.get(i), 4, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(Items.KELP)
                        .addIngredient(RICE.get())
                        .addIngredient(RICE.get())
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.TEMAKI.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //hosomaki
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.HOSOMAKI.get(i), 4, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(RICE.get())
                        .addIngredient(RICE.get())
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HOSOMAKI.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //uramaki
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.URAMAKI.get(i), 4, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.KELP)
                        .addIngredient(RICE.get())
                        .addIngredient(RICE.get())
                        .addIngredient(RICE.get())
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.URAMAKI.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //nigiri
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.NIGIRI.get(i), 4, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(RICE.get())
                        .addIngredient(RICE.get())
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.NIGIRI.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //healthy fish omelette
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.HEALTHY_FISH_OMELETTE.get(i), 1, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                        .addIngredient(CommonTags.Items.FOODS_TOMATO)
                        .addIngredient(Items.EGG)
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.HEALTHY_FISH_OMELETTE.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //fish salad
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.FISH_SALAD.get(i), 1, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(CommonTags.Items.FOODS_LEAFY_GREEN)
                        .addIngredient(CommonTags.Items.FOODS_TOMATO)
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_SALAD.get(i).get()).getPath()).withPrefix("farmers_delight/"));

                //fish and chips
                CookingPotRecipeBuilder.cookingPotRecipe(SDItems.FISH_AND_CHIPS.get(i), 1, SLOW_COOKING, MEDIUM_EXP)
                        .addIngredient(SDItems.STARCAUGHT_FILLET.get(i))
                        .addIngredient(Items.POTATO)
                        .addIngredient(Items.POTATO)
                        .setRecipeBookTab(CookingPotRecipeBookTab.MEALS)
                        .save(output, StarcatcherDelight.rl(BuiltInRegistries.ITEM.getKey(SDItems.FISH_AND_CHIPS.get(i).get()).getPath()).withPrefix("farmers_delight/"));

            }
        }
    }

    public static TagKey<Item> commonTag(String s)
    {
        return TagKey.create(Registries.ITEM, Utils.rl("c", s));
    }
}
