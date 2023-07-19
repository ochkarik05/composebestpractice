package com.chisw.domain.profile

import com.chisw.data.repository.ProfileRepository
import com.chisw.data.repository.ProfileRepositoryImpl

@Suppress("unused")
class ObserveProfileInteractor(
    private val repository: ProfileRepository,
) {
    constructor() : this(ProfileRepositoryImpl)

    operator fun invoke() = repository.observeProfile()
}
