package com.yan.valkyrietool.v2.db

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yan.valkyrietool.v2.bean.Team
import com.yan.valkyrietool.v2.bean.Teams
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.update

/**
 *  @author : yan
 *  @date   : 2018/7/3 15:06
 *  @desc   : 队伍db管理
 */
object TeamDBManager {

    private val TAG = javaClass.simpleName

    private val gson = Gson()

    fun insert(context: Context, teams: Teams): Boolean {
        val json = gson.toJson(teams.team)
        val result = context.database.use {
            insert(TeamTable.TABLE_NAME,
                    TeamTable.LINK_URL to teams.linkUrl,
                    TeamTable.TEAM to json)
        }
        return result != -1L
    }

    /**
     * 按照linkurl进行删除
     */
    fun delete(context: Context, linkUrl: String) {
        val result = context.database.use {
            delete(TeamTable.TABLE_NAME, "${TeamTable.LINK_URL} = {url}", "url" to linkUrl)
        }
        Log.d(TAG, "delete(): $result")
    }

    /**
     * 按照team进行删除
     */
    fun delete(context: Context, team: Team) {
        val teamJson = gson.toJson(team)
        val result = context.database.use {
            delete(TeamTable.TABLE_NAME, "${TeamTable.TEAM} = {team}", "team" to team)
        }
        Log.d(TAG, "delete(): $result")
    }

    fun update(context: Context, teams: Teams, id: Int): Boolean {
        val json = gson.toJson(teams.team)
        val result= context.database.use {
            update(TeamTable.TABLE_NAME, TeamTable.TEAM to json)
                    .whereArgs("${TeamTable.ID} = {id}", "id" to id)
                    .exec()
        }
        Log.d(TAG, "update(): $result")
        return result == 1
    }

    fun query(context: Context, linkUrl: String): ArrayList<String> {
        val datas = arrayListOf<String>()
        context.database.use {
            val cursor = rawQuery("select * from ${TeamTable.TABLE_NAME} where ${TeamTable.LINK_URL}=?", arrayOf(linkUrl))
            val index = cursor.getColumnIndex(TeamTable.TEAM)
            while (cursor.moveToNext()) {
                val team = cursor.getString(index)
                datas.add(team)
            }
            cursor.close()
        }
        return datas
    }


    fun queryId(context: Context, team: Team): Int {
        var id = -1
        val teamJson = gson.toJson(team)
        context.database.use {
            val cursor = rawQuery("select * from ${TeamTable.TABLE_NAME} where ${TeamTable.TEAM}=?", arrayOf(teamJson))
            cursor.moveToFirst()
            if (cursor.count != 0) {
                val index = cursor.getColumnIndex(TeamTable.ID)
                id = cursor.getInt(index)
            }
            cursor.close()
        }
        return id
    }
}