package com.banerjeec713.requestlydebugger.ui

import androidx.lifecycle.MutableLiveData
import com.banerjeec713.requestlydebugger.data.DataManager
import com.banerjeec713.requestlydebugger.data.models.MemeModel
import com.banerjeec713.requestlydebugger.data.models.ResponseModel
import com.banerjeec713.requestlydebugger.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel internal constructor(private val dataManager: DataManager) : BaseViewModel() {
    val error = MutableLiveData<Boolean>()
    val memes = MutableLiveData<MemeModel>()

    private val disposable: CompositeDisposable = CompositeDisposable()

    fun loadMeme(){
        disposable.add(
            dataManager.getMeme()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(ResponseModel::data)
                .subscribe({
                    if (it != null){
                        memes.postValue(it)
                        error.postValue(false)
                    }
                }) {this.error.postValue(true)}
        )
    }

    fun onClear() {
        disposable.dispose()
    }
}