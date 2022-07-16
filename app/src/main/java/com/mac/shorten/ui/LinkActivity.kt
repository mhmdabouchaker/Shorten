package com.mac.shorten.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mac.shorten.R
import com.mac.shorten.databinding.ActivityLinkBinding
import com.mac.shorten.domain.data.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LinkActivity : AppCompatActivity() {

    private val viewModel: LinkViewModel by viewModels()
    private lateinit var binding: ActivityLinkBinding
    private lateinit var adapter: LinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLinkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUp()
        observeChanges()
    }

    private fun setUp() = with(binding) {
        adapter = LinkAdapter(onDeleteRowClickListener = { code ->
            viewModel.deleteLink(code)
        })
        rvLinks.layoutManager = LinearLayoutManager(this@LinkActivity)
        rvLinks.setHasFixedSize(false)
        rvLinks.adapter = adapter

        etShortenLink.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (!s.isNullOrEmpty()){
                    etShortenLink.hint = getString(R.string.shorten_link_hint)
                    etShortenLink.setHintTextColor(ContextCompat.getColor(this@LinkActivity,
                        R.color.hint_color_gray))
                    etShortenLink.background = ContextCompat.getDrawable(this@LinkActivity,
                        R.drawable.et_bg)
                }
            }

        })
        shortenBtn.setOnClickListener {
            if (binding.etShortenLink.text.toString().isEmpty()){
                etShortenLink.hint = getString(R.string.error_add_link)
                etShortenLink.setHintTextColor(ContextCompat.getColor(this@LinkActivity,
                    R.color.error_red))
                etShortenLink.background = ContextCompat.getDrawable(this@LinkActivity,
                    R.drawable.et_error_bg)
            }else{
                viewModel.shortenLink(etShortenLink.text.toString())
            }
        }
    }

    private fun observeChanges() = with(binding) {
        lifecycleScope.launch {
            viewModel.linksList.collectLatest { dataState ->
                when (dataState?.status) {
                    DataState.Status.LOADING -> {
                        etShortenLink.isEnabled = false
                        shortenBtn.isEnabled = false
                        shortenBtn.text = ""
                        pbLoading.visibility = View.VISIBLE
                    }

                    DataState.Status.SUCCESS -> {
                        etShortenLink.text.clear()
                        etShortenLink.isEnabled = true
                        shortenBtn.isEnabled = true
                        shortenBtn.text = getString(R.string.btn_shorten_it)
                        pbLoading.visibility = View.GONE
                        noLinksParentView.visibility = View.GONE
                        rvParentView.visibility = View.VISIBLE
                        dataState.data?.let {
                            adapter.submitList(it)
                        }
                    }

                    DataState.Status.ERROR -> {
                        dataState.error.let {
                            Toast.makeText(
                                this@LinkActivity,
                                it, Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    DataState.Status.EMPTY -> {
                        rvParentView.visibility = View.GONE
                        noLinksParentView.visibility = View.VISIBLE
                    }
                    else -> {

                    }
                }
            }
        }
    }

}