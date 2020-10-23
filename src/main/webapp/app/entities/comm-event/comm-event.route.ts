import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommEvent, CommEvent } from 'app/shared/model/comm-event.model';
import { CommEventService } from './comm-event.service';
import { CommEventComponent } from './comm-event.component';
import { CommEventDetailComponent } from './comm-event-detail.component';
import { CommEventUpdateComponent } from './comm-event-update.component';

@Injectable({ providedIn: 'root' })
export class CommEventResolve implements Resolve<ICommEvent> {
  constructor(private service: CommEventService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommEvent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commEvent: HttpResponse<CommEvent>) => {
          if (commEvent.body) {
            return of(commEvent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommEvent());
  }
}

export const commEventRoute: Routes = [
  {
    path: '',
    component: CommEventComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CommEventDetailComponent,
    resolve: {
      commEvent: CommEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CommEventUpdateComponent,
    resolve: {
      commEvent: CommEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CommEventUpdateComponent,
    resolve: {
      commEvent: CommEventResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'jhipsterSampleApplicationApp.commEvent.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
