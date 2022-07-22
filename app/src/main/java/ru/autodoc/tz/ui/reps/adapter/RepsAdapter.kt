package ru.autodoc.tz.ui.reps.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.autodoc.tz.R
import ru.autodoc.tz.data.model.Rep
import ru.autodoc.tz.databinding.RepsItemBinding
import java.text.SimpleDateFormat
import javax.inject.Inject

class RepsAdapter @Inject constructor() :
    PagingDataAdapter<Rep, RepsAdapter.RepsViewHolder>(RepsComparator) {

    var repsClickListener: RepsClickListener? = null

    override fun onBindViewHolder(
        holder: RepsViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepsViewHolder =
        RepsViewHolder(
            RepsItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    inner class RepsViewHolder(
        private val binding: RepsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rep) {
            binding.apply {
                userName.text = item.owner.login
                item.updated_at?.let {
                    dateUpdated.text = root.context.getString(
                        R.string.updated_date_s,
                        SimpleDateFormat("MM-dd-yyyy").format(it)
                    )
                } ?: run {
                    dateUpdated.text = root.context.getString(
                        R.string.created_date_s,
                        SimpleDateFormat("MM-dd-yyyy").format(item.created_at)
                    )
                }
                item.full_name?.let { fullName.text = it }
                item.stargazers_count?.let { stars.text = it.toString() }
                item.description?.let { description.text = it }
                item.language?.let { language.text = it }
                Picasso
                    .get()
                    .load(item.owner.avatar_url)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(avatar)

                root.setOnClickListener {
                    repsClickListener?.onRepsClicked(
                        item
                    )
                }
            }
        }
    }

    object RepsComparator : DiffUtil.ItemCallback<Rep>() {
        override fun areItemsTheSame(oldItem: Rep, newItem: Rep) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Rep, newItem: Rep) =
            oldItem == newItem
    }

    interface RepsClickListener {
        fun onRepsClicked(rep: Rep)
    }
}