package com.daniyalirfan.kotlinbasewithcorutine.baseclasses

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.daniyalirfan.kotlinbasewithcorutine.ui.activity.MainActivity
import com.daniyalirfan.kotlinbasewithcorutine.SharedViewModel
import com.daniyalirfan.kotlinbasewithcorutine.utils.DialogClass


abstract class BaseFragment<T : ViewDataBinding, V : BaseViewModel> : Fragment() {

    lateinit var sharedViewModel: SharedViewModel

    private var mActivity: BaseActivity<*, *>? = null
    private lateinit var mViewDataBinding: T
    protected lateinit var mViewModel: V
    protected lateinit var loadingDialog: Dialog

    abstract val layoutId: Int
    abstract val viewModel: Class<V>
    abstract val bindingVariable: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        return mViewDataBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(bindingVariable, mViewModel)
        mViewDataBinding.lifecycleOwner = this
        mViewDataBinding.executePendingBindings()
        subscribeToShareLiveData()
        subscribeToNavigationLiveData()
        subscribeToViewLiveData()
    }

    open fun subscribeToViewLiveData() {

        //observe view data

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>)
            this.mActivity = context
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = ViewModelProviders.of(this).get(viewModel)
        loadingDialog = DialogClass.loadingDialog(requireContext())
        sharedViewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        subscribeToNetworkLiveData()
    }

    open fun subscribeToShareLiveData() {

    }

    open fun subscribeToNetworkLiveData() {
        //All Network Tasks
    }

    open fun subscribeToNavigationLiveData() {

    }

    open fun showProgressBar() {
        (activity as MainActivity).showProgressBar()
    }

    open fun hideProgressBar() {
        (activity as MainActivity).hideProgressBar()
    }

    open fun toast(msg: String){
        Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
    }
}