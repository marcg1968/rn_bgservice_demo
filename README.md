# React Native (Android) Background Service Demo app

## Installing and running

```bash
git clone REPO
cd REPO_DIR
yarn install
yarn android
```

Run the metro server by opening a new console and issuing

```bash
yarn start
```

## Creating the project

The boilerplate basis of the project was created thus

```bash
npx --package react-native-cli react-native init rn_bgservice_demo
```

Ensure the path to Android SDK is set in ``./android/local.properties``
(a file which should not be checked into version control)

```bash
cd rn_bgservice_demo
FP="$HOME/Android/Sdk"
echo -e "sdk.dir = ${FP}/\n" | tee android/local.properties
```

Build the initial app for development:

```bash
npx react-native run-android # or yarn android
```bash

Run the metro server by opening a new console and issuing

```bash
yarn start
```


