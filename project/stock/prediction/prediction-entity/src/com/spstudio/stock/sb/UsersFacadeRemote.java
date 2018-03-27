/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spstudio.stock.sb;

import com.spstudio.stock.entity.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author sp
 */
@Remote
public interface UsersFacadeRemote {

    void create(Users users);

    void edit(Users users);

    void remove(Users users);

    Users find(Object id);

    Users findByLoginId(String loginId);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();

}
