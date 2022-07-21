package com.example.myapplication.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSecondBinding
import com.example.myapplication.viewModel.SecondViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Samira Salah
 */
class SecondFragment : BaseFragment<FragmentSecondBinding,SecondViewModel>() {
    override val mViewModel: SecondViewModel by viewModel()

    override fun getLayoutResId(): Int=R.layout.fragment_second
    private val args: SecondFragmentArgs by navArgs()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       initView()
    }

    private fun initView() {
        mBinding.vm=mViewModel
        mViewModel.item.value=args.item
        mBinding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }



}