package jdbc3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductDao {

  //모든 상품 정보를 조회

  public List<Product> getAllProducts() throws SQLException {
    String sql =
      """
                
        select
            prod_no,
            prod_name ,
            prod_maker ,
            prod_price ,
            prod_discount_price ,
            prod_stock ,
            prod_sold_out ,
            prod_created_date ,
            prod_updated_date 
        from
            sample_products 
        order by
            prod_no desc       

                """;

    List<Product> productList = new ArrayList<Product>();

    Connection connection = getConnection();
    PreparedStatement pstmt = connection.prepareStatement(sql);
    ResultSet rs = pstmt.executeQuery();

    while (rs.next()) {
      int No = rs.getInt("prod_no");
      String Name = rs.getString("prod_name");
      String Maker = rs.getString("prod_maker");
      int Price = rs.getInt("prod_price");
      int DiscountPrice = rs.getInt("prod_discount_price");
      int Stock = rs.getInt("prod_stock");
      int SoldOut = rs.getInt("prod_sold_out");
      Date CreatedDate = rs.getDate("prod_created_date");
      Date UpdatedDate = rs.getDate("prod_updated_date");

      Product product = new Product();

      product.setNo(No);
      product.setName(Name);
      product.setMaker(Maker);
      product.setPrice(Price);
      product.setDiscountPrice(DiscountPrice);
      product.setStock(Stock);
      product.setSoldOut(SoldOut);
      product.setCreatedDate(CreatedDate);
      product.setUpdatedDate(UpdatedDate);

      productList.add(product);
    }

    rs.close();
    pstmt.close();
    connection.close();

    return productList;
  }

  //상품 상세정보 조회
  public Product getProductByNo(int productNo) throws SQLException {
    String sql =
      """
        
    select
        
            prod_no,
            prod_name ,
            prod_maker ,
            prod_price ,
            prod_discount_price ,
            prod_stock ,
            prod_sold_out 
           
    from
        sample_products
    where
        prod_no = ?

        """;

    Product product = null;

    Connection connection = getConnection();
    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setInt(1, productNo);
    ResultSet rs = pstmt.executeQuery();
    while (rs.next()) {
      int no = rs.getInt("prod_no");
      String name = rs.getString("prod_name");
      String maker = rs.getString("prod_maker");
      int price = rs.getInt("prod_price");
      int discountPrice = rs.getInt("prod_discount_price");
      int stock = rs.getInt("prod_stock");
      int SoldOut = rs.getInt("prod_sold_out");

      product = new Product();
      product.setNo(no);
      product.setName(name);
      product.setMaker(maker);
      product.setPrice(price);
      product.setDiscountPrice(discountPrice);
      product.setStock(stock);
      product.setSoldOut(SoldOut);
    }

    rs.close();
    pstmt.close();
    connection.close();
    return product;
  }

  //가격 범위 설정

  public List<Product> getProductsByPrice(int min, int max)
    throws SQLException {}

  //신규 상품 정보 등록

  public void insertProduct(Product product) throws SQLException {
    String sql =
      """
      insert into sample_products(
            prod_no,
            prod_name ,
            prod_maker ,
            prod_price ,
            prod_discount_price ,
            prod_stock ,
            prod_sold_out )
          
      values
      (?,?,?,?,?,?,?)

        """;

    Connection connection = getConnection();

    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setInt(1, product.getNo());
    pstmt.setString(2, product.getName());
    pstmt.setString(3, product.getMaker());
    pstmt.setInt(4, product.getPrice());
    pstmt.setInt(5, product.getDiscountPrice());
    pstmt.setInt(6, product.getStock());
    pstmt.setInt(7, product.getSoldOut());

    pstmt.executeUpdate();

    pstmt.close();
    connection.close();
  }

  //상품 정보 삭제

  public void deleteProductByNo(int productNo) throws SQLException {
    String sql = """
    delete from sample_products
    where prod_no = ?
    """;
    Connection connection = getConnection();

    PreparedStatement pstmt = connection.prepareStatement(sql);
    pstmt.setInt(1, productNo);

    pstmt.executeUpdate();

    pstmt.close();
    connection.close();
  }

  //상품 가격 수정

  public void updateProduct(int no, int price, int discountPrice)
    throws SQLException {
    String sql =
      """
        update sample_products
        set 
           
           
            prod_price = ?,
            prod_discount_price = ?,
            prod_updated_date = sysdate
           
        where
            prod_no = ?            
        """;

    Connection connection = getConnection();

    PreparedStatement pstmt = connection.prepareStatement(sql);

    pstmt.setInt(1, price);
    pstmt.setInt(2, discountPrice);
    pstmt.setInt(3, no);

    pstmt.executeUpdate();

    pstmt.close();
    connection.close();
  }

  //상품 수량 변경

  public void updateProductStock(int no, int stock) throws SQLException {
    String sql =
      """
        update sample_products
        set
          prod_stock = ?,
          prod_updated_date = sysdate

        where
            prod_no = ?  
        """;

        
  }

  private Connection getConnection() throws SQLException {
    try {
      Class.forName("oracle.jdbc.OracleDriver");
    } catch (ClassNotFoundException ex) {
      throw new SQLException(ex.getMessage(), ex);
    }

    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String user = "hr";
    String password = "zxcv1234";

    return DriverManager.getConnection(url, user, password);
  }
}
