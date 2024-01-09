package com.kamalnayan.knstarwars.di

import com.kamalnayan.data.repository.CharacterRepository
import com.kamalnayan.domain.domain.repository.ICharactersRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindCharacterRepository(characterRepository: CharacterRepository): ICharactersRepository

}