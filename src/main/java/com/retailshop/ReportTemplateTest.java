package com.retailshop;

import java.io.InputStream;
import net.sf.jasperreports.engine.JasperCompileManager;

public class ReportTemplateTest {

    public static void main(String[] args) {

        try (InputStream input =
                ReportTemplateTest.class.getResourceAsStream(
                    "/reports/sales_report.jrxml")) {

            if (input == null) {
                throw new IllegalStateException(
                    "Report file not found in src/main/resources/reports."
                );
            }

            JasperCompileManager.compileReport(input);

            System.out.println("Report template compiled successfully!");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}