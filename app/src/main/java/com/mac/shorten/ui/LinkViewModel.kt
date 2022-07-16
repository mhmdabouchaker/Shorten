package com.mac.shorten.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mac.shorten.domain.data.DataState
import com.mac.shorten.domain.model.Link
import com.mac.shorten.interactors.link.DeleteLink
import com.mac.shorten.interactors.linkList.GetLinkList
import com.mac.shorten.interactors.link.ShortenLink
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkViewModel
@Inject
constructor(
    private val getLinkList: GetLinkList,
    private val shortenLink: ShortenLink,
    private val deleteLink: DeleteLink
) : ViewModel(){

    private val _linksList = MutableStateFlow<DataState<List<Link>>?>(null)
    val linksList = _linksList

    init {
        getAllLinks()
    }

    private fun getAllLinks(){
        viewModelScope.launch {
            getLinkList.run().collectLatest {
                _linksList.value = it
            }
        }
    }

    fun shortenLink(url: String){
        viewModelScope.launch {
            shortenLink.run(url).collectLatest {
                _linksList.value = it
            }
        }
    }

    fun deleteLink(code: String){
        viewModelScope.launch {
            deleteLink.run(code).collectLatest {
                _linksList.value = it
            }
        }
    }
}