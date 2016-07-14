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

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.yuzumone.ssaptsal.databinding.ListItemBinding

class LastPassAdapter(context: Context?): ArrayAdapter<LastPassItem>(context, 0) {

    lateinit private var mInflater: LayoutInflater
    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        val binding: ListItemBinding

        if (view == null) {
            binding = DataBindingUtil.inflate(mInflater, R.layout.list_item, parent, false);
            view = binding.root
            view.tag = binding
        } else {
            binding = view.tag as ListItemBinding
        }

        binding.item = getItem(position)
        return view
    }
}