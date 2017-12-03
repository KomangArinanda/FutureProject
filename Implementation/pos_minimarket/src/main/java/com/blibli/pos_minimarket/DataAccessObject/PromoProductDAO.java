package com.blibli.pos_minimarket.DataAccessObject;

import com.blibli.pos_minimarket.Model.Promo;
import com.blibli.pos_minimarket.Model.PromoProduct;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PromoProductDAO extends ConnectionSettings implements InterfaceDAO <PromoProduct, Integer, String > {
    private GeneralDAO generalDAO = new GeneralDAO();

    @Override
    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.promo_product_discount" +
                "(" +
                "    p_discount_id SERIAL PRIMARY KEY NOT NULL," +
                "    discount_persen DOUBLE PRECISION NOT NULL," +
                "    start_date TIMESTAMP NOT NULL," +
                "    start_date TIMESTAMP NOT NULL," +
                "    product_id INTEGER NOT NULL," +
                "    CONSTRAINT product_id___fk FOREIGN KEY (product_id) REFERENCES product (product_id)" +
                ");";
        String message = "Error PromoProductDAO initTable";
        generalDAO.executeSet(sql, message);
    }

    @Override
    public List<PromoProduct> getAll() {
        List<PromoProduct> promoList = new ArrayList<>();
        String sql = "SELECT * FROM promo_product_discount ORDER BY p_discount_id;";
        String message = "Error PromoProductDAO getAll";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                while (resultSet.next()) {
                    PromoProduct promoProduct = new PromoProduct();
                    promoProduct.setId(resultSet.getInt("p_discount_id"));
                    promoProduct.setDiscountPercent(resultSet.getDouble("discount_persen"));
                    promoProduct.setStartDate(resultSet.getTimestamp("start_date"));
                    promoProduct.setEndDate(resultSet.getTimestamp("end_date"));
                    promoProduct.setProductId(resultSet.getInt("product_id"));
                    promoProduct.setStatus(resultSet.getString("status"));
                    promoList.add(promoProduct);
                }
                resultSet.close();
            }
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println(message);
            System.out.println(EX.toString());
        }
        return promoList;
    }

    @Override
    public PromoProduct getById(Integer key) {
        return null;
    }

    @Override
    public List<PromoProduct> search(String key) {
        return null;
    }

    @Override
    public void add(PromoProduct promoProduct) {
        String sql = "INSERT INTO promo_product_discount(p_discount_id, discount_persen, start_date, end_date, product_id, status)VALUES " +
                "("+promoProduct.getId()+","+
                "("+promoProduct.getDiscountPercent()+","+
                "("+promoProduct.getStartDate()+","+
                "("+promoProduct.getEndDate()+","+
                "("+promoProduct.getProductId()+","+
                "("+promoProduct.getStatus()+","+
                ");";
        String message = "Error PromoProduct Add";
        generalDAO.executeSet(sql,message);
    }

    @Override
    public void update(PromoProduct t) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void softDelete(Integer integer) {

    }
}