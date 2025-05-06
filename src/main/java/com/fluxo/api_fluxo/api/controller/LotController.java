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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fluxo.api_fluxo.api.dto.lot.IncomingLotRequestDTO;
import com.fluxo.api_fluxo.api.dto.lot.OutgoingLotRequestDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.LotOperationResponseDTO;
import com.fluxo.api_fluxo.api.dto.lot.component.SupplierInfoDTO;
import com.fluxo.api_fluxo.service.LotService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@CrossOrigin
@RequestMapping("/lote")
@Tag(name = "Lotes", description = "Operações relacionadas à gestão de lotes e movimentações")
public class LotController {

    @Autowired
    private LotService lotService;

    @PostMapping("/entrada")
    @Operation(summary = "Registrar entrada de lote", description = "Registra uma nova entrada de estoque")
    @ApiResponse(responseCode = "200", description = "Entrada registrada com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public ResponseEntity<?> registerIncomingLot(
            @RequestBody IncomingLotRequestDTO request) {

        return ResponseEntity.ok(lotService.processIncomingLot(request));
    }

    @PatchMapping("/saida/{lotId}")
    @Operation(summary = "Processar saída de lote", description = "Registra uma saída/redução no estoque")
    @ApiResponse(responseCode = "200", description = "Saída processada com sucesso")
    @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    @ApiResponse(responseCode = "400", description = "Quantidade insuficiente em estoque")
    public ResponseEntity<?> processOutgoingLot(
            @Parameter(description = "ID do lote") @PathVariable("lotId") Integer lotId,
            @RequestBody OutgoingLotRequestDTO request) {

        return ResponseEntity.ok(lotService.adjustOutgoingLot(lotId, request));
    }

    @GetMapping("/movimentacoes")
    @Operation(summary = "Listar movimentações", description = "Retorna todas as movimentações de estoque paginadas")
    @ApiResponse(responseCode = "200", description = "Lista de movimentações retornada")
    public ResponseEntity<List<LotOperationResponseDTO>> getAllOperations(
            @Parameter(description = "Número da página (base 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tamanho da página") @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(lotService.fetchAllLotOperations(page, size));
    }

    @GetMapping("/fornecedores")
    @Operation(summary = "Listar fornecedores", description = "Retorna todos os fornecedores cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de fornecedores retornada")
    public ResponseEntity<List<SupplierInfoDTO>> getAllSuppliers() {

        return ResponseEntity.ok(lotService.fetchAllSuppliers());
    }

    @DeleteMapping("/apagar/{operationId}")
    @Operation(summary = "Excluir movimentação", description = "Remove permanentemente uma movimentação de estoque")
    @ApiResponse(responseCode = "200", description = "Movimentação excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Movimentação não encontrada")
    public ResponseEntity<?> deleteOperation(
            @Parameter(description = "ID da operação") @PathVariable("operationId") Integer id) {

        try {

            lotService.removeOperation(id);
            return ResponseEntity.ok("Operação deletada");
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}