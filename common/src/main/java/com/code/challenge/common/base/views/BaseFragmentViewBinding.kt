package com.code.challenge.common.base.views

import android.graphics.Typeface
import android.os.Build.VERSION
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.code.challenge.common.R.color
import com.code.challenge.common.extensions.hideKeyboard
import com.google.android.material.snackbar.BaseTransientBottomBar.BaseCallback
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.addTo

abstract class BaseFragmentViewBinding<BINDING : ViewBinding> : Fragment() {

  private var _binding: BINDING? = null
  val binding get() = _binding!!
  protected val disposables = CompositeDisposable()

  private val snackbars = mutableMapOf<Snackbar, BaseCallback<Snackbar>>()
  protected var isRefreshing: Boolean = false

  protected abstract fun onBind(
    inflater: LayoutInflater,
    container: ViewGroup?
  ): BINDING

  @CallSuper
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = onBind(inflater, container)
    return binding.root
  }

  @CallSuper
  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
  }

  fun add(disposable: Disposable) {
    disposables.add(disposable)
  }

  fun Disposable.addDisposable() {
    this.addTo(disposables)
  }

  fun <T : Any> setBackStackResult(key: String, data: T, doBack: Boolean = true) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
    if (doBack) {
      findNavController().popBackStack()
    }
  }

  fun <T : Any> getBackStackResult(key: String, result: (T) -> (Unit)) {
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)?.observe(viewLifecycleOwner) {
      result(it)
    }
  }

  override fun onDestroyView() {
    hideKeyboard()
    super.onDestroyView()
    _binding = null
    disposables.dispose()
  }

  protected fun buildSpannableMessage(
    string: String,
    boldString: String
  ): SpannableStringBuilder {
    val boldStringStartIdx = string.indexOf("%s")
    val boldStringEndIdx = boldStringStartIdx + boldString.length
    val parts = string.split("%s")
    val message = SpannableStringBuilder()
    if (parts[0].isNotEmpty()) message.append(parts[0])
    message.append(boldString)
    message.append(parts[1])

    message.setSpan(
      ForegroundColorSpan(ContextCompat.getColor(requireContext(), color.white)),
      boldStringStartIdx,
      boldStringEndIdx,
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    message.setSpan(
      StyleSpan(Typeface.BOLD),
      boldStringStartIdx,
      boldStringEndIdx,
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    return message
  }

  open fun refresh() {
    val ft: FragmentTransaction = parentFragmentManager.beginTransaction()
    if (VERSION.SDK_INT >= 26) {
      ft.setReorderingAllowed(false)
    }
    try {
      ft.detach(this)
        .attach(this)
        .commit()
    } catch (ignored: IllegalStateException) {
      // There's no way to avoid getting this if saveInstanceState has already been called.
    }
  }
}