import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyIdentificationComponent } from './party-identification.component';
import { PartyIdentificationDetailComponent } from './party-identification-detail.component';
import { PartyIdentificationUpdateComponent } from './party-identification-update.component';
import { PartyIdentificationDeleteDialogComponent } from './party-identification-delete-dialog.component';
import { partyIdentificationRoute } from './party-identification.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyIdentificationRoute)],
  declarations: [
    PartyIdentificationComponent,
    PartyIdentificationDetailComponent,
    PartyIdentificationUpdateComponent,
    PartyIdentificationDeleteDialogComponent,
  ],
  entryComponents: [PartyIdentificationDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyIdentificationModule {}
