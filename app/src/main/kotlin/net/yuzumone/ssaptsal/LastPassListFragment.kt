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
import net.yuzumone.ssaptsal.databinding.FragmentLastpassListBinding
import org.parceler.Parcels
import java.util.*

class LastPassListFragment: Fragment() {

    private var mList: ArrayList<LastPassItem> = arrayListOf()

    companion object {
        val ARG_LIST = "list"
        fun newInstance(list: ArrayList<LastPassItem>): LastPassListFragment {
            val fragment = LastPassListFragment()
            val args = Bundle()
            args.putParcelable(ARG_LIST, Parcels.wrap(list))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mList = Parcels.unwrap(arguments.getParcelable(ARG_LIST))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentLastpassListBinding>(inflater,
                R.layout.fragment_lastpass_list, container, false)
        val adapter = LastPassAdapter(activity)
        adapter.addAll(mList)
        adapter.notifyDataSetChanged()
        binding.list.adapter = adapter
        binding.list.setOnItemClickListener { adapterView, view, i, l ->
            val item = adapter.getItem(i)
            val clipboard = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val pass = ClipData.newPlainText("copy", item.pass)
            clipboard.primaryClip = pass
            Toast.makeText(activity, "Copy pass!", Toast.LENGTH_SHORT).show()
        }
        binding.list.setOnItemLongClickListener { adapterView, view, i, l ->
            val item = adapter.getItem(i)
            val fragment = DetailFragment.newInstance(item)
            fragmentManager.beginTransaction().replace(android.R.id.content, fragment)
                    .addToBackStack(null).commit()
            true
        }

        return binding.root
    }
}
