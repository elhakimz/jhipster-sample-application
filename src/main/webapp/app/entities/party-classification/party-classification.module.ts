import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyClassificationComponent } from './party-classification.component';
import { PartyClassificationDetailComponent } from './party-classification-detail.component';
import { PartyClassificationUpdateComponent } from './party-classification-update.component';
import { PartyClassificationDeleteDialogComponent } from './party-classification-delete-dialog.component';
import { partyClassificationRoute } from './party-classification.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyClassificationRoute)],
  declarations: [
    PartyClassificationComponent,
    PartyClassificationDetailComponent,
    PartyClassificationUpdateComponent,
    PartyClassificationDeleteDialogComponent,
  ],
  entryComponents: [PartyClassificationDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyClassificationModule {}
