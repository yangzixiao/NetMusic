/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yzx.lib_play_client.client;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.yzx.lib_base.player.model.Album;
import com.yzx.lib_base.player.model.Music;
import com.yzx.lib_core.mmkv.MmkvUtils;
import com.yzx.lib_core.utils.JsonUtils;
import com.yzx.lib_play_client.client.bean.base.BaseAlbumItem;
import com.yzx.lib_play_client.client.bean.base.BaseMusicItem;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by KunMinX at 18/9/24
 */
public class PlayingInfoManager {

    private static final String SP_NAME = "KunMinX_Music";
    private static final String REPEAT_MODE = "REPEAT_MODE";
    private static final String LAST_CHAPTER_INDEX = "LAST_CHAPTER_INDEX";
    private static final String LAST_BOOK_DETAIL = "LAST_BOOK_DETAIL";

/*    //单曲循环
    public static final int ONE_LOOP = 1 << 1;
    //列表循环
    public static final int LIST_LOOP = 1 << 2;
    //随机播放
    public static final int RANDOM = 1 << 3;*/

    //播放使用的当前章节 index
    private int mPlayIndex = 0;
    //列表展示的当前章节 index
    private int mAlbumIndex = 0;

    //循环模式
    private Enum mRepeatMode = RepeatMode.LIST_LOOP;

    public enum RepeatMode {
        ONE_LOOP, LIST_LOOP, RANDOM
    }

    //原始列表
    private List<Music> mOriginPlayingList = new ArrayList<>();

    //随机播放列表
    private List<Music> mShufflePlayingList = new ArrayList<>();

    //专辑详情
    private Album mMusicAlbum;


    public void init(Context context) {

        String musicAlbum = MmkvUtils.INSTANCE.get(LAST_BOOK_DETAIL, "");
        mPlayIndex = MmkvUtils.INSTANCE.get(LAST_CHAPTER_INDEX, 0);
        int repeatMode = MmkvUtils.INSTANCE.get(REPEAT_MODE, 1);
        if (!TextUtils.isEmpty(musicAlbum)) {
            setMusicAlbum((Album) JsonUtils.stringToObject(musicAlbum, Album.class));
            mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
        }


        switch (repeatMode) {
            case 0:
                mRepeatMode = RepeatMode.ONE_LOOP;
                break;
            case 2:
                mRepeatMode = RepeatMode.RANDOM;
                break;
            default:
                mRepeatMode = RepeatMode.LIST_LOOP;
                break;
        }
    }

    public boolean isInited() {
        return mMusicAlbum != null;
    }

    private void fitShuffle() {
        mShufflePlayingList.clear();
        mShufflePlayingList.addAll(mOriginPlayingList);
        Collections.shuffle(mShufflePlayingList);
    }

    public Enum changeMode() {
        if (mRepeatMode == RepeatMode.LIST_LOOP) {
            mRepeatMode = RepeatMode.ONE_LOOP;
        } else if (mRepeatMode == RepeatMode.ONE_LOOP) {
            mRepeatMode = RepeatMode.RANDOM;
        } else {
            mRepeatMode = RepeatMode.LIST_LOOP;
        }
        return mRepeatMode;
    }

    public Album getMusicAlbum() {
        return mMusicAlbum;
    }

    public void setMusicAlbum(Album musicAlbum) {
        this.mMusicAlbum = musicAlbum;
        mOriginPlayingList.clear();
        mOriginPlayingList.addAll(mMusicAlbum.getMusics());
        fitShuffle();
    }

    public List<Music> getPlayingList() {
        if (mRepeatMode == RepeatMode.RANDOM) {
            return mShufflePlayingList;
        } else {
            return mOriginPlayingList;
        }
    }

    public List<Music> getOriginPlayingList() {
        return mOriginPlayingList;
    }

    public Music getCurrentPlayingMusic() {
        return getPlayingList().get(mPlayIndex);
    }

    public Enum getRepeatMode() {
        return mRepeatMode;
    }

    public void countPreviousIndex() {
        if (mPlayIndex == 0) {
            mPlayIndex = (getPlayingList().size() - 1);
        } else {
            --mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public void countNextIndex() {
        if (mPlayIndex == (getPlayingList().size() - 1)) {
            mPlayIndex = 0;
        } else {
            ++mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public int getAlbumIndex() {
        return mAlbumIndex;
    }

    public void setAlbumIndex(int albumIndex) {
        mAlbumIndex = albumIndex;
        mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(mAlbumIndex));
    }

    public void clear(Context context) {
        saveRecords(context);
    }

    public void saveRecords(Context context) {

        MmkvUtils.INSTANCE.put(LAST_BOOK_DETAIL, JsonUtils.objectToString(mMusicAlbum));
        MmkvUtils.INSTANCE.put(LAST_CHAPTER_INDEX, mPlayIndex);

        int repeatMode = 1;
        if (RepeatMode.ONE_LOOP == mRepeatMode) {
            repeatMode = 0;
        } else if (RepeatMode.RANDOM.equals(mRepeatMode)) {
            repeatMode = 3;
        }
        MmkvUtils.INSTANCE.put(REPEAT_MODE, repeatMode);
    }
}
