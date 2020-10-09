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

package com.yzx.lib_play_client.client.contract;

import androidx.lifecycle.MutableLiveData;

import com.yzx.lib_play_client.client.bean.dto.ChangeMusic;
import com.yzx.lib_play_client.client.bean.dto.PlayingMusic;


/**
 * Create by KunMinX at 19/11/1
 */
public interface ILiveDataNotifier {

    MutableLiveData<ChangeMusic> getChangeMusicLiveData();

    MutableLiveData<PlayingMusic> getPlayingMusicLiveData();

    MutableLiveData<Boolean> getPauseLiveData();

    MutableLiveData<Boolean> getLoadingLiveData();

    MutableLiveData<Enum> getPlayModeLiveData();

}
