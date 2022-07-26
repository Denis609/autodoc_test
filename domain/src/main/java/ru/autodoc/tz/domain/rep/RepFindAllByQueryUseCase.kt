package ru.autodoc.tz.domain.rep

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepFindAllByQueryUseCase @Inject constructor(private val repRepository: RepRepository) {

    suspend fun execute(query: String): Flow<PagingData<Rep>> = repRepository.findAllByQuery(query)
}