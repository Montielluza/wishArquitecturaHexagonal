package com.wishstore.application.usecase.product;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.exception.ProductNotFoundException;
import com.wishstore.application.mapper.ProductMapper;
import com.wishstore.domain.model.Product;
import com.wishstore.domain.ports.ProductRepositoryPort;
import org.springframework.stereotype.Service;

/*Caso de uso: obtener un producto especifico por su identificador.
 * <p>Responsabilidad: Validar la existencia del producto y calcular su
 * indicador de agotado antes de devolver la respuesta.</p>
 * * <p>Dependencias: {@link ProductRepositoryPort} para la consulta y
 * {@link ProductMapper} para la traduccion a DTO.</p>*/
@Service
public class GetProductByIdUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductMapper productMapper;

    /*Construye el caso de uso mediante inyeccion por constructor.
     * @param productRepositoryPort puerto de persistencia de productos
     * @param productMapper         mapper entre dominio y DTO de producto*/
    public GetProductByIdUseCase(ProductRepositoryPort productRepositoryPort, ProductMapper productMapper) {
        this.productRepositoryPort = productRepositoryPort;
        this.productMapper = productMapper;
    }

    /*Ejecuta el caso de uso: busca el producto por id y lo convierte a DTO.
     * @param productId identificador del producto solicitado
     * @return DTO de salida {@link ProductResponse}
     * @throws ProductNotFoundException si no existe un producto con ese id*/
    public ProductResponse execute(Long productId) {
        Product product = productRepositoryPort.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        ProductResponse response = productMapper.toResponse(product);
        response.setOutOfStock(!product.hasStockFor(1));
        return response;
    }
}