package fr.florianburel.things.droid.Fragment;


import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import fr.florianburel.things.droid.R;
import fr.florianburel.things.model.modelObject.Order;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IBasketRepository;
import fr.florianburel.things.model.services.ServiceLocator;
import fr.florianburel.things.droid.ListAdapter.BasketAdapter;


public class BasketFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {


    private ListView listView;
    private IBasketRepository repository;
    private BasketAdapter adapter;
    private TextView totalTextView;
    private Button buyButton;


    public static BasketFragment NewInstance() {
        return new BasketFragment();
    }

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_basket, null);

        this.listView = (ListView) v.findViewById(R.id.listView);

        this.totalTextView = (TextView) v.findViewById(R.id.totalTextView);

        this.buyButton = (Button) v.findViewById(R.id.buyButton);

        this.listView.setOnItemClickListener(this);

        this.repository = ServiceLocator.getInstance().getBasketRepository();


        this.adapter = new BasketAdapter(getActivity(), this.repository.getBasketContent());

        this.listView.setAdapter(this.adapter);

        double amount = ServiceLocator.getInstance().getBasketRepository().getBasketTotal();

        this.totalTextView.setText(amount + " €");

        this.buyButton.setAlpha(amount == 0 ? 0 : 1);

        this.buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });

        return v;
    }

    private void placeOrder() {

        // Recuperation ip
        WifiManager wm = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());

        Toast.makeText(getActivity(), ip, Toast.LENGTH_LONG).show();

        HashMap<Product, Integer> content = this.repository.getBasketContent();

        Order order = new Order(ip, content);

        ServiceLocator.getInstance().getThingClient().PlaceOrderAsync(order);

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        // Le produit selectionné
        final Product selectedProduct = (Product) adapterView.getItemAtPosition(i);


        // Remonte le produit selectionné a l'activity
        // IMPERATIF  : L'activity DOIT implementer ProductListFragmentListener
        ProductListFragmentListener activity = (ProductListFragmentListener) getActivity();

        activity.onProductSelected(selectedProduct);


    }
}
