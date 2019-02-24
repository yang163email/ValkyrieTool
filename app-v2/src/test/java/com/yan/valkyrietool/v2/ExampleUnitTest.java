package com.yan.valkyrietool.v2;

import com.google.gson.Gson;
import com.yan.valkyrietool.v2.bean.Team;
import com.yan.valkyrietool.v2.utils.Pinyin4j;

import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test() {
        Team team = new Gson().fromJson("", Team.class);
        System.out.println(team);
    }

    @Test
    public void test2() {
        try {
            Pinyin4j pinyin4j = new Pinyin4j();
            String first1 = pinyin4j.toPinYinUppercase("颐和园");
            String first2 = pinyin4j.toPinYinUppercase("颐和园", "**");
            String first3 = pinyin4j.toPinYinLowercase("颐和园");
            String first4 = pinyin4j.toPinYinLowercase("颐和园","**");
            String first5 = pinyin4j.toPinYinUppercaseInitials("颐和园");
            String first6 = pinyin4j.toPinYinLowercaseInitials("颐和园");
            System.out.println(first1);	//输出结果：YHY
            System.out.println(first2);	//输出结果：Y**H**Y
            System.out.println(first3);	//输出结果：yhy
            System.out.println(first4);	//输出结果：y**h**y
            System.out.println(first5);	//输出结果：Y
            System.out.println(first6);	//输出结果：y
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
    }
}