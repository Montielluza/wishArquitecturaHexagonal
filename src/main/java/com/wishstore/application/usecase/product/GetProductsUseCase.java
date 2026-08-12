package com.wishstore.application.usecase.product;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.mapper.ProductMapper;
import com.wishstore.domain.model.Product;
import com.wishstore.domain.ports.ProductRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: obtener el listado completo de productos del catalogo.
 * <p>Responsabilidad: Orquestar la consulta de productos y aplicar la regla
 * de negocio de disponibilidad (stock == 0 -&gt; agotado) antes de devolver
 * la respuesta.</p>
 */
@Service
public class GetProductsUseCase {

    private final ProductRepositoryPort productRepositoryPort;
    private final ProductMapper productMapper;

    /* Construye el caso de uso mediante inyeccion por constructor.
     * @param productRepositoryPort puerto de persistencia de productos
     * @param productMapper         mapper entre dominio y DTO de producto*/
    public GetProductsUseCase(ProductRepositoryPort productRepositoryPort, ProductMapper productMapper) {
        this.productRepositoryPort = productRepositoryPort;
        this.productMapper = productMapper;
    }

    /*Ejecuta el caso de uso: obtiene todos los productos y los convierte a DTO.
     * @return lista de {@link ProductResponse} con el estado de stock calculado*/
    public List<ProductResponse> execute() {
        List<Product> products = productRepositoryPort.findAll();

        return products.stream()
                .map(this::toResponseWithStockFlag)
                .toList();
    }

    /*Convierte un producto de dominio a su DTO de salida, calculando el
     * indicador de agotado.
     * @param product producto de dominio
     * @return DTO de salida con {@code outOfStock} calculado*/
    private ProductResponse toResponseWithStockFlag(Product product) {
        ProductResponse response = productMapper.toResponse(product);
        response.setOutOfStock(!product.hasStockFor(1));
        return response;
    }
}