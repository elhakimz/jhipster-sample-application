import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'party',
        loadChildren: () => import('./party/party.module').then(m => m.JhipsterSampleApplicationPartyModule),
      },
      {
        path: 'party-role',
        loadChildren: () => import('./party-role/party-role.module').then(m => m.JhipsterSampleApplicationPartyRoleModule),
      },
      {
        path: 'role-type',
        loadChildren: () => import('./role-type/role-type.module').then(m => m.JhipsterSampleApplicationRoleTypeModule),
      },
      {
        path: 'party-address',
        loadChildren: () => import('./party-address/party-address.module').then(m => m.JhipsterSampleApplicationPartyAddressModule),
      },
      {
        path: 'party-contact',
        loadChildren: () => import('./party-contact/party-contact.module').then(m => m.JhipsterSampleApplicationPartyContactModule),
      },
      {
        path: 'party-identification',
        loadChildren: () =>
          import('./party-identification/party-identification.module').then(m => m.JhipsterSampleApplicationPartyIdentificationModule),
      },
      {
        path: 'party-relationship',
        loadChildren: () =>
          import('./party-relationship/party-relationship.module').then(m => m.JhipsterSampleApplicationPartyRelationshipModule),
      },
      {
        path: 'agreement',
        loadChildren: () => import('./agreement/agreement.module').then(m => m.JhipsterSampleApplicationAgreementModule),
      },
      {
        path: 'party-classification',
        loadChildren: () =>
          import('./party-classification/party-classification.module').then(m => m.JhipsterSampleApplicationPartyClassificationModule),
      },
      {
        path: 'status-type',
        loadChildren: () => import('./status-type/status-type.module').then(m => m.JhipsterSampleApplicationStatusTypeModule),
      },
      {
        path: 'comm-event',
        loadChildren: () => import('./comm-event/comm-event.module').then(m => m.JhipsterSampleApplicationCommEventModule),
      },
      {
        path: 'comm-event-purpose',
        loadChildren: () =>
          import('./comm-event-purpose/comm-event-purpose.module').then(m => m.JhipsterSampleApplicationCommEventPurposeModule),
      },
      {
        path: 'comm-evt-ppos-type',
        loadChildren: () =>
          import('./comm-evt-ppos-type/comm-evt-ppos-type.module').then(m => m.JhipsterSampleApplicationCommEvtPposTypeModule),
      },
      {
        path: 'contact-mech',
        loadChildren: () => import('./contact-mech/contact-mech.module').then(m => m.JhipsterSampleApplicationContactMechModule),
      },
      {
        path: 'contact-mech-type',
        loadChildren: () =>
          import('./contact-mech-type/contact-mech-type.module').then(m => m.JhipsterSampleApplicationContactMechTypeModule),
      },
      {
        path: 'party-contact-mech',
        loadChildren: () =>
          import('./party-contact-mech/party-contact-mech.module').then(m => m.JhipsterSampleApplicationPartyContactMechModule),
      },
      {
        path: 'party-contact-mech-ppos',
        loadChildren: () =>
          import('./party-contact-mech-ppos/party-contact-mech-ppos.module').then(
            m => m.JhipsterSampleApplicationPartyContactMechPposModule
          ),
      },
      {
        path: 'contact-mech-ppos-type',
        loadChildren: () =>
          import('./contact-mech-ppos-type/contact-mech-ppos-type.module').then(m => m.JhipsterSampleApplicationContactMechPposTypeModule),
      },
      {
        path: 'contact-mech-link',
        loadChildren: () =>
          import('./contact-mech-link/contact-mech-link.module').then(m => m.JhipsterSampleApplicationContactMechLinkModule),
      },
      {
        path: 'facility-role',
        loadChildren: () => import('./facility-role/facility-role.module').then(m => m.JhipsterSampleApplicationFacilityRoleModule),
      },
      {
        path: 'facility-role-type',
        loadChildren: () =>
          import('./facility-role-type/facility-role-type.module').then(m => m.JhipsterSampleApplicationFacilityRoleTypeModule),
      },
      {
        path: 'facility',
        loadChildren: () => import('./facility/facility.module').then(m => m.JhipsterSampleApplicationFacilityModule),
      },
      {
        path: 'party-facility',
        loadChildren: () => import('./party-facility/party-facility.module').then(m => m.JhipsterSampleApplicationPartyFacilityModule),
      },
      {
        path: 'facility-contact-mech',
        loadChildren: () =>
          import('./facility-contact-mech/facility-contact-mech.module').then(m => m.JhipsterSampleApplicationFacilityContactMechModule),
      },
      {
        path: 'casus',
        loadChildren: () => import('./casus/casus.module').then(m => m.JhipsterSampleApplicationCasusModule),
      },
      {
        path: 'casus-role',
        loadChildren: () => import('./casus-role/casus-role.module').then(m => m.JhipsterSampleApplicationCasusRoleModule),
      },
      {
        path: 'casus-role-type',
        loadChildren: () => import('./casus-role-type/casus-role-type.module').then(m => m.JhipsterSampleApplicationCasusRoleTypeModule),
      },
      {
        path: 'work-effort',
        loadChildren: () => import('./work-effort/work-effort.module').then(m => m.JhipsterSampleApplicationWorkEffortModule),
      },
      {
        path: 'comm-work-effort',
        loadChildren: () => import('./comm-work-effort/comm-work-effort.module').then(m => m.JhipsterSampleApplicationCommWorkEffortModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JhipsterSampleApplicationEntityModule {}
