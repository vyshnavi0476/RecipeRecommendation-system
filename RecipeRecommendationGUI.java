import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

class Recipe {
    String name;
    String imagePath;
    String[] ingredients;

    static Map<String, String[]> ingredientSubstitutions = new HashMap<>();

    static {
        ingredientSubstitutions.put("tomato sauce", new String[]{"tomato", "ketchup", "pureed tomato"});
        ingredientSubstitutions.put("butter", new String[]{"ghee"});
        ingredientSubstitutions.put("milk", new String[]{"almond milk", "soy milk", "coconut milk"});
        ingredientSubstitutions.put("sugar", new String[]{"honey", "maple syrup", "jaggery"});
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

    public Recipe(String name, String[] ingredients, String imagePath) {
        this.name = name;
        this.ingredients = ingredients;
        this.imagePath = imagePath;
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

public class RecipeRecommendationGUI extends JFrame implements ActionListener {

    private JTextField ingredientsInput;
    private JTextArea outputArea;
    private JButton recommendButton;
    private JLabel imageLabel;
    private Recipe[] recipes;

    public RecipeRecommendationGUI() {
        setTitle("Recipe Recommendation System");
        setSize(600, 500);  // Reduced window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        ingredientsInput = new JTextField();
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        recommendButton = new JButton("Find Recipe");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Enter Available Ingredients (comma separated):"), BorderLayout.NORTH);
        topPanel.add(ingredientsInput, BorderLayout.CENTER);
        topPanel.add(recommendButton, BorderLayout.SOUTH);

        // Image label
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(300, 200)); // COMPRESSION - smaller size!

        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(350, 250));
        imagePanel.setBorder(BorderFactory.createTitledBorder("Recipe Image"));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(new JScrollPane(outputArea));
        centerPanel.add(imagePanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        recommendButton.addActionListener(this);

        recipes = new Recipe[]{
            new Recipe("Pasta", new String[]{"pasta", "tomato sauce", "cheese"}, "pasta.jpeg"),
            new Recipe("Omelette", new String[]{"egg", "salt", "butter"}, "omelette.jpg"),
            new Recipe("Salad", new String[]{"lettuce", "tomato", "cucumber", "olive oil"}, "salad.jpg"),
            new Recipe("Grilled Cheese", new String[]{"bread", "cheese", "butter"}, "Grilled Cheese.jpg"),
            new Recipe("Fruit Smoothie", new String[]{"blueberry", "milk", "honey"}, "fruit_smoothie.jpg"),
            new Recipe("Tomato Curry", new String[]{"tomato", "onions", "chilli powder", "oil", "salt"}, "tomato_curry.jpg"),
            new Recipe("Paneer Butter Masala", new String[]{"paneer", "tomato puree", "onion", "butter", "spices", "cream"}, "paneer_butter_masala.jpg"),
            new Recipe("Curd Rice", new String[]{"rice", "curd", "salt", "mustard seeds", "green chilli"}, "curd_rice.jpg"),
            new Recipe("Cutlet", new String[]{"potato", "garam masala", "bread crumbs", "chilli powder", "rice flour"}, "cutlet.jpg"),
            new Recipe("Paratha", new String[]{"wheat flour", "chickpeas", "ghee"}, "paratha.jpg"),
            new Recipe("Paneer Wrap", new String[]{"bread", "paneer", "onion", "tomato sauce", "cheese"}, "panner_wrap.jpg"),
            new Recipe("Butter Chicken", new String[]{"chicken", "butter", "tomato", "curd", "garam masala", "spices"}, "butter_chicken.jpg"),
            new Recipe("Fish Fry", new String[]{"fish", "ginger garlic paste", "chilli powder", "salt"}, "fish_fry.jpg"),
            new Recipe("Prawns Curry", new String[]{"prawns", "tomato", "oil", "mustard seeds", "onions", "spices"}, "prawns_curry.jpg"),
            new Recipe("Eggplant Curry", new String[]{"eggplant", "spices", "onion", "curd", "tomato"}, "eggplant_curry.jpg"),
            new Recipe("Sandwich", new String[]{"bread", "butter", "tomato", "onion", "capsicum", "pepper", "salt"}, "sandwich.jpg"),
            new Recipe("Palak Paneer", new String[]{"paneer", "spinach", "tomato", "garam masala", "chilli powder", "kasuri methi"}, "palak_paneer.jpg"),
            new Recipe("Kadai Chicken", new String[]{"chicken", "chicken masala", "onion", "tomato", "capsicum", "red chilli", "ginger garlic paste"}, "kadai_chicken.jpg"),
            new Recipe("Chicken Lollipop", new String[]{"chicken", "tomato sauce", "ginger garlic paste", "garam masala"}, "chicken_lollipop.jpg"),
            new Recipe("Sambar", new String[]{"sambar powder", "onion", "dal", "tamarind paste"}, "sambar.jpg"),
            new Recipe("Rasam", new String[]{"rasam powder", "pepper", "tamarind"}, "rasam.jpg")
        };
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = ingredientsInput.getText();
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
            outputArea.setText("Closest Matching Recipe: " + bestMatch.name + "\n");
            outputArea.append("Missing Ingredients: " + bestMatch.getMissingIngredients(availableIngredients));

            // Load and scale the image perfectly compressed
            ImageIcon icon = new ImageIcon(bestMatch.imagePath);
            Image img = icon.getImage();
            Image scaledImg = img.getScaledInstance(300, 200, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaledImg));
        } else {
            outputArea.setText("No suitable recipe found.");
            imageLabel.setIcon(null);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecipeRecommendationGUI().setVisible(true);
        });
    }
}

