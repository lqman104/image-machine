package com.luqman.imagemachine.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.imagemachine.database.entity.MachineEntity
import com.luqman.imagemachine.database.entity.MachineWithPictures
import com.luqman.imagemachine.database.entity.PictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Query("SELECT * FROM machine_entity ORDER BY name ASC")
    fun getAllSortByName(): Flow<List<MachineEntity>>

    @Query("SELECT * FROM machine_entity ORDER BY type ASC")
    fun getAllSortByType(): Flow<List<MachineEntity>>

    @Query("SELECT * FROM machine_entity WHERE id = :id")
    suspend fun get(id: String): MachineWithPictures

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(machine: MachineEntity, pictures: List<PictureEntity>)

    @Query("DELETE FROM machine_entity WHERE id=:id")
    suspend fun delete(id: String)

    @Query("DELETE FROM picture_entity WHERE machine_id=:machineId")
    suspend fun deletePictureByMachineId(machineId: String)
}