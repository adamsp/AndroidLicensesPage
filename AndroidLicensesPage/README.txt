Paste this over your src folder, assuming you're using the Gradle project format introduced with Android Studio. 

If you're still using Eclipse, just drop the LicensesFragment.java file into an appropriate place in your source tree, the licenses_fragment.xml layout file into /res/layout, and make sure licenses.html is in /res/raw.

To use, just reference the LicensesFragment like any other DialogFragment:

    public void onLicensesClick(View view) {
        // Create & show a licenses fragment just as you would any other DialogFragment.
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("licensesDialogFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = LicensesFragment.newInstance();
        newFragment.show(ft, "licensesDialogFragment");
    }