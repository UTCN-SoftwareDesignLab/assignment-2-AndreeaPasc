package com.assignment2.report;

import org.springframework.stereotype.Service;

import static com.assignment2.report.ReportType.CSV;

@Service
public class CSVReportService implements ReportService {
    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
