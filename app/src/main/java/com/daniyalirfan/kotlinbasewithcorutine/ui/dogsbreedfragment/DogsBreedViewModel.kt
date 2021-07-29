package com.daniyalirfan.kotlinbasewithcorutine.ui.dogsbreedfragment

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.daniyalirfan.kotlinbasewithcorutine.R
import com.daniyalirfan.kotlinbasewithcorutine.baseclasses.BaseViewModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogBreedsResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.DogImagesListResponseModel
import com.daniyalirfan.kotlinbasewithcorutine.data.models.PostsResponse
import com.daniyalirfan.kotlinbasewithcorutine.data.remote.Resource
import com.daniyalirfan.kotlinbasewithcorutine.data.remote.reporitory.MainRepository
import com.daniyalirfan.kotlinbasewithcorutine.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogsBreedViewModel @Inject constructor(
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper,
    @ApplicationContext private val context: Context
) : BaseViewModel() {

    private val _dogBreedsListResponse = MutableLiveData<Resource<List<DogBreedsResponseModel>>>()
    val dogBreedsListResponse: LiveData<Resource<List<DogBreedsResponseModel>>>
        get() = _dogBreedsListResponse

    private val _dogImagesListResponse = MutableLiveData<Resource<List<DogImagesListResponseModel>>>()
    val dogImagesListResponse: LiveData<Resource<List<DogImagesListResponseModel>>>
        get() = _dogImagesListResponse

    //api call for breed list
    fun getDogsBreedList() {
        viewModelScope.launch {
            _dogBreedsListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getDogsBreedList().let {
                        if (it.isSuccessful) {
                            _dogBreedsListResponse.postValue(Resource.success(it.body()!!))
                        } else {
                            _dogBreedsListResponse.postValue(
                                Resource.error(
                                    it.message(),
                                    null,
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _dogBreedsListResponse.postValue(Resource.error("" + e.message, null, null))
                }
            } else {
                _dogBreedsListResponse.postValue(
                    Resource.error(
                        context.getString(R.string.no_internet_connection),
                        null,
                        null
                    )
                )
            }
        }
    }

    //api call for dog images
    fun getDogImages(breedId: Int) {
        viewModelScope.launch {
            _dogImagesListResponse.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                try {
                    mainRepository.getDogImages(breedId).let {
                        if (it.isSuccessful) {
                            _dogImagesListResponse.postValue(Resource.success(it.body()!!))
                        } else {
                            _dogImagesListResponse.postValue(
                                Resource.error(
                                    it.message(),
                                    null,
                                    null
                                )
                            )
                        }
                    }
                } catch (e: Exception) {
                    _dogImagesListResponse.postValue(Resource.error("" + e.message, null, null))
                }
            } else {
                _dogImagesListResponse.postValue(
                    Resource.error(
                        context.getString(R.string.no_internet_connection),
                        null,
                        null
                    )
                )
            }
        }
    }

}