package com.mvp.nytimes.view;

import android.app.SearchManager;
import android.content.Context;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.mvp.nytimes.R;
import com.mvp.nytimes.adapter.MostPopularListAdapter;
import com.mvp.nytimes.contract.AppContractMvp;
import com.mvp.nytimes.model.MostViewedModel;
import com.mvp.nytimes.presenter.GetMostPopularIntractorImpl;
import com.mvp.nytimes.presenter.MostPopularPresenterImpl;

import java.util.ArrayList;

public class MostPopularActivity extends AppCompatActivity implements AppContractMvp.MostPopularListView {
    Toolbar toolbar;
    RecyclerView recyclerView;
    CustomProgressDialog progressDialog;
    SearchView searchView;
    MostPopularListAdapter adapter;
    private AppContractMvp.MostPopularPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_most_popular);
        initToolbarAndRecyclerView();
        setupNavDrawer();

        mPresenter = new MostPopularPresenterImpl(this, new GetMostPopularIntractorImpl(MostPopularActivity.this));
        mPresenter.fetchDataUsingAPI();
    }

    public void setupNavDrawer()
    {
        //Create instance of navigation drawer and use it in the main activity
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.nav_drawer_fragment);
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        navigationDrawerFragment.setupNavDrawer(R.id.nav_drawer_fragment,drawerLayout,toolbar);
    }

    public void initToolbarAndRecyclerView()
    {
        //init the toolbar
        toolbar = findViewById(R.id.toolbar);
        //inflate toolbar with menu items
        setupToolbar();

        //init the recyclerView
        recyclerView = findViewById(R.id.popular_recycler_view);
        LinearLayoutManager linLayoutMgr = new LinearLayoutManager(this);
        linLayoutMgr.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linLayoutMgr);

        //Setup the functionality of search button on toolbar
        setupSearchView();
    }

    public void setupToolbar()
    {
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.action_search:
                        Log.d("MY_APP","Search clicked");
                        break;
                    case R.id.action_settings:
                        Log.d("MY_APP","Settings Clicked");
                }
                return true;
            }
        });
    }

    public void setupSearchView()
    {
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(adapter!=null)
                    adapter.getFilter().filter(query);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(adapter!=null)
                    adapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void showToastMessage(String message, int statusCode) {
        String txt = getResources().getString(R.string.msg_text)+":"+message+"\n"+getResources().getString(R.string.status_text)+":"+statusCode;
        Toast.makeText(getApplicationContext(),txt,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new CustomProgressDialog(this);
            progressDialog.setCancelable(false);
        }
        progressDialog.showDialog();
    }

    @Override
    public void hideProgressDialog() {
        if (progressDialog!=null)
            progressDialog.hideDialog();
    }

    @Override
    public void addDataToRecyclerView(ArrayList<MostViewedModel> list) {
        adapter = new MostPopularListAdapter(list,MostPopularActivity.this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onFailedResponse(String message, int statusCode) {
        showToastMessage(message,statusCode);
    }
}
