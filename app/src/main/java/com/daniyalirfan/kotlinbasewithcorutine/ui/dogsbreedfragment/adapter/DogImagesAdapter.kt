package com.daniyalirfan.kotlinbasewithcorutine.ui.dogsbreedfragment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.daniyalirfan.kotlinbasewithcorutine.R
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogImagesListResponseModel
import com.github.ybq.android.spinkit.SpinKitView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class DogImagesAdapter(
    private val context: Context,
    private val list: List<DogImagesListResponseModel?>?
) :
    RecyclerView.Adapter<DogImagesAdapter.DogsImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogsImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DogsImagesViewHolder(
            inflater,
            parent
        )
    }

    override fun onBindViewHolder(holder: DogsImagesViewHolder, position: Int) {
        val dogImageModel: DogImagesListResponseModel? = list!![position]
        holder.bind(dogImageModel!!, context)
    }

    override fun getItemCount(): Int = list!!.size

    class DogsImagesViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_images_layout, parent, false)) {

        var image: AppCompatImageView? = null
        private var spinKitItem: SpinKitView? = null

        init {
            image = itemView.findViewById(R.id.dogImage)
            spinKitItem = itemView.findViewById(R.id.item_spin_kit)
        }

        fun bind(model: DogImagesListResponseModel, mContext: Context) {
            spinKitItem!!.visibility = View.VISIBLE
            Picasso.get()
                .load(model.url)
                .fit()
                .centerCrop()
                .tag(mContext)
                .into(image, object : Callback {
                    override fun onSuccess() {
                        spinKitItem!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        spinKitItem!!.visibility = View.GONE
                    }
                })
        }
    }
}
