package com.tmri.rfid.util;

import org.apache.commons.codec.binary.Hex;

import java.util.zip.Checksum;

/**
 * Created by Joey on 2015/9/2.
 */
public class CRC8 {

    private static int calculate(final int CRCacc, int cword) {
        int temp = CRCacc ^ cword;
        for ( int i = 0; i < 8; i++) {
            if ((temp & 0x0080) != 0) {
                temp = (temp << 1) & 0xfe;
                temp ^= 0x31;
            } else {
                temp = (temp << 1) & 0xfe;
            }
        }
        return temp;
    }

    public static int calculate(String hexStr) {
        String tempHexStr = hexStr;
        if (hexStr.startsWith("0x")) {
            tempHexStr = tempHexStr.substring(2);
        }
        if (hexStr.endsWith("h") || hexStr.endsWith("H")) {
            tempHexStr = tempHexStr.substring(0, tempHexStr.length() - 1);
        }
        if (tempHexStr.length() % 2 != 0) {
            throw new IllegalArgumentException("²ÎÊý´íÎó");
        }
        int CRCacc = 0xff;
        for (int i = 0; i < tempHexStr.length(); i += 2) {
            CRCacc = calculate(CRCacc, Short.parseShort(tempHexStr.substring(i, i + 2), 16));
        }
        return CRCacc;
    }

    public static void  main(String[] args) {
        String hexStr = "A5A5A5A5A5A5A5h";
        System.out.println("Calculate Result:\t" + CRC8.calculate(hexStr));
    }
}
