package fr.florianburel.things.droid.ListAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import fr.florianburel.things.droid.R;
import fr.florianburel.things.model.modelObject.Product;

/**
 * Created by fl0 on 06/08/15.
 */
public class BasketAdapter extends BaseAdapter {


    private final Context context;
    private final LayoutInflater layoutInflater;
    private final HashMap<Product, Integer> quantityProducts;
    private final ArrayList<Product> products;

    public BasketAdapter(Context context, HashMap<Product, Integer> basketContent) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.quantityProducts = basketContent;
        this.products = new ArrayList<Product>(basketContent.keySet());
    }

    @Override
    public int getCount() {
        return this.products.size();
    }

    @Override
    public Object getItem(int i) {
        return this.products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View cell = prepareView(convertView);

        configureView((Product) getItem(position), (BasketCellViewHolder)cell.getTag());

        return cell;
    }

    private void configureView(Product item, BasketCellViewHolder viewHolder) {

        int qte  = quantityProducts.get(item);

        viewHolder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.unknown));

        viewHolder.referenceTextView.setText("#" + item.getRef());
        viewHolder.productNameTextView.setText(item.getDesignation());
        viewHolder.priceTextView.setText(item.getPrice() + "€");
        viewHolder.quantityTextView.setText("x" + qte);

    }

    private View prepareView(View convertView) {
        View cell = convertView;

        if(cell == null)
        {

            cell = this.layoutInflater.inflate(R.layout.basket_cell, null);

            // Creation du ViewHolder

            BasketCellViewHolder viewHolder = new BasketCellViewHolder();

            viewHolder.imageView = (ImageView) cell.findViewById(R.id.imageView);
            viewHolder.referenceTextView = (TextView) cell.findViewById(R.id.refTextView);
            viewHolder.productNameTextView = (TextView) cell.findViewById(R.id.productNameTextView);
            viewHolder.priceTextView = (TextView) cell.findViewById(R.id.priceTextView);
            viewHolder.quantityTextView = (TextView) cell.findViewById(R.id.quantityTextView);
            cell.setTag(viewHolder);

        }

        return cell;
    }

    private class BasketCellViewHolder {
        public ImageView imageView;
        public TextView referenceTextView;
        public TextView productNameTextView;
        public TextView priceTextView;
        public TextView quantityTextView;
    }
}
