package com.liyz.boot3.common.util;

import cn.hutool.core.collection.CollectionUtil;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Desc:Random tool class
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/3/8 16:30
 */
@UtilityClass
public class RandomUtil {

    public static final String NUMBER_CODES = "0123456789";
    public static final String UP_CASE_LETTERS = "ABCDEFGHIGKLMNOPQRSTUVMXYZ";
    public static final String LW_CASE_LETTERS = "abcdefghigklmnopqrstuvmxyz";
    public static final String[] EMOJI = {"😀", "😃", "😄", "😁", "😆", "😅", "😂", "🤣", "☺", "😊", "😇", "🙂", "🙃",
            "😉", "😌", "😍", "🥰", "😘", "😗", "😙", "😚", "😋", "😛", "😝", "😜", "🤪", "🤨", "🧐", "🤓", "😎", "🤩",
            "🥳 😏 😒 😞 😔 😟 😕 🙁 ☹ 😣 😖 😫 😩 🥺 😢 😭 😤 😠 😡 🤬 🤯 😳 🥵 🥶 😱 😨 😰 😥 😓 🤗 🤔 🤭",
            "🤫 🤥 😶 😐 😑 😬 🙄 😯 😦 😧 😮 😲 😴 🤤 😪 😵 🤐 🥴 🤢 🤮 🤧 😷 🤒 🤕 🤑 🤠 😈 👿 👹 👺 🤡 💩",
            "👻 💀 ☠ 👽 👾 🤖 🎃 😺 😸 😹 😻 😼 😽 🙀 😿 😾"};

    /**
     * 获取随机数字
     *
     * @param length 长度
     * @return 数字字符串
     */
    public static String randomInteger(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(ThreadLocalRandom.current().nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 根据字符源生产对应长度字符
     *
     * @param length 长度
     * @param sources 来源
     * @return 字符串
     */
    public static String randomChars(int length, String... sources) {
        if (sources == null || sources.length == 0) {
            sources = new String[]{NUMBER_CODES, UP_CASE_LETTERS, LW_CASE_LETTERS};
        }
        StringBuilder sb = new StringBuilder();
        for (String item : sources) {
            sb.append(item);
        }
        String sourceStr = sb.toString();
        StringBuilder result = new StringBuilder(length);
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            result.append(sourceStr.charAt(random.nextInt(sourceStr.length() - 1)));
        }
        return result.toString();
    }

    /**
     * 随机emoji表情
     *
     * @param length 长度
     * @param sources 来源
     * @return emoji表情
     */
    public static String randomEmoji(int length, String... sources) {
        if (sources == null || sources.length == 0) {
            sources = EMOJI;
        }
        int sourceLength = sources.length;
        StringBuilder result = new StringBuilder();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < length; i++) {
            result.append(sources[random.nextInt(sourceLength - 1)]);
        }
        return result.toString();
    }

    /**
     * 对目标list随机取n个
     *
     * @param list 原数据
     * @param count 随机数量
     * @return 结果数据
     * @param <T>
     */
    public static <T> List<T> randomList(List<T> list, int count) {
        if (CollectionUtil.isEmpty(list) || count <= 0) {
            return new ArrayList<>();
        }
        List<T> copyList = new ArrayList<>(list);
        if (copyList.size() <= count) {
            return copyList;
        }
        List<T> result = new ArrayList<>(count);
        int listSize = copyList.size();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < listSize; i++) {
            T item = copyList.remove(random.nextInt(listSize - i));
            result.add(item);
        }
        return result;
    }

    /**
     * 对目标list随机取一个值
     *
     * @param list 原数据
     * @return 结果数据
     * @param <T>
     */
    public static <T> T randomElement(List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        return list.get(ThreadLocalRandom.current().nextInt(list.size()));
    }
}
