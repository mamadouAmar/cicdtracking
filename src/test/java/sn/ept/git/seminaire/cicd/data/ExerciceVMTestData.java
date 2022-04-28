package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.ExerciceVM;

import java.util.UUID;

public final class ExerciceVMTestData extends TestData {

    public  static ExerciceVM defaultVM(){
        return ExerciceVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .enabled(Default.enabled)
                .version(Default.version)
                .deleted(Default.deleted)
                .name(Default.name)
                .start(Default.start)
                .end(Default.end)
                .status(Default.status)
                .idSociete(Default.idSociete)
                .build();
    }

    public static ExerciceVM exerciceVMLinkedWithSociete(UUID societe){
        return ExerciceVM
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .enabled(Default.enabled)
                .version(Default.version)
                .deleted(Default.deleted)
                .name(Default.name)
                .start(Default.start)
                .end(Default.end)
                .status(Default.status)
                .idSociete(societe)
                .build();
    }

    public  static ExerciceVM updateVM(){
        return ExerciceVM
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .enabled(Update.enabled)
                .version(Update.version)
                .deleted(Update.deleted)
                .name(Update.name)
                .status(Update.status)
                .build();
    }
}
