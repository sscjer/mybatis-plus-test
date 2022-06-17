package com.cj.modular;

import com.alibaba.fastjson.JSONObject;
import com.cj.modular.service.MoviesService;
import com.cj.modular.service.TAreaService;
import com.cj.modular.service.TUserService;
import com.cj.modular.service.TempService;
import com.cj.modular.utils.HttpUtils;
import com.cj.modular.utils.IDCardVerify;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;

@Slf4j
@SpringBootTest
class MybatisPlusTestApplicationTests {
    @Resource
    TAreaService tAreaService;
    @Autowired
    TUserService tUserService;
    @Autowired
    private MoviesService moviesService;
    @Autowired
    private TempService tempService;

    @Test
    void contextLoads() {
        JSONObject requestJson = new JSONObject();
        requestJson.put("param1", 30);
        requestJson.put("param2", "222");
        requestJson.put("param3", "3333");

        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("Content-Type", "application/json");
        headerMap.put("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzZXJ2aWNlVHlwZSI6MTEsInVzZXJfbmFtZSI6IjE5NTU1NTU1NTUyIiwicm9sZUlkIjoicmVnX2luaXRfcm9sZSIsImF1dGhvcml0aWVzIjpbIjI3Il0sIm9yZ0lkIjoiaW8wMDEiLCJjbGllbnRfaWQiOiJ0ZXN0X2NsaWVudCIsImFwcFJvbGVJZCI6bnVsbCwic2NvcGUiOlsicmVhZCJdLCJvcmdhbml6YXRpb24iOiIxOTU1NTU1NTU1MiIsIm5hbWUiOiIxOTU1NTU1NTU1MiIsImlkIjoiMTQ3MzQ3MTY0MDE4MzUxNzE4NSIsInN0YXRlIjoxLCJ1c2VyVHlwZSI6MSwicG93ZXJTdHJpbmciOm51bGwsImV4cCI6MTY0MjExNDM5NCwianRpIjoic0syZzl6TGtwNzZjZ0l3bHAyUTZiS0cycXdzIiwidXNlcm5hbWUiOiIxOTU1NTU1NTU1MiJ9.2ZKjEiIispYD0T2gEMbPAte2DQi4DtSK1CQ_Rl_My58");
        for (int i = 0; i < 10; i++) {
//            String s = HttpUtils.requestPayload("http://192.168.0.31:8843/api/dr-test6/cust/cust/selectAggregator",
//                    requestJson.toString(), headerMap);
            String s = HttpUtils.doPost("http://192.168.0.31:8843/api/dr-test6/cust/cust/selectAggregator",
                    new HashMap<>(), headerMap);
            System.out.println(s);
        }
//        for (int i = 0; i < 5; i++) {
//            String s = HttpUtil.get("https://movie.douban.com/j/new_search_subjects?sort=U&amp;range=0,10&amp;tags=&amp;start=" + i * 20);
//            Object[] objects = JSONUtil.parseArray(s).toArray();
//            DoubanResponse doubanResponse = JsonUtils.stringToJson(objects.toString(), DoubanResponse.class);
//            System.out.println(doubanResponse);
//            try {
//                TimeUnit.SECONDS.sleep(5);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    @Test
    void test() throws ParseException {
        for (int i = 0; i < 100; i++) {
            for (int j = 1; j < 10; j += 2) {
                String temp = i < 10 ? "0" + i : String.valueOf(i);
                System.out.println(IDCardVerify.getCheckCode("32080319930901" + temp + j));
            }
        }
    }

    @Test
    void test1() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Set<Integer> relation = new TreeSet<>();
        for (int i = 0; i < m; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            int c = scanner.nextInt();
            if (c == 0) {
                relation.add(a);
                relation.add(b);
            } else if (c == 1) {
                if (relation.contains(a) && relation.contains(b)) {
                    System.out.println("we are a team");
                } else {
                    System.out.println("we are not a team");
                }
            } else {
                System.out.println("da pian zi");
            }
        }
    }
}
