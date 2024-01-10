package com.kamalnayan.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kamalnayan.data.api.ApiService
import com.kamalnayan.data.db.dao.CharactersDao
import com.kamalnayan.domain.domain.models.character.CharacterItem
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

/** @Author Kamal Nayan
Created on: 10/01/24
 **/
@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator @Inject constructor(
    private val apiService: ApiService,
    private val charactersDao: CharactersDao
) : RemoteMediator<Int, CharacterItem>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterItem>
    ): MediatorResult {
        return try {
            val pageNumber = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    // if lastItem.id is null this means
                    // database is empty hence make api call to 1st page
                    if (lastItem?.id == null) {
                        1
                    } else {
                        // calculating index of next page
                        // eg. 29 is last Index, page Size is 10
                        // so next page to load is 30/10 =3 , then 3+1 ,
                        // hence 3 page needs to be loaded
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            if(loadType ==LoadType.REFRESH)
                return MediatorResult.Success(endOfPaginationReached = false)

            val characters = apiService.getCharacters(pageNumber)
            var isEndOfData = false

            characters.suspendOnSuccess {
                withContext(Dispatchers.IO)
                {
                    isEndOfData = this@suspendOnSuccess.data.next.isNullOrBlank()
                    charactersDao.insertCharacters(this@suspendOnSuccess.data.characterItems)
                }
            }

            return MediatorResult.Success(endOfPaginationReached = isEndOfData)

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }

    }
}