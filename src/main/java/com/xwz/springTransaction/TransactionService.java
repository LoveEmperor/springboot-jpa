package com.xwz.springTransaction;

import org.springframework.data.domain.Page;

import java.util.List;

public interface TransactionService {

    List<Acount> getUserList();
    Acount addUserInfo(Acount userInfo);
    Acount getUserByName(String name);
    Acount updateUserInfoById(Acount userInfo);
    void deleteUserInfoById(Long Id);
//    Acount updateUserInfoById(Acount userInfo);

    Page<Acount> findByCondition(AcountListVo vo);
}
