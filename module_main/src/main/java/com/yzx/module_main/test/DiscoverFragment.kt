package com.yzx.module_main.test

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.yzx.lib_base.base.BaseFragment
import com.yzx.lib_base.base.BaseViewModel
import com.yzx.lib_base.widget.viewpager.BaseLazyLoadFragmentPagerAdapter
import com.yzx.module_main.databinding.FragmentTestBinding


/**
 * @author yzx
 * @date 2020/5/15
 * Description
 */
class DiscoverFragment : BaseFragment<BaseViewModel>() {
    private lateinit var binding: FragmentTestBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        binding = FragmentTestBinding.inflate(inflater)
        return binding.root
    }


    override fun lazyLoad() {
        super.lazyLoad()
        Log.i("Mine", "DiscoverFragmentlazyLoad: " + arguments?.getString("111", "222"))

//        saveUser2(User("1"))
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