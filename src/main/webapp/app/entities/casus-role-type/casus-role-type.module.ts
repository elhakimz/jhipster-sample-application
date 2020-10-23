import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CasusRoleTypeComponent } from './casus-role-type.component';
import { CasusRoleTypeDetailComponent } from './casus-role-type-detail.component';
import { CasusRoleTypeUpdateComponent } from './casus-role-type-update.component';
import { CasusRoleTypeDeleteDialogComponent } from './casus-role-type-delete-dialog.component';
import { casusRoleTypeRoute } from './casus-role-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(casusRoleTypeRoute)],
  declarations: [CasusRoleTypeComponent, CasusRoleTypeDetailComponent, CasusRoleTypeUpdateComponent, CasusRoleTypeDeleteDialogComponent],
  entryComponents: [CasusRoleTypeDeleteDialogComponent],
})
export class JhipsterSampleApplicationCasusRoleTypeModule {}
