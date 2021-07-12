package com.code.challenge.common.base.views

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("unused", "LeakingThis")
abstract class BaseDialogFragment<BINDING : ViewBinding> : DialogFragment() {

  private var _binding: BINDING? = null
  protected val binding get() = _binding!!
  protected val disposables = CompositeDisposable()
  private val snackbars = mutableMapOf<Snackbar, BaseTransientBottomBar.BaseCallback<Snackbar>>()

  protected var isRefreshing: Boolean = false

  protected abstract fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): BINDING

  @CallSuper
  override fun onStart() {
    super.onStart()
  }

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = onBind(inflater, container)
    return binding.root
  }
  fun add(disposable: Disposable) {
    disposables.add(disposable)
  }

  @SuppressLint("UseRequireInsteadOfGet")
  @CallSuper
  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
    disposables.dispose()
  }
}