package report;

import java.util.List;

/**
 * Interface cơ bản cho việc tạo báo cáo
 * Tuân thủ Interface Segregation Principle - chỉ chứa các method cần thiết
 */
public interface ReportGenerator {
    void generateReport(List<?> data);
    String getReportType();
}

