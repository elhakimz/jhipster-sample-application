import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICasusRoleType, CasusRoleType } from 'app/shared/model/casus-role-type.model';
import { CasusRoleTypeService } from './casus-role-type.service';
import { CasusRoleTypeComponent } from './casus-role-type.component';
import { CasusRoleTypeDetailComponent } from './casus-role-type-detail.component';
import { CasusRoleTypeUpdateComponent } from './casus-role-type-update.component';

@Injectable({ providedIn: 'root' })
export class CasusRoleTypeResolve implements Resolve<ICasusRoleType> {
  constructor(private service: CasusRoleTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICasusRoleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((casusRoleType: HttpResponse<CasusRoleType>) => {
          if (casusRoleType.body) {
            return of(casusRoleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CasusRoleType());
  }
}

export const casusRoleTypeRoute: Routes = [
  {
    path: '',
    component: CasusRoleTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CasusRoleTypeDetailComponent,
    resolve: {
      casusRoleType: CasusRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CasusRoleTypeUpdateComponent,
    resolve: {
      casusRoleType: CasusRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CasusRoleTypeUpdateComponent,
    resolve: {
      casusRoleType: CasusRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.casusRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
