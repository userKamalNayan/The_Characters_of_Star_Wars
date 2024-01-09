package com.kamalnayan.domain.domain.base.usecase

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
abstract class BaseUseCase<in Params, out Result> {
    abstract suspend operator fun invoke(params: Params?=null): Result
}