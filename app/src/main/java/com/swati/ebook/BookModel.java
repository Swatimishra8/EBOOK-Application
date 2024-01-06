package com.swati.ebook;

public class BookModel {

    private String BookName;
    private String BookCategory;
    private String BookAuthor;
    private String PdfUrl;
    private String PdfIconUrl;

    public BookModel(String bookName, String bookCategory, String bookAuthor, String pdfUrl, String pdfIconUrl) {
        BookName = bookName;
        BookCategory = bookCategory;
        BookAuthor = bookAuthor;
        PdfUrl = pdfUrl;
        PdfIconUrl = pdfIconUrl;
    }

    public String getBookName() {
        return BookName;
    }

    public String getBookCategory() {
        return BookCategory;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public String getPdfUrl() {
        return PdfUrl;
    }

    public String getPdfIconUrl() {
        return PdfIconUrl;
    }

}
