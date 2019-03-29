package com.inyomanw.corelibrary.base

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class GeneralRecyclerViewAdapter<T : Any>(
    private val diffCallback: DiffCallback,
    @LayoutRes val holderResId: Int,
    @IdRes val specificResViewId: Int? = null,
    private val onBind: (T, View) -> Unit,
    private val itemListener: (T, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run { } },
    private val specificViewListener: (T, pos: Int, View) -> Unit = { _, _, _ -> kotlin.run { } }
) : RecyclerView.Adapter<GeneralRecyclerViewAdapter.GeneralViewHolder<T>>() {

    private val listData = mutableListOf<T>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): GeneralViewHolder<T> {
        val itemView = LayoutInflater.from(p0.context).inflate(holderResId,p0,false)
        var specificView : View? =null
        specificResViewId?.let {
            specificView = itemView?.findViewById(it)
        }
        return GeneralViewHolder(itemView,specificView)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(p0: GeneralViewHolder<T>, p1: Int) {
        p0.bindView(listData[p0.adapterPosition], onBind, itemListener, specificViewListener)
    }

    fun setData(datas: List<T>) {
        calculateDiff(datas)
    }

    fun addData(newDatas: List<T>) {
        val list = ArrayList(this.listData)
        list.addAll(newDatas)
        calculateDiff(list)
    }

    fun clearData() {
        calculateDiff(emptyList())
    }

    private fun calculateDiff(newDatas: List<T>) {
        diffCallback.setList(listData, newDatas)
        val result = DiffUtil.calculateDiff(diffCallback)
        with(listData) {
            clear()
            addAll(newDatas)
        }
        result.dispatchUpdatesTo(this)
    }


    class GeneralViewHolder<T : Any>(itemView: View, private val specificView: View? = null) :
        RecyclerView.ViewHolder(itemView) {
        fun bindView(
            item: T,
            onBind: (T, View) -> Unit,
            itemListener: (T, pos: Int, View) -> Unit,
            specificViewListener: (T, pos: Int, View) -> Unit
        ) {
            with(itemView) {
                onBind.invoke(item, this)
                setOnClickListener {
                    itemListener.invoke(item, adapterPosition, this)
                }
            }
            specificView?.setOnClickListener {
                specificViewListener.invoke(item, adapterPosition, itemView)
            }
        }
    }

}