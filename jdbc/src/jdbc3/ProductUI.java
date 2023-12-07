package jdbc3;

import java.sql.SQLException;
import java.util.List;

public class ProductUI {

  private ProductDao productDao = new ProductDao();
  private Scanner scanner = new Scanner(System.in);

  public void showMenu() {
    System.out.println(
      "----------------------------------------------------------------------------------------"
    );
    System.out.println(
      "1. 전체조회 2. 상세조회 3. 검색 4. 신규등록 5. 삭제 6. 가격변경 7. 수량변경 0. 종료 "
    );
    System.out.println(
      "----------------------------------------------------------------------------------------"
    );

    System.out.println("### 메뉴선택: ");
    int menuNo = scanner.nextInt();

    try {
      switch (menuNo) {
        case 1:
          전체조회();
          break;
        case 2:
          상세조회();
          break;
        case 3:
          검색();
          break;
        case 4:
          신규등록();
          break;
        case 5:
          삭제();
          break;
        case 6:
          가격변경();
          break;
        case 7:
          수량변경();
          break;
        case 0:
          종료();
          break;
      }
    } catch (SQLException ex) {
      System.out.println("### 데이터 베이스 작업중 오류가 발생하였습니다.");
      ex.printStackTrace();
    }

    System.out.println();
    System.out.println();
    System.out.println();

    showMenu();
  }

  private void 전체조회() throws SQLException {
    System.out.println("<< 전체 상품 조회 >>");

    List<Product> products = productDao.getAllProducts();

    if (products.isEmpty()) {
      System.out.println("### 상품 정보가 존재하지 않습니다.");
      return;
    }

    System.out.println(
      "-------------------------------------------------------------------"
    );
    System.out.println(
      "상품번호\t이름\t메이커\t가격\t할인율\t재고\t재고여부\t생산연도\t갱신일"
    );

    System.out.println(
      "-------------------------------------------------------------------"
    );

    for (Product product : products) {
      System.out.print(product.getNo() + "\t");
      System.out.print(product.getName() + "\t");
      System.out.print(product.getMaker() + "\t");
      System.out.print(product.getPrice() + "\t");
      System.out.print(product.getDiscountPrice() + "\t");
      System.out.print(product.getStock() + "\t");
      System.out.print(product.getSoldOut() + "\t");
      System.out.print(product.getCreatedDate() + "\t");
      System.out.println(product.getUpdatedDate());
    }

    System.out.println(
      "------------------------------------------------------------------------------------"
    );
  }

  private void 상세조회() throws SQLException {
    System.out.println("<< 상품 정보 상세 조회 >>");

    System.out.println("### 상품번호를 입력해서 상품 상세 정보를 확인.");
    System.out.println("### 상품번호: ");
    int productNo = scanner.nextInt();

    Product product = productDao.getProductByNo(productNo);
    if (product == null) {
      System.out.println(
        "### 상품번호: [" + productNo + "] 상품정보가 존재하지 않습니다"
      );
      return;
    }

    System.out.println("### 상품 상세 정보");
    System.out.println(
      "--------------------------------------------------------------------------------"
    );

    System.out.println("번호" + product.getNo());
    System.out.println("이름" + product.getName());
    System.out.println("메이커" + product.getMaker());
    System.out.println("가격" + product.getPrice());
    System.out.println("할인율" + product.getDiscountPrice());
    System.out.println("재고" + product.getStock());
    System.out.println("재고유무" + product.getSoldOut());
    System.out.println("생산일" + product.getCreatedDate());
    System.out.println("갱신일" + product.getUpdatedDate());
    System.out.println(
      "--------------------------------------------------------------------------------"
    );
  }

  // 최저가격, 최고 가격을 전달받아서 해당 가격범위에 속하는 상품정보를 반환하는 기능
  private void 검색() throws SQLException {}

  private void 신규등록() throws SQLException {
    System.out.println("### 상품번호: ");
    int no = scanner.nextInt();
    System.out.println("### 상품이름: ");
    String name = scanner.nextString();
    System.out.println("### 메이커: ");
    String maker = scanner.nextString();
    System.out.println("### 가격: ");
    int price = scanner.nextInt();
    System.out.println("### 할인율: ");
    int discountPrice = scanner.nextInt();
    System.out.println("### 재고: ");
    int stock = scanner.nextInt();

    Product product = new Product();

    product.setNo(no);
    product.setName(name);
    product.setMaker(maker);
    product.setPrice(price);
    product.setDiscountPrice(discountPrice);
    product.setStock(stock);

    productDao.insertProduct(product);
    System.out.println("### 신규상품을 등록하였습니다.");
  }

  private void 삭제() throws SQLException {
    System.out.println("<< 상품 정보 삭제 >>");
    System.out.println("### 상품번호를 입력받아서 상품정보를 삭제합니다.");

    System.out.println("### 상품번호 입력: ");
    int productNo = scanner.nextInt();

    productDao.deleteProductByNo(productNo);

    System.out.println(
      "상품번호: [" + productNo + "] 상품정보가 삭제되었습니다."
    );
  }

  // 상품번호, 가격, 할인 가격을 전달받아서 해당 상품의 가격을 수정하는 기능
  // 할인된 가격을 표기하는 기능?
  private void 가격변경() throws SQLException {
    System.out.println("<< 상품 가격 수정 >>");

    System.out.println("### 변경할 상품번호: ");
    int no = scanner.nextInt();

    System.out.println("변경할 상품가격: ");
    int price = scanner.nextInt();

    Product product = new Product();

    product.setPrice(price);

    productDao.updateProduct(product);
    System.out.println("### 상품 가격을 변경하였습니다.");
  }

  // 상품번호, 수량을 전달받아서 해당상품의 수량을 변경하는 기능
  
  private void 수량변경() throws SQLException {}

  private void 종료() {
    System.out.println("### 프로그램을 종료합니다.");
    System.exit(0);
  }

  public static void main(String[] args) throws Exception {
    new ProductUI().showMenu();
  }
}
