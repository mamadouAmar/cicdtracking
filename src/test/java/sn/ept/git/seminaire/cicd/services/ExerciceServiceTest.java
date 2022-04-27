package sn.ept.git.seminaire.cicd.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.SiteVMTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.data.TestData;
import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.exceptions.ItemExistsException;
import sn.ept.git.seminaire.cicd.exceptions.ItemNotFoundException;
import sn.ept.git.seminaire.cicd.mappers.ExerciceMapper;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.ExerciceRepository;
import sn.ept.git.seminaire.cicd.repositories.SocieteRepository;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExerciceServiceTest extends ServiceBaseTest{

    @Autowired
    protected ExerciceMapper mapper;
    @Autowired
    protected ExerciceVMMapper vmMapper;
    @Autowired
    ExerciceRepository exerciceRepository;
    @Autowired
    IExerciceService service;
    Optional<Exercice> exercice;

    Optional<Societe> societe;
    @Autowired
    protected SocieteMapper societeMapper;
    @Autowired
    protected SocieteVMMapper societeVMMapper;
    static ExerciceVM vm;
    ExerciceDTO dto;

    SocieteDTO societeDTO;

    static SocieteVM societeVM;

    @Autowired
    SocieteRepository societeRepository;

    @Autowired
    ISocieteService societeService;

    @BeforeAll
    static void beforeAll(){
        societeVM = SocieteVMTestData.defaultVM();
        vm = ExerciceVMTestData.exerciceVMLinkedWithSociete(societeVM.getId());
    }

    @Test
    void save_shouldSaveSite() {
        societeDTO = societeService.save(societeVM);
        vm.setIdSociete(societeDTO.getId());
        dto = service.save(vm);
        assertThat(dto)
                .isNotNull()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_shouldReturnResult() {
        societeDTO = societeService.save(societeVM);
        vm.setIdSociete(societeDTO.getId());
        dto =service.save(vm);
        final Optional<ExerciceDTO> optional = service.findById(dto.getId());
        assertThat(optional)
                .isNotNull()
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void findById_withBadId_ShouldReturnNoResult() {
        final Optional<ExerciceDTO> optional = service.findById(UUID.randomUUID());
        assertThat(optional)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void delete_shouldDeleteSociete() {
        societeDTO = societeService.save(societeVM);
        vm.setIdSociete(societeDTO.getId());
        dto = service.save(vm);
        long oldCount = exerciceRepository.count();
        service.delete(dto.getId());
        long newCount = exerciceRepository.count();
        assertThat(oldCount).isEqualTo(newCount+1);
    }

    @Test
    void delete_withBadId_ShouldThrowException() {
        assertThrows(
                ItemNotFoundException.class,
                () ->service.delete(UUID.randomUUID())
        );
    }

}
