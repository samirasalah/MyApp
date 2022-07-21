package com.example.myapplication.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.myapplication.viewModel.BaseViewModel


/**
 * Created by Samira Salah
 */
abstract class BaseActivity<B:ViewDataBinding, V:BaseViewModel>  : AppCompatActivity(){
    lateinit var binding: B
    protected abstract val  mViewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            layoutInflater, getLayoutResId(), null, false)

        setContentView(binding.root)
    }

    abstract fun getLayoutResId(): Int

}