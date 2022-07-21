package ru.autodoc.tz.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.autodoc.tz.data.model.Reps
import ru.autodoc.tz.databinding.RepsItemBinding
import javax.inject.Inject

class RepsAdapter @Inject constructor() :
    PagingDataAdapter<Reps, RepsAdapter.RepsViewHolder>(RepsComparator) {

    override fun onBindViewHolder(holder: RepsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepsViewHolder =
        RepsViewHolder(
            RepsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    inner class RepsViewHolder(
        private val binding: RepsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Reps) {
            binding.apply {
                id.text = item.id.toString()
                name.text = item.name
                fullname.text = item.full_name
            }
        }
    }

    object RepsComparator : DiffUtil.ItemCallback<Reps>() {
        override fun areItemsTheSame(oldItem: Reps, newItem: Reps) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Reps, newItem: Reps) =
            oldItem == newItem
    }


}