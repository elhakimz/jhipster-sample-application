import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyFacilityComponent } from './party-facility.component';
import { PartyFacilityDetailComponent } from './party-facility-detail.component';
import { PartyFacilityUpdateComponent } from './party-facility-update.component';
import { PartyFacilityDeleteDialogComponent } from './party-facility-delete-dialog.component';
import { partyFacilityRoute } from './party-facility.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyFacilityRoute)],
  declarations: [PartyFacilityComponent, PartyFacilityDetailComponent, PartyFacilityUpdateComponent, PartyFacilityDeleteDialogComponent],
  entryComponents: [PartyFacilityDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyFacilityModule {}
