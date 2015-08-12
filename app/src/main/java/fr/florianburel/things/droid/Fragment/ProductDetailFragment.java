package fr.florianburel.things.droid.Fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import fr.florianburel.things.droid.R;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.IBasketRepository;
import fr.florianburel.things.model.services.ServiceLocator;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailFragment extends Fragment {


    private static Product SelectedProduct;
    private TextView productNameLabel;
    private TextView productDetailLabel;
    private ImageView productImageView;
    private TextView productPriceLabel;
    private TextView quantityLable;
    private IBasketRepository repository;
    private Button addButton;
    private Button delButton;
    private Button removeButton;

    public ProductDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_product_detail, container, false);


        this.repository = ServiceLocator.getInstance().getBasketRepository();


        this.productNameLabel = (TextView) v.findViewById(R.id.productNameLabel);
        this.productDetailLabel = (TextView) v.findViewById(R.id.descriptionTextView);
        this.productImageView = (ImageView) v.findViewById(R.id.imageView);
        this.productPriceLabel = (TextView) v.findViewById(R.id.priceTextView);
        this.quantityLable = (TextView) v.findViewById(R.id.quantityTextView);

        this.productNameLabel.setText(SelectedProduct.getDesignation());
        this.productDetailLabel.setText(SelectedProduct.getDescription());

        this.productPriceLabel.setText(SelectedProduct.getPrice() + "€");

        this.addButton = (Button) v.findViewById(R.id.addButton);
        this.delButton = (Button) v.findViewById(R.id.delButton);
        this.removeButton = (Button) v.findViewById(R.id.removeButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.addProduct(SelectedProduct);
                updateQuantityLabel();
            }
        });

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                repository.removeProduct(SelectedProduct);
                updateQuantityLabel();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                while(repository.getBasketContent().containsKey(SelectedProduct))
                {
                    repository.removeProduct(SelectedProduct);
                }
                updateQuantityLabel();
            }
        });

        updateQuantityLabel();




        return v;
    }

    private void updateQuantityLabel() {


        int quantity = this.repository.getBasketContent().containsKey(SelectedProduct) ? this.repository.getBasketContent().get(SelectedProduct) : 0 ;

        this.quantityLable.setText(quantity + " dans le panier");

        removeButton.setAlpha(quantity != 0 ? 1 : 0);
        delButton.setAlpha(quantity != 0 ? 1 : 0);


    }


    public static ProductDetailFragment NewInstance(Product selectedProduct) {

        SelectedProduct = selectedProduct;

        return new ProductDetailFragment();
    }
}
