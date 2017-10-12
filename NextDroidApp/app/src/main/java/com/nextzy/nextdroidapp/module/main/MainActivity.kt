package com.nextzy.nextdroidapp.module.main

import android.os.Bundle
import com.nextzy.nextdroidapp.R
import com.nextzy.tabcustomize.base.mvvm.CustomMvvmActivity

class MainActivity : CustomMvvmActivity() {

    private lateinit var viewModel: MainViewModel

    override
    fun setupViewModel(){
        viewModel = getViewModel( MainViewModel::class.java)
    }

    override
    fun setupLayoutView(): Int = R.layout.activity_main


    override
    fun setupInstance() {

    }

    override
    fun setupView() {

    }

    override
    fun initialize() {

    }

    override
    fun onRestoreArgument(bundle: Bundle) {
        super.onRestoreArgument(bundle)
    }

    override
    fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

    override
    fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}
