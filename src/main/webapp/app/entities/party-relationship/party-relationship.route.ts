import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyRelationship, PartyRelationship } from 'app/shared/model/party-relationship.model';
import { PartyRelationshipService } from './party-relationship.service';
import { PartyRelationshipComponent } from './party-relationship.component';
import { PartyRelationshipDetailComponent } from './party-relationship-detail.component';
import { PartyRelationshipUpdateComponent } from './party-relationship-update.component';

@Injectable({ providedIn: 'root' })
export class PartyRelationshipResolve implements Resolve<IPartyRelationship> {
  constructor(private service: PartyRelationshipService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyRelationship> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyRelationship: HttpResponse<PartyRelationship>) => {
          if (partyRelationship.body) {
            return of(partyRelationship.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyRelationship());
  }
}

export const partyRelationshipRoute: Routes = [
  {
    path: '',
    component: PartyRelationshipComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyRelationship.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyRelationshipDetailComponent,
    resolve: {
      partyRelationship: PartyRelationshipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyRelationship.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyRelationshipUpdateComponent,
    resolve: {
      partyRelationship: PartyRelationshipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyRelationship.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyRelationshipUpdateComponent,
    resolve: {
      partyRelationship: PartyRelationshipResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyRelationship.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
