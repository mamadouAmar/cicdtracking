package sn.ept.git.seminaire.cicd.mapper;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.ExerciceVMTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.mappers.vm.ExerciceVMMapper;
import sn.ept.git.seminaire.cicd.mappers.vm.SocieteVMMapper;
import sn.ept.git.seminaire.cicd.models.Exercice;
import sn.ept.git.seminaire.cicd.models.Societe;

import static org.assertj.core.api.Assertions.assertThat;

class ExerciceVMMapperTest extends MapperBaseTest{

    static ExerciceVM vm;

    static Exercice entity;

    static Societe societe;

    static SocieteVM societeVM;

    @Autowired
    private SocieteVMMapper societeVMMapper;
    @Autowired
    private ExerciceVMMapper mapper;

    @BeforeAll
    static void  beforeAll() {
        societeVM = SocieteVMTestData.defaultVM();
        vm = ExerciceVMTestData.exerciceVMLinkedWithSociete(societeVM.getId());
    }

    @Test
    void toEntityShouldReturnCorrectEntity() {
        entity = mapper.asEntity(vm);
        assertThat(entity)
                .isNotNull()
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .ignoringFields("agents")
                .isEqualTo(vm);
    }

    @Test
    void toDTOShouldReturnCorrectDTO() {
        societe = societeVMMapper.asEntity(societeVM);
        entity = mapper.asEntity(vm);
        vm = mapper.asDTO(entity);
        System.out.println(vm);
        assertThat(vm)
                .isNotNull()
                .hasNoNullFieldsOrPropertiesExcept("idSociete")
                .usingRecursiveComparison()
                .ignoringActualNullFields()
                .isEqualTo(entity);
    }
}
