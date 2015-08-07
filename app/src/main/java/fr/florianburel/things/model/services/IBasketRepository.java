package fr.florianburel.things.model.services;

import fr.florianburel.things.model.modelObject.Product;

import java.util.HashMap;

/**
 * Created by fl0 on 04/08/15.
 */
public interface IBasketRepository {

    // Ajout un produit au panier
    void addProduct(Product p);

    // Supprime un produit du panier
    void removeProduct(Product p);

    // Retourne le prix total du panier
    double getBasketTotal();

    // Retourne le contenu du panier sous forme produit - Qte
    HashMap<Product, Integer> getBasketContent();

}
