import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CommWorkEffortComponent } from './comm-work-effort.component';
import { CommWorkEffortDetailComponent } from './comm-work-effort-detail.component';
import { CommWorkEffortUpdateComponent } from './comm-work-effort-update.component';
import { CommWorkEffortDeleteDialogComponent } from './comm-work-effort-delete-dialog.component';
import { commWorkEffortRoute } from './comm-work-effort.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(commWorkEffortRoute)],
  declarations: [
    CommWorkEffortComponent,
    CommWorkEffortDetailComponent,
    CommWorkEffortUpdateComponent,
    CommWorkEffortDeleteDialogComponent,
  ],
  entryComponents: [CommWorkEffortDeleteDialogComponent],
})
export class JhipsterSampleApplicationCommWorkEffortModule {}
