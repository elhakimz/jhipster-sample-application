import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CommEvtPposTypeComponent } from './comm-evt-ppos-type.component';
import { CommEvtPposTypeDetailComponent } from './comm-evt-ppos-type-detail.component';
import { CommEvtPposTypeUpdateComponent } from './comm-evt-ppos-type-update.component';
import { CommEvtPposTypeDeleteDialogComponent } from './comm-evt-ppos-type-delete-dialog.component';
import { commEvtPposTypeRoute } from './comm-evt-ppos-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(commEvtPposTypeRoute)],
  declarations: [
    CommEvtPposTypeComponent,
    CommEvtPposTypeDetailComponent,
    CommEvtPposTypeUpdateComponent,
    CommEvtPposTypeDeleteDialogComponent,
  ],
  entryComponents: [CommEvtPposTypeDeleteDialogComponent],
})
export class JhipsterSampleApplicationCommEvtPposTypeModule {}
