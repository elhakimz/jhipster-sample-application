import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { FacilityRoleTypeComponent } from './facility-role-type.component';
import { FacilityRoleTypeDetailComponent } from './facility-role-type-detail.component';
import { FacilityRoleTypeUpdateComponent } from './facility-role-type-update.component';
import { FacilityRoleTypeDeleteDialogComponent } from './facility-role-type-delete-dialog.component';
import { facilityRoleTypeRoute } from './facility-role-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(facilityRoleTypeRoute)],
  declarations: [
    FacilityRoleTypeComponent,
    FacilityRoleTypeDetailComponent,
    FacilityRoleTypeUpdateComponent,
    FacilityRoleTypeDeleteDialogComponent,
  ],
  entryComponents: [FacilityRoleTypeDeleteDialogComponent],
})
export class JhipsterSampleApplicationFacilityRoleTypeModule {}
