package fr.florianburel.things.droid.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import fr.florianburel.things.droid.WebService.MyHTTPD;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IThingsClient;
import fr.florianburel.things.model.services.ServiceLocator;
import fr.florianburel.things.droid.R;
import fr.florianburel.things.droid.ListAdapter.ProductAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductListFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {


    private BroadcastReceiver broadcastReceiver;

    public ProductListFragment() {
        // Required empty public constructor
    }

    private static ArrayList<Product> products;

    private ListAdapter adapter;

    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product_list, null);

        this.listView = (ListView) v.findViewById(R.id.listView);

        this.listView.setOnItemClickListener(this);

        if(products != null)
        {
            adapter = new ProductAdapter(getActivity(), products);

            listView.setAdapter(adapter);
        }
        else
        {
            updateDataSource();

        }

        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateDataSource();
            }
        };

        getActivity().registerReceiver(this.broadcastReceiver, new IntentFilter(MyHTTPD.UPDATE_AVAILABLE));

        return v;
    }

    private void updateDataSource() {

        final ProgressDialog spinner = new ProgressDialog(getActivity());

        spinner.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        spinner.setMessage("Downloading...");

        spinner.show();

        ServiceLocator.getInstance().getThingClient().FetchAllProducts(new IThingsClient.OnFetchResultsListener() {
            @Override
            public void OnResult(ArrayList<Product> results, Exception error) {

                spinner.dismiss();

                products = results;

                adapter = new ProductAdapter(getActivity(), products);

                listView.setAdapter(adapter);

            }
        });
    }

    public static ProductListFragment NewInstance() {

        return new ProductListFragment();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        // Le produit selectionné
        final Product selectedProduct = products.get(position);


        // Remonte le produit selectionné a l'activity
        // IMPERATIF  : L'activity DOIT implementer ProductListFragmentListener
        ProductListFragmentListener activity = (ProductListFragmentListener) getActivity();

        activity.onProductSelected(selectedProduct);


    }
}
