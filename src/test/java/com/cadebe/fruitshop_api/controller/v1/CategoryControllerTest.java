package com.cadebe.fruitshop_api.controller.v1;

import com.cadebe.fruitshop_api.api.v1.dto.CategoryDTO;
import com.cadebe.fruitshop_api.exception.ResourceNotFoundException;
import com.cadebe.fruitshop_api.exception.RestResponseEntityExceptionHandler;
import com.cadebe.fruitshop_api.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Tag("controller")
@DisplayName("Test CategoryController")
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest extends AbstractRestControllerTest {

    private static final UUID ID = UUID.randomUUID();
    private static final String NAME = "category1";

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        // Add exception handler here
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    @DisplayName("Test get all categories")
    void getAllCategories() throws Exception {
        List<CategoryDTO> categories = Arrays.asList(CategoryDTO.builder().build(), CategoryDTO.builder().build());

        when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get(CategoryController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).getAllCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test get category by id")
    void getCategoryById() throws Exception {
        when(categoryService.getCategoryById(any(UUID.class))).thenReturn(CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .build());

        mockMvc.perform(get(CategoryController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid", equalTo(ID.toString())));

        verify(categoryService, times(1)).getCategoryById(any(UUID.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test get category by id (not found)")
    void getCategoryByIdNotFound() throws Exception {
        when(categoryService.getCategoryById(any(UUID.class))).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(get(CategoryController.BASE_URL + "/a1a1a1a1-a1a1-a1a1-a1a1-a1a1a1a1a1a")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(categoryService, times(1)).getCategoryById(any(UUID.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test create new category")
    void createNewCategory() throws Exception {
        CategoryDTO category = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .categoryUrl(CategoryController.BASE_URL + "/" + ID)
                .build();

        CategoryDTO returnDTO = CategoryDTO.builder()
                .uuid(category.getUuid())
                .name(category.getName())
                .categoryUrl(category.getCategoryUrl())
                .build();

        when(categoryService.createNewCategory(category)).thenReturn(returnDTO);

        mockMvc.perform(post(CategoryController.BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.category_url", equalTo(CategoryController.BASE_URL + "/" + ID)));

        verify(categoryService, times(1)).createNewCategory(any(CategoryDTO.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test update existing category")
    void updateExistingCategory() throws Exception {
        CategoryDTO category = CategoryDTO.builder()
                .uuid(ID)
                .name(NAME)
                .build();

        CategoryDTO returnDTO = CategoryDTO.builder()
                .uuid(category.getUuid())
                .name(category.getName())
                .build();

        when(categoryService.updateExistingCategory(any(UUID.class), any(CategoryDTO.class))).thenReturn(returnDTO);

        mockMvc.perform(put(CategoryController.BASE_URL + "/" + ID)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));

        verify(categoryService, times(1)).updateExistingCategory(any(UUID.class), any(CategoryDTO.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test delete category by id")
    void deleteCategoryById() throws Exception {
        mockMvc.perform(delete(CategoryController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService).deleteCategoryById(any(UUID.class));
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    @DisplayName("Test delete category by id")
    void deleteAll() throws Exception {
        mockMvc.perform(delete(CategoryController.BASE_URL + "/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(categoryService).deleteAllCategories();
        verifyNoMoreInteractions(categoryService);
    }
}