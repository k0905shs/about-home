package com.myHome.entity;

import com.myHome.common.PostType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class postTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void postTest() {
        em.persist(new Post("title", "content", PostType.NOTICE));

    }

}
