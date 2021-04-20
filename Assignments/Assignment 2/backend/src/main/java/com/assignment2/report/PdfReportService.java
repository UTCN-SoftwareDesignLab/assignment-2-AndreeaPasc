package com.assignment2.report;

import com.assignment2.book.model.Book;
import com.assignment2.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.assignment2.report.ReportType.PDF;

@RequiredArgsConstructor
@Service
public class PdfReportService implements ReportService {

    private final BookRepository bookRepository;

    public void pdfGenerate() throws IOException {
        List<Book> books = bookRepository.findAll().stream().filter(book -> book.getQuantity() == 0).collect(Collectors.toList());

        PDDocument doc = new PDDocument();
        PDPage page = doc.getPage(1);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        contentStream.beginText();
        contentStream.newLineAtOffset(20, 450);
        for(Book book: books) {
            contentStream.showText(book.toString());
            contentStream.newLine();
        }
        contentStream.endText();

        doc.save("PrettyPDF");
        doc.close();
    }

    @Override
    public String export() {
        return "I am a PDF reporter.";
    }


    @Override
    public ReportType getType() {
        return PDF;
    }
}
