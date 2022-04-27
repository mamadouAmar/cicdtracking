package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.ExerciceDTO;

import java.time.Instant;

public final class ExerciceDTOTestData extends TestData{

    public  static ExerciceDTO defaultDTO(){
        return ExerciceDTO
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
                .societe(Default.societe)
                .build();
    }

    public  static ExerciceDTO updateDTO(){
        return ExerciceDTO
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
