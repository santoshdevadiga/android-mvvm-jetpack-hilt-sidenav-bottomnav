package com.santoshdevadiga.sampleapp.ui.listing.list

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView

import com.santoshdevadiga.sampleapp.ui.listing.list.placeholder.PlaceholderContent.PlaceholderItem
import com.santoshdevadiga.sampleapp.databinding.FragmentListItemBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */
class ListRecyclerViewAdapter(
    private val values: List<PlaceholderItem>,
    private val itemClickListener: (PlaceholderItem) -> Unit
) : RecyclerView.Adapter<ListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id
        holder.contentView.text = item.content
        holder.contentView.setOnClickListener { itemClickListener(item) }

    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.itemNumber
        val contentView: TextView = binding.content

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}