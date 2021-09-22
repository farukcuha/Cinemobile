package com.pandorina.cinemobile.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<T : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T,
    private val argumentTag: String?
) : Fragment() {
    private var _binding: T? = null
    val binding get() = _binding!!

    var argument: Any? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        argument = arguments?.get(argumentTag)
        setUpViews()
        observeData()
    }

    abstract fun observeData()

    abstract fun setUpViews()

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}