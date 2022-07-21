package com.example.myapplication.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.myapplication.data.remote.api.Resource
import com.example.myapplication.util.onProgress
import com.example.myapplication.viewModel.BaseViewModel
import androidx.lifecycle.Observer

/**
 * Created by Samira Salah
 */
abstract class BaseFragment   < B: ViewDataBinding,V:BaseViewModel>: Fragment() {
    lateinit var mBinding: B
    protected abstract val  mViewModel: V

    abstract fun getLayoutResId(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DataBindingUtil.inflate<B>(inflater, getLayoutResId(), container, false).apply {
        lifecycleOwner = viewLifecycleOwner
        mBinding=this
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initEvent()
    }

    private fun initEvent() {
        mViewModel.resourceLiveData.observe(requireActivity(), Observer {

            when (it){
                is Resource.Success -> {
                    requireContext().onProgress(false )
                }
                is Resource.Loading ->  requireContext().onProgress(true )
                is Resource.Failure -> {
                    requireContext().onProgress(false )
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                else -> {
                    requireContext().onProgress(false )
                    Toast.makeText(requireContext(), "Something wrong", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}