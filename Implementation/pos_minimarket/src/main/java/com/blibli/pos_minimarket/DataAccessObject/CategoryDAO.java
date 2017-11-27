package com.blibli.pos_minimarket.DataAccessObject;

import com.blibli.pos_minimarket.Model.Category;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDAO extends ConnectionSettings implements InterfaceDAO<Category,Integer,String>{
    private GeneralDAO generalDAO = new GeneralDAO();

    public CategoryDAO() {
    }

    @Override
    public void initTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.category" +
                "(" +
                "    category_id SERIAL PRIMARY KEY NOT NULL," +
                "    name VARCHAR(25) NOT NULL," +
                "    description VARCHAR(250)," +
                "    status VARCHAR(15) NOT NULL" +
                ");";
        String message = "Error CategoryDAO initTable";

        generalDAO.initTable(sql,message);
    }

    @Override
    public List<Category> getAll() {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM category ORDER BY category_id;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Category category = new Category();
                    category.setCategoryId(resultSet.getInt("category_Id"));
                    category.setName(resultSet.getString("name"));
                    category.setDescription(resultSet.getString("description"));
                    category.setStatus(resultSet.getString("status"));
                    categoryList.add(category);
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO getAll");
            System.out.println(EX.toString());
        }
        return categoryList;
    }

    @Override
    public Category getById(Integer categoryId) {
        Category category = new Category();
        String sql = "SELECT * FROM category WHERE category_id = ? ORDER BY category_id;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    category.setCategoryId(resultSet.getInt("category_Id"));
                    category.setName(resultSet.getString("name"));
                    category.setDescription(resultSet.getString("description"));
                    category.setStatus(resultSet.getString("status"));
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO getById");
            System.out.println(EX.toString());
        }
        return category;
    }

    @Override
    public List<Category> search(String searchKey) {
        List<Category> categoryList = new ArrayList<>();
        String sql = "SELECT category_id,name,description,status FROM category WHERE name = ? ORDER BY category_id;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1,searchKey);
            ResultSet resultSet = preparedStatement.executeQuery();
            this.closeConnection();
            if (resultSet != null) {
                while (resultSet.next()) {
                    Category category = new Category();
                    category.setCategoryId(resultSet.getInt("category_Id"));
                    category.setName(resultSet.getString("name"));
                    category.setStatus(resultSet.getString("status"));
                    category.setDescription(resultSet.getString("description"));
                    categoryList.add(category);
                }
                resultSet.close();
            }
            preparedStatement.close();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO search");
            System.out.println(EX.toString());
        }
        return categoryList;
    }

    @Override
    public void add(Category category) {

        String sql = "INSERT INTO category (name,description,status) VALUES (?,?,?);";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setString(3, "active");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO Add");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void update(Category category) {
        String sql = "UPDATE category SET name = ?, description = ? WHERE category_id = ?;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setString(1,category.getName());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.setInt(3,category.getCategoryId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO Update");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void delete(Integer categoryId) {
        String sql="DELETE FROM category WHERE category_id = ? ;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(1,categoryId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO Delete");
            System.out.println(EX.toString());
        }
    }

    @Override
    public void softDelete(Integer categoryId) {
        String sql = "UPDATE category SET status = ? WHERE category_id = ?;";
        try {
            this.makeConnection();
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
            preparedStatement.setInt(2,categoryId);
            preparedStatement.setString(1,"not active");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            this.closeConnection();
        } catch (Exception EX) {
            System.out.println("Error CategoryDAO softDelete");
            System.out.println(EX.toString());
        }
    }
}
