package com.shgs.lodge.util;

import com.shgs.lodge.dto.HeaderColumns;
import com.shgs.lodge.primary.entity.AccounCode;
import com.shgs.lodge.primary.entity.CustomParame;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.interfaces.RSAPrivateKey;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.net.URLDecoder.decode;

/**
 * 辅助工具类
 */
public class CmsUtils {

    private static final Integer DEF_DIV_SCALE = 2;

    /**
     * 获取表头
     *
     * @param clas
     * @return
     */
    public static List<HeaderColumns> getHeaderColumns(String clas) {
        List<HeaderColumns> list = new ArrayList<HeaderColumns>();
        try {
            Class<?> cls = Class.forName(clas);
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(HeaderEnum.class)) {
                    HeaderEnum file = (HeaderEnum) field.getAnnotation(HeaderEnum.class);
                    list.add(new HeaderColumns(file.field(), file.title(), file.width(), file.sortable(), file.hidden(), file.status()));
                }
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取模块授权列表
     *
     * @return
     */
    public static List<SelectJson> getRoleType() {
        Map<String, String> type = EnumUtils.enumProp2NameMap(RoleType.class, "name");
        List<SelectJson> select = new ArrayList<SelectJson>();
        select.add(new SelectJson(null, "请选择..."));
        for (Map.Entry<String, String> entry : type.entrySet()) {
            select.add(new SelectJson(entry.getKey(), entry.getValue()));
        }
        return select;
    }

    /**
     * str转Integer数组
     *
     * @param s
     * @return
     */
    public static Integer[] string2Integer(String s) {
        String[] str = s.split(",");
        Integer[] n = new Integer[str.length];
        for (int i = 0; i < str.length; i++) {
            n[i] = Integer.valueOf(str[i]);
        }
        return n;
    }

    /**
     * str转String数组
     *
     * @param list
     * @return
     */
    public static String[] list2String(List<String> list) {
        return list.toArray(new String[list.size()]);
    }

    /**
     * str转list
     *
     * @param s
     * @param regex 分割符号
     * @return
     */
    public static List<String> string2Array(String s, String regex) {
        if (regex == null || regex.isEmpty())
            regex = ",";
        List<String> array = new ArrayList<String>();
        if (s != null && !s.isEmpty()) {
            String[] str = s.split(regex);
            for (int i = 0; i < str.length; i++) {
                array.add(str[i]);
            }
        }
        return array;
    }

    /**
     * list转String
     *
     * @param array
     * @return
     */
    public static String array2String(List<Integer> array) {
        if (array.size() > 0) {
            return StringUtils.join(array, ",");
        } else {
            return null;
        }

    }

    /**
     * 两个字符串比较
     *
     * @param str1
     * @param str2
     * @return
     */
    public static boolean is_eq(String str1, String str2) {
        if (str1 == null && str2 == null)
            return true;
        if (str1 == null || str2 == null)
            return false;
        if (str1 == null)
            str1 = "";
        return str1.equals(str2);
    }

    /**
     * 获取IP地址
     *
     * @param request
     * @return
     * @throws Exception
     */
    public static String getIpAddr(HttpServletRequest request) throws Exception {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }

    /**
     * 数字补位
     *
     * @param number
     * @return
     */
    public static String int2Formatter(int number, int digit) {
        NumberFormat formatter = NumberFormat.getNumberInstance();
        formatter.setMinimumIntegerDigits(digit);
        formatter.setGroupingUsed(false);
        String s = formatter.format(number);
        return s;

    }

    /**
     * 金额加法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double addDouble(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 金额减法运算
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static Double mul(Double value1, Double value2) {
        BigDecimal b1 = new BigDecimal(Double.toString(value1));
        BigDecimal b2 = new BigDecimal(Double.toString(value2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时， 精确到小数点以后10位，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor) {
        return divide(dividend, divisor, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。 当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入。
     *
     * @param dividend 被除数
     * @param divisor  除数
     * @param scale    表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static Double divide(Double dividend, Double divisor, Integer scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(dividend));
        BigDecimal b2 = new BigDecimal(Double.toString(divisor));
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 提供指定数值的（精确）小数位四舍五入处理。
     *
     * @param value 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("小数位数必须是正整数或零");
        }
        BigDecimal b = new BigDecimal(Double.toString(value));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 获取当前时间
     *
     * @return String
     */
    public static String getNowDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(date);
        return time;
    }

    /**
     * 获取当前时间戳
     *
     * @return
     */
    public static String getTimeMillis() {
        Date d = new Date();
        String s = String.valueOf(d.getTime());
        return s;
    }


    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds 精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    /**
     * 格式化关联操作按钮
     *
     * @param id
     * @return
     */
    public static String formatHandle(String id) {
        String editRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='editRow(&quot;" + id + "&quot;);' ><i class=\"fa fa-edit\"></i>编辑</a>";
        String deleteRow = "<a href=\"javascript:void(0)\" class='easyui-linkbutton' plain='true' onclick='deleteRow(&quot;" + id + "&quot;);' ><i class=\"fa fa-trash\"></i>删除</a>";
        return editRow + "&nbsp;&nbsp;" + deleteRow;

    }

    /**
     * 格式化状态图标
     *
     * @param status
     * @return
     */
    public static String formatStatus(Integer status) {
        return status == 1 ? "<i class='fa fa-check fa-fw green '></i>" : "<i class='fa fa-close fa-fw red'></i>";
    }

    /**
     * 参数格式化
     *
     * @param outputStr
     * @return
     */
    public static String decryptBASE64(String outputStr) {
        BASE64Decoder decoder = new BASE64Decoder();
        String value = "";
        try {
            byte[] key = decoder.decodeBuffer(outputStr);
            value = new String(key, "UTF-8");
        } catch (Exception e) {
        }
        return value;
    }

    /**
     * 字符串去除空格
     *
     * @param str
     * @return
     */
    public static String strToTrim(String str) {
        if (str != null && !str.isEmpty()) {
            return str.replaceAll("\\s*", "");
        }
        return null;
    }

    /**
     * 拼接单号
     *
     * @param accounCode
     * @param deptBh     部门编号
     * @param corpBh     客户编号
     * @return
     */
    public static String getAccounCode(AccounCode accounCode, String deptBh, String corpBh, Integer orderNum) {
        List<String> str = new ArrayList<String>();
        if (accounCode != null) {
            if (accounCode.getPrefixOne() != null) {
                CustomParame parame = accounCode.getPrefixOne();
                if ("DeptBh".equals(parame.getParameCode())) {//部门
                    str.add(deptBh);
                } else if ("Custom".equals(parame.getParameCode())) {//自定义编码
                    str.add(accounCode.getPrefixOneCode());
                } else if ("CorpBh".equals(parame.getParameCode())) {//客户编号
                    str.add(corpBh);
                } else if ("DATE".equals(parame.getParameCode())) {//日期
                    str.add(BeanUtil.timestampToStr(BeanUtil.dateToTimestamp(new Date()), accounCode.getPrefixOneCode()));
                }else if("Serial".equals(parame.getParameCode())){
                    str.add(CmsUtils.int2Formatter(orderNum, accounCode.getJhSerialLength()));
                }
            }
            if (accounCode.getPrefixTwo() != null) {
                CustomParame parame = accounCode.getPrefixTwo();
                if ("DeptBh".equals(parame.getParameCode())) {//部门
                    str.add(deptBh);
                } else if ("Custom".equals(parame.getParameCode())) {//自定义编码
                    str.add(accounCode.getPrefixTwoCode());
                } else if ("CorpBh".equals(parame.getParameCode())) {//客户编号
                    str.add(corpBh);
                } else if ("DATE".equals(parame.getParameCode())) {//日期
                    str.add(BeanUtil.timestampToStr(BeanUtil.dateToTimestamp(new Date()), accounCode.getPrefixTwoCode()));
                }else if("Serial".equals(parame.getParameCode())){
                    str.add(CmsUtils.int2Formatter(orderNum, accounCode.getJhSerialLength()));
                }
            }
            if (accounCode.getPrefixThree() != null) {
                CustomParame parame = accounCode.getPrefixThree();
                if ("DeptBh".equals(parame.getParameCode())) {//部门
                    str.add(deptBh);
                } else if ("Custom".equals(parame.getParameCode())) {//自定义编码
                    str.add(accounCode.getPrefixThreeCode());
                } else if ("CorpBh".equals(parame.getParameCode())) {//客户编号
                    str.add(corpBh);
                } else if ("DATE".equals(parame.getParameCode())) {//日期
                    str.add(BeanUtil.timestampToStr(BeanUtil.dateToTimestamp(new Date()), accounCode.getPrefixThreeCode()));
                }else if("Serial".equals(parame.getParameCode())){
                    str.add(CmsUtils.int2Formatter(orderNum, accounCode.getJhSerialLength()));
                }
            }
            if (accounCode.getPrefixFour() != null) {
                CustomParame parame = accounCode.getPrefixFour();
                if ("DeptBh".equals(parame.getParameCode())) {//部门
                    str.add(deptBh);
                } else if ("Custom".equals(parame.getParameCode())) {//自定义编码
                    str.add(accounCode.getPrefixFourCode());
                } else if ("CorpBh".equals(parame.getParameCode())) {//客户编号
                    str.add(corpBh);
                } else if ("DATE".equals(parame.getParameCode())) {//日期
                    str.add(BeanUtil.timestampToStr(BeanUtil.dateToTimestamp(new Date()), accounCode.getPrefixFourCode()));
                }else if("Serial".equals(parame.getParameCode())){
                    str.add(CmsUtils.int2Formatter(orderNum, accounCode.getJhSerialLength()));
                }
            }
            return StringUtils.join(str, accounCode.getSeparator().getParameCode());

        } else {
            return "";
        }

    }

    /**
     * 判断String是否为double类型
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]+[.]{0,1}[0-9]*[dD]{0,1}");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    public static String decryptPassword(String pwd, HttpSession session) throws Exception {
        try {
            RSAPrivateKey privateKey = (RSAPrivateKey) session.getAttribute("loginKey");
            if (pwd != null && !pwd.isEmpty()) {
                return decode(RSAUtils.decryptByPrivateKey(pwd, privateKey), "UTF-8");
            }else {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            session.removeAttribute("loginKey");
        }

    }


}
