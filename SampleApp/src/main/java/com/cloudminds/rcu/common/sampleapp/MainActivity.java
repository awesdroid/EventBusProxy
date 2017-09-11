/*
 * Copyright (c) 2017. CloudMinds Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cloudminds.rcu.common.sampleapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import butterknife.ButterKnife;

/**
 * @author Bin YANG <michael.yang@cloudminds.com>
 */

public class MainActivity extends AppCompatActivity {

    private FragmentManager mFragmentManager;
    private FragmentEventBusTest mFragmentEventBusTest;
    private Fragment mCurrentFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_eventbus:
                    if (mFragmentEventBusTest == null) {
                        throw new NullPointerException("mFragmentLogTest is NULL");
                    }
                    loadFragment(mFragmentEventBusTest);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = ButterKnife.findById(this, R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragment();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mCurrentFragment == null) {
            throw new NullPointerException("mCurrentFragment is NULL");
        }
        loadFragment(mCurrentFragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initFragment() {
        mFragmentManager = getFragmentManager();
        mFragmentEventBusTest = new FragmentEventBusTest();
        mCurrentFragment = mFragmentEventBusTest;
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mCurrentFragment);
        fragmentTransaction.commit();
    }

    private void loadFragment(Fragment fragment) {
        if (fragment == null) {
            throw new IllegalArgumentException("fragment is NULL");
        }

        if (mCurrentFragment == null) {
            throw new NullPointerException("mCurrentFragment is NULL");
        }

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        if (fragment != mCurrentFragment) {
            fragmentTransaction.remove(mCurrentFragment);
            fragmentTransaction.add(R.id.fragment_container, fragment);
        }
        fragmentTransaction.commit();

        mCurrentFragment = fragment;
    }

}
