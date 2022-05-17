package com.game.entity;

import com.game.controller.PlayerRequest;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Player {

    public Player() {

    }

    public Player(String name, String title, Race race, Profession profession, Integer experience, Date birthday, Boolean banned) {
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.experience = experience;
        this.birthday = birthday;
        this.banned = banned;
        updateLevelAndUntilNextLevel();
    }

    public Player(PlayerRequest playerRequest) {
        this.name = playerRequest.getName();
        this.title = playerRequest.getTitle();
        this.race = playerRequest.getRace();
        this.profession = playerRequest.getProfession();
        this.experience = playerRequest.getExperience();
        this.birthday = new Date(playerRequest.getBirthday());
        this.banned = playerRequest.getBanned();
        updateLevelAndUntilNextLevel();
    }

    private void updateLevelAndUntilNextLevel() {
        this.level = ((Double) ((Math.sqrt(2500 + 200 * this.experience) - 50) / 100)).intValue();
        this.untilNextLevel = 50 * (this.level + 1) * (this.level + 2) - this.experience;
    }

    private Long id;

    private String name;

    private String title;

    private Race race;

    private Profession profession;

    private Integer experience;

    private Integer level;

    private Integer untilNextLevel;

    private Date birthday;

    private Boolean banned;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Enumerated(EnumType.STRING)
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Enumerated(EnumType.STRING)
    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
        updateLevelAndUntilNextLevel();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getUntilNextLevel() {
        return untilNextLevel;
    }

    public void setUntilNextLevel(Integer untilNextLevel) {
        this.untilNextLevel = untilNextLevel;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public void update(PlayerRequest playerRequest) {

        String newName = playerRequest.getName();
        if (newName != null) {
            this.name = newName;
        }

        String newTitle = playerRequest.getTitle();
        if (newTitle != null) {
            this.title = newTitle;
        }

        Race newRace = playerRequest.getRace();
        if (newRace != null) {
            this.race = newRace;
        }

        Profession newProfession = playerRequest.getProfession();
        if (newProfession != null) {
            this.profession = newProfession;
        }

        Integer newExperience = playerRequest.getExperience();
        if (newExperience != null) {
            this.experience = newExperience;
            updateLevelAndUntilNextLevel();
        }

        Long newBirthday = playerRequest.getBirthday();
        if (newBirthday != null) {
            this.birthday = new Date(newBirthday);
        }

        Boolean newBanned = playerRequest.getBanned();
        if (newBanned != null) {
            this.banned = newBanned;
        }
    }
}
