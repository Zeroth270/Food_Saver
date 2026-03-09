package com.Food_Saver.demo.repository;

import com.Food_Saver.demo.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepo extends JpaRepository<Food, Long> {

    @Query("SELECT f FROM Food f WHERE f.user.userId = :userId")
    List<Food> findByUserId(@Param("userId") Long userId);


    @Query(value = """
        SELECT * FROM food f
        WHERE f.status = 'AVAILABLE'
        AND (
            6371 * acos(
                cos(radians(:lat)) *
                cos(radians(f.latitude)) *
                cos(radians(f.longitude) - radians(:lng)) +
                sin(radians(:lat)) *
                sin(radians(f.latitude))
            )
        ) < :radius
        ORDER BY (
            6371 * acos(
                cos(radians(:lat)) *
                cos(radians(f.latitude)) *
                cos(radians(f.longitude) - radians(:lng)) +
                sin(radians(:lat)) *
                sin(radians(f.latitude))
            )
        )
        """,
            nativeQuery = true)
    List<Food> findNearbyFood(
            @Param("lat") double latitude,
            @Param("lng") double longitude,
            @Param("radius") double radius
    );


    @Query(value = """
    SELECT * FROM food f
    WHERE f.status = 'AVAILABLE'
    AND (:location IS NULL OR LOWER(f.location) LIKE LOWER(CONCAT('%', :location, '%')))
    AND (:foodType IS NULL OR f.food_type = :foodType)
    AND (:freeOnly IS NULL OR f.is_free = :freeOnly)
    AND (
        :lat IS NULL OR :lng IS NULL OR
        (
            6371 * acos(
                cos(radians(:lat)) *
                cos(radians(f.latitude)) *
                cos(radians(f.longitude) - radians(:lng)) +
                sin(radians(:lat)) *
                sin(radians(f.latitude))
            )
        ) <= :radius
    )
""", nativeQuery = true)
    List<Food> advancedSearchWithRadius(
            @Param("location") String location,
            @Param("foodType") String foodType,
            @Param("freeOnly") Boolean freeOnly,
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radius") Double radius
    );
}
