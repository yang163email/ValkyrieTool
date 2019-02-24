package com.yan.valkyrietool.v2.db

import android.content.Context
import android.database.Cursor
import android.util.Log
import com.yan.webdata.bean.Character
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert

/**
 *  @author : yan
 *  @date   : 2018/7/3 15:06
 *  @desc   : 个人角色db管理
 */
object PersonalCharactersDBManager {

    private val TAG = javaClass.simpleName

    fun insert(context: Context, character: Character): Boolean {
        val result = context.database.use {
            insert(CharacterTable.TABLE_NAME,
                    CharacterTable.LINK_URL to character.linkUrl,
                    CharacterTable.NAME to character.name,
                    CharacterTable.AVATAR to character.avatar,
                    CharacterTable.SITE to character.site,
                    CharacterTable.NEW_SORT to character.newSort,
                    CharacterTable.SITE_SORT to character.siteSort,
                    CharacterTable.RACE to character.race,
                    CharacterTable.INIT_STAR to character.initStar,
                    CharacterTable.GENDER to character.gender,
                    CharacterTable.ATTR_RESISTANCE to character.attrResistance,
                    CharacterTable.AS_ATTR to character.aSAttr,
                    CharacterTable.AWAKENING_AS_ATTR to character.awakeningASAttr)
        }
        return result != -1L
    }

    /**
     * 按照linkurl进行删除
     */
    fun delete(context: Context, linkUrl: String) {
        val result = context.database.use {
            delete(CharacterTable.TABLE_NAME,
                    "${CharacterTable.LINK_URL} = {url}", "url" to linkUrl)
        }
        Log.d(TAG, "delete(): $result")
    }

    fun query(context: Context, site: Int? = null): ArrayList<Character> {
        val datas = arrayListOf<Character>()

        context.database.use {
            val cursor: Cursor
            if (site == null) {
                cursor = rawQuery("select * from ${CharacterTable.TABLE_NAME}", null)
            } else {
                cursor = rawQuery("select * from ${CharacterTable.TABLE_NAME} where ${CharacterTable.SITE}=?", arrayOf("$site"))
            }

            val linkUrlIndex = cursor.getColumnIndex(CharacterTable.LINK_URL)
            val nameIndex = cursor.getColumnIndex(CharacterTable.NAME)
            val avatarIndex = cursor.getColumnIndex(CharacterTable.AVATAR)
            val siteIndex = cursor.getColumnIndex(CharacterTable.SITE)
            val newSortIndex = cursor.getColumnIndex(CharacterTable.NEW_SORT)
            val siteSortIndex = cursor.getColumnIndex(CharacterTable.SITE_SORT)
            val raceIndex = cursor.getColumnIndex(CharacterTable.RACE)
            val initStarIndex = cursor.getColumnIndex(CharacterTable.INIT_STAR)
            val genderIndex = cursor.getColumnIndex(CharacterTable.GENDER)
            val attrResistanceIndex = cursor.getColumnIndex(CharacterTable.ATTR_RESISTANCE)
            val aSAttrIndex = cursor.getColumnIndex(CharacterTable.AS_ATTR)
            val awakeningASAttrIndex = cursor.getColumnIndex(CharacterTable.AWAKENING_AS_ATTR)
            while (cursor.moveToNext()) {
                val link = cursor.getString(linkUrlIndex)
                val name = cursor.getString(nameIndex)
                val avatar = cursor.getString(avatarIndex)
                val site2 = cursor.getString(siteIndex)
                val newSort = cursor.getInt(newSortIndex)
                val siteSort = cursor.getInt(siteSortIndex)
                val race = cursor.getString(raceIndex)
                val initStar = cursor.getString(initStarIndex)
                val gender = cursor.getString(genderIndex)
                val attrResistance = cursor.getString(attrResistanceIndex)
                val aSAttr = cursor.getString(aSAttrIndex)
                val awakeningASAttr = cursor.getString(awakeningASAttrIndex)

                val character = Character(link, name, avatar, site2, newSort, siteSort, race, initStar, gender, attrResistance, aSAttr, awakeningASAttr)
                datas.add(character)
            }
            cursor.close()
        }
        return datas
    }
}