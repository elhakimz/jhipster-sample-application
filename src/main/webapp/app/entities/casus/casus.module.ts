import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CasusComponent } from './casus.component';
import { CasusDetailComponent } from './casus-detail.component';
import { CasusUpdateComponent } from './casus-update.component';
import { CasusDeleteDialogComponent } from './casus-delete-dialog.component';
import { casusRoute } from './casus.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(casusRoute)],
  declarations: [CasusComponent, CasusDetailComponent, CasusUpdateComponent, CasusDeleteDialogComponent],
  entryComponents: [CasusDeleteDialogComponent],
})
export class JhipsterSampleApplicationCasusModule {}
