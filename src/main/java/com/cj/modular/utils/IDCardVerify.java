package com.cj.modular.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * verify corrective of identity card
 * </p>
 *
 * @author Caoj create on 2021-12-20 9:37
 */
@Slf4j
public class IDCardVerify {
    private static final Integer[] CHECK_CODE = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
    private static final String[] REMAINDER = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

    /**
     * judge correctly of identity code according to identity serialize
     *
     * @param identity identity serialize, maybe one or more and more, the length must be 18
     * @return boolean[]
     * @author Caoj creat on 12/20/2021 10:03
     */
    public static boolean[] isCorrect(String... identity) {
        boolean[] booleans = new boolean[identity.length];
        int index = 0;
        for (String code : identity) {
            if (code == null || code.length() != 18) {
                log.warn("证件号为{}的号码位数有问题", code);
                continue;
            }
            int sum = 0;
            for (int i = 0; i < code.length() - 1; i++) {
                sum += Integer.parseInt(code.substring(i, i + 1)) * CHECK_CODE[i];
            }
            booleans[index] = code.substring(17).toUpperCase().equals(REMAINDER[sum % 11]);
            if (!booleans[index]) {
                log.warn("证件号为{}的号码有问题, 可能为{}", code, code.substring(0, 17) + REMAINDER[sum % 11]);
            }
            index ++;
        }
        return booleans;
    }

    /**
     * provide 17 identity code, return 18 identity code
     *
     * @param identity 17位身份证
     * @return java.lang.String
     * @date 2022/3/14 0:02
     * @author Caoj
     */
    public static String getCheckCode(String identity) {
        int sum = 0;
        for (int i = 0; i < identity.length(); i++) {
            sum += Integer.parseInt(identity.substring(i, i + 1)) * CHECK_CODE[i];
        }
        return identity + REMAINDER[sum % 11];
    }
}
