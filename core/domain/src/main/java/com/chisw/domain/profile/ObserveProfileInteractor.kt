package com.chisw.domain.profile

import com.chisw.data.repository.ProfileRepository
import javax.inject.Inject

class ObserveProfileInteractor @Inject constructor(
    private val repository: ProfileRepository,
) {

    operator fun invoke() = repository.observeProfile()
}
