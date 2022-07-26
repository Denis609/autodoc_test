package ru.autodoc.tz.ui.reps

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.autodoc.tz.R
import ru.autodoc.tz.domain.rep.Rep
import ru.autodoc.tz.databinding.RepsItemBinding
import ru.autodoc.tz.utils.ImageLoader
import java.text.SimpleDateFormat
import java.util.*

class RepsAdapter(var repsClickListener: ((Rep) -> Unit)) :
    PagingDataAdapter<Rep, RepsAdapter.RepsViewHolder>(RepsComparator) {

    override fun onBindViewHolder(
        holder: RepsViewHolder,
        position: Int
    ) {
        getItem(position = position)?.let { holder.bind(item = it) }
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

    object RepsComparator : DiffUtil.ItemCallback<Rep>() {
        override fun areItemsTheSame(oldItem: Rep, newItem: Rep) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Rep, newItem: Rep) =
            oldItem == newItem
    }

    inner class RepsViewHolder(
        private val binding: RepsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Rep) {
            item.apply {
                setUserName(userName = owner.login)
                setDate(updatedAt = updatedAt, createdAt = createdAt)
                setFullName(fullName = fullName)
                setStargazersCount(stargazersCount = stargazersCount)
                setDescription(description = description)
                setLanguage(language = language)
                setAvatar(url = owner.avatarUrl)

                binding.root.setOnClickListener {
                    repsClickListener.invoke(item)
                }
            }
        }

        private fun setUserName(userName: String) {
            binding.userName.text = userName
        }

        private fun setDate(updatedAt: Date?, createdAt: Date) {
            binding.dateUpdated.text = binding.root.context.getString(
                R.string.created_date_s,
                SimpleDateFormat("MM-dd-yyyy").format(updatedAt ?: createdAt)
            )
        }

        private fun setFullName(fullName: String?) {
            fullName?.let { binding.fullName.text = it }
        }

        private fun setStargazersCount(stargazersCount: Int?) {
            stargazersCount?.let { binding.stars.text = it.toString() }
        }

        private fun setDescription(description: String?) {
            description?.let { binding.description.text = it }
        }

        private fun setLanguage(language: String?) {
            language?.let { binding.language.text = it }
        }

        private fun setAvatar(url: String?) {
            ImageLoader.picasso(
                url = url,
                imageView = binding.avatar
            )
        }
    }
}