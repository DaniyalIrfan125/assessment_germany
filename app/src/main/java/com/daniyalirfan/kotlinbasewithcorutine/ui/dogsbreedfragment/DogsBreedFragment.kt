package com.daniyalirfan.kotlinbasewithcorutine.ui.dogsbreedfragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniyalirfan.kotlinbasewithcorutine.BR
import com.daniyalirfan.kotlinbasewithcorutine.R
import com.daniyalirfan.kotlinbasewithcorutine.baseclasses.BaseFragment
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogBreedsResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogImagesListResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.remote.Resource
import com.daniyalirfan.kotlinbasewithcorutine.databinding.FragmentDogsBreedLayoutBinding
import com.daniyalirfan.kotlinbasewithcorutine.ui.dogsbreedfragment.adapter.DogImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dogs_breed_layout.*

@AndroidEntryPoint
class DogsBreedFragment : BaseFragment<FragmentDogsBreedLayoutBinding, DogsBreedViewModel>() {

    private var dogsBreedModel: List<DogBreedsResponseModel>? = null
    private var mBreedId: Int = 0

    //dog images adapter
    private lateinit var dogImagesAdapter: DogImagesAdapter
    private var dogImagesList: ArrayList<DogImagesListResponseModel?>? = ArrayList()

    //dogsBreed spinner
    private var breedNameList: ArrayList<String> = arrayListOf()
    lateinit var breedListAdapter: ArrayAdapter<String>

    override val layoutId: Int
        get() = R.layout.fragment_dogs_breed_layout
    override val viewModel: Class<DogsBreedViewModel>
        get() = DogsBreedViewModel::class.java
    override val bindingVariable: Int
        get() = BR.viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel.getDogsBreedList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialization()
    }

    private fun initialization() {
        //breed spinner item listener
        breedSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val breedId = dogsBreedModel?.get(position)?.id!!
                mBreedId = breedId
                mViewModel.getDogImages(breedId)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        //initializing dogs images adapter
        dogImagesAdapter = DogImagesAdapter(requireContext(), dogImagesList)
        dogImagesRv!!.layoutManager =
            GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        dogImagesRv!!.adapter = dogImagesAdapter
    }

    override fun subscribeToNetworkLiveData() {
        super.subscribeToNetworkLiveData()

        //observing breed list
        mViewModel.dogBreedsListResponse.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    dogsBreedModel = it.data
                    breedNameList.clear()
                    it.data?.forEach { breed ->
                        breedNameList.add(breed.name!!)
                    }

                    breedListAdapter = ArrayAdapter(
                        requireContext(),
                        R.layout.support_simple_spinner_dropdown_item,
                        breedNameList.toMutableList()
                    )
                    breedSpinner.adapter = breedListAdapter
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (it.message != null) {
                        toast(it.message)
                    }
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
            }
        })

        //observing dog images list
        mViewModel.dogImagesListResponse.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    loadingDialog.dismiss()
                    dogImagesList?.clear()
                    dogImagesList?.addAll(it.data!!)
                    dogImagesAdapter.notifyDataSetChanged()
                }

                Resource.Status.ERROR -> {
                    loadingDialog.dismiss()
                    if (it.message != null) {
                        toast(it.message)
                    }
                }

                Resource.Status.LOADING -> {
                    loadingDialog.show()
                }
            }
        })
    }
}