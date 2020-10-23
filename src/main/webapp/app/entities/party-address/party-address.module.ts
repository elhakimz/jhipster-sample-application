import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSampleApplicationSharedModule } from 'app/shared/shared.module';
import { PartyAddressComponent } from './party-address.component';
import { PartyAddressDetailComponent } from './party-address-detail.component';
import { PartyAddressUpdateComponent } from './party-address-update.component';
import { PartyAddressDeleteDialogComponent } from './party-address-delete-dialog.component';
import { partyAddressRoute } from './party-address.route';

@NgModule({
  imports: [JhipsterSampleApplicationSharedModule, RouterModule.forChild(partyAddressRoute)],
  declarations: [PartyAddressComponent, PartyAddressDetailComponent, PartyAddressUpdateComponent, PartyAddressDeleteDialogComponent],
  entryComponents: [PartyAddressDeleteDialogComponent],
})
export class JhipsterSampleApplicationPartyAddressModule {}
