package com.app.miniapp;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/7/29
 * <p>UPDATE DATE: 2025/7/29
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class FileUtilTest {

    public static void main(String[] args) {
//        String ext = FilenameUtils.getExtension("test.txt\0malicious.txt");
//        System.out.println("File extension: " + ext);

        String filepath =
                "D:/0x00ss/sss0x00.g.log";
        String ss =
                FilenameUtils.getBaseName(filepath);
        String ss2 =
                FilenameUtils.getFullPath(filepath);
        System.out.println(ss2);
        System.out.println(ss);
    }
}
