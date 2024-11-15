package dto;

public class KdhBookDTO {
    private int bookNo;
    private String bookName;
    private String genre;
    private String author;
    private String publisher;

    public KdhBookDTO(int bookNo, String bookName, String genre, String authorno, String publisherno) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.genre = genre;
        this.author = authorno;
        this.publisher = publisherno;
    }
    
    public KdhBookDTO(int bookNo, String bookName, String authorno, String publisherno) {
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
