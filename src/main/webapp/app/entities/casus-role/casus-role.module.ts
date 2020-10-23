import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { CasusRoleComponent } from './casus-role.component';
import { CasusRoleDetailComponent } from './casus-role-detail.component';
import { CasusRoleUpdateComponent } from './casus-role-update.component';
import { CasusRoleDeleteDialogComponent } from './casus-role-delete-dialog.component';
import { casusRoleRoute } from './casus-role.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(casusRoleRoute)],
  declarations: [CasusRoleComponent, CasusRoleDetailComponent, CasusRoleUpdateComponent, CasusRoleDeleteDialogComponent],
  entryComponents: [CasusRoleDeleteDialogComponent],
})
export class JhipsterSampleApplicationCasusRoleModule {}
