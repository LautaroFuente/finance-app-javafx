package com.finance_app.finance_app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.finance_app.finance_app.entities.Category;
import com.finance_app.finance_app.repository.CategoryRepository;

@Component
public class CategoryDataInitializer implements ApplicationRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (categoryRepository.count() == 0) {
            categoryRepository.save(new Category("Alimentos"));
            categoryRepository.save(new Category("Transporte"));
            categoryRepository.save(new Category("Salud"));
            categoryRepository.save(new Category("Entretenimiento"));
            categoryRepository.save(new Category("Otros"));
        }
    }
}
