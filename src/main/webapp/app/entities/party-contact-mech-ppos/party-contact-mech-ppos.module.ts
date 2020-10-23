import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyContactMechPposComponent } from './party-contact-mech-ppos.component';
import { PartyContactMechPposDetailComponent } from './party-contact-mech-ppos-detail.component';
import { PartyContactMechPposUpdateComponent } from './party-contact-mech-ppos-update.component';
import { PartyContactMechPposDeleteDialogComponent } from './party-contact-mech-ppos-delete-dialog.component';
import { partyContactMechPposRoute } from './party-contact-mech-ppos.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyContactMechPposRoute)],
  declarations: [
    PartyContactMechPposComponent,
    PartyContactMechPposDetailComponent,
    PartyContactMechPposUpdateComponent,
    PartyContactMechPposDeleteDialogComponent,
  ],
  entryComponents: [PartyContactMechPposDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyContactMechPposModule {}
