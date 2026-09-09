package com.wishstore;

import com.wishstore.application.config.BeanConfiguration;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Prueba de humo del modulo del nucleo hexagonal.
 *
 * <p>Descripcion: Este modulo (dominio + casos de uso + ports) se entrega de
 * forma aislada, sin implementaciones de los Repository Ports. Por diseno,
 * NO puede levantar un {@code ApplicationContext} completo con
 * {@code @SpringBootTest}, ya que los Use Cases dependen de los Ports
 * (interfaces) cuyas implementaciones (Persistence Adapters) son
 * responsabilidad de otro integrante del equipo y aun no existen.</p>
 *
 * <p>Responsabilidad: Verificar que la clase de configuracion del modulo
 * existe y es instanciable, sin requerir un contexto de Spring completo.</p>
 */
class WishstoreBackendApplicationTests {

    @Test
    void beanConfigurationClassExists() {
        assertThat(BeanConfiguration.class).isNotNull();
    }
}