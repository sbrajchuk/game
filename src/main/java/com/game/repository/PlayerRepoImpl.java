package com.game.repository;

import com.game.controller.PlayerOrder;
import com.game.entity.Player;
import com.game.entity.Profession;
import com.game.entity.Race;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class PlayerRepoImpl implements PlayerRepo {
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    public void setSessionFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public List<Player> getPlayers(String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel, PlayerOrder order, int startOffset, int size) {
        List<Player> players;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Player> criteriaQuery = criteriaBuilder.createQuery(Player.class);
            Root<Player> root = criteriaQuery.from(Player.class);
            criteriaQuery.select(root);
            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(order.getFieldName())));
            constructAndApplyPredicates(criteriaBuilder, root, criteriaQuery, name, title, race, profession, after, before,
                    banned, minExperience, maxExperience, minLevel, maxLevel);
            Query query = entityManager.createQuery(criteriaQuery);
            query.setFirstResult(startOffset);
            query.setMaxResults(size);
            players = query.getResultList();
        } finally {
            entityManager.close();
        }
        return players;
    }

    private void constructAndApplyPredicates(CriteriaBuilder criteriaBuilder, Root<Player> root, CriteriaQuery<?> criteriaQuery, String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        ArrayList<Predicate> predicates = new ArrayList<>();
        if (name != null) {
            String nameLike = "%" + name.toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), nameLike));
        }
        if (title != null) {
            String titleLike = "%" + title.toLowerCase() + "%";
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), titleLike));
        }
        if (race != null) {
            predicates.add(criteriaBuilder.equal(root.get("race"), race));
        }
        if (profession != null) {
            predicates.add(criteriaBuilder.equal(root.get("profession"), profession));
        }
        if (after != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), after));
        }
        if (before != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), before));
        }
        if (banned != null) {
            predicates.add(criteriaBuilder.equal(root.get("banned"), banned));
        }
        if (minExperience != null) {
            predicates.add(criteriaBuilder.ge(root.get("experience"), minExperience));
        }
        if (maxExperience != null) {
            predicates.add(criteriaBuilder.le(root.get("experience"), maxExperience));
        }
        if (minLevel != null) {
            predicates.add(criteriaBuilder.ge(root.get("level"), minLevel));
        }
        if (maxLevel != null) {
            predicates.add(criteriaBuilder.le(root.get("level"), maxLevel));
        }
        if (predicates.size() > 0) {
            Predicate[] arr = new Predicate[predicates.size()];
            arr = predicates.toArray(arr);
            criteriaQuery.where(arr);
        }
        return;
    }

    @Override
    public int countPlayers(String name, String title, Race race, Profession profession, Date after, Date before, Boolean banned, Integer minExperience, Integer maxExperience, Integer minLevel, Integer maxLevel) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        int numOfPlayers;
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
            Root<Player> root = criteriaQuery.from(Player.class);
            criteriaQuery.select(criteriaBuilder.count(root));
            constructAndApplyPredicates(criteriaBuilder, root, criteriaQuery, name, title, race, profession, after, before, banned, minExperience, maxExperience, minLevel, maxLevel);
            Query query = entityManager.createQuery(criteriaQuery);
            numOfPlayers = (int) (long) query.getSingleResult();
        } finally {
            entityManager.close();
        }

        return numOfPlayers;
    }

    @Override
    public Player add(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(player);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return player;
    }

    @Override
    public void delete(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            player = entityManager.find(Player.class, player.getId());
            entityManager.remove(player);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Player edit(Player player) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(player);
            entityManager.getTransaction().commit();
        } finally {
            entityManager.close();
        }
        return player;
    }

    @Override
    public Player getById(int id) {
        Player player;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            player = entityManager.find(Player.class, (long) id);
        } finally {
            entityManager.close();
        }
        return player;
    }

}
