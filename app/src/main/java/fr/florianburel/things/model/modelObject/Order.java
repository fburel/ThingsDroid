package fr.florianburel.things.model.modelObject;

import java.util.HashMap;

import fr.florianburel.things.model.modelObject.Product;

/**
 * Created by fl0 on 07/08/15.
 */
public class Order {
    private final String ip;

    public HashMap<Product, Integer> getContent() {
        return content;
    }

    private final HashMap<Product, Integer> content;

    public Order(String ip, HashMap<Product, Integer> content) {
        this.ip = ip;
        this.content = content;
    }

    public String getIp() {
        return ip;
    }
}
