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


