package com.chisw.domain.profile

import com.chisw.data.repository.ProfileRepository
import me.tatarka.inject.annotations.Inject

@Inject
class ObserveProfileInteractor(
    private val repository: ProfileRepository,
) {

    operator fun invoke() = repository.observeProfile()
}
