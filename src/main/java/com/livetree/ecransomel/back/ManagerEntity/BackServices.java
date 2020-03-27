package com.livetree.ecransomel.back.ManagerEntity;

import javax.persistence.EntityManager;

public class BackServices {
    private EntityManager em;

    public BackServices(EntityManager em) {
        this.em = em;
    }
}
