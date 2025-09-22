package report.impl;

import model.Member;
import report.ExportableReport;
import report.PrintableReport;
import report.ReportGenerator;
import java.io.File;
import java.util.List;

/**
 * Báo cáo thành viên - implement nhiều interface nhưng chỉ những gì cần thiết
 * Tuân thủ Interface Segregation Principle - có thể chọn implement các interface phù hợp
 */
public class MemberReportGenerator implements ReportGenerator, ExportableReport, PrintableReport {
    private String printSettings = "Default";
    
    @Override
    public void generateReport(List<?> data) {
        System.out.println("👥 MEMBER REPORT");
        System.out.println("================");
        for (Object item : data) {
            if (item instanceof Member) {
                Member member = (Member) item;
                System.out.println("Name: " + member.getName());
                System.out.println("Email: " + member.getEmail());
                System.out.println("Phone: " + member.getPhone());
                System.out.println("---");
            }
        }
    }
    
    @Override
    public String getReportType() {
        return "Member Report";
    }
    
    @Override
    public void exportToFile(File file) {
        System.out.println("💾 Exporting member report to: " + file.getName());
        System.out.println("Export completed successfully!");
    }
    
    @Override
    public String getSupportedFormats() {
        return "PDF, Excel, CSV";
    }
    
    @Override
    public void printReport() {
        System.out.println("🖨️ Printing member report with settings: " + printSettings);
        System.out.println("Print job sent to printer!");
    }
    
    @Override
    public void setPrintSettings(String settings) {
        this.printSettings = settings;
    }
}

