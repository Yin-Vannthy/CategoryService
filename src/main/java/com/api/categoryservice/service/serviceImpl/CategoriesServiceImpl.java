package com.api.categoryservice.service.serviceImpl;

import com.api.categoryservice.exception.CustomNotFoundException;
import com.api.categoryservice.model.dto.AppUserDTO;
import com.api.categoryservice.model.dto.CategoriesDTO;
import com.api.categoryservice.model.entity.Categories;
import com.api.categoryservice.model.request.CategoryRequest;
import com.api.categoryservice.repository.CategoriesRepository;
import com.api.categoryservice.response.ApiResponse;
import com.api.categoryservice.service.CategoriesService;
import lombok.AllArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoriesServiceImpl implements CategoriesService {
    private final CategoriesRepository categoriesRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<CategoriesDTO> getAllCategories() {
        return categoriesRepository
                .findAll()
                .stream()
                .map(this::setUserDetails)
                .collect(Collectors.toList());
    }

    @Override
    public CategoriesDTO getCategoryById(Integer categoryId) {
        Categories category = categoriesRepository.findById(categoryId).orElseThrow(
                () -> new CustomNotFoundException("No categories found with id: " + categoryId)
        );
        return setUserDetails(category);
    }

    @Override
    public Categories addNewCategory(CategoryRequest categoryRequest) {
        getUserDetails(categoryRequest.getUserId());

        Categories categories = Categories.builder()
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .userId(categoryRequest.getUserId())
                .build();

        return categoriesRepository.save(categories);
    }

    @Override
    public Categories updateCategoryById(Integer categoryId, CategoryRequest categoryRequest) {
        getUserDetails(categoryRequest.getUserId());

        Categories categories = Categories.builder()
                .categoryId(categoryId)
                .name(categoryRequest.getName())
                .description(categoryRequest.getDescription())
                .userId(categoryRequest.getUserId())
                .build();

        return categoriesRepository.save(categories);
    }

    @Override
    public Boolean deleteCategoryId(Integer categoryId) {
        getCategoryById(categoryId);
        categoriesRepository.deleteById(categoryId);
        return true;
    }

    private CategoriesDTO setUserDetails(Categories category) {
        AppUserDTO appUserDTO = getUserDetails(category.getUserId());

        return CategoriesDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .user(appUserDTO)
                .build();
    }

    private AppUserDTO getUserDetails(Integer userId) {
        try{
            return Objects.requireNonNull(
                    restTemplate.exchange(
                            "http://localhost:8080/api/v1/authentication/getUserById/" + userId,
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<ApiResponse<AppUserDTO>>() {}
                    ).getBody()
            ).getPayload();
        }catch (HttpClientErrorException.NotFound e){
            throw new CustomNotFoundException("No user found with id: " + userId);
        }
    }
}
