package com.game.controller;

import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import com.game.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.regex.Pattern;

@Controller
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public void setPlayerService(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(value = "/rest/players")
    @ResponseBody
    public List<Player> getPlayers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel,
            @RequestParam(required = false) PlayerOrder order,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize
    ) {
        if (order == null) {
            order = PlayerOrder.ID;
        }
        if (pageSize == null) {
            pageSize = 3;
        }
        if (pageNumber == null) {
            pageNumber = 0;
        }
        Date dateAfter = null, dateBefore = null;
        if (after != null) {
            dateAfter = new Date(after);
        }
        if (before != null) {
            dateBefore = new Date(before);
        }
        int startOffset = pageNumber * pageSize;
        return playerService.getPlayers(name, title, race, profession, dateAfter, dateBefore, banned,
                minExperience, maxExperience, minLevel, maxLevel, order, startOffset, pageSize);
    }

    @GetMapping(value = "/rest/players/count")
    @ResponseBody
    public int countPlayers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Race race,
            @RequestParam(required = false) Profession profession,
            @RequestParam(required = false) Long after,
            @RequestParam(required = false) Long before,
            @RequestParam(required = false) Boolean banned,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false) Integer maxExperience,
            @RequestParam(required = false) Integer minLevel,
            @RequestParam(required = false) Integer maxLevel

    ) {
        Date dateAfter = null, dateBefore = null;
        if (after != null) {
            dateAfter = new Date(after);
        }
        if (before != null) {
            dateBefore = new Date(before);
        }
        return playerService.countPlayers(name, title, race, profession, dateAfter, dateBefore, banned,
                minExperience, maxExperience, minLevel, maxLevel);
    }

    @PostMapping(value = "/rest/players")
    @ResponseBody
    public Player addPlayer(@RequestBody PlayerRequest playerRequest) {
        playerRequest.initDefaultFields();
        if (playerRequest.checkFieldsForNull()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation error: not all fields defined");
        }
        if (!playerRequest.validateFields()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation error");
        }
        Player player = new Player(playerRequest);
        return playerService.add(player);
    }


    @GetMapping(value = "/rest/players/{id:.+}")
    @ResponseBody
    public Player getPlayer(@PathVariable String id) {
        int intId = getIntIdThrowExceptionIfIncorrect(id);

        Player player = playerService.getById(intId);

        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return player;
    }

    @PostMapping(value = "/rest/players/{id:.+}")
    @ResponseBody
    public Player updatePlayer(@PathVariable String id, @RequestBody PlayerRequest playerRequest) {

        int intId = getIntIdThrowExceptionIfIncorrect(id);

        if (!playerRequest.validateFields()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Validation error");
        }

        Player player = playerService.getById(intId);

        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        player.update(playerRequest);

        return playerService.edit(player);
    }

    private final Pattern pattern = Pattern.compile("\\d+");

    private int getIntIdThrowExceptionIfIncorrect(String id) {

        if (!pattern.matcher(id).matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        int intId;
        try {
            intId = Integer.parseInt(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (intId == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        return intId;
    }

    @DeleteMapping(value = "/rest/players/{id:.+}")
    @ResponseBody
    public void deletePlayer(@PathVariable String id) {
        int intId = getIntIdThrowExceptionIfIncorrect(id);

        Player player = playerService.getById(intId);

        if (player == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        playerService.delete(player);
    }
}
