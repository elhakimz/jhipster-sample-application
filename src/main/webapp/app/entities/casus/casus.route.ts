import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICasus, Casus } from 'app/shared/model/casus.model';
import { CasusService } from './casus.service';
import { CasusComponent } from './casus.component';
import { CasusDetailComponent } from './casus-detail.component';
import { CasusUpdateComponent } from './casus-update.component';

@Injectable({ providedIn: 'root' })
export class CasusResolve implements Resolve<ICasus> {
  constructor(private service: CasusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICasus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((casus: HttpResponse<Casus>) => {
          if (casus.body) {
            return of(casus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Casus());
  }
}

export const casusRoute: Routes = [
  {
    path: '',
    component: CasusComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CasusDetailComponent,
    resolve: {
      casus: CasusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CasusUpdateComponent,
    resolve: {
      casus: CasusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CasusUpdateComponent,
    resolve: {
      casus: CasusResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casus.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
