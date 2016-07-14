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

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.yuzumone.ssaptsal.databinding.FragmentCsvSelectBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.*

class CSVSelectFragment: Fragment() {

    companion object {
        val READ_REQUEST_CODE = 77;
        fun newInstance(): CSVSelectFragment {
            val fragment = CSVSelectFragment()
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = DataBindingUtil.inflate<FragmentCsvSelectBinding>(inflater,
                R.layout.fragment_csv_select, container, false)
        binding.buttonStart.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "text/comma-separated-values"
            startActivityForResult(intent, READ_REQUEST_CODE)
        }
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val uri = data!!.data
            readCSVFromUri(uri)
        }
    }

    fun readCSVFromUri(uri: Uri) {
        val inputStream = activity.contentResolver.openInputStream(uri)
        val reader = BufferedReader(InputStreamReader(inputStream))
        val list = ArrayList<LastPassItem>()

        for (line in reader.readLines()) {
            val l = line.split(",")
            val item = LastPassItem(l[0], l[1], l[2], l[3], l[4])
            list.add(item)
        }
        reader.close();

        list.sortBy { s -> s.name }
        val fragment = LastPassListFragment.newInstance(list)
        fragmentManager.beginTransaction().replace(android.R.id.content, fragment).commit()
    }
}