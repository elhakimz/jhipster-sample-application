import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommEvtPposType, CommEvtPposType } from 'app/shared/model/comm-evt-ppos-type.model';
import { CommEvtPposTypeService } from './comm-evt-ppos-type.service';
import { CommEvtPposTypeComponent } from './comm-evt-ppos-type.component';
import { CommEvtPposTypeDetailComponent } from './comm-evt-ppos-type-detail.component';
import { CommEvtPposTypeUpdateComponent } from './comm-evt-ppos-type-update.component';

@Injectable({ providedIn: 'root' })
export class CommEvtPposTypeResolve implements Resolve<ICommEvtPposType> {
  constructor(private service: CommEvtPposTypeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommEvtPposType> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commEvtPposType: HttpResponse<CommEvtPposType>) => {
          if (commEvtPposType.body) {
            return of(commEvtPposType.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommEvtPposType());
  }
}

export const commEvtPposTypeRoute: Routes = [
  {
    path: '',
    component: CommEvtPposTypeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvtPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommEvtPposTypeDetailComponent,
    resolve: {
      commEvtPposType: CommEvtPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvtPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommEvtPposTypeUpdateComponent,
    resolve: {
      commEvtPposType: CommEvtPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvtPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommEvtPposTypeUpdateComponent,
    resolve: {
      commEvtPposType: CommEvtPposTypeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvtPposType.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
