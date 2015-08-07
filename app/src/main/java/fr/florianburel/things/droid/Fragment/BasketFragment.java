package fr.florianburel.things.droid.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.Random;

import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IBasketRepository;
import fr.florianburel.things.model.services.ServiceLocator;
import fr.florianburel.things.droid.ListAdapter.BasketAdapter;


public class BasketFragment extends Fragment implements AdapterView.OnItemClickListener {


    private ListView listView;
    private IBasketRepository repository;
    private BasketAdapter adapter;


    public static Fragment NewInstance() {
        return new BasketFragment();
    }

    public BasketFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.listView = new ListView(getActivity());



        this.listView.setOnItemClickListener(this);


        this.repository = ServiceLocator.getInstance().getBasketRepository();

        //TODO : remove this method once proper add-to-cart ui is implemented.
        fillBasketWithDummyIfEmpty();


        this.adapter = new BasketAdapter(getActivity(), this.repository.getBasketContent());

        this.listView.setAdapter(this.adapter);

        return this.listView;
    }

    private void fillBasketWithDummyIfEmpty() {

        if(this.repository.getBasketContent().size() == 0) {

            Random r = new Random();

            for(int i = 0 ; i < 14 ; i++)
            {
                Product p = new Product("" + i);
                p.setDesignation("Product " + i);
                p.setDescription("Un super produit");

                // Prix entre 1 et 100 €
                p.setPrice(Math.abs(r.nextInt() % 100 + 1));

                repository.addProduct(p);
            }

        }
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
