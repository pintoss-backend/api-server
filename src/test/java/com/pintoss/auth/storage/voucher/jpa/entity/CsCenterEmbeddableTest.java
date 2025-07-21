package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class CsCenterEmbeddableTest {

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("전화번호 길이가 20글자를 초과하면 예외가 발생한다.")
    void givenLongTelLength_whenCreateCsCenter_thenThrowPersistenceException() {
        // given
        String longTel = "1".repeat(21); // 21글자 길이의 전화번호
        CsCenterEmbeddable csCenter = new CsCenterEmbeddable(longTel);
        TestEntity testEntity = new TestEntity(csCenter);

        // when, then
        assertThatThrownBy(() -> {
            em.persist(testEntity);
            em.flush();
        }).isInstanceOf(PersistenceException.class);
    }

    @Test
    @DisplayName("전화번호 길이가 20글자 이하이면 정상적으로 저장된다.")
    void given20Length_whenCreateCsCenter_thenCreateEntity() {
        // given
        String validTel = "1".repeat(20); // 10글자 길이의 전화번호
        CsCenterEmbeddable csCenter = new CsCenterEmbeddable(validTel);
        TestEntity testEntity = new TestEntity(csCenter);

        // when
        em.persist(testEntity);
        em.flush();

        // then
        TestEntity foundEntity = em.find(TestEntity.class, testEntity.getId());
        assertThat(foundEntity).isNotNull();
    }

    @Entity
    @Table(name = "cs_center_test")
    public static class TestEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Embedded
        private CsCenterEmbeddable csCenterEmbeddable;

        protected TestEntity() {}

        public TestEntity(CsCenterEmbeddable csCenterEmbeddable) {
            this.csCenterEmbeddable = csCenterEmbeddable;
        }

        public Long getId() {
            return id;
        }
    }
}
