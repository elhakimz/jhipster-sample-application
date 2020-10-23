import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyContactComponent } from './party-contact.component';
import { PartyContactDetailComponent } from './party-contact-detail.component';
import { PartyContactUpdateComponent } from './party-contact-update.component';
import { PartyContactDeleteDialogComponent } from './party-contact-delete-dialog.component';
import { partyContactRoute } from './party-contact.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyContactRoute)],
  declarations: [PartyContactComponent, PartyContactDetailComponent, PartyContactUpdateComponent, PartyContactDeleteDialogComponent],
  entryComponents: [PartyContactDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyContactModule {}
