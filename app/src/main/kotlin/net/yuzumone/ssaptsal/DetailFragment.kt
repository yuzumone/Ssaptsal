/*
 * Copyright (C) 2016 yuzumone
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package net.yuzumone.ssaptsal

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import net.yuzumone.ssaptsal.databinding.FragmentDetailBinding
import org.parceler.Parcels

class DetailFragment: Fragment() {

    private var mItem: LastPassItem? = null

    companion object {
        val ARG_ITEM = "item"
        fun newInstance(item: LastPassItem): DetailFragment {
            val fragment = DetailFragment()
            val args = Bundle()
            args.putParcelable(ARG_ITEM, Parcels.wrap(item))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mItem = Parcels.unwrap(arguments.getParcelable(ARG_ITEM))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentDetailBinding>(inflater,
                R.layout.fragment_detail, container, false)
        binding.item = mItem
        binding.user.setOnClickListener {
            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newPlainText("user", mItem!!.user)
            Toast.makeText(activity, "Copy user!", Toast.LENGTH_SHORT).show()
        }
        binding.pass.setOnClickListener {
            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.primaryClip = ClipData.newPlainText("pass", mItem!!.pass)
            Toast.makeText(activity, "Copy pass!", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}
