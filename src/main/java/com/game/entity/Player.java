package com.game.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


import java.util.Date;
@org.hibernate.annotations.NamedQueries({@org.hibernate.annotations.NamedQuery(name = "AllCount", query = "select count(*) from Player")})
@Entity
@Table(name="player", schema="rpg")
public class Player {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "42"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    @Column(name = "id", nullable=false)
    private Long id;

    @Column(name = "name", length = 12, nullable=false)
    private String name;

    @Column(name = "title", length = 30, nullable=false)
    private String title;

    @Column(name = "race", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private Race race;

    @Column(name = "profession", nullable=false)
    @Enumerated(EnumType.ORDINAL)
    private Profession profession;

    @Column(name = "birthday", nullable=false)
    private Date birthday;

    @Column(name = "banned", nullable=false)
    private Boolean banned;

    @Column(name = "level", nullable=false)
    private Integer level;

    public Player() {
    }

    public Player(Long id, String name, String title, Race race, Profession profession, Date birthday, Boolean banned, Integer level) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.race = race;
        this.profession = profession;
        this.birthday = birthday;
        this.banned = banned;
        this.level = level;
    }

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

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Profession getProfession() {
        return profession;
    }

    public void setProfession(Profession profession) {
        this.profession = profession;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}