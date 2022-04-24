package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.data.SiteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.repositories.SiteRepository;
import sn.ept.git.seminaire.cicd.repositories.SocieteRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class SiteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private SiteMapper mapper;
    @Autowired
    private SiteRepository repository;

    @Autowired
    private SocieteRepository societeRepository;

    Societe societeEntity;

    static SiteDTO dto;
    static SocieteDTO societeDTO;

    @Autowired
    private SocieteMapper societeMapper;

    Site entity;
    Optional<Site> optionalSite;

    @BeforeAll
    static  void beforeAll() {
        dto = SiteDTOTestData.defaultDTO();
        societeDTO = SocieteDTOTestData.defaultDTO();
        societeDTO.setId(dto.getSociete().getId());
        dto.setSociete(societeDTO);
        System.out.println("Societe id " +societeDTO.getId());
        System.out.println("societe dans site id " + dto.getSociete().getId());
    }

    @BeforeEach
    void setUp() {
        repository.deleteAll();
        societeRepository.deleteAll();
        societeEntity = societeMapper.asEntity(societeDTO);
        entity = mapper.asEntity(dto);
        System.out.println(societeEntity.getId());
        System.out.println(entity.getSociete().getId());
        societeEntity = societeRepository.saveAndFlush(societeEntity);
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void FindByName_thenResult() {
        optionalSite = repository.findByName(entity.getName());
        assertThat(optionalSite)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void FindByBadName_thenNotFound() {
        optionalSite = repository.findByName(UUID.randomUUID().toString());
        assertThat(optionalSite)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void FindDeleted_thenNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        optionalSite = repository.findByName(entity.getName());
        assertThat(optionalSite)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByNameWithIdDifferent_thenResult() {
        optionalSite = repository.findByNameWithIdDifferent(entity.getName(),UUID.randomUUID());
        assertThat(optionalSite)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByNameWithIdDifferent_withSameId_shouldReturnNoResult() {
        optionalSite = repository.findByNameWithIdDifferent(entity.getName(),entity.getId());
        assertThat(optionalSite)
                .isNotNull()
                .isNotPresent();
    }
}
