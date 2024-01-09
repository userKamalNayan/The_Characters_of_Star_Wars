package com.kamalnayan.domain.domain.base.response

import kotlinx.parcelize.Parcelize

/** @Author Kamal Nayan
Created on: 09/01/24
 **/
@Parcelize
class BasePaginatedResponse : BaseResponse() {
    var next: String? = null
    var previous: String? = null
}