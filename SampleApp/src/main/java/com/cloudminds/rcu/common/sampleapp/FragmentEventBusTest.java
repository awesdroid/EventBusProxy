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
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cloudminds.rcu.common.cmeventbus.CmEventBus;
import com.cloudminds.rcu.common.cmeventbus.model.Subscriber;
import com.cloudminds.rcu.common.sampleapp.model.TestEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.cloudminds.rcu.common.sampleapp.model.TestEvent.EventId.EVT_TEST;


/**
 * @author Bin YANG <michael.yang@cloudminds.com>
 */

public class FragmentEventBusTest extends Fragment {
    @BindView(R.id.bt_eb_send) Button mButton;
    @BindView(R.id.tv_eb) TextView mTextView;
    @BindView(R.id.editor_eb) EditText mEditor;
    Unbinder mUnbider;
    private Subscriber subscriber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventbus, container, false);
        mUnbider = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = mEditor.getText().toString();
                CmEventBus.send(new TestEvent().setId(EVT_TEST).setData(data));
            }
        });
        CmEventBus.init();
    }

    @Override
    public void onResume() {
        super.onResume();
        subscriber = new Subscriber<TestEvent>() {
            @Override
            public void onEvent(TestEvent event) {
                onTestEvent(event);
            }
        };
        CmEventBus.register(subscriber);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUnbider != null) {
            mUnbider.unbind();
        }
        CmEventBus.unregister(subscriber);
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onTestEvent(final TestEvent event) {
        switch (event.getId()) {
            case EVT_TEST:
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTextView.setText((String)event.getData());
                    }
                });
                break;
            default:
                break;
        }
    }
}
