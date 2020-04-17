package com.company.project.common.utils;

public class CodeUtil {

    private static final String DEPT_TPYE = "D";

    /**
     * 右补位，左对齐
     *
     * @param len    目标字符串长度
     * @param alexin 补位字符
     * @return 目标字符串
     * 以alexin 做为补位
     * @pparam oriStr  原字符串
     */
    public static String padRight(int oriStr, int len, String alexin) {
        StringBuffer str = new StringBuffer();
        int strlen = String.valueOf(oriStr).length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexin);
            }
        }
        str.append(oriStr);
        return DEPT_TPYE + str.toString();
    }

    public static void main(String[] args) {
        System.out.println(padRight(2,6,"0"));
    }

}
