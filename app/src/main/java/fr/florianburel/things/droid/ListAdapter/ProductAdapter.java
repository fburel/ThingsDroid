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

import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.droid.R;

/**
 * Created by fl0 on 05/08/15.
 */
public class ProductAdapter extends BaseAdapter {


    private final ArrayList<Product> items;
    private final Context context;


    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.items = products;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View cell = prepareView(convertView);

        configureView((Product) getItem(position), (ProductCellViewHolder)cell.getTag());

        return cell;
    }

    private void configureView(Product product, ProductCellViewHolder holder)
    {
        //Utiliser volley pour charger une image depuis une url
        holder.imageView.setImageResource(R.drawable.unknown);

        holder.textLabel.setText(product.getDesignation());
        holder.detailTextLabel.setText(product.getDescription());
        holder.button.setText("" + product.getPrice() + "€");
    }

    private View prepareView(View convertView) {


        View cell = convertView;

        if(cell == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            cell = inflater.inflate(R.layout.product_cell, null);

            // Creation du ViewHolder

            ProductCellViewHolder viewHolder = new ProductCellViewHolder();

            viewHolder.imageView = (ImageView) cell.findViewById(R.id.imageView);
            viewHolder.textLabel = (TextView) cell.findViewById(R.id.textLabel);
            viewHolder.detailTextLabel = (TextView) cell.findViewById(R.id.detailTextLabel);
            viewHolder.button = (Button) cell.findViewById(R.id.button);

            cell.setTag(viewHolder);

        }

        return cell;
    }

    private class ProductCellViewHolder {
        public ImageView imageView;
        public TextView textLabel;
        public TextView detailTextLabel;
        public Button button;
    }
}

