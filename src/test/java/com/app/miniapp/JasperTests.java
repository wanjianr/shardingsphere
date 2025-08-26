package com.app.miniapp;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/26
 * <p>UPDATE DATE: 2025/8/26
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
@SpringBootTest
public class JasperTests {

    public static void main(String[] args) {
        // 1. 准备路径
        String jrxmlFile = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\6502_v0.jrxml";  // 你的模板文件
        String xmlFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\6502.xml";          // XML 数据文件
        String outputFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\6502-output.pdf";     // 输出的 PDF


        try {
            // 加载JRXML模板
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

            // 加载XML数据源
            JRXmlDataSource dataSource = new JRXmlDataSource(new File(xmlFile), "/DOCUMENT");

            // 填充报表
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // 导出为PDF
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

            System.out.println("PDF generated successfully: " + outputFile);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    /**
     * 简单的pdf生成（带样式）
     * @throws Exception
     */
    @Test
    public void testGenPdfV1() throws Exception {
        // 1. 准备路径
        String jrxmlFile = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\demo_v1.jrxml";  // 你的模板文件
        String xmlFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\test.xml";          // XML 数据文件
        String outputFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\invoice-output.pdf";     // 输出的 PDF


        // 加载JRXML模板
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

        // 加载XML数据源
        JRXmlDataSource dataSource = new JRXmlDataSource(new File(xmlFile), "/invoice");

        // 填充报表
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        // 导出为PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

        System.out.println("PDF generated successfully: " + outputFile);
    }


    /**
     * 简单的pdf生成
     * @throws Exception
     */
    @Test
    public void testGenPdfV0() throws Exception {
        // 1. 准备路径
        String jrxmlFile = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\demo_v0.jrxml";  // 你的模板文件
        String xmlFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\test.xml";          // XML 数据文件
        String outputFile   = "E:\\Project\\sharding\\miniapp\\src\\main\\resources\\templates\\invoice-output.pdf";     // 输出的 PDF


        // 加载JRXML模板
        JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlFile);

        // 加载XML数据源
        JRXmlDataSource dataSource = new JRXmlDataSource(new File(xmlFile), "/invoice");

        // 填充报表
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

        // 导出为PDF
        JasperExportManager.exportReportToPdfFile(jasperPrint, outputFile);

        System.out.println("PDF generated successfully: " + outputFile);
    }
}
