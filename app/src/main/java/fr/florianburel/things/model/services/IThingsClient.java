package fr.florianburel.things.model.services;

import fr.florianburel.things.model.modelObject.Order;
import fr.florianburel.things.model.modelObject.Product;

import java.util.ArrayList;

/**
 * Created by fl0 on 03/08/15.
 */
public interface IThingsClient {

    void PlaceOrderAsync(Order order);

    public interface OnFetchResultsListener {
        void OnResult(ArrayList<Product> results, Exception error);
    }

    void FetchAllProducts(OnFetchResultsListener listener);

    ArrayList<Product> getAllProducts() throws Exception;

    Product getProductByRef(String ref) throws Exception;
}
