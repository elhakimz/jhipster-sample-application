import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyContact, PartyContact } from 'app/shared/model/party-contact.model';
import { PartyContactService } from './party-contact.service';
import { PartyContactComponent } from './party-contact.component';
import { PartyContactDetailComponent } from './party-contact-detail.component';
import { PartyContactUpdateComponent } from './party-contact-update.component';

@Injectable({ providedIn: 'root' })
export class PartyContactResolve implements Resolve<IPartyContact> {
  constructor(private service: PartyContactService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyContact> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyContact: HttpResponse<PartyContact>) => {
          if (partyContact.body) {
            return of(partyContact.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyContact());
  }
}

export const partyContactRoute: Routes = [
  {
    path: '',
    component: PartyContactComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyContactDetailComponent,
    resolve: {
      partyContact: PartyContactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyContactUpdateComponent,
    resolve: {
      partyContact: PartyContactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyContactUpdateComponent,
    resolve: {
      partyContact: PartyContactResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContact.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
