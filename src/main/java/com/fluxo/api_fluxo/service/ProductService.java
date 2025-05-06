package com.fluxo.api_fluxo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fluxo.api_fluxo.api.dto.product.CategoryResponseDTO;
import com.fluxo.api_fluxo.api.dto.product.FetchProductsPageDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductPatchDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductRequestDTO;
import com.fluxo.api_fluxo.api.dto.product.ProductResponseDTO;
import com.fluxo.api_fluxo.api.dto.product.component.FetchProductsDTO;
import com.fluxo.api_fluxo.api.dto.product.component.PriceInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.ProductInfoDTO;
import com.fluxo.api_fluxo.api.dto.product.component.TechnicalInfoDTO;
import com.fluxo.api_fluxo.domain.lot.LotInfo;
import com.fluxo.api_fluxo.domain.product.PriceInfo;
import com.fluxo.api_fluxo.domain.product.ProductInfo;
import com.fluxo.api_fluxo.domain.product.TechnicalInfo;
import com.fluxo.api_fluxo.repositories.lot.LotInfoRepository;
import com.fluxo.api_fluxo.repositories.product.PriceInfoRepository;
import com.fluxo.api_fluxo.repositories.product.ProductInfoRepository;
import com.fluxo.api_fluxo.repositories.product.TechnicalInfoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

        @Autowired
        private ProductInfoRepository productInfoRepository;

        @Autowired
        private PriceInfoRepository priceInfoRepository;

        @Autowired
        private TechnicalInfoRepository technicalInfoRepository;

        @Autowired
        private LotInfoRepository lotInfoRepository;

        @Autowired
        private LotService lotOperationsService;

        // ---- Método para adicionar um novo produto ---- //
        public ProductResponseDTO addProduct(ProductRequestDTO request) {
                ProductInfo product = saveProductInfo(request);
                PriceInfo price = savePriceInfo(product, request);
                TechnicalInfo tech = saveTechnicalInfo(product, request);

                return buildProductResponse(
                                product,
                                price,
                                tech,
                                null);
        }
        // ---- Método para adicionar um novo produto ---- //

        // ---- Método para atualizar um produto existente ---- //
        public ProductResponseDTO patchProduct(Integer productId, ProductPatchDTO productPatch) {

                ProductInfo product = productInfoRepository.findById(productId)
                                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado"));

                if (productPatch.productInfo().productName() != null) {

                        product.setProductName(productPatch.productInfo().productName());
                }
                if (productPatch.productInfo().productDescription() != null) {

                        product.setProductDescription(productPatch.productInfo().productDescription());
                }
                if (productPatch.productInfo().productSKU() != null) {

                        product.setProductSKU(productPatch.productInfo().productSKU());
                }
                if (productPatch.productInfo().productCategory() != null) {

                        product.setProductCategory(productPatch.productInfo().productCategory());
                }
                if (productPatch.productInfo().productBrand() != null) {

                        product.setProductBrand(productPatch.productInfo().productBrand());
                }
                if (productPatch.productInfo().productModel() != null) {

                        product.setProductModel(productPatch.productInfo().productModel());
                }

                ProductInfo updatedProduct = productInfoRepository.save(product);

                if (productPatch.priceInfo() != null) {

                        PriceInfo priceInfo = priceInfoRepository.findByProductId(productId)
                                        .orElseThrow(() -> new EntityNotFoundException("Preço não encontrado"));

                        if (productPatch.priceInfo().productPrice() != null) {

                                priceInfo.setProductPrice(productPatch.priceInfo().productPrice());
                        }
                        if (productPatch.priceInfo().promotionalPrice() != null) {

                                priceInfo.setPromotionalPrice(productPatch.priceInfo().promotionalPrice());
                        }

                        priceInfoRepository.save(priceInfo);
                }

                if (productPatch.technicalInfo() != null) {

                        TechnicalInfo technicalInfo = technicalInfoRepository.findByProductId(productId).orElseThrow(
                                        () -> new EntityNotFoundException("INformações técnicas não encontradas"));

                        if (productPatch.technicalInfo().productWeight() != null) {

                                technicalInfo.setProductWeight(productPatch.technicalInfo().productWeight());
                        }
                        if (productPatch.technicalInfo().productLength() != null) {

                                technicalInfo.setProductLength(productPatch.technicalInfo().productLength());
                        }
                        if (productPatch.technicalInfo().productWidth() != null) {

                                technicalInfo.setProductWidth(productPatch.technicalInfo().productWidth());
                        }
                        if (productPatch.technicalInfo().productHeight() != null) {

                                technicalInfo.setProductHeight(productPatch.technicalInfo().productHeight());
                        }

                        technicalInfoRepository.save(technicalInfo);
                }

                return buildProductResponse(updatedProduct,
                                priceInfoRepository.findByProductId(productId).orElseThrow(),
                                technicalInfoRepository.findByProductId(productId).orElseThrow(),
                                lotInfoRepository.findByProductId(productId));
        }
        // ---- Método para atualizar um produto existente ---- //

        // ---- Método para retornar todas as categorias cadastradas ---- //
        public List<CategoryResponseDTO> fetchAllCategories() {

                List<String> allCategories = productInfoRepository.findAllCategories();

                return allCategories.stream()
                .map(category -> new CategoryResponseDTO(category))
                .collect(Collectors.toList());
        }
        // ---- Método para retornar todas as categorias cadastradas ---- //

        // ---- Método para retornar todos os dados do produto pelo id---- //
        public ProductResponseDTO fetchProduct(Integer productId) {

                ProductInfo product = productInfoRepository.findById(productId)
                                .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado: " + productId));

                PriceInfo price = priceInfoRepository.findByProductId(productId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Preço não encontrado para produto: " + productId));

                TechnicalInfo tech = technicalInfoRepository.findByProductId(productId)
                                .orElseThrow(() -> new EntityNotFoundException(
                                                "Dados técnicos não encontrados: " + productId));

                List<LotInfo> lots = lotInfoRepository.findByProductId(productId);

                return buildProductResponse(product, price, tech, lots);
        }
        // ---- Método para retornar todos os dados do produto pelo id---- //

        // ---- Método para retornar uma lista paginada de produtos ---- //
        public FetchProductsPageDTO fetchAllProducts(int page, int size, String search) {
                Pageable pageable = PageRequest.of(page, size);
                Page<ProductInfo> productPage;

                if (search != null && !search.isEmpty()) {
                        productPage = productInfoRepository
                                        .findByProductNameContainingIgnoreCaseOrProductSKUContainingIgnoreCase(search,
                                                        search, pageable);
                } else {
                        productPage = productInfoRepository.findAll(pageable);
                }

                List<Integer> productIds = productPage.map(ProductInfo::getId).getContent();

                Map<Integer, PriceInfo> prices = priceInfoRepository.findByProductIdIn(productIds)
                                .stream()
                                .collect(Collectors.toMap(p -> p.getProduct().getId(), Function.identity()));

                Map<Integer, List<LotInfo>> lots = lotInfoRepository.findByProductIdIn(productIds)
                                .stream()
                                .collect(Collectors.groupingBy(l -> l.getProduct().getId()));

                List<FetchProductsDTO> productsInPage = productPage.getContent().stream().map(product -> {
                        BigDecimal price = Optional.ofNullable(prices.get(product.getId()))
                                        .map(PriceInfo::getProductPrice)
                                        .orElse(BigDecimal.ZERO);

                        List<LotInfo> productLots = lots.getOrDefault(product.getId(), Collections.emptyList());

                        return new FetchProductsDTO(
                                        product.getId(),
                                        product.getProductName(),
                                        product.getProductSKU(),
                                        price,
                                        productLots.stream().mapToInt(LotInfo::getRemainingQuantity).sum(),
                                        productLots.stream()
                                                        .map(LotInfo::getExpiryDate)
                                                        .min(LocalDate::compareTo)
                                                        .orElse(null));
                }).collect(Collectors.toList());

                return new FetchProductsPageDTO(productPage.getTotalPages(), productsInPage);
        }
        // ---- Método para retornar uma lista paginada de produtos ---- //

        // ---- Método para remover um produto ---- //
        public void removeProduct(Integer productId) {

                if (!productInfoRepository.existsById(productId)) {
                        throw new EntityNotFoundException("Produto não encontrado com ID: " + productId);
                }

                productInfoRepository.deleteById(productId);
        }
        // ---- Método para remover um produto ---- //

        // ---- Método para criar um produto no banco a partir da requisição ---- //
        private ProductInfo saveProductInfo(ProductRequestDTO request) {
                ProductInfo product = new ProductInfo();
                product.setProductName(request.productInfo().productName());
                product.setProductDescription(request.productInfo().productDescription());
                product.setProductSKU(request.productInfo().productSKU());
                product.setProductCategory(request.productInfo().productCategory());
                product.setProductBrand(request.productInfo().productBrand());
                product.setProductModel(request.productInfo().productModel());
                return productInfoRepository.save(product);
        }
        // ---- Método para criar um produto no banco a partir da requisição ---- //

        // ---- Método para salvar os dados de preço no banco a partir da requisição
        // ---- //
        private PriceInfo savePriceInfo(ProductInfo product, ProductRequestDTO request) {

                PriceInfo price = new PriceInfo();
                price.setProduct(product);
                price.setProductPrice(request.priceInfo().productPrice());
                price.setPromotionalPrice(request.priceInfo().promotionalPrice());
                return priceInfoRepository.save(price);
        }
        // ---- Método para salvar os dados de preço no banco a partir da requisição
        // ---- //

        // ---- Método para salvar os dados técnicos no banco a partir da requisição
        // ---- //
        private TechnicalInfo saveTechnicalInfo(ProductInfo product, ProductRequestDTO request) {

                TechnicalInfo tech = new TechnicalInfo();
                tech.setProduct(product);
                tech.setProductWeight(request.technicalInfo().productWeight());
                tech.setProductLength(request.technicalInfo().productLength());
                tech.setProductWidth(request.technicalInfo().productWidth());
                tech.setProductHeight(request.technicalInfo().productHeight());
                return technicalInfoRepository.save(tech);
        }
        // ---- Método para salvar os dados técnicos no banco a partir da requisição
        // ---- //

        // ---- Método para mapear os dados gerais de resposta do produto ---- //
        private ProductResponseDTO buildProductResponse(
                        ProductInfo product,
                        PriceInfo price,
                        TechnicalInfo tech,
                        List<LotInfo> lots) {

                // Quando o produto é criado ele é nulo
                if (lots != null) {

                        return new ProductResponseDTO(
                                        product.getId(),
                                        mapToProductInfoDTO(product),
                                        mapToPriceInfoDTO(price),
                                        mapToTechnicalInfoDTO(tech),
                                        lots.stream()
                                                        .map(lot -> lotOperationsService.mapToLotInfoDTO(lot))
                                                        .collect(Collectors.toList()));
                } else {

                        return new ProductResponseDTO(
                                        product.getId(),
                                        mapToProductInfoDTO(product),
                                        mapToPriceInfoDTO(price),
                                        mapToTechnicalInfoDTO(tech),
                                        null);
                }
        }
        // ---- Método para mapear os dados gerais de resposta do produto ---- //

        // ---- Método para mapear os dados de resposta apenas do produto ---- //
        private ProductInfoDTO mapToProductInfoDTO(ProductInfo product) {

                return new ProductInfoDTO(
                                product.getProductName(),
                                product.getProductDescription(),
                                product.getProductSKU(),
                                product.getProductCategory(),
                                product.getProductBrand(),
                                product.getProductModel());
        }
        // ---- Método para mapear os dados de resposta apenas do produto ---- //

        // ---- Método para mapear os dados de resposta apenas do preço ---- //
        private PriceInfoDTO mapToPriceInfoDTO(PriceInfo price) {

                return new PriceInfoDTO(
                                price.getProductPrice(),
                                price.getPromotionalPrice());
        }
        // ---- Método para mapear os dados de resposta apenas do produto ---- //

        // ---- Método para mapear os dados de resposta apenas dos dados técnicos ----
        // //
        private TechnicalInfoDTO mapToTechnicalInfoDTO(TechnicalInfo tech) {

                return new TechnicalInfoDTO(
                                tech.getProductWeight(),
                                tech.getProductLength(),
                                tech.getProductWidth(),
                                tech.getProductHeight());
        }
        // ---- Método para mapear os dados de resposta apenas dos dados técnicos ----
        // //
}