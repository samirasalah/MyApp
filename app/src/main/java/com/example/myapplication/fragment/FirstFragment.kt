package com.example.myapplication.fragment

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.viewModel.FirstViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Created by Samira Salah
 */
class FirstFragment : BaseFragment<FragmentFirstBinding,FirstViewModel>() {
     override val mViewModel: FirstViewModel by viewModel()

     override fun getLayoutResId(): Int=R.layout.fragment_first

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
        initView()
     }

     private fun initView() {
         mBinding.vm=mViewModel
         mViewModel.getAdapterMethode(mBinding.rcv){
             findNavController().navigate(FirstFragmentDirections.actionFirstFragmentToSecondFragment(item=it))
         }
     }

    override fun onResume() {
        super.onResume()
        mViewModel.refreshList()
    }

}