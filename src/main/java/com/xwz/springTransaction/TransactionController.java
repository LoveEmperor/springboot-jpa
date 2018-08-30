package com.xwz.springTransaction;

import jdk.nashorn.internal.ir.RuntimeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TransactionController {


    @Autowired
    private Acount acount;

    @Resource
    private TransactionService transactionService;

    @GetMapping(value = "/getUserInfo")
    public Acount getUserInfoByName(@RequestParam("name") String name) {
        return transactionService.getUserByName(name);
    }

    @PostMapping("/getAccountPageList")
    public Page<Acount> getAllInvoiceInfos(AcountListVo vo) throws Exception{

        return transactionService.findByCondition(vo);
    }

    /**
     * 获取所有用户
     * @return
     */
    @GetMapping(value = "/getUserList")
    public List<Acount> getUserList() {
        return transactionService.getUserList();
    }

    //transfer

    @Transactional
    @ResponseBody
    @RequestMapping("/transfer")
    public void transfer(String name,String name2,double money)throws Exception  {

       Acount acount1 = transactionService.getUserByName(name);
       Acount acount2 = transactionService.getUserByName(name2);
       acount1.setMoney(acount1.getMoney()-money);
       acount2.setMoney(acount2.getMoney()+money);

        transactionService.updateUserInfoById(acount1);
//        int i = 1 / 0;
        transactionService.updateUserInfoById(acount2);

    }


    @PostMapping(value ="/updateUserInfo")
    public Acount updateUserInfo(Acount userInfo){
        return transactionService.updateUserInfoById(userInfo);
    }

    @PutMapping(value = "/addUserInfo")
    public Acount addUserInfo(Acount userInfo) {
        return transactionService.addUserInfo(userInfo);
    }

    @PostMapping(value="/deleteUserInfo")
    public void deleteUserInfo(@RequestParam("id") Long id){
        transactionService.deleteUserInfoById(id);
    }

//    @PostMapping(value ="/updateUserInfo")
//    public Acount updateUserInfo(Acount userInfo){
//        return transactionService.updateUserInfoById(userInfo);
//    }


}
