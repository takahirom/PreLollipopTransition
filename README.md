# PreLollipopTransition
Simple tool which help you to implement activity transition for pre-Lollipop devices.
![prelollipopanimation](https://cloud.githubusercontent.com/assets/1386930/7614022/1db52580-f9cf-11e4-8d2a-5b58fb9a0b0b.gif)

## Download
In your app build.gradle add

```
dependencies {
    compile 'com.kogitune:pre-lollipop-activity-transition:0.0.1'
}
```

## Code
Start Activity in first activity.

```
findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final Intent intent = new Intent(MainActivity.this, SubActivity.class);
        ActivityTransitionLauncher.with(MainActivity.this).from(v).launch(intent);
    }
});
```

Receive intent in second activity.

```
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.sub_imageView)).start(savedInstanceState);
    }
```

If if you want the exit animation, you can do like this.
```
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

## Sample
![image](https://cloud.githubusercontent.com/assets/1386930/6871816/7e2a25f2-d4e8-11e4-966d-028014e79a5a.gif)

## Thanks
Sample Photo
Luke Ma
https://www.flickr.com/photos/lukema/12499338274/in/photostream/

DevBytes: Custom Activity Animations
https://www.youtube.com/watch?v=CPxkoe2MraA

## License

This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
