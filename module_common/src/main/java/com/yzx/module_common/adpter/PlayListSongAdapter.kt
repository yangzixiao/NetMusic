package com.yzx.module_common.adpter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.yzx.module_common.R
import com.yzx.module_common.model.PlayListSinger
import com.yzx.module_common.model.Track


class PlayListSongAdapter(songs: MutableList<Track>? = null) :
    BaseQuickAdapter<Track, BaseViewHolder>(R.layout.item_song, data = songs) {

    private val stringBuilder = StringBuilder()
    override fun convert(holder: BaseViewHolder, item: Track) {


        holder.setText(R.id.tvIndex, (holder.layoutPosition + 1).toString())
        holder.setText(R.id.tvSongName, item.name)
        holder.setText(R.id.tvSongSinger, getSingers(item.ar, item.al.name))
        holder.itemView.setOnClickListener {

        }
    }

    private fun getSingers(ar: List<PlayListSinger>, albumName: String): String {
        if (ar.isNullOrEmpty()) {
            return ""
        }
        if (ar.size == 1) {
            return ar[0].name
        }

        stringBuilder.clear()
        ar.forEachIndexed { index, a ->
            stringBuilder.append(a.name)
            if (index != ar.lastIndex) {
                stringBuilder.append("/")
            } else {
                stringBuilder.append(" - $albumName")
            }

        }
        return stringBuilder.toString()
    }
}