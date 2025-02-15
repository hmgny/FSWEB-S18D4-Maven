package com.workintech.s18d1.controller;

import com.workintech.s18d1.dao.BurgerDao;
import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.util.BurgerValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/burger")
public class BurgerController {
    private final BurgerDao burgerDao;

    @Autowired
    public BurgerController(BurgerDao burgerDao){
        this.burgerDao = burgerDao;
    }

    @GetMapping
    public List<Burger> getAllBurger(){
        return burgerDao.findAll();
    }

    @GetMapping("/{id}")
    public Burger getIdBurger(@PathVariable Long id){
        return burgerDao.findById(id);
    }

    @PostMapping
    public Burger saveBurger(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.save(burger);
    }

    @PutMapping
    public Burger getUpdateBurger(@RequestBody Burger burger){
        BurgerValidation.checkName(burger.getName());
        return burgerDao.update(burger);
    }

    @DeleteMapping("/{id}")
    public Burger getDeleteBurger(@PathVariable long id){
        return burgerDao.remove(id);
    }

    @GetMapping("/price/{price}")
    public List<Burger> getFindByPrice(@PathVariable("price") Integer price){
        return burgerDao.findByPrice(price);
    }
    @GetMapping("/breadType/{breadType}")
    public List<Burger> getFindByBreadType(@PathVariable("breadType") String breadType){
        BreadType btEnum = BreadType.valueOf(breadType);
        return burgerDao.findByBreadType(btEnum);
    }
    @GetMapping("/content/{content}")
    public List<Burger> getFindByContent(@PathVariable("content") String contents){
        return burgerDao.findByContent(contents);
    }

}
