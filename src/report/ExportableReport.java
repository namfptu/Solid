package report;

import java.io.File;

/**
 * Interface cho báo cáo có thể xuất file
 * Tuân thủ Interface Segregation Principle - tách riêng chức năng export
 */
public interface ExportableReport {
    void exportToFile(File file);
    String getSupportedFormats();
}

