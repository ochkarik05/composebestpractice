package com.chisw.data.repository

import com.chisw.data.model.Profile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import me.tatarka.inject.annotations.Inject
import java.io.IOException

interface ProfileRepository {
    fun observeProfile(): Flow<Profile>
    suspend fun saveProfile(profile: Profile)
}

private const val DELAY = 5000L

@Inject
class ProfileRepositoryImpl : ProfileRepository {
    private val profileHolder: MutableStateFlow<Profile> = MutableStateFlow(Profile("", ""))
    override fun observeProfile(): Flow<Profile> {
        return profileHolder
    }

    override suspend fun saveProfile(profile: Profile) {
        delay(DELAY)
        // Imitate random error
        if ((profile.firstName.length + profile.lastName.length) % 2 == 0) {
            throw IOException("Error: Cannot save profile. The sum of chars in both fields must be odd")
        }
        profileHolder.value = profile
    }
}
