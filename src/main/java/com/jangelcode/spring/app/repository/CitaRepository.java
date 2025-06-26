package com.jangelcode.spring.app.repository;

import com.jangelcode.spring.app.entity.Cita;
import com.jangelcode.spring.app.entity.EstadoCita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    @Query("SELECT c FROM Cita c WHERE (:fecha IS NULL OR c.fecha = :fecha)")
    List<Cita> findByFecha(LocalDate fecha);

    Page<Cita> findByFechaGreaterThanEqualAndEstado(LocalDate fecha, EstadoCita estado,
            Pageable pageable);

    Page<Cita> findByEstado(EstadoCita estado, Pageable pageable);

    Page<Cita> findByFechaGreaterThanEqual(LocalDate fecha, Pageable pageable);
}
