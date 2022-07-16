package com.mac.shorten.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mac.shorten.R
import com.mac.shorten.databinding.ListItemLinkBinding
import com.mac.shorten.domain.model.Link

class LinkAdapter(
    private val onDeleteRowClickListener: (code: String) -> Unit
) : ListAdapter<Link, LinkAdapter.LinkViewHolder>(LinkComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinkViewHolder {
        return LinkViewHolder(
            ListItemLinkBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onDeleteRowClickListener
        )
    }

    override fun onBindViewHolder(holder: LinkViewHolder, position: Int) {
        holder.bindViews(getItem(position))
    }

    class LinkViewHolder(
        private val binding: ListItemLinkBinding,
        private val onDeleteRowClickListener: (code: String) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindViews(link: Link) = with(binding) {
            originalLinkTv.text = link.original_link
            shortLinkTv.text = link.full_short_link

            deleteBtn.setOnClickListener {
                onDeleteRowClickListener.invoke(link.code)
            }
            copyBtn.setOnClickListener {
                val clipboard: ClipboardManager? =
                    binding.root.context.getSystemService(AppCompatActivity.CLIPBOARD_SERVICE) as ClipboardManager?
                val clip = ClipData.newPlainText(
                    link.full_short_link,
                    link.full_short_link
                )
                clipboard?.setPrimaryClip(clip)
                copyBtn.text = binding.root.context.getString(R.string.btn_copied)
                copyBtn.setBackgroundColor(Color.parseColor("#3B3054"))
            }
        }
    }

    object LinkComparator : DiffUtil.ItemCallback<Link>() {
        override fun areItemsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: Link, newItem: Link): Boolean {
            return oldItem == newItem
        }

    }

}