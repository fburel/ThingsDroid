package fr.florianburel.things.droid.AsyncTask;

import android.os.AsyncTask;

import java.util.ArrayList;

import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.ServiceLocator;

/**
 * Created by fl0 on 05/08/15.
 */
public class FetchProductTask extends AsyncTask<Void, Integer, ArrayList<Product>> {

    public interface OnFetchFinishedLister {
        void OnFetchFinished(ArrayList<Product> result);
    }


    public ArrayList<Product> result;

    public OnFetchFinishedLister getListener() {
        return listener;
    }

    public void setListener(OnFetchFinishedLister listener) {
        this.listener = listener;
    }

    private OnFetchFinishedLister listener;

    @Override
    protected ArrayList<Product> doInBackground(Void... voids) {


        ArrayList<Product> products = null;

        try {
            products = ServiceLocator.getInstance().getThingClient().getAllProducts();
        } catch (Exception e) {
            products = new ArrayList<Product>();
        }

        return products;
    }


    @Override
    protected void onPostExecute(ArrayList<Product> products) {

        result = products;

        listener.OnFetchFinished(result);

    }

    public void run(OnFetchFinishedLister l)
    {
        setListener(l);

        execute();
    }

}
