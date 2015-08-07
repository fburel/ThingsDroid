package fr.florianburel.things.droid.Activity;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import de.greenrobot.inject.Injector;
import de.greenrobot.inject.annotation.InjectView;
import fr.florianburel.things.droid.Fragment.BasketFragment;
import fr.florianburel.things.droid.Fragment.ProductDetailFragment;
import fr.florianburel.things.droid.Fragment.ProductListFragmentListener;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.dummy.VolleyParseClient;
import fr.florianburel.things.droid.Fragment.ProductListFragment;
import fr.florianburel.things.droid.R;


public class MainActivity extends Activity implements ProductListFragmentListener{

    @InjectView(id = R.id.frameLayout)
    private FrameLayout frameLayout;

    private final ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Injector.injectInto(this);

        if(fragments.size() == 0)
        {

            fragments.add(BasketFragment.NewInstance());
        }


        Fragment lastDisplayedFragment = fragments.get(fragments.size() - 1);

        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, lastDisplayedFragment)
                .commit();

        VolleyParseClient.DownloadQueue = Volley.newRequestQueue(this);



    }

    @Override
    public void onProductSelected(Product selectedProduct) {

        this.fragments.add(ProductDetailFragment.NewInstance(selectedProduct));

        Fragment lastDisplayedFragment = fragments.get(fragments.size() - 1);

        getFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, lastDisplayedFragment)
                .commit();
    }


    @Override
    public void onBackPressed() {
        if(this.fragments.size() == 1)
        {
            super.onBackPressed();
        }
        else
        {
            fragments.remove(fragments.size() - 1);

            Fragment lastDisplayedFragment = fragments.get(fragments.size() - 1);

            getFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, lastDisplayedFragment)
                    .commit();
        }
    }
}
