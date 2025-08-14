package com.app.miniapp.sonarqube.service;

import com.app.miniapp.sonarqube.dto.SonarIssue;
import com.app.miniapp.sonarqube.dto.SumSonarIssue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class SumSonarIssueExporter {

    public static void main(String[] args) {
        // 严重
//        String baseUrl = "http://172.16.21.88:5001/api/issues/search?components=bsw-server&s=FILE_LINE&severities=CRITICAL&issueStatuses=CONFIRMED%2COPEN&ps=100&facets=cleanCodeAttributeCategories%2CimpactSoftwareQualities%2Cseverities%2Ctypes%2CimpactSeverities&additionalFields=_all&timeZone=Asia%2FShanghai";
        // 阻断
        String baseUrl = "http://172.16.21.88:5001/api/issues/search?components=bsw-server&s=FILE_LINE&severities=BLOCKER&issueStatuses=CONFIRMED%2COPEN&ps=100&facets=cleanCodeAttributeCategories%2CimpactSoftwareQualities%2Cseverities%2Ctypes%2CimpactSeverities&additionalFields=_all&timeZone=Asia%2FShanghai";
        String cookie = "";
        List<SonarIssue> issues = fetchAllIssues(baseUrl, cookie);
        Map<String, SumSonarIssue> ruleSummary = summarizeRules(issues);
        List<SumSonarIssue> summaryList = new ArrayList<>(ruleSummary.values());
        exportSummaryToExcel(summaryList, "阻断sonar_rule_summary.xlsx");
        //exportToExcel(issues, "sonar_issues.xlsx");
    }

    private static List<SonarIssue> fetchAllIssues(String baseUrl, String cookie) {
        List<SonarIssue> allIssues = new ArrayList<>();
        int pageSize = 100;
        int total = 0;

        try {
            int page = 1;
            while (true) {
                String urlString = baseUrl + "&p=" + page;
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Cookie", cookie);
                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(conn.getInputStream());
                    JsonNode issuesNode = root.path("issues");

                    for (JsonNode issueNode : issuesNode) {
                        SonarIssue issue = new SonarIssue();
                        issue.setRule(issueNode.path("rule").asText());
                        issue.setSeverity(issueNode.path("severity").asText());
                        issue.setComponent(issueNode.path("component").asText());
                        issue.setProject(issueNode.path("project").asText());
                        issue.setStatus(issueNode.path("status").asText());
                        issue.setMessage(issueNode.path("message").asText());
                        issue.setAuthor(issueNode.path("author").asText());
                        issue.setType(issueNode.path("type").asText());
                        issue.setScope(issueNode.path("scope").asText());
                        issue.setIssueStatus(issueNode.path("issueStatus").asText());
                        allIssues.add(issue);
                    }

                    if (page == 1) {
                        total = root.path("total").asInt();
                    }

                    if (allIssues.size() >= total || issuesNode.size() < pageSize) {
                        break;
                    }
                    page++;
                }
                conn.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allIssues;
    }

    private static Map<String, SumSonarIssue> summarizeRules(List<SonarIssue> issues) {
        Map<String, SumSonarIssue> ruleSummary = new HashMap<>();
        for (SonarIssue issue : issues) {
            String rule = issue.getRule();
            SumSonarIssue summary = ruleSummary.computeIfAbsent(rule, k -> new SumSonarIssue());
            summary.setRule(rule);
            summary.setMessage(issue.getMessage());
            summary.setCnt(summary.getCnt() != null ? summary.getCnt() + 1 : 1);
            if (summary.getComponents() == null) {
                summary.setComponents(new ArrayList<>());
            }
            summary.getComponents().add(issue.getComponent());
        }
        return ruleSummary;
    }

    private static void exportSummaryToExcel(List<SumSonarIssue> summaries, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rule Summary");

        String[] headers = {"rule", "message", "cnt", "components"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (SumSonarIssue summary : summaries) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(summary.getRule());
            row.createCell(1).setCellValue(summary.getMessage());
            row.createCell(2).setCellValue(summary.getCnt());
            Cell componentsCell = row.createCell(3);
            if (summary.getComponents() != null) {
                List<String> components = summary.getComponents();
                componentsCell.setCellValue(components.get(0));
            }
        }

        // 添加总计行
        Row totalRow = sheet.createRow(rowNum);
        Cell totalLabelCell = totalRow.createCell(0);
        totalLabelCell.setCellValue("Total");
        Cell totalValueCell = totalRow.createCell(2);
        totalValueCell.setCellFormula("SUM(C2:C" + rowNum + ")");

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private static void exportSummaryToExcel(List<SumSonarIssue> summaries, String fileName) {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Rule Summary");
//
//        String[] headers = {"rule", "message", "cnt", "components"};
//        Row headerRow = sheet.createRow(0);
//        for (int i = 0; i < headers.length; i++) {
//            Cell cell = headerRow.createCell(i);
//            cell.setCellValue(headers[i]);
//        }
//
//        int rowNum = 1;
//        for (SumSonarIssue summary : summaries) {
//            Row row = sheet.createRow(rowNum++);
//            row.createCell(0).setCellValue(summary.getRule());
//            row.createCell(1).setCellValue(summary.getMessage());
//            row.createCell(2).setCellValue(summary.getCnt());
////            Cell componentsCell = row.createCell(3);
////            if (summary.getComponents() != null) {
////                componentsCell.setCellValue(String.join("\n", summary.getComponents()));
////            }
//        }
//
//        for (int i = 0; i < headers.length; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
//            workbook.write(fileOut);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

    private static void exportToExcel(List<SonarIssue> issues, String fileName) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sonar Issues");

        String[] headers = {"rule", "severity", "component", "project", "status", "message", "author", "type", "scope", "issueStatus"};
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
        }

        int rowNum = 1;
        for (SonarIssue issue : issues) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(issue.getRule());
            row.createCell(1).setCellValue(issue.getSeverity());
            row.createCell(2).setCellValue(issue.getComponent());
            row.createCell(3).setCellValue(issue.getProject());
            row.createCell(4).setCellValue(issue.getStatus());
            row.createCell(5).setCellValue(issue.getMessage());
            row.createCell(6).setCellValue(issue.getAuthor());
            row.createCell(7).setCellValue(issue.getType());
            row.createCell(8).setCellValue(issue.getScope());
            row.createCell(9).setCellValue(issue.getIssueStatus());
        }

        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
