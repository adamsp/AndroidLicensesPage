/**
 * Copyright 2013 Adam Speakman

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.example.androidlicensespageexample;

import android.os.AsyncTask;
import android.os.Bundle;
// TODO If you don't support Android 2.x, you should use the non-support version!
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Adam Speakman on 24/09/13.
 * http://speakman.net.nz
 */
public class LicensesFragment extends DialogFragment {

    private AsyncTask<Void, Void, String> mLicenseLoader;

    private static final String FRAGMENT_TAG = "nz.net.speakman.androidlicensespage.LicensesFragment";

    public static LicensesFragment newInstance() {
        return new LicensesFragment();
    }

    /**
     * Builds and displays a licenses fragment for you. Requires "/res/raw/licenses.html" and
     * "/res/layout/licenses_fragment.xml" to be present.
     *
     * @param fm A fragment manager instance used to display this LicensesFragment.
     */
    public static void displayLicensesFragment(FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag(FRAGMENT_TAG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = LicensesFragment.newInstance();
        newFragment.show(ft, FRAGMENT_TAG);
    }

    private WebView mWebView;
    private ProgressBar mIndeterminateProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Extract this title out into your strings resource file.
        getDialog().setTitle("Open Source licenses");
        View view = inflater.inflate(R.layout.licenses_fragment, container, false);
        mIndeterminateProgress = (ProgressBar)view.findViewById(R.id.licensesFragmentIndeterminateProgress);
        mWebView = (WebView)view.findViewById(R.id.licensesFragmentWebView);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadLicenses();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLicenseLoader != null) {
            mLicenseLoader.cancel(true);
        }
    }

    private void loadLicenses() {
        // Load asynchronously in case of a very large file.
        mLicenseLoader = new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                InputStream rawResource = getActivity().getResources().openRawResource(R.raw.licenses);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(rawResource));

                String line;
                StringBuilder sb = new StringBuilder();

                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                        sb.append("\n");
                    }
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO You may want to include some logging here.
                }

                return sb.toString();
            }

            @Override
            protected void onPostExecute(String licensesBody) {
                super.onPostExecute(licensesBody);
                if (getActivity() == null || isCancelled()) return;
                mIndeterminateProgress.setVisibility(View.INVISIBLE);
                mWebView.setVisibility(View.VISIBLE);
                mWebView.loadDataWithBaseURL(null, licensesBody, "text/html", "utf-8", null);
                mLicenseLoader = null;
            }

        }.execute();
    }
}
