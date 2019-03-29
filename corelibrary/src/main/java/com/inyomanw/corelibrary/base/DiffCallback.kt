package com.inyomanw.corelibrary.base

import android.support.v7.util.DiffUtil

class DiffCallback : DiffUtil.Callback() {

    private var oldList: List<Any> = emptyList()
    private var newList: List<Any> = emptyList()

    fun setList(oldList: List<Any>, newList: List<Any>) {
        this.oldList = oldList
        this.newList = newList
    }

    override fun areItemsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0] == newList[p1]
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(p0: Int, p1: Int): Boolean {
        return oldList[p0] == newList[p1]
    }
}