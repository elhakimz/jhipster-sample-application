import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { RoleTypeComponent } from './role-type.component';
import { RoleTypeDetailComponent } from './role-type-detail.component';
import { RoleTypeUpdateComponent } from './role-type-update.component';
import { RoleTypeDeleteDialogComponent } from './role-type-delete-dialog.component';
import { roleTypeRoute } from './role-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(roleTypeRoute)],
  declarations: [RoleTypeComponent, RoleTypeDetailComponent, RoleTypeUpdateComponent, RoleTypeDeleteDialogComponent],
  entryComponents: [RoleTypeDeleteDialogComponent],
})
export class JhipsterSampleApplicationRoleTypeModule {}
