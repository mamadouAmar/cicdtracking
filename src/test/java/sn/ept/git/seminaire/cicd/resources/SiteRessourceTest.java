package sn.ept.git.seminaire.cicd.resources;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import sn.ept.git.seminaire.cicd.data.SiteVMTestData;
import sn.ept.git.seminaire.cicd.data.SocieteVMTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.dto.vm.SiteVM;
import sn.ept.git.seminaire.cicd.dto.vm.SocieteVM;
import sn.ept.git.seminaire.cicd.services.ISiteService;
import sn.ept.git.seminaire.cicd.services.ISocieteService;
import sn.ept.git.seminaire.cicd.utils.TestUtil;
import sn.ept.git.seminaire.cicd.utils.UrlMapping;


import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class SiteRessourceTest extends BasicResourceTest {

    static private SiteVM vm;

    static private SocieteVM societeVM;

    @Autowired
    private ISiteService service;

    @Autowired
    private ISocieteService societeService;

    private SiteDTO dto;

    private SocieteDTO societeDTO;



    @BeforeEach
    void  beforeEach() {
        service.deleteAll();
        societeService.deleteAll();
        societeVM = SocieteVMTestData.defaultVM();
        vm = SiteVMTestData.defaultVM();
    }

    @Test
    void findAll_shouldReturnSites() throws Exception {
        societeDTO = societeService.save(societeVM);
        vm.setIdSociete(societeDTO.getId());
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Site.ALL)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content.[0].id").exists())
                .andExpect(jsonPath("$.content.[0].version").exists())
                .andExpect(jsonPath("$.content.[0].enabled").exists())
                .andExpect(jsonPath("$.content.[0].deleted").exists())
                .andExpect(jsonPath("$.content.[0].enabled", is(true)))
                .andExpect(jsonPath("$.content.[0].deleted").value(false))
                .andExpect(jsonPath("$.content.[0].name", is(dto.getName())))
                .andExpect(jsonPath("$.content.[0].phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.content.[0].email").value(dto.getEmail()))
                .andExpect(jsonPath("$.content.[0].longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.content.[0].latitude").value(dto.getLatitude()))
                .andExpect(jsonPath("$.content.[0].societe").exists());
    }

    @Test
    void findById_shouldReturnSite() throws Exception {
        societeDTO = societeService.save(societeVM);
        vm.setIdSociete(societeDTO.getId());
        dto = service.save(vm);
        mockMvc.perform(get(UrlMapping.Site.FIND_BY_ID, dto.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.version").exists())
                .andExpect(jsonPath("$.enabled").exists())
                .andExpect(jsonPath("$.deleted").exists())
                .andExpect(jsonPath("$.name", is(dto.getName())))
                .andExpect(jsonPath("$.phone").value(dto.getPhone()))
                .andExpect(jsonPath("$.email").value(dto.getEmail()))
                .andExpect(jsonPath("$.longitude").value(dto.getLongitude()))
                .andExpect(jsonPath("$.latitude").value(dto.getLatitude()))
                .andExpect(jsonPath("$.societe").exists());
    }

    @Test
    void findById_withBadId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(UrlMapping.Site.FIND_BY_ID, UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

//    @Test
//    void add_shouldCreateSite() throws Exception {
//        societeDTO = societeService.save(societeVM);
//        vm.setIdSociete(societeDTO.getId());
//        mockMvc.perform(
//                        post(UrlMapping.Site.ADD)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(TestUtil.convertObjectToJsonBytes(vm))
//                )
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists())
//                .andExpect(jsonPath("$.version").exists())
//                .andExpect(jsonPath("$.enabled").exists())
//                .andExpect(jsonPath("$.deleted").exists())
//                .andExpect(jsonPath("$.name").value(vm.getName()))
//                .andExpect(jsonPath("$.phone").value(vm.getPhone()))
//                .andExpect(jsonPath("$.email").value(vm.getEmail()))
//                .andExpect(jsonPath("$.longitude").value(vm.getLongitude()))
//                .andExpect(jsonPath("$.latitude").value(vm.getLatitude()))
//                .andExpect(jsonPath("$.societe").exists());
//    }
}
