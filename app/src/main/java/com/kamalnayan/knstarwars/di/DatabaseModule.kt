package com.kamalnayan.knstarwars.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.kamalnayan.data.api.ApiService
import com.kamalnayan.data.datasource.CharacterRemoteMediator
import com.kamalnayan.data.db.StarWarsDB
import com.kamalnayan.data.db.dao.CharactersDao
import com.kamalnayan.domain.domain.models.character.CharacterItem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): StarWarsDB {
        return Room.databaseBuilder(context, StarWarsDB::class.java, "star_wars_app_database")
            .build()
    }

    @Singleton
    @Provides
    fun providedPostDao(appDatabase: StarWarsDB): CharactersDao {
        return appDatabase.charactersDao()
    }

   /* NOT USING PAGER
   @Singleton
    @Provides
    fun provideCharacterPager(
        apiService: ApiService,
        charactersDao: CharactersDao
    ): Pager<Int, CharacterItem> {
        return Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 10),
            remoteMediator = CharacterRemoteMediator(apiService, charactersDao),
            pagingSourceFactory = { charactersDao.getCharacters() },
            initialKey = null
        )
    }*/
}