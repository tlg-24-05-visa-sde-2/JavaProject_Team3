package gov.trivia.model;

public enum Category {
    SPORTS(1),
    HISTORY(2),
    MUSIC(3),
    JAYISMS(4);

    private final int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Category fromNumber(int number) {
        for (Category category : Category.values()) {
            if (category.getId() == number) {
                return category;
            }
        }
        throw new IllegalArgumentException("Invalid category number: " + number);
    }
}
