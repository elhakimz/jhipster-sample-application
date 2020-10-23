import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { FacilityRoleComponent } from './facility-role.component';
import { FacilityRoleDetailComponent } from './facility-role-detail.component';
import { FacilityRoleUpdateComponent } from './facility-role-update.component';
import { FacilityRoleDeleteDialogComponent } from './facility-role-delete-dialog.component';
import { facilityRoleRoute } from './facility-role.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(facilityRoleRoute)],
  declarations: [FacilityRoleComponent, FacilityRoleDetailComponent, FacilityRoleUpdateComponent, FacilityRoleDeleteDialogComponent],
  entryComponents: [FacilityRoleDeleteDialogComponent],
})
export class JhipsterSampleApplicationFacilityRoleModule {}
