package fr.florianburel.things.model.services.dummy;

import fr.florianburel.things.model.modelObject.Order;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IThingsClient;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by fl0 on 04/08/15.
 */
public class DummyThingClient implements IThingsClient {


    private final ArrayList<Product> products = new ArrayList<Product>();

    public DummyThingClient() {

        Random r = new Random();

        for(int i = 0 ; i < 14 ; i++)
        {
            Product p = new Product("" + i);
            p.setDesignation("Product " + i);
            p.setDescription("Un super produit");

            // Prix entre 1 et 100 €
            p.setPrice(Math.abs(r.nextInt() % 100 + 1));

            products.add(p);
        }

    }

    @Override
    public void PlaceOrderAsync(Order order) {

    }

    @Override
    public void FetchAllProducts(OnFetchResultsListener listener) {

    }

    @Override
    public ArrayList<Product> getAllProducts() {

        return products;
    }

    @Override
    public Product getProductByRef(String ref) throws Exception {


        for (Product product : products) {
            if(product.getRef().equals(ref))
            {
                return product;
            }
        }

        throw new Exception("Aucun produit ne correspond a cette reference");

    }

}
