# PreLollipopTransition
Simple tool which help you to activity transition for pre-Lollipop devices.

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



## Sample
![image](https://cloud.githubusercontent.com/assets/1386930/6871816/7e2a25f2-d4e8-11e4-966d-028014e79a5a.gif)

## Thanks
Sample Photo
Luke Ma
https://www.flickr.com/photos/lukema/12499338274/in/photostream/

DevBytes: Window Animations
https://www.youtube.com/watch?v=Ho8vk61lVIU

## License

This project is released under the Apache License, Version 2.0.

* [The Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0)
