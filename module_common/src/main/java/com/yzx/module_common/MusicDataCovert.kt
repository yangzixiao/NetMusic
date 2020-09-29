package com.yzx.module_common

import android.text.TextUtils
import com.yzx.lib_base.player.model.Album
import com.yzx.lib_base.player.model.Music
import com.yzx.lib_base.player.model.Singer
import com.yzx.lib_play_client.PlayerManager
import com.yzx.module_common.model.Track

object MusicDataCovert {
    private val musicList = mutableListOf<Music>()

    fun coverPlayList(playListId: Long, playList: List<Track>, index: Int) {
        //数据异常
        if (playList.isNullOrEmpty() || index < 0) {
            return
        }

        val playManagerInstance = PlayerManager.getInstance()
        if (playManagerInstance.album != null && TextUtils.equals(
                playManagerInstance.album.albumId, playListId.toString()
            )
        ) {
            playManagerInstance.playAudio(index)
        } else {
            playManagerInstance.loadAlbum(covertPlayList2Album(playListId, playList),index)
            playManagerInstance.playAudio()
        }
    }

    private fun covertPlayList2Album(playListId: Long, playList: List<Track>): Album? {
        val album = Album()
        album.albumId = playListId.toString()

        musicList.clear()

        playList.forEachIndexed { index, track ->
            musicList.add(track2Music(track))
        }

        album.musics = musicList
        return album
    }

    private fun track2Music(track: Track): Music {
        val music = Music()
        track.apply {
            music.musicId = id.toString()
            music.coverImg = al.picUrl
            music.title = name
            music.url=url
            music.duration=dt
            if (ar.isNotEmpty()) {
                val singer = Singer()
                singer.name = ar[0].name
                music.artist = singer
            }
        }
        return music
    }
}