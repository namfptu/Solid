package report;

/**
 * Interface cho báo cáo có thể in
 * Tuân thủ Interface Segregation Principle - tách riêng chức năng in ấn
 */
public interface PrintableReport {
    void printReport();
    void setPrintSettings(String settings);
}

