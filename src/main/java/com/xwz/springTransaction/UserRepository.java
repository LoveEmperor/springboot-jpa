package com.xwz.springTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UserRepository extends JpaRepository <Acount,Long>,JpaSpecificationExecutor<Acount> {
            List<Acount> findAcountByName(String name);
}