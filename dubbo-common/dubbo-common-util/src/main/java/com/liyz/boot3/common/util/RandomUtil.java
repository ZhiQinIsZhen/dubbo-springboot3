package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;

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
     * @param length
     * @return
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
     * @param length
     * @param sources
     * @return
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
}
