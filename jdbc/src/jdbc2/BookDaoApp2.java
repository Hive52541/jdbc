package jdbc2;

import java.sql.SQLException;
import java.util.List;

public class BookDaoApp2 {

  public static void main(String[] args) throws SQLException {
    BookDao bookDao = new BookDao();

    //모든 책 정보 조회하기
    List<Book> books = bookDao.getAllBooks();
    for (Book b : books) {
      System.out.println(b.getNo() + "," + b.getTitle());
    }
  }
}
