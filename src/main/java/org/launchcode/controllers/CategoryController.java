package org.launchcode.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.Category;
import org.launchcode.models.Cheese;

import javax.validation.Valid;

@Controller
//request path: /category
@RequestMapping("category")

public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;


    @RequestMapping(value = "")
    public String index(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "My Categories");
        return "category/index";
    }


    public String processAddCheeseForm(
            @ModelAttribute  @Valid Cheese newCheese,
            Errors errors,
            @RequestParam int categoryId,
            Model model) {
        Category cat = categoryDao.findOne(categoryId);
        newCheese.setCategory(cat);
        return "category/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCategoryForm(Model model) {
        model.addAttribute("title", "Add Category");
        model.addAttribute(new Category());
//        Can't get the .values() method to work. This should be why it isn't displaying in category/add
//        model.addAttribute("categories", Category.values());
        return "category/add";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCategoryForm(@ModelAttribute  @Valid Category newCategory,
                                       Errors errors, Model model) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Category");
            return "category/add";
        }

        categoryDao.save(newCategory);
        return "redirect:";
    }

}