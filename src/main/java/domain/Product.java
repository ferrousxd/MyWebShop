package domain;

public class Product {
    private long id;
    private String name;
    private String category;
    private String description;
    private double price;

    public Product() {}

    public Product(long id, String name, String category, String description, double price) {
        setId(id);
        setName(name);
        setCategory(category);
        setDescription(description);
        setPrice(price);
    }

    public Product(long id, String name, double price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
