package com.sqs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 * @Auther: sqs
 * @Date: 2019/11/20 21:10
 * @Description:
 * @@Version: 1.0
 */
@RestController
public class UserController {

    @RequestMapping("/sendCode")
    public String sendCode(String phone){

        if(phone==null){
            return "error";
        }

        Jedis jedis = new Jedis("192.168.230.128",6379);

        String num = jedis.get("num"+phone);

        if(jedis.exists(jedis.get("num"+phone))){
            jedis.decr("num"+phone);
            if(jedis.get("num"+phone).equals("1")){
                return "outTime";
            }
        } else {
            jedis.setex("num"+phone,3600*24,"3");
        }

        String phoneKey = "phoneNum:"+phone;

        String code = getCode(4);

        jedis.setex(phoneKey,10,code);

        jedis.close();

        System.out.println(code);

        return "success";
    }

    private String getCode(int codeLength){
        String code = "";
        for (int i = 0; i < codeLength; i++) {
            int num = new Random().nextInt(10);
            code += num;
        }
        return code;
    }

    @RequestMapping("/verifiCode")
    public String verifiCode(String phone,String verify_code){

        if(verify_code==null){
            return "error";
        }

        Jedis jedis = new Jedis("192.168.230.128",6379);

        String phoneValue = jedis.get("phoneNum:" + phone);

        System.out.println(phoneValue);

        if(verify_code.equals(phoneValue)){
            return "success";
        }

        jedis.close();

        return "error";

    }



}
