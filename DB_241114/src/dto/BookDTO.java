package dto;

public class BookDTO {
    private int bookNo;
    private String bookName;
    private String genre;
    private String author;
    private String publisher;

    public BookDTO(int bookNo, String bookName, String genre, String authorno, String publisherno) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.genre = genre;
        this.author = authorno;
        this.publisher = publisherno;
    }
    
    public BookDTO(int bookNo, String bookName, String authorno, String publisherno) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.author = authorno;
        this.publisher = publisherno;
    }

    // Getters
    public int getBookNo() { return bookNo; }
    public String getBookName() { return bookName; }
    public String getGenre() { return genre; }
    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
}



