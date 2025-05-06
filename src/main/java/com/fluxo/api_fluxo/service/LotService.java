package com.fluxo.api_fluxo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fluxo.api_fluxo.api.dto.lot.IncomingLotRequestDTO;
import com.fluxo.api_fluxo.api.dto.lot.LotResponseDTO;
import com.fluxo.api_fluxo.api.dto.lot.OutgoingLotRequestDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.LotInfoResponseDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.LotOperationRequestDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.LotOperationResponseDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.SupplierInfoDTO;
import com.fluxo.api_fluxo.domain.lot.LotInfo;
import com.fluxo.api_fluxo.domain.lot.LotOperation;
import com.fluxo.api_fluxo.domain.lot.Operations;
import com.fluxo.api_fluxo.domain.lot.SupplierInfo;
import com.fluxo.api_fluxo.domain.product.PriceInfo;
import com.fluxo.api_fluxo.domain.product.ProductInfo;
import com.fluxo.api_fluxo.repositories.lot.LotInfoRepository;
import com.fluxo.api_fluxo.repositories.lot.LotOperationRepository;
import com.fluxo.api_fluxo.repositories.lot.SupplierInfoRepository;
import com.fluxo.api_fluxo.repositories.product.PriceInfoRepository;
import com.fluxo.api_fluxo.repositories.product.ProductInfoRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LotService {

    @Autowired
    private ProductInfoRepository productRepository;

    @Autowired
    private PriceInfoRepository priceRepository;

    @Autowired
    private SupplierInfoRepository supplierRepository;

    @Autowired
    private LotInfoRepository lotInfoRepository;

    @Autowired
    private LotOperationRepository lotOperationRepository;

    // ---- Método para adicionar um novo lote ---- //
    public LotResponseDTO processIncomingLot(IncomingLotRequestDTO request) {
        ProductInfo product = productRepository.findById(request.productId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Produto não encontrado com o ID: " + request.productId()));

        LotInfo lot = saveIncomingLotInfo(product, request);
        LotOperation operation = saveLotOperation(lot, request.lotOperation());

        return mapLotResponse(
                mapToLotInfoDTO(lot),
                mapToLotOperationDTO(operation));
    }
    // ---- Método para adicionar um novo lote ---- //

    // ---- Método para adicionar uma movimentação a um lote existente ---- //
    public LotResponseDTO adjustOutgoingLot(Integer lotId, OutgoingLotRequestDTO request) {

        LotInfo lot = adjustExistingLot(lotId, request);
        LotOperation operation = saveLotOperation(lot, request.lotOperation());

        return mapLotResponse(
                mapToLotInfoDTO(lot),
                mapToLotOperationDTO(operation));
    }
    // ---- Método para adicionar uma movimentação a um lote existente ---- //

    // ---- Método para retornar uma lista paginada de operações ---- //
    public List<LotOperationResponseDTO> fetchAllLotOperations(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<LotOperation> operationPage = lotOperationRepository.findAll(pageable);

        return operationPage.getContent().stream()
                .map(this::mapToLotOperationDTO)
                .toList();
    }
    // ---- Método para retornar uma lista paginada de operações ---- //

    // ---- Método para retornar uma lista fornecedores ---- //
    public List<SupplierInfoDTO> fetchAllSuppliers() {

        List<SupplierInfo> allSuppliers = supplierRepository.findAll();

        return allSuppliers.stream()
                .map(this::mapToSupplierDTO)
                .collect(Collectors.toList());
    }
    // ---- Método para retornar uma lista fornecedores ---- //

    // ---- Método para remover uma operação ---- //
    public void removeOperation(Integer operationId) {

        LotOperation removedOperation = lotOperationRepository.findById(operationId)
                .orElseThrow(() -> new EntityNotFoundException("Operação não encontrada com ID: " + operationId));
    
        LotInfo existingLot = lotInfoRepository.findById(removedOperation.getLot().getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Lote não encontrado com ID: " + removedOperation.getLot().getId()));
    
        if (removedOperation.getMovementType() == Operations.ENTRADA) {

            lotOperationRepository.delete(removedOperation);
            lotInfoRepository.delete(existingLot);
        } else if (removedOperation.getMovementType() == Operations.SAIDA) {

            existingLot.setRemainingQuantity(existingLot.getRemainingQuantity() + removedOperation.getMovementAmount());
            lotInfoRepository.save(existingLot);
            lotOperationRepository.delete(removedOperation);
        } else if (removedOperation.getMovementType() == Operations.RESERVA) {
            
            existingLot.setRemainingQuantity(existingLot.getRemainingQuantity() + removedOperation.getMovementAmount());
            lotInfoRepository.save(existingLot);
            lotOperationRepository.delete(removedOperation);
        } else {
            throw new UnsupportedOperationException(
                    "Tipo de movimentação não suportada: " + removedOperation.getMovementType());
        }
    }
    // ---- Método para remover uma operação ---- //

    // ---- Método para criar ou encontrar um fornecedor ---- //
    private SupplierInfo getOrCreateSupplier(String supplierName) {
        return supplierRepository.findBySupplierName(supplierName)
                .orElseGet(() -> {
                    SupplierInfo newSupplier = new SupplierInfo();
                    newSupplier.setSupplierName(supplierName);
                    return supplierRepository.save(newSupplier);
                });
    }
    // ---- Método para criar ou encontrar um fornecedor ---- //

    // ---- Método para criar um lote no banco a partir da requisição ---- //
    private LotInfo saveIncomingLotInfo(ProductInfo product, IncomingLotRequestDTO request) {

        PriceInfo newPrice = priceRepository.findByProductId(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Preço não encontrado"));

        newPrice.setProductPrice(request.lotOperation().unitPrice());
        priceRepository.save(newPrice);

        LotInfo newLot = new LotInfo();
        newLot.setProduct(product);
        newLot.setSupplier(getOrCreateSupplier(request.supplierInfo().supplierName()));
        newLot.setLotCode(request.lotInfo().lotCode());
        newLot.setExpiryDate(request.lotInfo().expiryDate());
        newLot.setRemainingQuantity(request.lotOperation().movementAmount());
        newLot.setLotLocation(request.lotInfo().lotLocation());

        return lotInfoRepository.save(newLot);
    }
    // ---- Método para criar um lote no banco a partir da requisição ---- //

    // ---- Método para criar uma operação no banco a partir da requisição ---- //
    private LotOperation saveLotOperation(LotInfo lot, LotOperationRequestDTO request) {
        LotOperation operation = new LotOperation();
        operation.setLot(lot);
        operation.setMovementAmount(request.movementAmount());
        operation.setUnitPrice(request.unitPrice());
        operation.setNotes(request.notes());
        operation.setMovementType(request.movementType());

        return lotOperationRepository.save(operation);
    }
    // ---- Método para criar uma operação no banco a partir da requisição ---- //

    // ---- Método para atualizar informações de um lote existente ---- //
    private LotInfo adjustExistingLot(Integer lotId, OutgoingLotRequestDTO request) {

        LotInfo existingLot = lotInfoRepository.findById(lotId)
                .orElseThrow(() -> new EntityNotFoundException("Lote não encontrado com o ID: " + lotId));

        int movementAmount = request.lotOperation().movementAmount();
        int newRemainingQuantity = existingLot.getRemainingQuantity() - movementAmount;

        if (newRemainingQuantity < 0) {
            throw new IllegalArgumentException("Quantidade insuficiente em estoque");
        }

        existingLot.setRemainingQuantity(newRemainingQuantity);

        return lotInfoRepository.save(existingLot);
    }
    // ---- Método para atualizar informações de um lote existente ---- //

    // ---- Método para mapear os dados de resposta do fornecedor ---- //
    private SupplierInfoDTO mapToSupplierDTO(SupplierInfo supplier) {
        return new SupplierInfoDTO(supplier.getId(), supplier.getSupplierName());
    }
    // ---- Método para mapear os dados de resposta do fornecedor ---- //

    // ---- Método para mapear os dados de resposta das informações do lote ---- //
    public LotInfoResponseDTO mapToLotInfoDTO(LotInfo lot) {
        return new LotInfoResponseDTO(
                lot.getId(),
                mapToSupplierDTO(lot.getSupplier()),
                lot.getLotCode(),
                lot.getExpiryDate(),
                lot.getRemainingQuantity(),
                lot.getLotLocation());
    }
    // ---- Método para mapear os dados de resposta das informações do lote ---- //

    // ---- Método para mapear os dados de operação do lote ---- //
    private LotOperationResponseDTO mapToLotOperationDTO(LotOperation operation) {
        return new LotOperationResponseDTO(
                operation.getId(),
                operation.getMovementDate(),
                operation.getMovementAmount(),
                operation.getUnitPrice(),
                operation.getNotes(),
                operation.getMovementType());
    }
    // ---- Método para mapear os dados de operação do lote ---- //

    // ---- Método para mapear os dados gerais do lote ---- //
    private LotResponseDTO mapLotResponse(
            LotInfoResponseDTO lotInfo,
            LotOperationResponseDTO operation) {
        return new LotResponseDTO(lotInfo, operation);
    }
    // ---- Método para mapear os dados gerais do lote ---- //
}