import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;




class Recipe {
    String name;
    String[] ingredients;
    static Map<String, String[]> ingredientSubstitutions = new HashMap<>();




    static {
        // Define taste-based ingredient substitutions
        ingredientSubstitutions.put("tomato sauce", new String[]{"tomato", "ketchup", "pureed tomato"});
        ingredientSubstitutions.put("butter", new String[]{"ghee"});
        ingredientSubstitutions.put("milk", new String[]{"almond milk", "soy milk", "coconut milk"});
        ingredientSubstitutions.put("sugar", new String[]{"honey", "maple syrup","jaggery"});
        ingredientSubstitutions.put("olive oil", new String[]{"groundnut oil", "mustard oil"});
        ingredientSubstitutions.put("vegetable oil", new String[]{"sunflower oil", "coconut oil"});
        ingredientSubstitutions.put("cheese", new String[]{"cream cheese", "mozzarella"});
        ingredientSubstitutions.put("bread", new String[]{"bun", "tortilla"});
        ingredientSubstitutions.put("vinegar", new String[]{"lemon juice"});
        ingredientSubstitutions.put("chilli powder", new String[]{"pepper"});
        ingredientSubstitutions.put("chicken masala", new String[]{"garam masala"});
        ingredientSubstitutions.put("green peas", new String[]{"chick peas"});
        ingredientSubstitutions.put("bread crumbs", new String[]{"cornflakes powder", "roasted suji"});
        ingredientSubstitutions.put("yogurt", new String[]{"curd", "buttermilk"});
        ingredientSubstitutions.put("corn flour", new String[]{"rice flour"});
        ingredientSubstitutions.put("maida", new String[]{"wheat flour", "besan", "rice flour"});
        ingredientSubstitutions.put("tamarind", new String[]{"amchur", "lemon"});
        ingredientSubstitutions.put("paneer", new String[]{"tofu", "chenna"});
        ingredientSubstitutions.put("mustard seeds", new String[]{"cumin seeds", "fenugreek seeds"});
        ingredientSubstitutions.put("curry masala", new String[]{"sambar podi", "rasam podi"});
        ingredientSubstitutions.put("chutney powder", new String[]{"idli podi", "coconut chutney powder"});
        ingredientSubstitutions.put("hazelnut", new String[]{"almond"});




    }




    public Recipe(String name, String[] ingredients) {
        this.name = name;
        this.ingredients = ingredients;
    }




    public int countMissingIngredients(String[] availableIngredients) {
        int missingCount = 0;
        for (String recipeIngredient : ingredients) {
            if (!isIngredientAvailable(recipeIngredient, availableIngredients)) {
                missingCount++;
            }
        }
        return missingCount;
    }




    public String getMissingIngredients(String[] availableIngredients) {
        StringBuilder missingIngredients = new StringBuilder();
        for (String recipeIngredient : ingredients) {
            if (!isIngredientAvailable(recipeIngredient, availableIngredients)) {
                missingIngredients.append(recipeIngredient).append(", ");
            }
        }
        return missingIngredients.length() > 0 ? missingIngredients.substring(0, missingIngredients.length() - 2) : "None";
    }




    private boolean isIngredientAvailable(String recipeIngredient, String[] availableIngredients) {
        for (String available : availableIngredients) {
            if (recipeIngredient.equalsIgnoreCase(available.trim()) || isTasteSubstitute(recipeIngredient, available.trim())) {
                return true;
            }
        }
        return false;
    }




    private boolean isTasteSubstitute(String recipeIngredient, String availableIngredient) {
        if (ingredientSubstitutions.containsKey(recipeIngredient)) {
            for (String substitute : ingredientSubstitutions.get(recipeIngredient)) {
                if (substitute.equalsIgnoreCase(availableIngredient)) {
                    return true;
                }
            }
        }
        return false;
    }
}




public class RecipeRecommendation {
    public static void main(String[] args) {
        Recipe[] recipes = {
            new Recipe("Pasta", new String[]{"pasta", "tomato sauce", "cheese"}),
            new Recipe("Omelette", new String[]{"egg", "salt", "butter"}),
            new Recipe("Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}),
            new Recipe("Grilled Cheese", new String[]{"bread", "cheese", "butter"}),
            new Recipe("Fruit Smoothie", new String[]{"banana", "milk", "honey"}),
            new Recipe("tomato curry", new String[]{"tomato", "onions", "chilli powder","oil","salt"}),
            new Recipe("paneer butter masala", new String[]{"paneer", "tomato puree", "onion","butter","spices","cream"}),
            new Recipe("curd rice", new String[]{"rice", "curd", "salt","mustard seeds","green chilli"}),
            new Recipe("cutlet", new String[]{"potato", "garam masala", "bread crumbs","chilli powder","rice flour"}),
            new Recipe("paratha", new String[]{"wheat flour", "chickpeas", "ghee"}),
            new Recipe("paneer wrap", new String[]{"bread", "paneer", "onion","tomato sauce","cheese"}),
            new Recipe("butter chicken", new String[]{"chicken", "butter","tomato","curd","garam masala","spices"}),
            new Recipe("fish fry", new String[]{"fish","ginger garlic paste","chilli powder","salt"}),
            new Recipe("prawns curry", new String[]{"prawns", "tomato", "oil","mustard seeds","onions","spices"}),
            new Recipe("egg plant curry", new String[]{"egg plant", "spices", "onion","curd","tomato"}),
            new Recipe("sandwich", new String[]{"bread", "butter", "tomato","onion","capsicum","pepper","salt"}),
            new Recipe("palak paneer", new String[]{"paneer", "spinach", "tomato","garam masala","chilli powder","kasuri methi"}),
            new Recipe("kadai chicken", new String[]{"chicken", "chicken masala", "onion","tomato","capssicum","red chilli","ginger garlic paste"}),
            new Recipe("chicken lollipop", new String[]{"chicken", "tomato sauce", "ginger garlic paste","garam masala"}),
            new Recipe("sambar", new String[]{"sambar powder", "onion","dal","tamarind paste"}),
            new Recipe("rasam", new String[]{"rasam powder","pepper","tamarind"}),


        };




        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter available ingredients (comma separated): ");
        String input = scanner.nextLine();
        scanner.close();




        String[] availableIngredients = input.split(",\\s*");




        Recipe bestMatch = null;
        int minMissing = Integer.MAX_VALUE;




        for (Recipe recipe : recipes) {
            int missingCount = recipe.countMissingIngredients(availableIngredients);
            if (missingCount < minMissing) {
                minMissing = missingCount;
                bestMatch = recipe;
            }
        }
        if (bestMatch != null) {
            System.out.println("\nClosest Matching Recipe: " + bestMatch.name);
            System.out.println("Missing Ingredients: " + bestMatch.getMissingIngredients(availableIngredients));
        } else {
            System.out.println("No suitable recipe found.");
        }
    }
}


