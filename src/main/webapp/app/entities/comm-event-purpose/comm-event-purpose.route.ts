import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommEventPurpose, CommEventPurpose } from 'app/shared/model/comm-event-purpose.model';
import { CommEventPurposeService } from './comm-event-purpose.service';
import { CommEventPurposeComponent } from './comm-event-purpose.component';
import { CommEventPurposeDetailComponent } from './comm-event-purpose-detail.component';
import { CommEventPurposeUpdateComponent } from './comm-event-purpose-update.component';

@Injectable({ providedIn: 'root' })
export class CommEventPurposeResolve implements Resolve<ICommEventPurpose> {
  constructor(private service: CommEventPurposeService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommEventPurpose> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commEventPurpose: HttpResponse<CommEventPurpose>) => {
          if (commEventPurpose.body) {
            return of(commEventPurpose.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommEventPurpose());
  }
}

export const commEventPurposeRoute: Routes = [
  {
    path: '',
    component: CommEventPurposeComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEventPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommEventPurposeDetailComponent,
    resolve: {
      commEventPurpose: CommEventPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEventPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommEventPurposeUpdateComponent,
    resolve: {
      commEventPurpose: CommEventPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEventPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommEventPurposeUpdateComponent,
    resolve: {
      commEventPurpose: CommEventPurposeResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEventPurpose.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
