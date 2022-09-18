package com.cj.modular.controller;


import com.cj.modular.entity.TArea;
import com.cj.modular.service.TAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * <p>
 * 地区码表 前端控制器
 * </p>
 *
 * @author caoj
 * @since 2021-08-30
 */
@RestController
@RequestMapping("/modular/t-area/")
public class TAreaController {
    @Autowired
    private TAreaService tAreaService;

    @GetMapping("getTree")
    public TArea getTree() {
        List<TArea> list = tAreaService.list().stream().sorted(Comparator.comparingInt(o -> Integer.parseInt(o.getId()))).collect(Collectors.toList());
        Map<Integer, TArea> map = new ConcurrentHashMap<>();
        map.put(-1, new TArea().setAreaName("中国").setLevel(0).setId("-1"));
        list.forEach(tArea -> map.put(Integer.parseInt(tArea.getId()), tArea));
        list.forEach(tArea -> {
            if (map.get(tArea.getParentId()).getChild() == null) {
                map.get(tArea.getParentId()).setChild(new ArrayList<>());
            }
            map.get(tArea.getParentId()).getChild().add(tArea);
        });
        return map.get(-1);
    }

}

