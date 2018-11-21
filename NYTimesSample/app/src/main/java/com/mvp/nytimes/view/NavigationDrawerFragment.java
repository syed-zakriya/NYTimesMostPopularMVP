package com.mvp.nytimes.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mvp.nytimes.R;

/*
    This is a sample navigation drawer fragment.
    It contains a dummy view showing profile image
 */

public class NavigationDrawerFragment extends Fragment{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarToggle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation_drawer,container,false);
        return view;
    }

    public void setupNavDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar)
    {
        mDrawerLayout = drawerLayout;
        //handling the toggle of Navigation drawer button
        mActionBarToggle = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarToggle);
        //put the syncState in runnable, as it handles the icons changing from the burger icon to the back icon
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarToggle.syncState();
            }
        });
    }
}
