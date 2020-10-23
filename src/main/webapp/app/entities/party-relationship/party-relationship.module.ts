import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyRelationshipComponent } from './party-relationship.component';
import { PartyRelationshipDetailComponent } from './party-relationship-detail.component';
import { PartyRelationshipUpdateComponent } from './party-relationship-update.component';
import { PartyRelationshipDeleteDialogComponent } from './party-relationship-delete-dialog.component';
import { partyRelationshipRoute } from './party-relationship.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyRelationshipRoute)],
  declarations: [
    PartyRelationshipComponent,
    PartyRelationshipDetailComponent,
    PartyRelationshipUpdateComponent,
    PartyRelationshipDeleteDialogComponent,
  ],
  entryComponents: [PartyRelationshipDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyRelationshipModule {}
