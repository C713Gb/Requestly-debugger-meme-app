package com.banerjeec713.requestlydebugger.util

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.View
import com.google.android.material.snackbar.Snackbar
import android.widget.TextView
import com.banerjeec713.requestlydebugger.R

object Util {

    fun isNetworkAvailable(context: Context): Boolean {
        val mConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        // Test for connection
        val mCapabilities =
            mConnectivityManager.getNetworkCapabilities(mConnectivityManager.activeNetwork)
        return mCapabilities != null &&
                (mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        mCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }

    // Showing the status in Snackbar
    fun showSnack(view: View, isError: Boolean, message: String?) {
        var color = Color.WHITE
        if (isError) color = Color.RED
        val snackbar = Snackbar
            .make(view, message!!, Snackbar.LENGTH_LONG)
        val sbView = snackbar.view
        val textView = sbView.findViewById<TextView>(R.id.snackbar_text)
        textView.setTextColor(color)
        snackbar.show()
    }
}