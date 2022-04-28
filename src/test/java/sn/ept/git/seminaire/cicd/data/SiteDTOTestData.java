package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.SiteDTO;

public final class SiteDTOTestData extends TestData{

    public static SiteDTO defaultDTO() {
        return SiteDTO
                .builder()
                .id(Default.id)
                .createdDate(Default.createdDate)
                .lastModifiedDate(Default.lastModifiedDate)
                .enabled(Default.enabled)
                .version(Default.version)
                .deleted(Default.deleted)
                .name(Default.name)
                .phone(Default.phone)
                .email(Default.email)
                .longitude(Default.longitude)
                .latitude(Default.latitude)
                .societe(Default.societe)
                .build();
    }

    public static SiteDTO updateDTO() {
        return SiteDTO
                .builder()
                .id(Default.id)
                .createdDate(Update.createdDate)
                .lastModifiedDate(Update.lastModifiedDate)
                .enabled(Update.enabled)
                .version(Update.version)
                .deleted(Update.deleted)
                .name(Update.name)
                .phone(Update.phone)
                .email(Update.email)
                .longitude(Update.longitude)
                .latitude(Update.latitude)
                .build();
    }
}
