package com.inyomanw.corelibrary.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    private val isLoading = MutableLiveData<Boolean>()
    fun observeLoading(): LiveData<Boolean> = isLoading

    protected val isError = MutableLiveData<NetworkError?>()
    fun observeError(): LiveData<NetworkError?> = isError

    protected val isEmtyData = MutableLiveData<Boolean>()
    fun observeEmptyData(): LiveData<Boolean> = isEmtyData

    private fun launch(movie: () -> Disposable) {
        disposable.add(movie())
    }

    protected fun <T> Single<T>.onResult(action: (T) -> Unit, error: (NetworkError) -> Unit) {
        launch {
            this
                .doOnSubscribe {
                    isLoading.postValue(true)
                }
                .doOnError {
                    isLoading.postValue(false)
                }
                .doOnSuccess {
                    isLoading.postValue(false)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe(
                    { result ->
                        action.invoke(result)
                        isLoading.postValue(false)
                    },
                    { err ->
                        error.invoke(NetworkError(err))
                        isLoading.postValue(false)
                    }
                )
        }
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}