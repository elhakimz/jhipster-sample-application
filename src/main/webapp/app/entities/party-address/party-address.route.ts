import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyAddress, PartyAddress } from 'app/shared/model/party-address.model';
import { PartyAddressService } from './party-address.service';
import { PartyAddressComponent } from './party-address.component';
import { PartyAddressDetailComponent } from './party-address-detail.component';
import { PartyAddressUpdateComponent } from './party-address-update.component';

@Injectable({ providedIn: 'root' })
export class PartyAddressResolve implements Resolve<IPartyAddress> {
  constructor(private service: PartyAddressService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyAddress> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyAddress: HttpResponse<PartyAddress>) => {
          if (partyAddress.body) {
            return of(partyAddress.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyAddress());
  }
}

export const partyAddressRoute: Routes = [
  {
    path: '',
    component: PartyAddressComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyAddressDetailComponent,
    resolve: {
      partyAddress: PartyAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyAddressUpdateComponent,
    resolve: {
      partyAddress: PartyAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyAddressUpdateComponent,
    resolve: {
      partyAddress: PartyAddressResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyAddress.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
