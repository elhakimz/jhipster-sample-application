import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyFacility, PartyFacility } from 'app/shared/model/party-facility.model';
import { PartyFacilityService } from './party-facility.service';
import { PartyFacilityComponent } from './party-facility.component';
import { PartyFacilityDetailComponent } from './party-facility-detail.component';
import { PartyFacilityUpdateComponent } from './party-facility-update.component';

@Injectable({ providedIn: 'root' })
export class PartyFacilityResolve implements Resolve<IPartyFacility> {
  constructor(private service: PartyFacilityService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyFacility> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyFacility: HttpResponse<PartyFacility>) => {
          if (partyFacility.body) {
            return of(partyFacility.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyFacility());
  }
}

export const partyFacilityRoute: Routes = [
  {
    path: '',
    component: PartyFacilityComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyFacilityDetailComponent,
    resolve: {
      partyFacility: PartyFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyFacilityUpdateComponent,
    resolve: {
      partyFacility: PartyFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyFacilityUpdateComponent,
    resolve: {
      partyFacility: PartyFacilityResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyFacility.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
