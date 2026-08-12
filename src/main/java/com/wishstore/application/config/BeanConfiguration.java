package com.wishstore.application.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/* Clase de configuracion central del nucleo hexagonal de WishStore.
 * <p>Descripcion: Punto de entrada de configuracion del modulo desarrollado
 * por el Lider Tecnico (dominio, casos de uso, ports, dto, mappers y
 * excepciones).</p>
 * <p>Responsabilidad: Garantizar que Spring detecte y registre como beans
 * todos los componentes del paquete raiz {@code com.wishstore}, sin importar
 * en que paquete resida la clase {@code @SpringBootApplication} final del
 * proyecto integrado (que sera ensamblado junto con los modulos de Controller
 * y Persistence de otros integrantes).</p>
 * <p>Nota de diseno: Los Use Cases estan anotados con {@code @Service} y los
 * Mappers con {@code @Mapper(componentModel = "spring")}, por lo que Spring
 * ya los detecta automaticamente via component scanning. Esta clase no
 * declara beans manualmente con {@code @Bean} para evitar redundancia
 * (violaria el principio DRY); en su lugar, fija explicitamente el alcance
 * del escaneo como contrato de integracion del modulo.</p>
 * <p>Dependencias: Ninguna del dominio; unicamente de anotaciones de
 * configuracion de Spring, ya que esta clase pertenece a la capa de
 * configuracion de la aplicacion, no al dominio puro.</p>*/
@Configuration
@ComponentScan(basePackages = "com.wishstore")
public class BeanConfiguration {
}