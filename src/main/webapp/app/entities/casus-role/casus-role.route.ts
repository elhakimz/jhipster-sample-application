import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICasusRole, CasusRole } from 'app/shared/model/casus-role.model';
import { CasusRoleService } from './casus-role.service';
import { CasusRoleComponent } from './casus-role.component';
import { CasusRoleDetailComponent } from './casus-role-detail.component';
import { CasusRoleUpdateComponent } from './casus-role-update.component';

@Injectable({ providedIn: 'root' })
export class CasusRoleResolve implements Resolve<ICasusRole> {
  constructor(private service: CasusRoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICasusRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((casusRole: HttpResponse<CasusRole>) => {
          if (casusRole.body) {
            return of(casusRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CasusRole());
  }
}

export const casusRoleRoute: Routes = [
  {
    path: '',
    component: CasusRoleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CasusRoleDetailComponent,
    resolve: {
      casusRole: CasusRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CasusRoleUpdateComponent,
    resolve: {
      casusRole: CasusRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CasusRoleUpdateComponent,
    resolve: {
      casusRole: CasusRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
