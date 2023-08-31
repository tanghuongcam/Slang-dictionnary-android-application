package com.example.slangapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.slangapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    MenuItem menuSetting;
    DictionnaryFragment dictionnaryFragment;
    BookmarkFragment bookmarkFragment;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        dbHelper = new DBHelper(this);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dictionnary, R.id.nav_bookmark, R.id.nav_rate, R.id.nav_share, R.id.nav_help, R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setNavigationItemSelectedListener(this);


        dictionnaryFragment = new DictionnaryFragment();
        bookmarkFragment = BookmarkFragment.getNewInstance(dbHelper);

        goToFragment(dictionnaryFragment,true);


        dictionnaryFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this,"dic_type");
                int dicType = id == null? R.id.action_fr_vn:Integer.valueOf(id);
                goToFragment(DetailFragment.getNewInstance(value,dbHelper,dicType),true);
            }
        });

        bookmarkFragment.setOnFragmentListener(new FragmentListener() {
            @Override
            public void onItemClick(String value) {
                String id = Global.getState(MainActivity.this,"dic_type");
                int dicType = id == null? R.id.action_fr_vn:Integer.valueOf(id);
                goToFragment(DetailFragment.getNewInstance(value,dbHelper,dicType),false);
            }
        });

        EditText edit_search = findViewById(R.id.edit_search);
        edit_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dictionnaryFragment.filterValue((charSequence.toString()));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuSetting = menu.findItem(R.id.action_settings);

        String id = Global.getState(this,"dic_type");
        if(id != null)
            menu.findItem(Integer.valueOf(id));
        else {
            ArrayList<String> source = dbHelper.getWord(R.id.action_fr_vn);
            dictionnaryFragment.resetDataSource(source);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.action_settings) return true;
        ArrayList<String> source;
        if (id == R.id.action_eng_fr){
            Global.saveState(this,"dic_type", String.valueOf(id));
            source = dbHelper.getWord(id);
            dictionnaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.english));
        }
        else if (id == R.id.action_eng_vn) {
            Global.saveState(this,"dic_type", String.valueOf(id));
            source = dbHelper.getWord(id);
            dictionnaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.english));
        }
        else if (id == R.id.action_fr_vn) {
            Global.saveState(this,"dic_type", String.valueOf(id));
            source = dbHelper.getWord(id);
            dictionnaryFragment.resetDataSource(source);
            menuSetting.setIcon(getDrawable(R.drawable.francais));
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.nav_bookmark) {
            String activeFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getClass().getSimpleName();
            if(!activeFragment.equals(BookmarkFragment.class.getSimpleName())){
                goToFragment(bookmarkFragment,false);
            }
        }
        else if (id == R.id.nav_rate) {
            goToFragment(new RateFragment(),false);
        } else if (id == R.id.nav_help) {
            goToFragment(new HelpFragment(),false);
        } else if (id == R.id.nav_about) {
            goToFragment(new AboutFragment(),false);
        } else if (id == R.id.nav_share) {
            goToFragment(new ShareFragment(),false);
        } else if (id == R.id.nav_dictionnary) {
            String activeFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getClass().getSimpleName();
            if(!activeFragment.equals(DictionnaryFragment.class.getSimpleName())){
                goToFragment(dictionnaryFragment,true);
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void goToFragment(Fragment fragment, boolean isTop){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String activeFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main).getClass().getSimpleName();
        if(activeFragment.equals(BookmarkFragment.class.getSimpleName())){
            menuSetting.setVisible(false);
            binding.appBarMain.toolbar.findViewById(R.id.edit_search).setVisibility(View.GONE);
            binding.appBarMain.toolbar.setTitle("Bookmark");
        }
        else{
            menuSetting.setVisible(true);
            binding.appBarMain.toolbar.findViewById(R.id.edit_search).setVisibility(View.VISIBLE);
            binding.appBarMain.toolbar.setTitle("");
        }
        return true;
    }
}