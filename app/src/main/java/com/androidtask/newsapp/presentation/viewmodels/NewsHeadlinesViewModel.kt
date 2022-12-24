package com.androidtask.newsapp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidtask.newsapp.domain.models.NewsHeadlineDTO
import com.androidtask.newsapp.domain.models.Resource
import com.androidtask.newsapp.domain.repositories.NewsHeadlinesRepository
import com.androidtask.newsapp.utils.Constants.CLOSE_POPUP
import com.androidtask.newsapp.utils.handleApiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsHeadlinesViewModel @Inject constructor(
    private val newsHeadlinesRepository: NewsHeadlinesRepository //REPOSITORY INSTANCE USED TO HANDLE API CALL
) : ViewModel() {

    var loaderAlertDialogueMutableState: MutableState<Boolean> = mutableStateOf(false) //SHOW OR HIDE LOADER DIALOGE WITH MUTABLE STATE
    var errorMessageDialoguMutableState: MutableState<String> = mutableStateOf(CLOSE_POPUP) //SHOW ERROR MESSAGE DIALOGE WITH STRING AND HIDE WITH CLOSE POPUP STRING
    var newsHeadlinesListMutableState:MutableState<ArrayList<NewsHeadlineDTO>> = mutableStateOf(arrayListOf()) //SHOW LIST OF HEADLINES FROM API RESPONSE

    //CALL API TO GET NEWS HEADLINES FOR SPECIFIC SOURCE
    fun callTopHeadlinesApiToGetNewsForSource(newsSource: String) = viewModelScope.launch {
        var apiResponse = newsHeadlinesRepository.callTopHeadlinesApiToGetNewsForSource(newsSource)
        when(apiResponse)
        {
            is Resource.Success-> //ASSIGN RESPONSE TO NEWS HEADLINE MUTABLE STATE ON SUCCESS
            {
                showHideLoaderAlertDialoge(false)
                newsHeadlinesListMutableState.value = apiResponse.value.articles
            }
            is Resource.Failure-> //ASSIGN ERROR TO MUTABLE STATE ON FAILURE
            {
                errorMessageDialoguMutableState.value= handleApiError(apiResponse)
            }
            is Resource.Loading->
            {
            }
        }

    }

    //SET TRUE OR FALSE ON MUTABLE STATE TO SHOW OR HIDE LOADER DIALOGE
    fun showHideLoaderAlertDialoge(isShow:Boolean)
    {
        loaderAlertDialogueMutableState.value = isShow
    }
}