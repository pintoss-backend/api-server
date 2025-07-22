package com.pintoss.auth.storage.voucher.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@ActiveProfiles("test")
class HomePageEmbeddableTest {

    @Autowired
    EntityManager em;

    private Validator validator;

    @BeforeEach
    void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("url 길이가 100글자를 초과하면 예외가 발생한다.")
    void giveOver100LengthUrl_whenPersist_thenThrowException() {
        // given
        String longUrl = "a".repeat(101); // 101글자 길이의 URL
        HomePageEmbeddable homePage = new HomePageEmbeddable(longUrl);

        // when
        TestEntity testEntity = new TestEntity(homePage);

        // then
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(testEntity);
        assertThat(violations).hasSize(1);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("homePage.url"));
    }

    @Test
    @DisplayName("url 길이가 100글자 이하이면 정상적으로 저장된다.")
    void given100LengthUrl_whenPersist_thenCreateEntity() {
        // given
        String validUrl = "http://example.com"; // 20글자 길이의 URL
        HomePageEmbeddable homePage = new HomePageEmbeddable(validUrl);
        TestEntity testEntity = new TestEntity(homePage);

        // when
        em.persist(testEntity);
        em.flush();

        // then
        TestEntity foundEntity = em.find(TestEntity.class, testEntity.getId());
        assertThat(foundEntity).isNotNull();
    }

    @Test
    @DisplayName("url이 빈 문자열이면 예외가 발생한다.")
    void givenEmptyUrl_whenPersist_thenThrowsException() {
        // given
        HomePageEmbeddable homePage = new HomePageEmbeddable("");

        // when
        TestEntity testEntity = new TestEntity(homePage);

        // then
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(testEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("homePage.url"));
    }

    @Test
    @DisplayName("url이 null이면 예외가 발생한다.")
    void givenZeroLengthUrl_whenPersist_thenThrowsException() {
        // given
        Validator validator;
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        // when
        String url = null;
        HomePageEmbeddable homePage = new HomePageEmbeddable(url);
        TestEntity testEntity = new TestEntity(homePage);

        // then
        Set<ConstraintViolation<TestEntity>> violations = validator.validate(testEntity);
        assertThat(violations).anyMatch(v -> v.getPropertyPath().toString().equals("homePage.url"));
    }

    @Entity
    @Table(name = "home_page_test")
    static class TestEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Embedded
        @Valid
        private HomePageEmbeddable homePage;

        protected TestEntity() { }

        public TestEntity(HomePageEmbeddable homePage) {
            this.homePage = homePage;
        }

        public Long getId() {
            return id;
        }
    }

}
