package in.timesinternet.foodbooking.service.impl;

import in.timesinternet.foodbooking.dto.request.CategoryDto;
import in.timesinternet.foodbooking.dto.request.CategoryUpdateDto;
import in.timesinternet.foodbooking.entity.Category;
import in.timesinternet.foodbooking.entity.Restaurant;
import in.timesinternet.foodbooking.exception.AlreadyExistException;
import in.timesinternet.foodbooking.exception.NotFoundException;
import in.timesinternet.foodbooking.exception.UnauthorizedException;
import in.timesinternet.foodbooking.repository.CategoryRepository;
import in.timesinternet.foodbooking.repository.RestaurantRepository;
import in.timesinternet.foodbooking.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Category createCategory(CategoryDto categoryDto, Integer restaurantId) {

        if (categoryRepository.existsByNameAndRestaurantId(categoryDto.getName(), restaurantId))
            throw new AlreadyExistException("category with this name already exits");


        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            Category category = new Category();
            category.setName(categoryDto.getName());
            restaurant.addCategory(category);

            return categoryRepository.save(category);


        } else
            throw new NotFoundException("restaurant not found with id " + restaurantId);
    }


    @Override
    public List<Category> getAllCategory(Integer restaurantId) {

        List<Category> allCategory = categoryRepository.findAllByRestaurantId(restaurantId);
        return allCategory;
    }

    @Override
    public Category deleteCategory(Integer categoryId, Integer restaurantId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);


        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            if ((category.getRestaurant()).getId() == restaurantId) {
                categoryRepository.deleteById(categoryId);
                return categoryOptional.get();
            } else
                throw new UnauthorizedException("unauthorised access for deleting category");
        } else
            throw new NotFoundException("category not found ");
    }

    @Override
    public Category updateCategory(CategoryUpdateDto categoryUpdateDto, Integer restaurantId) {

        Optional<Category> categoryOptional = categoryRepository.findById(categoryUpdateDto.getId());

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();

            if (category.getRestaurant().getId() == restaurantId) {

                if (categoryUpdateDto.getName() != null)
                    category.setName(categoryUpdateDto.getName());

                if (categoryUpdateDto.getIsAvailable() != null)
                    category.setIsAvailable(categoryUpdateDto.getIsAvailable());

                categoryRepository.save(category);
                return category;
            } else
                throw new UnauthorizedException("authorised access for updating category");
        } else
            throw new NotFoundException("category not found");
    }

    @Override
    public Category getCategory(Integer categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
        if (categoryOptional.isPresent())
            return categoryOptional.get();
        else
            throw new NotFoundException("category not found with id " + categoryId);
    }
}
