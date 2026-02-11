package com.mirkamolcode.service;

import com.mirkamolcode.dto.NewProductRequest;
import com.mirkamolcode.dto.ProductResponse;
import com.mirkamolcode.dto.UpdateProductRequest;
import com.mirkamolcode.exception.ResourceNotFound;
import com.mirkamolcode.model.Product;
import com.mirkamolcode.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService underTest;
    @Captor
    private ArgumentCaptor<Product> productArgumentCaptor;

    @Test
    void shouldReturnAllProducts() {
        // given
        List<Product> allProducts = productRepository.findAll();
        // when
        List<ProductResponse> allProducts1 = underTest.getAllProducts();
        // then
        assertThat(allProducts).hasSize(allProducts1.size());
    }

    @Test
    void shouldReturnProductById() {
        // given
        UUID productId = UUID.randomUUID();

        Optional<Product> product = Optional.of(
                new Product(
                        productId,
                        "name",
                        "description",
                        BigDecimal.TEN,
                        "/product",
                        10,
                        Instant.now(),
                        null,
                        null,
                        true
                )
        );
        given(productRepository.findById(productId)).willReturn(product);
        // when
        ProductResponse productById = underTest.getProductById(productId);
        // then
        assertThat(product).isNotEmpty();
    }

    @Test
    void shouldThrowWhenProductIsNotFound() {
        // given
        UUID productId = UUID.randomUUID();

        given(productRepository.findById(productId)).willReturn(Optional.empty());
        // when
        assertThatThrownBy(() -> underTest.getProductById(productId))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("product with id [" + productId + "] not found");
    }

    @Test
    void shouldThrowWhenProductDoesNotExistToDelete() {
        // given
        UUID productId = UUID.randomUUID();

        given(productRepository.existsById(productId)).willReturn(false);
        // then
        assertThatThrownBy(() -> underTest.deleteProductById(productId))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("product with id [" + productId + "] not found");
    }

    @Test
    void shouldDeleteProductProductById() {
        // given
        UUID productId = UUID.randomUUID();
        given(productRepository.existsById(productId)).willReturn(true);

        // when
        underTest.deleteProductById(productId);
        // then
        verify(productRepository).deleteById(productId);
        assertThat(productRepository.findById(productId)).isEmpty();
    }

    @Test
    void shouldSaveNewProductProduct() {
        // given
        NewProductRequest newProductRequest = new NewProductRequest(
                "Jamila",
                "short description",
                BigDecimal.TEN,
                "/jamilaProduct", 10);


        // when
        underTest.saveNewProduct(newProductRequest);
        verify(productRepository).save(productArgumentCaptor.capture());
        Product product = productArgumentCaptor.getValue();

        // then
        assertThat(product.getId()).isNotNull();
        assertThat(product.getName()).isEqualTo(newProductRequest.name());
        assertThat(product.getDescription()).isEqualTo(newProductRequest.description());
        assertThat(product.getPrice()).isEqualTo(newProductRequest.price());
        assertThat(product.getStockLevel()).isEqualTo(newProductRequest.stockLevel());
        assertThat(product.getImageUrl()).isEqualTo(newProductRequest.imageUrl());
    }

    @Test
    void shouldThrowWhenProductIdDoesNotExistToUpdate() {
        // given
        UUID productId = UUID.randomUUID();
        given(productRepository.findById(productId)).willReturn(Optional.empty());
        // then
        assertThatThrownBy(() -> underTest.updateProduct(productId, any()))
                .isInstanceOf(ResourceNotFound.class)
                .hasMessageContaining("product with id [" + productId + "] not found");
    }

    @Test
    void shouldUpdateProduct() {
        // given
        UUID productId = UUID.randomUUID();
        Product foundProduct = new Product(productId,
                "Alica",
                "long description",
                BigDecimal.ONE, "/alicaProduct",
                1,
                Instant.now(),
                null,
                null,
                false);
        given(productRepository.findById(productId)).willReturn(Optional.of(foundProduct));
        UpdateProductRequest updateProductRequest = new UpdateProductRequest(
                "Jamila",
                "short description",
                BigDecimal.TEN,
                "/jamilaProduct",
                10,
                true
        );
        // when
        underTest.updateProduct(productId, updateProductRequest);
        verify(productRepository).save(productArgumentCaptor.capture());
        Product product = productArgumentCaptor.getValue();
        // then
        assertThat(updateProductRequest.name()).isEqualTo(product.getName());
        assertThat(updateProductRequest.description()).isEqualTo(product.getDescription());
        assertThat(updateProductRequest.price()).isEqualTo(product.getPrice());
        assertThat(updateProductRequest.imageUrl()).isEqualTo(product.getImageUrl());
        assertThat(updateProductRequest.stockLevel()).isEqualTo(product.getStockLevel());
        assertThat(updateProductRequest.isPublished()).isEqualTo(product.getPublished());
        assertThat(product.getUpdatedAt()).isNotNull();
        assertThat(product.getCreatedAt()).isNotNull();
    }
}