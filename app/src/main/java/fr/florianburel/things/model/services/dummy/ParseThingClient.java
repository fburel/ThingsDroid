package fr.florianburel.things.model.services.dummy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import fr.florianburel.things.model.modelObject.Order;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IThingsClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * Created by fl0 on 04/08/15.
 */
public class ParseThingClient implements IThingsClient
{
    private static final String APPLICATION_ID = "CLTf4PG1mjIZpo07PMhSWeSdk7OX7nCULBsbJfNy";

    private static final String REST_API_KEY = "xHBZ299h8O2MmPN73GIuqwbsJCxLIbAu4RnH16ze";

    private static  String BASE_URL = "https://api.parse.com";


    private static String OBJECT_ID_KEY = "objectId";
    private static String NAME_KEY = "name";
    private static String DETAIL_KEY = "detail";
    private static String PRICE_KEY = "price";
    private static String IMAGE_KEY = "image";



    private ArrayList<Product> products = new ArrayList<Product>();

    @Override
    public void PlaceOrderAsync(Order order) {

    }

    @Override
    public void FetchAllProducts(OnFetchResultsListener listener) {

    }

    @Override
    public ArrayList<Product> getAllProducts() throws Exception {

        String url = BASE_URL + "/1/classes/Product";

        String response = sendGet(url);

        products = parseJson(response);


        return new ArrayList<Product>(products);
    }

    private ArrayList<Product> parseJson(String response) throws JSONException {

        ArrayList<Product> productArrayList = new ArrayList<Product>();

        JSONObject root = new JSONObject(response);
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

        return productArrayList;
    }


    // HTTP GET request
    private String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.addRequestProperty("X-Parse-Application-Id", APPLICATION_ID);
        con.addRequestProperty("X-Parse-REST-API-Key", REST_API_KEY);

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return response.toString();

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
