package com.jangelcode.spring.app.repository;

import com.jangelcode.spring.app.entity.ProductoMagistral;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductoMagistralRepository extends JpaRepository<ProductoMagistral, Long> {
    @Query("SELECT p FROM ProductoMagistral p WHERE " +
            "(:nombre IS NULL OR LOWER(p.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) AND " +
            "(:minPrecio IS NULL OR p.precio >= :minPrecio) AND " +
            "(:maxPrecio IS NULL OR p.precio <= :maxPrecio)")
    Page<ProductoMagistral> buscarPorFiltros(@Param("nombre") String nombre,
            @Param("minPrecio") Double minPrecio,
            @Param("maxPrecio") Double maxPrecio,
            Pageable pageable);
}
