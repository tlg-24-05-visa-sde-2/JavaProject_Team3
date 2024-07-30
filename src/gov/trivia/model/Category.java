package gov.trivia.model;

public enum Category {
    SPORTS(1),
    HISTORY(2),
    MUSIC(3),
    JAYISMS(4);

    private int id;

    private Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Category fromId(int id) {
        for (Category category : Category.values()) {
            if (category.getId() == id) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category ID. Please select a valid category. " + id);
    }

    // Convert category name to string
    public String getName() {
        return this.name();
    }

    // Convert string to category
    public static Category fromName(String name) {
        for (Category category : Category.values()) {
            if (category.name().equalsIgnoreCase(name)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category name. Please select a valid category. " + name);
    }

    // List all category names
    public static String listAllCategories() {
        StringBuilder categories = new StringBuilder();
        for (Category category : Category.values()) {
            categories.append(category.name()).append(", ");
        }
        return categories.substring(0, categories.length() - 2);
    }
}
