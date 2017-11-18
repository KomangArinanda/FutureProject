package com.blibli.pos_minimarket.DataAccessObject;

import com.blibli.pos_minimarket.Model.Category;
import com.blibli.pos_minimarket.Model.Product;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDAO extends ConnectionSettings implements InterfaceDAO<Product,Integer, String> {
    private CategoryDAO categoryDAO = new CategoryDAO();
    private Product product = new Product();
    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM product ORDER BY productId";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("productId"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(categoryDAO.getById(resultSet.getInt("categoryId")));
                    product.setStatus(resultSet.getString("status"));
                    productList.add(product);
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO getAllProduct");
            System.out.println(EX.toString());
        }
        return productList;
    }

    @Override
    public Product getById(Integer productId) {
        String sql = "SELECT * FROM product WHERE productid = ? ORDER BY productid";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    product.setProductId(resultSet.getInt("productId"));
                    product.setName(resultSet.getString("name"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(categoryDAO.getById(resultSet.getInt("categoryId")));
                    product.setStatus(resultSet.getString("status"));
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO getById");
            System.out.println(EX.toString());
        }
        return product;
    }

    @Override
    public List<Product> search(String searchKey) {
        List<Product> productList = new ArrayList<>();
        String sql = "SELECT * FROM product WHERE name = ? ORDER BY productid;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, searchKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Product product = new Product();
                    product.setProductId(resultSet.getInt("productId"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setName(resultSet.getString("name"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setDescription(resultSet.getString("description"));
                    product.setCategory(categoryDAO.getById(resultSet.getInt("categoryId")));
                    product.setStatus(resultSet.getString("status"));
                    productList.add(product);
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO search");
            System.out.println(EX.toString());
        }
        return productList;
    }

    @Override
    public void add(Product product) {
        Category category = categoryDAO.getByName(product.getName());
        String sql = "INSERT INTO product (name,price,quantity,description,categoryid,status) VALUES (?,?,?,?,?,?);";
        try {
            System.out.println("E5");
            this.makeConnection();
            System.out.println("E6");
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            System.out.println("E7");
            preparedStatement.setString(1, product.getName());
            System.out.println("E8");
            preparedStatement.setDouble(2, product.getPrice());
            System.out.println("E9");
            preparedStatement.setInt(3, 0);
            System.out.println("E10");
            preparedStatement.setString(4, product.getDescription());
            System.out.println("E1 = "+ category.getCategoryId());
            preparedStatement.setInt(5, category.getCategoryId());
            System.out.println("E2");
            preparedStatement.setString(6, "active");
            System.out.println("E3");
            preparedStatement.executeUpdate();
            System.out.println("E4");
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO Add");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void update(Product product) {
        Category category = categoryDAO.getByName((product.getName()));
        String sql = "UPDATE product SET name = ?, price = ?, quantity = ?, description = ?, categoryid = ? WHERE productid = ?;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, product.getName());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, product.getQuantity());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, category.getCategoryId());
            preparedStatement.setInt(6, product.getProductId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO Update");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void delete(Integer productId) {
        String sql = "DELETE FROM product WHERE productid = ? ;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, productId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO Delete");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void softDelete(Integer productId) {
        String sql = "UPDATE product SET status = ? WHERE productid = ?;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, "not active");
            preparedStatement.setInt(2, productId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO softDelete");
            System.out.println(EX.toString());
        }
    }

    public void updateQuantity(Product product) {
        product = this.getById(product.getProductId());
        String sql = "UPDATE product SET quantity = ? WHERE productid = ?;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1,product.getQuantity());
            preparedStatement.setInt(2,product.getProductId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error ProductDAO UpdateQuantity");
            System.out.println(EX.toString());
        }
    }
}