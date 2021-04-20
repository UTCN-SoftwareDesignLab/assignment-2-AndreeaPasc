package com.assignment2.book;

import com.assignment2.BaseControllerTest;
import com.assignment2.TestCreationFactory;
import com.assignment2.book.controller.BookController;
import com.assignment2.book.model.Book;
import com.assignment2.book.model.dto.BookDTO;
import com.assignment2.book.service.BookService;
import com.assignment2.report.CSVReportService;
import com.assignment2.report.PdfReportService;
import com.assignment2.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static com.assignment2.UrlMapping.*;
import static com.assignment2.report.ReportType.CSV;
import static com.assignment2.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BookControllerTest extends BaseControllerTest {
    @InjectMocks
    private BookController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allItems() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOK));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void exportReport() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse);
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse);

        ResultActions pdfExport = mockMvc.perform(get(BOOK + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOK + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOK, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        when(bookService.edit(reqBook.getId(), reqBook)).thenReturn(reqBook);

        ResultActions result = performPatchWithRequestBodyPathVariable(BOOK + ENTITY, reqBook.getId().toString(), reqBook );
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void sell() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        //when(bookService.create(reqBook)).thenReturn(reqBook);
        when(bookService.sell(reqBook.getId(), 2)).thenReturn(true);
        ResultActions result = performPostWithRequestBodyPathVariable(BOOK + ENTITY + SELL, reqBook.getId().toString(), 2);
        //System.out.print(result.andReturn().getResponse().getContentAsString());
        result.andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(5L)
                .author("Lev Tolstoy")
                .genre("Classicism")
                .price(50.0)
                .title("Anna Karenina")
                .quantity(10)
                .build();
        //when(bookService.create(reqBook)).thenReturn(reqBook);
        ResultActions result = performDeleteWithPathVariable(BOOK + ENTITY, reqBook.getId().toString());
        result.andExpect(status().isOk());
    }
}
