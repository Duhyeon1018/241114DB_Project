package dto;


public class MyRentalDTO {
    private int rentalId;
    private int userId;
    private int bookId;
    private String rentalDate;
    private String returnDate;
    
    
    // KBOOKTABLE
    private String bookName;

    // Getter and Setter methods
    public int getRentalId() {
    	return rentalId; 
	}
    public void setRentalId(int rentalId) { 
    	this.rentalId = rentalId; 
	}
    
    public int getUserId() { 
    	return userId; 
    }
    public void setUserId(int userId) { 
    	this.userId = userId; 
    }
    
    public int getBookId() { 
    	return bookId; 
    }
    public void setBookId(int bookId) { 
    	this.bookId = bookId; 
    }
    
    public String getRentalDate() { 
    	return rentalDate; 
    }
    public void setRentalDate(String rentalDate) { 
    	this.rentalDate = rentalDate; 
    }
    
    public String getReturnDate() { 
    	return returnDate; 
    }
    public void setReturnDate(String returnDate) { 
    	this.returnDate = returnDate; 
    }
    
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
    
    
    
    
    // 나의 대여 목록
//	-- 새로운 더미 데이터 삽입
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (1, 1, SYSDATE - 30, SYSDATE - 23); -- 30일 전 대여, 23일 전 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (2, 2, SYSDATE - 15, SYSDATE - 8);  -- 15일 전 대여, 8일 전 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (3, 3, SYSDATE - 10, SYSDATE - 3);  -- 10일 전 대여, 3일 전 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (4, 4, SYSDATE - 5, SYSDATE + 2);   -- 5일 전 대여, 2일 후 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (5, 5, SYSDATE - 2, SYSDATE + 5);   -- 2일 전 대여, 5일 후 반납 예정
//
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (6, 6, SYSDATE, SYSDATE + 7);       -- 오늘 대여, 7일 후 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (7, 7, SYSDATE + 1, SYSDATE + 8);   -- 내일 대여, 8일 후 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (8, 2, SYSDATE - 15, SYSDATE - 8);  -- 15일 전 대여, 8일 전 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (9, 3, SYSDATE - 10, SYSDATE - 3);  -- 10일 전 대여, 3일 전 반납 예정
//	INSERT INTO KRENTALTABLE (BOOKNO, USERNO, RENTALSTARTDATE, RENTALENDDATE) VALUES (10, 4, SYSDATE - 5, SYSDATE + 2);   -- 5일 전 대여, 2일 후 반납 예정
	    
    
    
    
    
}