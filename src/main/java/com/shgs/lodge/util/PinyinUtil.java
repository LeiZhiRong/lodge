package com.shgs.lodge.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

  private final static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();

  static {
    format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    format.setVCharType(HanyuPinyinVCharType.WITH_V);
  }

  public static String str2Pinyin(String str, String fill) {
    try {
      StringBuilder sb = new StringBuilder();
      if (fill == null) fill = "";
      boolean isCn = true;
      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        if (i > 0 && isCn) {
          sb.append(fill);
        }
        if (c == ' ') {
          sb.append(fill);
        }
        //1、判断c是不是中文
        if (c >= '\u4e00' && c <= '\u9fa5') {
          isCn = true;
          sb.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0]);
        } else {
          //不是中文
          if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            sb.append(c);
          }
          isCn = false;
        }
      }
      return sb.toString();
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String strFirst2Pinyin(String str) {
    try {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < str.length(); i++) {
        char c = str.charAt(i);
        //1、判断c是不是中文
        if (c >= '\u4e00' && c <= '\u9fa5') {
          sb.append(PinyinHelper.toHanyuPinyinStringArray(c, format)[0].charAt(0));
        }
      }
      return sb.toString().toLowerCase();
    } catch (BadHanyuPinyinOutputFormatCombination e) {
      e.printStackTrace();
    }
    return null;
  }
}

