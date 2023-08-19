package com.chisw.domain.profile

import com.chisw.data.model.Profile
import com.chisw.data.repository.ProfileRepository
import com.chisw.domain.utils.AppCoroutineDispatchers
import com.chisw.domain.utils.InvokeError
import com.chisw.domain.utils.InvokeStarted
import com.chisw.domain.utils.InvokeStatus
import com.chisw.domain.utils.InvokeSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import me.tatarka.inject.annotations.Inject
import java.io.IOException

@Inject
class SaveProfileInteractor(
    private val repository: ProfileRepository,
    private val dispatchers: AppCoroutineDispatchers,
) {

    class Params(
        val profile: Profile,
    )

    operator fun invoke(params: Params): Flow<InvokeStatus> {
        return flow {
            emit(InvokeStarted)
            try {
                withContext(dispatchers.io) {
                    repository.saveProfile(params.profile)
                }
                emit(InvokeSuccess)
            } catch (e: IOException) {
                emit(InvokeError(e))
            }
        }
    }
}
