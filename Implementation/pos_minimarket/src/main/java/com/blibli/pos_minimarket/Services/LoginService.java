package com.blibli.pos_minimarket.Services;

import com.blibli.pos_minimarket.DataAccessObject.LoginDAO;
import com.blibli.pos_minimarket.Model.Pegawai;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {
    private LoginDAO loginDAO = new LoginDAO();

    public Map<String, Object> doLogin(Integer id, String password){
        Map <String,Object> parameter = new HashMap<>();
        Pegawai pegawai = loginDAO.getByIdAndPassword(id,password);
        if(pegawai == null){
            parameter.put("loginError",true);
            parameter.put("pegawai",null);
            return parameter;
        }
        else {
            parameter.put("loginError",false);
            parameter.put("pegawai",pegawai);
            return parameter;
        }
    }
}
