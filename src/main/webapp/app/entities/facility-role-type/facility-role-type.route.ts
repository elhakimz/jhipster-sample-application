import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFacilityRoleType, FacilityRoleType } from 'app/shared/model/facility-role-type.model';
import { FacilityRoleTypeService } from './facility-role-type.service';
import { FacilityRoleTypeComponent } from './facility-role-type.component';
import { FacilityRoleTypeDetailComponent } from './facility-role-type-detail.component';
import { FacilityRoleTypeUpdateComponent } from './facility-role-type-update.component';

@Injectable({ providedIn: 'root' })
export class FacilityRoleTypeResolve implements Resolve<IFacilityRoleType> {
  constructor(private service: FacilityRoleTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFacilityRoleType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((facilityRoleType: HttpResponse<FacilityRoleType>) => {
          if (facilityRoleType.body) {
            return of(facilityRoleType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FacilityRoleType());
  }
}

export const facilityRoleTypeRoute: Routes = [
  {
    path: '',
    component: FacilityRoleTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: FacilityRoleTypeDetailComponent,
    resolve: {
      facilityRoleType: FacilityRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: FacilityRoleTypeUpdateComponent,
    resolve: {
      facilityRoleType: FacilityRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: FacilityRoleTypeUpdateComponent,
    resolve: {
      facilityRoleType: FacilityRoleTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.facilityRoleType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
