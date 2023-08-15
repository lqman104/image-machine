package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.database.dao.MachineDao
import com.luqman.imagemachine.database.entity.MachineEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataRepository(
    private val dao: MachineDao,
    private val dispatcher: CoroutineDispatcher
) : DataSource {

    override suspend fun getAll(
        sortBy: SortMenuType
    ): Flow<List<Machine>> {
        val sort = if (sortBy == SortMenuType.TYPE) {
            MachineEntity.TYPE
        } else {
            MachineEntity.NAME
        }
        return withContext(dispatcher) {
            dao.getAll(sort).map { list ->
                list.map { entity ->
                    Machine(
                        id = entity.id,
                        name = entity.name,
                        type = entity.type,
                        code = entity.code,
                        lastMaintain = entity.lastMaintain,
                    )
                }
            }
        }
    }
}