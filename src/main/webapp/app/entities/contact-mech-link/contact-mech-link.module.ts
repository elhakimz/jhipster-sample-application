import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { ContactMechLinkComponent } from './contact-mech-link.component';
import { ContactMechLinkDetailComponent } from './contact-mech-link-detail.component';
import { ContactMechLinkUpdateComponent } from './contact-mech-link-update.component';
import { ContactMechLinkDeleteDialogComponent } from './contact-mech-link-delete-dialog.component';
import { contactMechLinkRoute } from './contact-mech-link.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(contactMechLinkRoute)],
  declarations: [
    ContactMechLinkComponent,
    ContactMechLinkDetailComponent,
    ContactMechLinkUpdateComponent,
    ContactMechLinkDeleteDialogComponent,
  ],
  entryComponents: [ContactMechLinkDeleteDialogComponent],
})
export class JhipsterSampleApplicationContactMechLinkModule {}
