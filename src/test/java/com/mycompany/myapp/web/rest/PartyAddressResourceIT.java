package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.PartyAddress;
import com.mycompany.myapp.repository.PartyAddressRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PartyAddressResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyAddressResourceIT {

    private static final String DEFAULT_ADDRESS_LINE_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_LINE_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_LINE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    @Autowired
    private PartyAddressRepository partyAddressRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyAddressMockMvc;

    private PartyAddress partyAddress;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyAddress createEntity(EntityManager em) {
        PartyAddress partyAddress = new PartyAddress()
            .addressLine1(DEFAULT_ADDRESS_LINE_1)
            .addressLine2(DEFAULT_ADDRESS_LINE_2)
            .city(DEFAULT_CITY);
        return partyAddress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PartyAddress createUpdatedEntity(EntityManager em) {
        PartyAddress partyAddress = new PartyAddress()
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY);
        return partyAddress;
    }

    @BeforeEach
    public void initTest() {
        partyAddress = createEntity(em);
    }

    @Test
    @Transactional
    public void createPartyAddress() throws Exception {
        int databaseSizeBeforeCreate = partyAddressRepository.findAll().size();
        // Create the PartyAddress
        restPartyAddressMockMvc.perform(post("/api/party-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyAddress)))
            .andExpect(status().isCreated());

        // Validate the PartyAddress in the database
        List<PartyAddress> partyAddressList = partyAddressRepository.findAll();
        assertThat(partyAddressList).hasSize(databaseSizeBeforeCreate + 1);
        PartyAddress testPartyAddress = partyAddressList.get(partyAddressList.size() - 1);
        assertThat(testPartyAddress.getAddressLine1()).isEqualTo(DEFAULT_ADDRESS_LINE_1);
        assertThat(testPartyAddress.getAddressLine2()).isEqualTo(DEFAULT_ADDRESS_LINE_2);
        assertThat(testPartyAddress.getCity()).isEqualTo(DEFAULT_CITY);
    }

    @Test
    @Transactional
    public void createPartyAddressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyAddressRepository.findAll().size();

        // Create the PartyAddress with an existing ID
        partyAddress.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyAddressMockMvc.perform(post("/api/party-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PartyAddress in the database
        List<PartyAddress> partyAddressList = partyAddressRepository.findAll();
        assertThat(partyAddressList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPartyAddresses() throws Exception {
        // Initialize the database
        partyAddressRepository.saveAndFlush(partyAddress);

        // Get all the partyAddressList
        restPartyAddressMockMvc.perform(get("/api/party-addresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(partyAddress.getId().intValue())))
            .andExpect(jsonPath("$.[*].addressLine1").value(hasItem(DEFAULT_ADDRESS_LINE_1)))
            .andExpect(jsonPath("$.[*].addressLine2").value(hasItem(DEFAULT_ADDRESS_LINE_2)))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY)));
    }
    
    @Test
    @Transactional
    public void getPartyAddress() throws Exception {
        // Initialize the database
        partyAddressRepository.saveAndFlush(partyAddress);

        // Get the partyAddress
        restPartyAddressMockMvc.perform(get("/api/party-addresses/{id}", partyAddress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(partyAddress.getId().intValue()))
            .andExpect(jsonPath("$.addressLine1").value(DEFAULT_ADDRESS_LINE_1))
            .andExpect(jsonPath("$.addressLine2").value(DEFAULT_ADDRESS_LINE_2))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY));
    }
    @Test
    @Transactional
    public void getNonExistingPartyAddress() throws Exception {
        // Get the partyAddress
        restPartyAddressMockMvc.perform(get("/api/party-addresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePartyAddress() throws Exception {
        // Initialize the database
        partyAddressRepository.saveAndFlush(partyAddress);

        int databaseSizeBeforeUpdate = partyAddressRepository.findAll().size();

        // Update the partyAddress
        PartyAddress updatedPartyAddress = partyAddressRepository.findById(partyAddress.getId()).get();
        // Disconnect from session so that the updates on updatedPartyAddress are not directly saved in db
        em.detach(updatedPartyAddress);
        updatedPartyAddress
            .addressLine1(UPDATED_ADDRESS_LINE_1)
            .addressLine2(UPDATED_ADDRESS_LINE_2)
            .city(UPDATED_CITY);

        restPartyAddressMockMvc.perform(put("/api/party-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPartyAddress)))
            .andExpect(status().isOk());

        // Validate the PartyAddress in the database
        List<PartyAddress> partyAddressList = partyAddressRepository.findAll();
        assertThat(partyAddressList).hasSize(databaseSizeBeforeUpdate);
        PartyAddress testPartyAddress = partyAddressList.get(partyAddressList.size() - 1);
        assertThat(testPartyAddress.getAddressLine1()).isEqualTo(UPDATED_ADDRESS_LINE_1);
        assertThat(testPartyAddress.getAddressLine2()).isEqualTo(UPDATED_ADDRESS_LINE_2);
        assertThat(testPartyAddress.getCity()).isEqualTo(UPDATED_CITY);
    }

    @Test
    @Transactional
    public void updateNonExistingPartyAddress() throws Exception {
        int databaseSizeBeforeUpdate = partyAddressRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyAddressMockMvc.perform(put("/api/party-addresses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(partyAddress)))
            .andExpect(status().isBadRequest());

        // Validate the PartyAddress in the database
        List<PartyAddress> partyAddressList = partyAddressRepository.findAll();
        assertThat(partyAddressList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePartyAddress() throws Exception {
        // Initialize the database
        partyAddressRepository.saveAndFlush(partyAddress);

        int databaseSizeBeforeDelete = partyAddressRepository.findAll().size();

        // Delete the partyAddress
        restPartyAddressMockMvc.perform(delete("/api/party-addresses/{id}", partyAddress.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PartyAddress> partyAddressList = partyAddressRepository.findAll();
        assertThat(partyAddressList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
