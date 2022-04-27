package sn.ept.git.seminaire.cicd.data;

import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;

import java.util.UUID;

public final class SiteVMTestData extends TestData {

    public static SiteVM defaultVM() {
        return SiteVM
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
                .idSociete(Default.idSociete)
                .build();
    }

    public static SiteVM siteVMLinkedWithSociete(UUID societe){
        return SiteVM
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
                .idSociete(societe)
                .build();
    }

    public static SiteVM updateVM() {
        return SiteVM
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
