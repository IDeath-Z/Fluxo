package com.fluxo.api_fluxo.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fluxo.api_fluxo.api.dto.product.CategoryResponseDTO;
import com.fluxo.api_fluxo.api.dto.product.FetchProductsPageDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductPatchDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductRequestDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductResponseDTO;
import com.fluxo.api_fluxo.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Operações relacionadas a gestão de produtos")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar novo produto", description = "Cria um novo produto no sistema")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Dados inválidos na requisição")
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO product) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.addProduct(product));
    }

    @PatchMapping("/atualizar/{productId}")
    @Operation(summary = "Atualizar produto", description = "Atualiza parcialmente os dados de um produto")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<?> patchProduct(
            @Parameter(description = "ID do produto") @PathVariable("productId") Integer id,
            @RequestBody ProductPatchDTO productPatch) {

        try {
            return ResponseEntity.ok(productService.patchProduct(id, productPatch));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/categorias")
    @Operation(summary = "Listar categorias", description = "Retorna todas as categorias cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {

        return ResponseEntity.ok(productService.fetchAllCategories());
    }

    @GetMapping("/consulta/{productId}")
    @Operation(summary = "Consultar produto", description = "Obtém os detalhes completos de um produto")
    @ApiResponse(responseCode = "200", description = "Produto encontrado")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<?> getProduct(
            @Parameter(description = "ID do produto") @PathVariable("productId") Integer id) {

        try {
            return ResponseEntity.ok(productService.fetchProduct(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/todos")
    @Operation(summary = "Listar produtos", description = "Retorna uma lista paginada de todos os produtos")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    public ResponseEntity<FetchProductsPageDTO> getAllProducts(
            @Parameter(description = "Número da página (base 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página (padrão 10)") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filtro de pesquisa (opcional)") @RequestParam(required = false) String search) {

        return ResponseEntity.ok(productService.fetchAllProducts(page, size, search));
    }

    @DeleteMapping("/apagar/{productId}")
    @Operation(summary = "Excluir produto", description = "Remove permanentemente um produto do sistema")
    @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<?> deleteProduct(
            @Parameter(description = "ID do produto") @PathVariable("productId") Integer id) {

        try {
            productService.removeProduct(id);
            return ResponseEntity.ok("Produto deletado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
