package com.spotBus.backend.repository;

import com.spotBus.backend.entity.Bus;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface BusRepository extends JpaRepository<Bus, Long> {

    Optional<Bus> findByBusNumber(String busNumber); //JPA query method

    List<Bus> findByRouteId(Long routeId);

    @Query("""
        SELECT b
        FROM Bus b
        WHERE b.busNumber = :busNumber
        """)
    Optional<Bus> getBus(String busNumber); //JPQL

    @Query(value =
            "SELECT * FROM bus WHERE bus_number=?1",
            nativeQuery = true)
    Bus getBusNative(String busNumber); // native query

}