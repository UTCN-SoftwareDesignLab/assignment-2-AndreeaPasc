package com.assignment2.report;

import com.assignment2.book.model.Book;
import com.assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

import static com.assignment2.report.ReportType.CSV;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {

    private final BookRepository bookRepository;

    public void csvGenerate() throws IOException {
        List<Book> books = bookRepository.findAll().stream().filter(book -> book.getQuantity() == 0).collect(Collectors.toList());

        try (PrintWriter writer = new PrintWriter(new File("PrettyCSV.csv"))) {

            for (Book book : books) {
                StringBuilder sb = new StringBuilder();
                sb.append(book.getId().toString()).append(book.getTitle()).append(book.getAuthor()).append(book.getPrice().toString()).append(book.getQuantity()).append('\n');
                writer.write(sb.toString());
            }
        }
    }

    @Override
    public String export() {
        return "I am a CSV reporter.";
    }

    @Override
    public ReportType getType() {
        return CSV;
    }
}
