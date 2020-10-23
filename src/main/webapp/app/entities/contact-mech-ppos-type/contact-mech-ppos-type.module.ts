import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ContactMechPposTypeComponent } from './contact-mech-ppos-type.component';
import { ContactMechPposTypeDetailComponent } from './contact-mech-ppos-type-detail.component';
import { ContactMechPposTypeUpdateComponent } from './contact-mech-ppos-type-update.component';
import { ContactMechPposTypeDeleteDialogComponent } from './contact-mech-ppos-type-delete-dialog.component';
import { contactMechPposTypeRoute } from './contact-mech-ppos-type.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(contactMechPposTypeRoute)],
  declarations: [
    ContactMechPposTypeComponent,
    ContactMechPposTypeDetailComponent,
    ContactMechPposTypeUpdateComponent,
    ContactMechPposTypeDeleteDialogComponent,
  ],
  entryComponents: [ContactMechPposTypeDeleteDialogComponent],
})
export class JhipsterSampleApplicationContactMechPposTypeModule {}
