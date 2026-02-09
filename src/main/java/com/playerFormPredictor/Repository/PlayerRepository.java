package com.playerFormPredictor.Repository;

import com.playerFormPredictor.Entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {

    Optional<Player> findByNameIgnoreCase(String name);

}
