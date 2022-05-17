package com.game.controller;

import com.game.entity.Profession;
import com.game.entity.Race;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class PlayerRequest {
    private String name;

    private String title;

    private Race race;

    private Profession profession;

    private Integer experience;

    private Long birthday;

    private Boolean banned;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public Race getRace() {
        return race;
    }

    public Profession getProfession() {
        return profession;
    }

    public Integer getExperience() {
        return experience;
    }

    public Long getBirthday() {
        return birthday;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void initDefaultFields() {
        if (banned == null) {
            banned = false;
        }
    }

    public boolean checkFieldsForNull() {
        if (name == null || title == null || race == null ||
                profession == null || experience == null || birthday == null || banned == null) {
            return true;
        }
        return false;
    }

    public boolean validateFields() {
        if (name != null && (name.length() > 12 || name.length() == 0)) {
            return false;
        }
        if (title != null && title.length() > 30) {
            return false;
        }
        if (birthday != null && birthday < 0) {
            return false;
        }
        if (birthday != null) {
            Calendar calendarBirthday = new GregorianCalendar();
            calendarBirthday.setTime(new Date(birthday));
            if (calendarBirthday.get(Calendar.YEAR) < 2000 || calendarBirthday.get(Calendar.YEAR) > 3000) {
                return false;
            }
        }
        if (experience != null && (experience < 0 || experience > 10000000)) {
            return false;
        }
        return true;
    }
}
