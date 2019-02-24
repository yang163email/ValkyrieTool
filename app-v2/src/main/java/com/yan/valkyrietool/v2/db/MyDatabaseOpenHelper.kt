package com.yan.valkyrietool.v2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

/**
 *  @author : yan
 *  @date   : 2018/7/3 12:45
 *  @desc   : todo
 */
class MyDatabaseOpenHelper(context: Context)
    : ManagedSQLiteOpenHelper(context, "arrivalTool.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        //队伍表
        db.createTable(TeamTable.TABLE_NAME, true,
                TeamTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                TeamTable.LINK_URL to TEXT,
                TeamTable.TEAM to TEXT)

        //个人角色库表
        db.createTable(CharacterTable.TABLE_NAME, true,
                CharacterTable.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                CharacterTable.LINK_URL to TEXT,
                CharacterTable.NAME to TEXT,
                CharacterTable.AVATAR to TEXT,
                CharacterTable.SITE to TEXT,
                CharacterTable.NEW_SORT to INTEGER,
                CharacterTable.SITE_SORT to INTEGER,
                CharacterTable.RACE to TEXT,
                CharacterTable.INIT_STAR to TEXT,
                CharacterTable.GENDER to TEXT,
                CharacterTable.ATTR_RESISTANCE to TEXT,
                CharacterTable.AS_ATTR to TEXT,
                CharacterTable.AWAKENING_AS_ATTR to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(TeamTable.TABLE_NAME, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)