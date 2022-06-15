package com.banerjeec713.requestlydebugger.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.banerjeec713.requestlydebugger.R
import com.banerjeec713.requestlydebugger.ui.base.BaseFragment
import com.banerjeec713.requestlydebugger.util.Constants.TAG

class MainFragment : BaseFragment<MainViewModel>() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =  MainFragment()
    }

    override fun getViewModel(): MainViewModel {
        Log.d(TAG, "getViewModel: $fViewModel")
        return fViewModel!!
    }
}