# PreLollipopTransition

![build status](https://circleci.com/gh/takahirom/PreLollipopTransition/tree/master.svg?style=shield&circle-token=1759143e61c73eeca59280337c8c95ab9f8dbafb) [![API](https://img.shields.io/badge/API-14%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=14)

Simple tool which help you to implement activity and fragment transition for pre-Lollipop devices.

![prelollipopanimation](https://cloud.githubusercontent.com/assets/1386930/7614211/53ca12d8-f9d0-11e4-8b98-b6d98272f67d.gif)

## Download
In your app build.gradle add

```groovy
dependencies {
    compile 'com.kogitune:pre-lollipop-activity-transition:1.3.2'
}
```

## Code
### Activity
Start Activity in first activity.

```java
findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(MainActivity.this, SubActivity.class);
        ActivityTransitionLauncher.with(MainActivity.this).from(v).launch(intent);
    }
});
```

Receive intent in second activity.

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub);
    ActivityTransition.with(getIntent()).to(findViewById(R.id.sub_imageView)).start(savedInstanceState);
}
```

#### If you want the exit animation, you can do like this.

```java
private ExitActivityTransition exitTransition;
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub2);
    exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.sub_imageView)).start(savedInstanceState);
}
@Override
public void onBackPressed() {
    exitTransition.exit(this);
}
```

#### If you want to use `startActivityForResult`, you should do below.
1. Use `createBundle()`.
2. Put Extra to intent.
3. Call overridePendingTransition after `startActivityForResult`.

```java
Bundle transitionBundle = ActivityTransitionLauncher.with(MainActivity.this).from(v).createBundle();
intent.putExtras(transitionBundle);
startActivityForResult(intent, REQUEST_CODE);
// you should prevent default activity transition animation
overridePendingTransition(0, 0);
```

### Fragment
Start fragment transition in first fragment.

```java
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.support_fragment_start, container, false);
    v.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final EndFragment toFragment = new EndFragment();
            FragmentTransitionLauncher
                    .with(view.getContext())
                    .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
                    .from(view.findViewById(R.id.imageView)).prepare(toFragment);
            getFragmentManager().beginTransaction().replace(R.id.content, toFragment).addToBackStack(null).commit();
        }
    });
    return v;
}
```

Start animation in second fragment.
```java
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View v = inflater.inflate(R.layout.support_fragment_end, container, false);
    final ExitFragmentTransition exitFragmentTransition = FragmentTransition.with(this).to(v.findViewById(R.id.fragment_imageView)).start(savedInstanceState);
    exitFragmentTransition.startExitListening();
    return v;
}
```

If you want to pass argument to second fragment, you should use `Fragment#getArguments()` after call prepare().

```java
final EndFragment toFragment = new EndFragment();
FragmentTransitionLauncher
        .with(view.getContext())
        .image(BitmapFactory.decodeResource(getResources(), R.drawable.photo))
        .from(view.findViewById(R.id.imageView)).prepare(toFragment);
toFragment.getArguments().putString("stringArgKey", "this is value");
getFragmentManager().beginTransaction().replace(R.id.content, toFragment).addToBackStack(null).commit();
```

## Sample
![image](https://cloud.githubusercontent.com/assets/1386930/7668974/019262a0-fc95-11e4-906a-84a2b744a12c.gif)

## Contributors
* [shiraji](http://www.github.com/shiraji)


## Thanks
Sample Photo
Luke Ma
https://www.flickr.com/photos/lukema/12499338274/in/photostream/

DevBytes: Custom Activity Animations
https://www.youtube.com/watch?v=CPxkoe2MraA

## License

This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
