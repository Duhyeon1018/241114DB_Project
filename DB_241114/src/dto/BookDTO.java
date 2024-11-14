package dto;

public class BookDTO {
    private int bookNo;
    private String bookName;
    private String genre;
    private String publisher;
    private String author;

    // 생성자
    public BookDTO(int bookNo, String bookName, String genre, String publisher, String author) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.genre = genre;
        this.publisher = publisher;
        this.author = author;
    }

    // Getter 메서드
    public int getBookNo() {
        return bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public String getGenre() {
        return genre;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getAuthor() {
        return author;
    }
}

