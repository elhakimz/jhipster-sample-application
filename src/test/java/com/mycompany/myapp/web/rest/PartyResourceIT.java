package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.JhipsterSampleApplicationApp;
import com.mycompany.myapp.domain.Party;
import com.mycompany.myapp.repository.PartyRepository;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.domain.enumeration.Gender;
import com.mycompany.myapp.domain.enumeration.PartyType;
/**
 * Integration tests for the {@link PartyResource} REST controller.
 */
@SpringBootTest(classes = JhipsterSampleApplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PartyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LAST_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LAST_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_BIRTH_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_BIRTH_PLACE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTH_PLACE = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.MALE;
    private static final Gender UPDATED_GENDER = Gender.FEMALE;

    private static final PartyType DEFAULT_PARTY_TYPE = PartyType.PERSON;
    private static final PartyType UPDATED_PARTY_TYPE = PartyType.ORGANIZATION;

    @Autowired
    private PartyRepository partyRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPartyMockMvc;

    private Party party;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Party createEntity(EntityManager em) {
        Party party = new Party()
            .name(DEFAULT_NAME)
            .firstName(DEFAULT_FIRST_NAME)
            .lastName(DEFAULT_LAST_NAME)
            .birthDate(DEFAULT_BIRTH_DATE)
            .birthPlace(DEFAULT_BIRTH_PLACE)
            .gender(DEFAULT_GENDER)
            .partyType(DEFAULT_PARTY_TYPE);
        return party;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Party createUpdatedEntity(EntityManager em) {
        Party party = new Party()
            .name(UPDATED_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .birthPlace(UPDATED_BIRTH_PLACE)
            .gender(UPDATED_GENDER)
            .partyType(UPDATED_PARTY_TYPE);
        return party;
    }

    @BeforeEach
    public void initTest() {
        party = createEntity(em);
    }

    @Test
    @Transactional
    public void createParty() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();
        // Create the Party
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isCreated());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate + 1);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testParty.getFirstName()).isEqualTo(DEFAULT_FIRST_NAME);
        assertThat(testParty.getLastName()).isEqualTo(DEFAULT_LAST_NAME);
        assertThat(testParty.getBirthDate()).isEqualTo(DEFAULT_BIRTH_DATE);
        assertThat(testParty.getBirthPlace()).isEqualTo(DEFAULT_BIRTH_PLACE);
        assertThat(testParty.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testParty.getPartyType()).isEqualTo(DEFAULT_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void createPartyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = partyRepository.findAll().size();

        // Create the Party with an existing ID
        party.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPartyMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = partyRepository.findAll().size();
        // set the field null
        party.setName(null);

        // Create the Party, which fails.


        restPartyMockMvc.perform(post("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllParties() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get all the partyList
        restPartyMockMvc.perform(get("/api/parties?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(party.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].firstName").value(hasItem(DEFAULT_FIRST_NAME)))
            .andExpect(jsonPath("$.[*].lastName").value(hasItem(DEFAULT_LAST_NAME)))
            .andExpect(jsonPath("$.[*].birthDate").value(hasItem(DEFAULT_BIRTH_DATE.toString())))
            .andExpect(jsonPath("$.[*].birthPlace").value(hasItem(DEFAULT_BIRTH_PLACE)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].partyType").value(hasItem(DEFAULT_PARTY_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", party.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(party.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.firstName").value(DEFAULT_FIRST_NAME))
            .andExpect(jsonPath("$.lastName").value(DEFAULT_LAST_NAME))
            .andExpect(jsonPath("$.birthDate").value(DEFAULT_BIRTH_DATE.toString()))
            .andExpect(jsonPath("$.birthPlace").value(DEFAULT_BIRTH_PLACE))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.partyType").value(DEFAULT_PARTY_TYPE.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingParty() throws Exception {
        // Get the party
        restPartyMockMvc.perform(get("/api/parties/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // Update the party
        Party updatedParty = partyRepository.findById(party.getId()).get();
        // Disconnect from session so that the updates on updatedParty are not directly saved in db
        em.detach(updatedParty);
        updatedParty
            .name(UPDATED_NAME)
            .firstName(UPDATED_FIRST_NAME)
            .lastName(UPDATED_LAST_NAME)
            .birthDate(UPDATED_BIRTH_DATE)
            .birthPlace(UPDATED_BIRTH_PLACE)
            .gender(UPDATED_GENDER)
            .partyType(UPDATED_PARTY_TYPE);

        restPartyMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedParty)))
            .andExpect(status().isOk());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
        Party testParty = partyList.get(partyList.size() - 1);
        assertThat(testParty.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testParty.getFirstName()).isEqualTo(UPDATED_FIRST_NAME);
        assertThat(testParty.getLastName()).isEqualTo(UPDATED_LAST_NAME);
        assertThat(testParty.getBirthDate()).isEqualTo(UPDATED_BIRTH_DATE);
        assertThat(testParty.getBirthPlace()).isEqualTo(UPDATED_BIRTH_PLACE);
        assertThat(testParty.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testParty.getPartyType()).isEqualTo(UPDATED_PARTY_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingParty() throws Exception {
        int databaseSizeBeforeUpdate = partyRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPartyMockMvc.perform(put("/api/parties")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(party)))
            .andExpect(status().isBadRequest());

        // Validate the Party in the database
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteParty() throws Exception {
        // Initialize the database
        partyRepository.saveAndFlush(party);

        int databaseSizeBeforeDelete = partyRepository.findAll().size();

        // Delete the party
        restPartyMockMvc.perform(delete("/api/parties/{id}", party.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Party> partyList = partyRepository.findAll();
        assertThat(partyList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
