package com.yzx.module_main.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yzx.lib_base.base.BaseFragment
import com.yzx.lib_base.ext.toast
import com.yzx.lib_base.utils.glide.GlideUtils
import com.yzx.module_main.databinding.FragmentTestBinding


/**
 * @author yzx
 * @date 2020/5/15
 * Description
 */
class DiscoverFragment : BaseFragment() {
    private lateinit var binding: FragmentTestBinding
    val url = "http://p2.music.126.net/QJYb5PP_YtSU8yeopkMnWg==/109951163077573267.jpg"
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }


    override fun lazyLoad() {
        super.lazyLoad()
        Log.i("Mine", "DiscoverFragmentlazyLoad: " + arguments?.getString("111", "222"))

        binding.apply {
            GlideUtils.loadDrawable(url, iv1, 10, 5)
            GlideUtils.loadDrawable(url, iv2, 50, 5)
            GlideUtils.loadDrawable(url, iv3, 100, 5)
            GlideUtils.loadDrawable(url, iv4, 150, 5)
            GlideUtils.loadDrawable(url, iv5, 200, 5)
            GlideUtils.loadDrawable(url, iv6, 250, 5)
            GlideUtils.loadDrawable(url, iv11, 100, 5)
            GlideUtils.loadDrawable(url, iv12, 100, 10)
            GlideUtils.loadDrawable(url, iv13, 100, 15)
            GlideUtils.loadDrawable(url, iv14, 100, 20)
            GlideUtils.loadDrawable(url, iv15, 100, 25)
            GlideUtils.loadDrawable(url, iv16, 100, 30)
        }
        saveUser2(User("1"))
    }


    fun saveUser2(user: User) {
        fun validate(value: String, fildName: String) {
            if (value.isEmpty()) {
                toast(fildName + "为空")
            }
        }

        user.apply {

            validate(name, "Name")
            validate(address, "Address")
            validate(email, "Email")
        }
        // ...
    }

    class User(var name: String) {

        var address = ""
        var email = ""
    }
}