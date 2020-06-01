package domain;

public class Order {
    private String username;
    private String product_name;
    private double product_price;
    private double sum;
    private long user_id;
    private long product_id;

    public Order(String username, String product_name, double product_price) {
        setUsername(username);
        setProduct_name(product_name);
        setProduct_price(product_price);
    }

    public Order(long user_id, long product_id) {
        setUser_id(user_id);
        setProduct_id(product_id);
    }

    public Order(String username, double sum) {
        setUsername(username);
        setSum(sum);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(double product_price) {
        this.product_price = product_price;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }
}
