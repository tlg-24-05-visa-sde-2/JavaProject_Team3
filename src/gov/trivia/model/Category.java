package gov.quizwiz.model;

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
        throw new IllegalArgumentException("Invalid category ID: " + id);
    }
}
