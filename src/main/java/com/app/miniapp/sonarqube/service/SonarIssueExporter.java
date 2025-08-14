package com.app.miniapp.sonarqube.service;

import com.app.miniapp.sonarqube.dto.SonarIssue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/8/14
 * <p>UPDATE DATE: 2025/8/14
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class SonarIssueExporter {

    public static void main(String[] args) {
        String baseUrl = "http://172.16.21.88:5001/api/issues/search?components=bsw-server&s=FILE_LINE&severities=BLOCKER&issueStatuses=CONFIRMED%2COPEN&ps=100&facets=cleanCodeAttributeCategories%2CimpactSoftwareQualities%2Cseverities%2Ctypes%2CimpactSeverities&additionalFields=_all&timeZone=Asia%2FShanghai";
        String cookie = "jenkins-timestamper-offset=-28800000; XSRF-TOKEN=st704t28utc5ibgs24s1bli6tc; JWT-SESSION=eyJhbGciOiJIUzI1NiJ9.eyJsYXN0UmVmcmVzaFRpbWUiOjE3NTUxMzM2MzI3NjYsInhzcmZUb2tlbiI6InN0NzA0dDI4dXRjNWliZ3MyNHMxYmxpNnRjIiwianRpIjoiMGFkNzExYWItNjFhNi00NTQyLWE1NzYtMzg2MGNlMmM2YmRmIiwic3ViIjoiQVl6WGFva2RtWXZmaWVvVktsNWYiLCJpYXQiOjE3NTUxMzIxMjIsImV4cCI6MTc1NTM5MjgzMn0.uAId_UBxuTJE8ovMN5fVcIx6U2FEwsdBYhyR_uPK4q0";
        List<SonarIssue> issues = fetchAllIssues(baseUrl, cookie);
        exportToExcel(issues, "sonar_issues.xlsx");
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
