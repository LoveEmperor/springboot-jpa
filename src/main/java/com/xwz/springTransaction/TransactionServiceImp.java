package com.xwz.springTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.xwz.springTransaction.UserRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService{

    @Autowired
    private UserRepository userRepository;

    /**
     * 通过姓名获取用户信息
     * @param name 用户姓名
     * @return
     */
    public Acount getUserByName(String name) {
        return userRepository.findAcountByName(name).get(0);
    }

    public Page<Acount> findByCondition(AcountListVo vo) {
        Pageable pageable = new PageRequest(vo.getPage(), vo.getSize(), Sort.Direction.ASC, "id");

        Page<Acount> page = this.userRepository.findAll(getCondition(vo), pageable);

        return page;
    }

    private Specification<Acount> getCondition(AcountListVo vo){
        Specification<Acount> sp = new Specification<Acount>() {
            /**
             * @param root root参数是我们用来对应实体的信息的。
             * @param query
             * @param cb criteriaBuilder可以帮助我们制作查询信息。
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Acount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                // 创建 Predicate
                Predicate predicate = cb.conjunction();
                // 组装条件
                if(vo.getName().equals("")){
//                    predicate.add(cb.equal(root.get("phone"), vo.getPhone()));
                    predicate.getExpressions().add(cb.equal(root.get("name"), vo.getName()));
                }
                if(0 != vo.getMoney()){
                    predicate.getExpressions().add(cb.equal(root.get("money"), vo.getMoney()));
                }
                return predicate;
            }

        };
        return sp;
    }

    /**
     * 获取所有用户列表
     * @return
     */
    public List<Acount> getUserList(){
        List<Acount> userList=new ArrayList<Acount>();
        userList=userRepository.findAll();
        return  userList;
    }


    /**
     * 新增用户信息
     * @param userInfo 用户信息
     * @return
     */
    public Acount addUserInfo(Acount userInfo) {
        return userRepository.save(userInfo);
    }

    @Transactional
    public void transfers(String name,String name2,double money){

    }

    /**
     * 更新用户信息
     * @param userInfo 用户信息
     * @return
     */
    public Acount updateUserInfoById(Acount userInfo) {
        return userRepository.save(userInfo);
    }

    /**
     * 删除用户信息
     * @param id 主键Id
     */
    public void deleteUserInfoById(Long id) {
        userRepository.deleteById(id);
    }
    /**
     * 获取最新的用户
     * @return
     */
    public List<Acount> getCurrentUserList() {
        Sort sort=new Sort(Sort.Direction.DESC,"createTime");
        return userRepository.findAll(sort);

    }

}
