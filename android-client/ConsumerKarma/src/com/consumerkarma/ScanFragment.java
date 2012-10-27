
package com.consumerkarma;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class ScanFragment extends Fragment {

    private Button mBtnScan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mBtnScan = (Button) getView().findViewById(R.id.btn_scan);
        mBtnScan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                getBarcoeScan();
            }
        });
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.scan_fragment, container, false);
    }

}
