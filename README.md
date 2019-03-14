# android_flutter_demo
- demonstrates how to integrate flutter to existing code

## Get Started
- create flutter module
  - `flutter create -t module flutter_demo`
  - you can put `flutter_demo` inside `android_flutter_demo` or beside `android_flutter_demo`
  - `NOTE`: a better solution is make `flutter_demo` a git repo and add it to android_flutter_demo as submodule
    - ref: https://github.com/hpsoar/flutter_demo
- setup existing project
  - udpate `settings.gradle`
    - ref: https://github.com/hpsoar/android_flutter_demo/blob/master/settings.gradle
    - `NOTE`: use `settingsDir` or `settingsDir.parentFile` depends on you put `flutter_demo` inside or beside existing project
  - update 'app/build.gradle
    - ref: https://github.com/hpsoar/android_flutter_demo/blob/master/app/build.gradle
    - `NOTE`: 
      - flutter & flutter_utils are set in https://github.com/hpsoar/android_flutter_demo/blob/master/config.gradle
      - you can also ref the two directly by `implementation project(':flutter')` and `implementation project(':flutter_utils'),`
      - `:flutter` is the android project generated in flutter_demo when you create the project
      - `:flutter_utils` is project for utils that make flutter easier to use in exsiting android project, you can go without it
      - one more thing, if you accidentally removed flutter_demo/.android or flutter_demo/.ios, run `flutter packages get` in flutter_demo
