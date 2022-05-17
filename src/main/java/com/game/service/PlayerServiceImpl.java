package com.game.service;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.repository.PlayerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepo playerRepo;

    @Autowired
    public void setFilmDAO(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    @Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession,
                                   Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience,
                                   Integer minLevel, Integer maxLevel, PlayerOrder order, int startOffset, int size) {
        return playerRepo.getPlayers(name, title, race, profession, after, before, banned, minExperience, maxExperience,
                minLevel, maxLevel, order, startOffset, size);
    }

    @Override
    public int countPlayers(String name, String title, Race race, Profession profession, Date after, Date before,
                            Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        return playerRepo.countPlayers(name, title, race, profession, after, before, banned,
                minExperience, maxExperience, minLevel, maxLevel);
    }

    @Override
    public Player add(Player player) {
        return playerRepo.add(player);
    }

    @Override
    public void delete(Player player) {
        playerRepo.delete(player);
    }

    @Override
    public Player edit(Player player) {
        return playerRepo.edit(player);
    }

    @Override
    public Player getById(int id) {
        return playerRepo.getById(id);
    }

}
