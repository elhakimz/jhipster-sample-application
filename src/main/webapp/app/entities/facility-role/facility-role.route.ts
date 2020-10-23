import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityRole, FacilityRole } from 'app/shared/model/facility-role.model';
import { FacilityRoleService } from './facility-role.service';
import { FacilityRoleComponent } from './facility-role.component';
import { FacilityRoleDetailComponent } from './facility-role-detail.component';
import { FacilityRoleUpdateComponent } from './facility-role-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityRoleResolve implements Resolve<IFacilityRole> {
  constructor(private service: FacilityRoleService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityRole> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityRole: HttpResponse<FacilityRole>) => {
          if (facilityRole.body) {
            return of(facilityRole.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityRole());
  }
}

export const facilityRoleRoute: Routes = [
  {
    path: '',
    component: FacilityRoleComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityRoleDetailComponent,
    resolve: {
      facilityRole: FacilityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityRoleUpdateComponent,
    resolve: {
      facilityRole: FacilityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityRoleUpdateComponent,
    resolve: {
      facilityRole: FacilityRoleResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRole.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
