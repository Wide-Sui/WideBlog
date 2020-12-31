package com.wide.controller.admin;

import com.github.pagehelper.PageInfo;
import com.wide.bean.Tag;
import com.wide.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired(required = false)
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, Model model) {
        List<Tag> tags = tagService.listTag(pageNum, pageSize);
        PageInfo<Tag> tagPageInfo = new PageInfo<>(tags);
        model.addAttribute("tags", tags);
        model.addAttribute("page", tagPageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String post(Tag tag, RedirectAttributes attributes, Model model) {
        if(tag.getName() == null)
        {
            model.addAttribute("RepeatError", "分类名不能为空！");
            return "admin/tags-input";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            model.addAttribute("RepeatError", "该分类已存在，不能添加重复！");
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(Tag tag, @PathVariable Long id, RedirectAttributes attributes, Model model) {
        if(tag.getName() == null)
        {
            model.addAttribute("RepeatError", "分类名不能为空！");
            return "admin/tags-input";
        }
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            model.addAttribute("RepeatError", "该分类已存在，不能添加重复！");
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}

