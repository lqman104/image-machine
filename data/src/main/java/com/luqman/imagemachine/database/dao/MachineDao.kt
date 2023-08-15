package com.luqman.imagemachine.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.luqman.imagemachine.database.entity.MachineEntity
import com.luqman.imagemachine.database.entity.PictureEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MachineDao {
    @Query("SELECT * FROM machine_entity ORDER BY :sort ASC")
    fun getAll(sort: String): Flow<List<MachineEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(machine: MachineEntity, pictures: List<PictureEntity>)

    @Query("DELETE FROM machine_entity WHERE id=:id")
    suspend fun delete(id: Int)
}