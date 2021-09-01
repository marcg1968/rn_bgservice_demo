## Creating the project

```bash
npx --package react-native-cli react-native init rn_bgservice_demo
cd rn_bgservice_demo
FP="$HOME/Android/Sdk"
echo -e "sdk.dir = ${FP}/\n" | tee android/local.properties
npx react-native run-android
```

Open a new console and run

```bash
yarn start
```

Before opening in Android Studio, check the version of Android Studio matches the version
in the following stanza in the file ``./android/build.gradle``:

```diff
dependencies {
-        classpath("com.android.tools.build:gradle:4.2.1")
+        classpath("com.android.tools.build:gradle:4.1.3")
         // NOTE: Do not place your application dependencies here; they belong
         // in the individual module build.gradle files
     }
```

Also change the version if nec. in the ``buildscript`` section of the same file:

```
buildscript {
    ext {
        buildToolsVersion = "29.0.3"
        minSdkVersion = 21
        compileSdkVersion = 29
        targetSdkVersion = 29
        ndkVersion = "20.1.5948944"
    }
...
```
Then run _F_ile \> Sync Project with _G_radle Files to update.
