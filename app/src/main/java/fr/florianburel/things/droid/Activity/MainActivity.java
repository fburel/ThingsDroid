package fr.florianburel.things.droid.Activity;


import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

import de.greenrobot.inject.Injector;
import de.greenrobot.inject.annotation.InjectView;
import fr.florianburel.things.droid.Fragment.BasketFragment;
import fr.florianburel.things.droid.Fragment.ProductDetailFragment;
import fr.florianburel.things.droid.Fragment.ProductListFragmentListener;
import fr.florianburel.things.model.modelObject.Basket;
import fr.florianburel.things.model.modelObject.Product;
import fr.florianburel.things.model.services.dummy.VolleyParseClient;
import fr.florianburel.things.droid.Fragment.ProductListFragment;
import fr.florianburel.things.droid.R;


public class MainActivity extends FragmentActivity implements ProductListFragmentListener{

    public static final String SELECTED_PRODUCT_KEY = "fr.florianburel.thing.droid.mainactivity.productselected";

    @InjectView(id = R.id.pager)
    private ViewPager pager;

    private final ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Important : ne pas oublier
        super.onCreate(savedInstanceState);

        // Charge le XML res/layout/activity_main.xml
        setContentView(R.layout.activity_main);

        // Recupere les view par le id (remplace le findViewById)
        Injector.injectInto(this);

        // Necessaire pour parametrer la lib Volley
        VolleyParseClient.DownloadQueue = Volley.newRequestQueue(this);

        // Pour avoir le supportFragmentManager (necessaire pour le swipe, il faut etrendre FragmentActivity)
        this.pager.setAdapter(new NavigationAdapter(getSupportFragmentManager()));


        // Configure l'actionBar
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        ActionBar.TabListener listener = new ActionBar.TabListener(){
            @Override
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }

            @Override
            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

            }
        };

        // Ajouter nos onglets a la tabBar
        actionBar.addTab(actionBar.newTab()
                .setText("Products")
                .setTabListener(listener));
        actionBar.addTab(actionBar.newTab()
                .setText("Basket")
                .setTabListener(listener));

        // Mettre a jour la tabBar quand on swipe avec le pager
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onProductSelected(Product selectedProduct) {


        Intent navToDetail = new Intent(this, DetailActivity.class);

        navToDetail.putExtra(SELECTED_PRODUCT_KEY, selectedProduct.getRef());


        startActivity(navToDetail);



    }




    private class NavigationAdapter extends FragmentPagerAdapter {

        private final ProductListFragment productListFragment;
        private final BasketFragment basketFragment;


        public NavigationAdapter(FragmentManager fm) {
            super(fm);
            productListFragment = ProductListFragment.NewInstance();
            basketFragment = BasketFragment.NewInstance();
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return position == 0 ? this.productListFragment : this.basketFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
