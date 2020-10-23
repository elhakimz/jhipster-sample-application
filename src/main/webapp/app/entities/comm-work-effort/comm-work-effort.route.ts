import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommWorkEffort, CommWorkEffort } from 'app/shared/model/comm-work-effort.model';
import { CommWorkEffortService } from './comm-work-effort.service';
import { CommWorkEffortComponent } from './comm-work-effort.component';
import { CommWorkEffortDetailComponent } from './comm-work-effort-detail.component';
import { CommWorkEffortUpdateComponent } from './comm-work-effort-update.component';

@Injectable({ providedIn: 'root' })
export class CommWorkEffortResolve implements Resolve<ICommWorkEffort> {
  constructor(private service: CommWorkEffortService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommWorkEffort> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commWorkEffort: HttpResponse<CommWorkEffort>) => {
          if (commWorkEffort.body) {
            return of(commWorkEffort.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommWorkEffort());
  }
}

export const commWorkEffortRoute: Routes = [
  {
    path: '',
    component: CommWorkEffortComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commWorkEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommWorkEffortDetailComponent,
    resolve: {
      commWorkEffort: CommWorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commWorkEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommWorkEffortUpdateComponent,
    resolve: {
      commWorkEffort: CommWorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commWorkEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommWorkEffortUpdateComponent,
    resolve: {
      commWorkEffort: CommWorkEffortResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commWorkEffort.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
