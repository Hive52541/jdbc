package jdbc2;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookDaoApp3 {

  public Book getbookByNo(int bookNo) throws SQLException {
    String sql =
      """
                select
    BOOK_NO, BOOK_TITLE, BOOK_WRITER, BOOK_PRICE, BOOK_STOCK, BOOK_REG_DATE
from
    sample_books
where
  book_no = ?
                """;
    Book book = null;

    Connection connection = getConnection();
    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setInt(1, bookNo);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next()) {
      int no = rs.getInt("book_no");
      String title = rs.getString("book_title");
      String writer = rs.getString("book_writer");
      int price = rs.getInt("book_price");
      int stock = rs.getInt("book_stock");
      Date regDate = rs.getDate("book_reg_date");

      book = new Book();
      book.setNo(no);
      book.setTitle(title);
      book.setWriter(writer);
      book.setPrice(price);
      book.setStock(stock);
      book.setRegDate(regDate);
    }
  }
}
