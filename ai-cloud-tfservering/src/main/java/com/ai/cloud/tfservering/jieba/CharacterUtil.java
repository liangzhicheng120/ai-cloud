package com.ai.cloud.tfservering.jieba;

import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CharacterUtil {

    public static Pattern reSkip = Pattern.compile("(\\d+\\.\\d+|[a-zA-Z0-9]+)");

    private static final char[] connectors = new char[]{'+', '#', '&', '.', '_', '-'};

    private static final Pattern p = Pattern.compile("[\\s*|\t|\r|\n∶；?‘’“”〝〞ˆˇ﹕︰﹔﹖﹑•¨….¸;！´？！～—ˉ｜‖＂〃｀@﹫¡¿﹏﹋﹌︴々﹟#﹩$﹠&﹪%*﹡﹢﹦﹤‐￣¯―﹨ˆ˜﹍﹎+=<＿_\\[\\]ˇ~﹉﹊（）〈〉‹›﹛﹜『』〖〗［］《》〔〕{}「」【】]");


    public static boolean isChineseLetter(char ch) {
        if (ch >= 0x4E00 && ch <= 0x9FA5)
            return true;
        return false;
    }


    public static boolean isEnglishLetter(char ch) {
        if ((ch >= 0x0041 && ch <= 0x005A) || (ch >= 0x0061 && ch <= 0x007A))
            return true;
        return false;
    }


    public static boolean isDigit(char ch) {
        if (ch >= 0x0030 && ch <= 0x0039)
            return true;
        return false;
    }


    public static boolean isConnector(char ch) {
        for (char connector : connectors)
            if (ch == connector)
                return true;
        return false;
    }


    public static boolean ccFind(char ch) {
        if (isChineseLetter(ch))
            return true;
        if (isEnglishLetter(ch))
            return true;
        if (isDigit(ch))
            return true;
        if (isConnector(ch))
            return true;
        return false;
    }


    /**
     * 全角 to 半角,大写 to 小写
     *
     * @param input 输入字符
     * @return 转换后的字符
     */
    public static char regularize(char input) {
        if (input == 12288) {
            return 32;
        } else if (input > 65280 && input < 65375) {
            return (char) (input - 65248);
        } else if (input >= 'A' && input <= 'Z') {
            return (input += 32);
        }
        return input;
    }

    /**
     * 处理特殊字符
     *
     * @param sentence 句子
     * @return 处理后的句子
     */
    public static String handleSpeciaWord(String sentence) {
        String newSentence = StringUtils.EMPTY;
        if (StringUtils.isNotEmpty(sentence)) {
            newSentence = p.matcher(sentence).replaceAll("");
        }
        return newSentence;
    }

}
