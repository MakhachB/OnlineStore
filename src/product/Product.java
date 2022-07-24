package product;

public class Product {
    private int id;
    private String product_name;
    private int units_in_stock;
    private double unit_price;

    public Product(int id, String product_name, int units_in_stock, double unit_price) {
        this.id = id;
        this.product_name = product_name;
        this.units_in_stock = units_in_stock;
        this.unit_price = unit_price;
    }



    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", units_in_stock=" + units_in_stock +
                ", unit_price=" + unit_price +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getUnits_in_stock() {
        return units_in_stock;
    }

    public void setUnits_in_stock(int units_in_stock) {
        this.units_in_stock = units_in_stock;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }
}
