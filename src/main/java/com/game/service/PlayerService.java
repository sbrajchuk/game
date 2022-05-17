package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Date;
import java.util.List;

public interface PlayerService {
    List<Player> getPlayers(
            String name,
            String title,
            Race race,
            Profession profession,
            Date after,
            Date before,
            Boolean banned,
            Integer minExperience,
            Integer maxExperience,
            Integer minLevel,
            Integer maxLevel,
            PlayerOrder order,
            int startOffset,
            int size);

    Player add(Player player);

    void delete(Player player);

    Player edit(Player player);

    Player getById(int id);

    int countPlayers(
            String name,
            String title,
            Race race,
            Profession profession,
            Date after,
            Date before,
            Boolean banned,
            Integer minExperience,
            Integer maxExperience,
            Integer minLevel,
            Integer maxLevel
    );
}
