package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.database.dao.MachineDao
import com.luqman.imagemachine.database.entity.MachineEntity
import com.luqman.imagemachine.database.entity.PictureEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DataRepository(
    private val dao: MachineDao,
    private val dispatcher: CoroutineDispatcher
) : DataSource {

    override suspend fun getAll(
        sortBy: String
    ): Flow<List<Machine>> {

        return withContext(dispatcher) {
            val query = if (sortBy == SortMenuType.TYPE.toString()) {
                dao.getAllSortByType()
            } else {
                dao.getAllSortByName()
            }
            query.map { list ->
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

    override suspend fun store(machine: Machine) {
        return withContext(dispatcher) {
            val entity = MachineEntity(
                id = machine.id,
                name = machine.name,
                type = machine.type,
                code = machine.code,
                lastMaintain = machine.lastMaintain,
            )

            val pictures = machine.pictures.map { picture ->
                PictureEntity(
                    machineId = machine.id,
                    path = picture.path
                )
            }

            dao.deletePictureByMachineId(machine.id)
            dao.insert(entity, pictures)
        }
    }
}