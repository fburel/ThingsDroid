package fr.florianburel.things.droid.Activity;

import android.app.Activity;
import android.support.annotation.MainThread;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import fr.florianburel.things.droid.Fragment.ProductDetailFragment;
import fr.florianburel.things.droid.R;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.ServiceLocator;

public class DetailActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);



        Product product = new Product("error");

        try {

            String ref = getIntent().getStringExtra(MainActivity.SELECTED_PRODUCT_KEY);
            product = ServiceLocator.getInstance().getThingClient().getProductByRef(ref);

        } catch (Exception e) {
            e.printStackTrace();
        }

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, ProductDetailFragment.NewInstance(product))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
