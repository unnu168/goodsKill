package org.seckill.web.controller;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.seckill.api.service.RoleService;
import org.seckill.api.service.UserAccountService;
import org.seckill.entity.Role;
import org.seckill.entity.RoleExample;
import org.seckill.entity.User;
import org.seckill.entity.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/seckill/admin")
@Slf4j
public class AdminController {
    @Autowired
    UserAccountService userAccountService;
    @Autowired
    RoleService roleService;

    @RequestMapping("/user")
    public String user(Model model, @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                       @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        PageInfo<User> pageInfo = userAccountService.selectByPage(new UserExample(), offset, limit);
        long totalNum = pageInfo.getTotal();
        long pageNum = (totalNum % limit == 0) ? totalNum / limit : totalNum / limit + 1;
        model.addAttribute("list", pageInfo.getList());
        model.addAttribute("totalNum", totalNum);
        model.addAttribute("pageNum", pageNum);
        return "admin/user";
    }

    @RequestMapping("/role")
    @ResponseBody
    public List<Role> role(Model model, @RequestParam(name = "offset", required = false, defaultValue = "0") int offset,
                           @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        PageInfo<Role> pageInfo = roleService.selectByPage(new RoleExample(), offset, limit);
        return pageInfo.getList();
    }
}
