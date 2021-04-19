package com.assignment2.report;

import org.springframework.stereotype.Service;

import static com.assignment2.report.ReportType.PDF;

@Service
public class PdfReportService implements ReportService {
    @Override
    public String export() {
        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
