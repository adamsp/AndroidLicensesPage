Android Licenses Page
============
This repository contains an example of how to produce a licenses page for an Android app similar to the ones found in Google apps such as Play Music and Gmail.

## How to use

You need to include `LicensesFragment.java` in your projects source, as well as including the `licenses_fragment.xml` layout file in `/res/layout` and the `licenses.html` file in `/res/raw`. You should update the namespace to suit.

To display licenses for your app, you need to update the `licenses.html` file to suit (including any libraries you've used, their licenses, copyrights, and any links to source you may have modified, if required), then you can display it as you would any other [DialogFragment](http://developer.android.com/reference/android/app/DialogFragment.html):

```java
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
```

There are some TODOs in the LicensesFragment file - you should modify these things to suit your environment, though things will work fine without you needing to touch anything.

If you clone the repository, you should be able to open the included AndroidLicensesPageExampleProject in Android Studio and run it directly on any device running Android 2.1 or higher.

## License

    Copyright 2013 Adam Speakman

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
