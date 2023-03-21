package com.srwing.album

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.srwing.album.callback.SelectCallback
import com.srwing.album.databinding.ActivityMainBinding
import com.srwing.album.engine.GlideEngine
import com.srwing.album.models.album.entity.Photo
import com.srwing.gxylib.coreui.mvvm.BaseBindingActivity

class MainActivity : BaseBindingActivity<ActivityMainBinding>() {
    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initViewData() {
        super.initViewData()
        val selectedPhotoList = java.util.ArrayList<Photo>()
        dataBinding.recycle.setLayoutManager(GridLayoutManager(this, 3))
        var mAdapter = Adapter(this)
        dataBinding.recycle.adapter = mAdapter
        dataBinding.rectBar.setOnClickListener {
            EasyPhotos.createAlbum(this, true, false, GlideEngine.getInstance()) //                        //.setFileProviderAuthority("com.huantansheng.easyphotos.demo.fileprovider")
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
    }
}


class Adapter(context: Context) : BaseQuickAdapter<Photo, BaseViewHolder>(R.layout.main_store_periphery_item) {
    override fun convert(holder: BaseViewHolder, item: Photo) {
        RoundImgUtil.setRoundImg(context, item.path, holder.getView(R.id.img), PxUtils.dip2px(context, 5f))

    }

}