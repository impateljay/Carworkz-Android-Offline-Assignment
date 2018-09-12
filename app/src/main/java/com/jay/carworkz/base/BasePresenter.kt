package com.jay.carworkz.base

interface BasePresenter<in T : BaseView> {
    fun onAttach(view: T)
    fun onDetach()
}