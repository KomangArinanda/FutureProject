package com.blibli.pos_minimarket.Controller;

import com.blibli.pos_minimarket.Model.Category;
import com.blibli.pos_minimarket.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "Category")
    public String showAllCategory(Model model) {
        categoryService.initTable();
        model.addAttribute("category", categoryService.showAll());
        model.addAttribute("category_nextId", categoryService.getNextId());
        return "Category";
    }

    @RequestMapping(value = "Category/Search")
    public String searchCategory(@ModelAttribute("searchKey")String searchKey,Model model){
        model.addAttribute("category", categoryService.search(searchKey));
        return "Category";
    }

    @PostMapping(value = "/Category/Add")
    public ModelAndView addCategory(@ModelAttribute("category") Category category){
        ModelAndView mav = new ModelAndView();
        category.setCategoryId(categoryService.getNextId());
        categoryService.add(category);
        mav.setViewName("redirect:/Category");
        return mav;
    }

    @PostMapping(value = "/Category/Update")
    public ModelAndView updateCategory(@ModelAttribute("category") Category category){
        ModelAndView mav = new ModelAndView();
        categoryService.update(category);
        mav.setViewName("redirect:/Category");
        return mav;
    }

    @RequestMapping(value = "/Category/Delete")
    public ModelAndView deleteCategory(@ModelAttribute("categoryId")Integer categoryId){
        ModelAndView mav = new ModelAndView();
        categoryService.softDelete(categoryId);
        mav.setViewName("redirect:/Category");
        return mav;
    }
}