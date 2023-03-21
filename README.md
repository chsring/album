# album-select-lib
- refrence EasyPhotos
- Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
    allprojects {
        repositories {
//            ...
            maven { url 'https://jitpack.io' }
        }
    }
```

- Step 2. Add the dependency
```groovy
    dependencies {
            implementation 'com.github.chsring:album:1.0.2'
    }
```

- Step 4. how to use
```kotlin
dataBinding.rectBar.setOnClickListener {
    EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance())
        .setCount(22)
        .setSelectedPhotos(selectedPhotoList)
        .setPuzzleMenu(false)
        .start(object : SelectCallback() {
            override fun onResult(photos: ArrayList<Photo>?, isOriginal: Boolean) {
                photos?.let {
                    selectedPhotoList.clear()
                    selectedPhotoList.addAll(it)
                    mAdapter.setList(selectedPhotoList)
                }
            }

            override fun onCancel() {
                ToastUtils.showShort("cancel")
            }
        })
}
```