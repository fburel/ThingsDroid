package fr.florianburel.things.model.services.dummy;

import fr.florianburel.things.model.services.IBasketRepository;
import fr.florianburel.things.model.modelObject.Basket;
import fr.florianburel.things.model.modelObject.Product;

import java.util.HashMap;

/**
 * Created by fl0 on 04/08/15.
 */
public class LocalBasketRepository implements IBasketRepository {

    private final Basket<Product> basket = new Basket<Product>();

    @Override
    public void addProduct(Product p) {
        basket.add(p);
    }

    @Override
    public void removeProduct(Product p) {
        basket.remove(p);
    }

    @Override
    public double getBasketTotal() {

        double price = 0;

        for (Product p : basket.toArray()) {
            price += p.getPrice();
        }

        return price;
    }

    @Override
    public HashMap<Product, Integer> getBasketContent() {
        return basket.getContent();
    }
}
