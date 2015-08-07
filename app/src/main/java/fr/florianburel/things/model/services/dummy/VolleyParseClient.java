package fr.florianburel.things.model.services.dummy;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IThingsClient;

/**
 * Created by fl0 on 05/08/15.
 */
public class VolleyParseClient implements IThingsClient {

    public static RequestQueue DownloadQueue;

    private static final String APPLICATION_ID = "CLTf4PG1mjIZpo07PMhSWeSdk7OX7nCULBsbJfNy";

    private static final String REST_API_KEY = "xHBZ299h8O2MmPN73GIuqwbsJCxLIbAu4RnH16ze";

    private static  String BASE_URL = "https://api.parse.com";


    private static String OBJECT_ID_KEY = "objectId";
    private static String NAME_KEY = "name";
    private static String DETAIL_KEY = "detail";
    private static String PRICE_KEY = "price";
    private static String IMAGE_KEY = "image";



    private ArrayList<Product> products;

    @Override
    public void FetchAllProducts(final OnFetchResultsListener listener) {


        DownloadQueue.start();


        String url = BASE_URL + "/1/classes/Product";


        JSONObject messageBody = null;


        Response.Listener<JSONObject> successListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject root) {

                try
                {
                    ArrayList<Product> productArrayList = new ArrayList<Product>();

                    JSONArray results = root.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {

                        JSONObject item = results.getJSONObject(i);

                        String id = item.getString(OBJECT_ID_KEY);

                        Product p = new Product(id);

                        p.setDesignation(item.getString(NAME_KEY));
                        p.setDescription(item.getString(DETAIL_KEY));
                        p.setPrice(item.getDouble(PRICE_KEY));
                        // p.setImageuRL(item.getString(IMAGE_KEY));

                        productArrayList.add(p);
                    }

                    listener.OnResult(productArrayList, null);
                }
                catch (Exception e)
                {
                    listener.OnResult(null, e);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnResult(null, error);
            }
        };

        Request request = new ParseRequest(
                Request.Method.GET,
                url,
                messageBody,
                successListener,
                errorListener
        );


        DownloadQueue.add(request);

    }

    @Override
    public ArrayList<Product> getAllProducts() throws Exception {
        return new ArrayList<Product>(products);
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



    // Json object request pour Parse.

    private class ParseRequest extends JsonObjectRequest {

        public ParseRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, jsonRequest, listener, errorListener);
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("X-Parse-Application-Id", APPLICATION_ID);
            params.put("X-Parse-REST-API-Key", REST_API_KEY);

            return params;
        }


    }
}
