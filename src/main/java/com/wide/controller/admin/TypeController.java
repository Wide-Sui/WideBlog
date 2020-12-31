package com.wide.controller.admin;

import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.wide.bean.Type;
import com.wide.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired(required = false)
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        List<Type> types = typeService.listType(pageNum, pageSize);
        PageInfo<Type> typePageInfo = new PageInfo<>(types);
        model.addAttribute("types", types);
        model.addAttribute("page", typePageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    @PostMapping("/types")
    public String post(Type type, RedirectAttributes attributes, Model model) {
        if(type.getName() == null)
        {
            model.addAttribute("RepeatError", "分类名不能为空！");
            return "admin/types-input";
        }
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            model.addAttribute("RepeatError", "该分类已存在，不能添加重复！");
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editPost(Type type, @PathVariable Long id, RedirectAttributes attributes, Model model) {
        if(type.getName() == null)
        {
            model.addAttribute("RepeatError", "分类名不能为空！");
            return "admin/types-input";
        }
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            model.addAttribute("RepeatError", "该分类已存在，不能添加重复！");
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/types";
    }
}
