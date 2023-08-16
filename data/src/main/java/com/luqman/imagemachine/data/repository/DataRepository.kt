package com.luqman.imagemachine.data.repository

import com.luqman.imagemachine.data.repository.model.Machine
import com.luqman.imagemachine.data.repository.model.Picture
import com.luqman.imagemachine.data.repository.model.SortMenuType
import com.luqman.imagemachine.database.dao.MachineDao
import com.luqman.imagemachine.database.entity.MachineEntity
import com.luqman.imagemachine.database.entity.MachineWithPictures
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
                list.map { entity -> entity.toMachine() }
            }
        }
    }

    override suspend fun get(id: String): Machine {
        return withContext(dispatcher) {
            dao.get(id).toMachine()
        }
    }

    override suspend fun store(machine: Machine) {
        return withContext(dispatcher) {
            val entity = machine.toMachineEntity()

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

    private fun MachineEntity.toMachine(): Machine {
        return Machine(
            id = id,
            name = name,
            type = type,
            code = code,
            lastMaintain = lastMaintain,
        )
    }

    private fun MachineWithPictures.toMachine(): Machine {
        return Machine(
            id = machineEntity.id,
            name = machineEntity.name,
            type = machineEntity.type,
            code = machineEntity.code,
            lastMaintain = machineEntity.lastMaintain,
            pictures = pictures.map {
                Picture(it.id, path = it.path)
            }
        )
    }

    private fun Machine.toMachineEntity(): MachineEntity {
        return MachineEntity(
            id = id,
            name = name,
            type = type,
            code = code,
            lastMaintain = lastMaintain,
        )
    }
}