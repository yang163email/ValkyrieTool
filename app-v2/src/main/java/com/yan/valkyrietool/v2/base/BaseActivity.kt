package com.yan.valkyrietool.v2.base

import android.support.v7.app.AppCompatActivity
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import org.jetbrains.anko.toast

/**
 *  @author : yan
 *  @date   : 2018/7/11 11:06
 *  @desc   : todo
 */
abstract class BaseActivity : AppCompatActivity() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    protected fun addDispose(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    abstract class BaseObserver<T>(val activity: BaseActivity) : Observer<T> {

        override fun onSubscribe(d: Disposable) {
            activity.addDispose(d)
        }

        override fun onError(e: Throwable) {
            activity.toast("请求出错：${e.message}")
            activity.hideLoading()
        }

        override fun onComplete() {
            activity.hideLoading()
        }
    }

    protected open fun hideLoading() {}
}