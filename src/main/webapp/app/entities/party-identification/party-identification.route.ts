import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyIdentification, PartyIdentification } from 'app/shared/model/party-identification.model';
import { PartyIdentificationService } from './party-identification.service';
import { PartyIdentificationComponent } from './party-identification.component';
import { PartyIdentificationDetailComponent } from './party-identification-detail.component';
import { PartyIdentificationUpdateComponent } from './party-identification-update.component';

@Injectable({ providedIn: 'root' })
export class PartyIdentificationResolve implements Resolve<IPartyIdentification> {
  constructor(private service: PartyIdentificationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyIdentification> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyIdentification: HttpResponse<PartyIdentification>) => {
          if (partyIdentification.body) {
            return of(partyIdentification.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyIdentification());
  }
}

export const partyIdentificationRoute: Routes = [
  {
    path: '',
    component: PartyIdentificationComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyIdentification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyIdentificationDetailComponent,
    resolve: {
      partyIdentification: PartyIdentificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyIdentification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyIdentificationUpdateComponent,
    resolve: {
      partyIdentification: PartyIdentificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyIdentification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyIdentificationUpdateComponent,
    resolve: {
      partyIdentification: PartyIdentificationResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyIdentification.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
