package com.blibli.pos_minimarket.DataAccessObject;

import com.blibli.pos_minimarket.Model.TransactionDetail;
import org.springframework.stereotype.Repository;


@Repository
public class TransactionDetailDAO extends ConnectionSettings {
    private GeneralDAO generalDAO = new GeneralDAO();

    public TransactionDetailDAO() {
    }

    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.transactionDetail" +
                "(" +
                "    detailId SERIAL PRIMARY KEY NOT NULL," +
                "    quantity INT," +
                "    price FLOAT," +
                "    discount FLOAT," +
                "    productId INT," +
                "    discountPid INT," +
                "    discountPxy INT," +
                "    column_8 INT," +
                "    CONSTRAINT transactionDetail_product_productid_fk FOREIGN KEY (productId) REFERENCES product (product_id)," +
                "    CONSTRAINT transactionDetail_discountProduct_discountPid_fk FOREIGN KEY (discountPid) REFERENCES discountProduct (discountPid),"+
                "    CONSTRAINT transactionDetail_discountProductXY_productPxy_fk FOREIGN KEY (discountPxy) REFERENCES discountProductXY (discountpxyid)"+
                ");";
        String message = "Error TransactionDetailDAO initTable";
        generalDAO.executeSet(sql,message);
    }

    public void add(TransactionDetail transactionDetail){
        System.out.println(transactionDetail.getDiscount());
        System.out.println(transactionDetail.getPrice());
        System.out.println(transactionDetail.getQuantity());
        System.out.println(transactionDetail.getProduct().getProductId());
        System.out.println(transactionDetail.getTransaction().getTransactionId());
        String sql = "INSERT INTO transaction_detail (quantity, price, discount, product_id, transaction_id) "+
                "VALUES("+
                "   '"+transactionDetail.getQuantity()+"',"+
                "   '"+transactionDetail.getPrice()+"',"+
                "   '"+transactionDetail.getDiscount()+"',"+
                "   '"+transactionDetail.getProduct().getProductId()+"',"+
                "   '"+transactionDetail.getTransaction().getTransactionId()+"'"+
                ");";
        String message = "Error TransactionDetailDAO add";
        generalDAO.executeSet(sql,message);
    }
/*
    public List<TransactionDetail> getAllTransactionDetail() {
        List<TransactionDetail> transactionDetailList = new ArrayList<>();

        String sql = "select * from TransactionDetail";
        try {
            //c koneksi global dari extends myConn
            Statement state = c.createStatement();
            ResultSet rs = state.executeQuery(sql);
            if (rs != null) {
                while (rs.next()) {
                    TransactionDetail transactionDetail = new TransactionDetail();

                    transactionDetail.setDetailId(rs.getInt("detailId"));
                    transactionDetail.setQuantity(rs.getInt("quantity"));
                    transactionDetail.setPrice(rs.getDouble("price"));
                    transactionDetail.setDiscount(rs.getDouble("discount"));
                    transactionDetail.setProductId(rs.getInt("productId"));
                    transactionDetail.setTransactionId(rs.getInt("transactionId"));
                    transactionDetail.setDiscountPId(rs.getInt("discountPId"));
                    transactionDetail.setDiscountPxy(rs.getInt("discountPxy"));
                    transactionDetailList.add(transactionDetail);
                }
            }
            rs.close();
            state.close();
        } catch (Exception EX) {
            //System.out.println("Error TransactionDetailDAO getAllTransactionDetail");
            System.out.println(EX.toString());
        }
        return transactionDetailList;
    }
    */
}
