package com.souptik.maiti.goscaleassignment.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.souptik.maiti.goscaleassignment.GoScaleApplication
import com.souptik.maiti.goscaleassignment.di.components.DaggerFragmentComponent
import com.souptik.maiti.goscaleassignment.di.components.FragmentComponent
import com.souptik.maiti.goscaleassignment.di.modules.FragmentModule
import com.souptik.maiti.goscaleassignment.utils.Toaster
import javax.inject.Inject

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    @Inject
    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies(buildFragmentComponent())
        super.onCreate(savedInstanceState)
        setupObservers()
        viewModel.onCreate()
    }

    private fun buildFragmentComponent() =
        DaggerFragmentComponent
            .builder()
            .applicationComponent((context!!.applicationContext as GoScaleApplication).applicationComponent)
            .fragmentModule(FragmentModule(this))
            .build()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(provideLayoutId(), container, false)

    protected open fun setupObservers() {
        viewModel.messageString.observe(this, Observer {
            it?.run { showMessage(this) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
    }


    fun showMessage(message: String) = context?.let { Toaster.showShort(it, message) }


    fun goBack() {
        if (activity is BaseActivity<*>) (activity as BaseActivity<*>).goBack()
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun injectDependencies(fragmentComponent: FragmentComponent)

    protected abstract fun setupView(view: View)
}