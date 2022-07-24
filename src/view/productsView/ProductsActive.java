package view.productsView;

import connection.ConnectionManager;
import product.Product;

import java.util.List;

public class ProductsActive {

    private static List<Product> products;

    static {
        products = ConnectionManager.getProducts();
    }

    public static List<Product> getProducts() {
        return products;
    }

    public static void setProducts(List<Product> products) {
        ProductsActive.products = products;
    }

    public static void printProductsInfo() {
         products.forEach(product -> System.out.printf("%-3d.%-30s : Price - %-10.2f .In stock - %d.\n",
                product.getId(), product.getProduct_name(), product.getUnit_price(), product.getUnits_in_stock()));


    }

    public static Product getProductById(int id) {
        for (Product temp : products) {
            if (temp.getId() == id) {
                return temp;
            }
        }
        return null;
    }
}
