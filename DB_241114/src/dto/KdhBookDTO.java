package dto;

public class KdhBookDTO {
    private int bookNo;
    private String bookName;
    private String authorName;
    private String publisherName;

    // 생성자
    public KdhBookDTO(int bookNo, String bookName, String authorName, String publisherName) {
        this.bookNo = bookNo;
        this.bookName = bookName;
        this.authorName = authorName;
        this.publisherName = publisherName;
    }

    // Getter와 Setter
    public int getBookNo() {
        return bookNo;
    }

    public void setBookNo(int bookNo) {
        this.bookNo = bookNo;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
