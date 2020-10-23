import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPartyContactMechPpos, PartyContactMechPpos } from 'app/shared/model/party-contact-mech-ppos.model';
import { PartyContactMechPposService } from './party-contact-mech-ppos.service';
import { PartyContactMechPposComponent } from './party-contact-mech-ppos.component';
import { PartyContactMechPposDetailComponent } from './party-contact-mech-ppos-detail.component';
import { PartyContactMechPposUpdateComponent } from './party-contact-mech-ppos-update.component';

@Injectable({ providedIn: 'root' })
export class PartyContactMechPposResolve implements Resolve<IPartyContactMechPpos> {
  constructor(private service: PartyContactMechPposService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPartyContactMechPpos> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((partyContactMechPpos: HttpResponse<PartyContactMechPpos>) => {
          if (partyContactMechPpos.body) {
            return of(partyContactMechPpos.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new PartyContactMechPpos());
  }
}

export const partyContactMechPposRoute: Routes = [
  {
    path: '',
    component: PartyContactMechPposComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContactMechPpos.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PartyContactMechPposDetailComponent,
    resolve: {
      partyContactMechPpos: PartyContactMechPposResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContactMechPpos.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PartyContactMechPposUpdateComponent,
    resolve: {
      partyContactMechPpos: PartyContactMechPposResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContactMechPpos.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PartyContactMechPposUpdateComponent,
    resolve: {
      partyContactMechPpos: PartyContactMechPposResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.partyContactMechPpos.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
