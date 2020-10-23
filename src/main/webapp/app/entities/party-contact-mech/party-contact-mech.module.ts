import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyContactMechComponent } from './party-contact-mech.component';
import { PartyContactMechDetailComponent } from './party-contact-mech-detail.component';
import { PartyContactMechUpdateComponent } from './party-contact-mech-update.component';
import { PartyContactMechDeleteDialogComponent } from './party-contact-mech-delete-dialog.component';
import { partyContactMechRoute } from './party-contact-mech.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyContactMechRoute)],
  declarations: [
    PartyContactMechComponent,
    PartyContactMechDetailComponent,
    PartyContactMechUpdateComponent,
    PartyContactMechDeleteDialogComponent,
  ],
  entryComponents: [PartyContactMechDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyContactMechModule {}
